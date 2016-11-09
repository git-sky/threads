package cn.com.sky.threads.concurrent.concurrenthashmap;

import java.io.IOException;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * jdk1.6源码解析
 */
public class ConcurrentHashMap<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V>, Serializable {
	private static final long serialVersionUID = 7249069246763182397L;

	/**
	 * table的默认初始大小
	 */
	static final int DEFAULT_INITIAL_CAPACITY = 16;

	/**
	 * 默认负载因子
	 */
	static final float DEFAULT_LOAD_FACTOR = 0.75f;

	/**
	 * 默认并发度
	 */
	static final int DEFAULT_CONCURRENCY_LEVEL = 16;

	/**
	 * 最大容量
	 */
	static final int MAXIMUM_CAPACITY = 1 << 30;

	/**
	 * 最大segments数量
	 */
	static final int MAX_SEGMENTS = 1 << 16; // slightly conservative

	/**
	 * size和containsValue方法在加锁前的重试次数
	 */
	static final int RETRIES_BEFORE_LOCK = 2;

	/**
	 * segment掩码
	 * 
	 * 选择segment用
	 */
	final int segmentMask;

	/**
	 * 偏移位数（获取segment用）
	 * 
	 * key的hash值右移位数
	 */
	final int segmentShift;

	/**
	 * segments数组，每个都是一个hashtable
	 */
	final Segment<K, V>[] segments;

	transient Set<K> keySet;
	transient Set<Map.Entry<K, V>> entrySet;
	transient Collection<V> values;

	/* ---------------- Small Utilities -------------- */

	/**
	 * 获取hash值
	 */
	private static int hash(int h) {
		h += (h << 15) ^ 0xffffcd7d;
		h ^= (h >>> 10);
		h += (h << 3);
		h ^= (h >>> 6);
		h += (h << 2) + (h << 14);
		return h ^ (h >>> 16);
	}

	/**
	 * 根据hash值获取对应的某个segment。
	 */
	final Segment<K, V> segmentFor(int hash) {
		System.out.println("hash:"+Integer.toBinaryString(hash));
		System.out.println("segmentShift:"+segmentShift);
		System.out.println("hash >>> segmentShift:"+Integer.toBinaryString(hash >>> segmentShift));
		System.out.println("segmentMask:"+Integer.toBinaryString(segmentMask));
		System.out.println("(hash >>> segmentShift) & segmentMask):"+Integer.toBinaryString((hash >>> segmentShift) & segmentMask));
		System.out.println("segments[" + ((hash >>> segmentShift) & segmentMask) + "]");
		return segments[(hash >>> segmentShift) & segmentMask];
	}

	/**
	 * 链表的结点
	 */
	static final class HashEntry<K, V> {
		final K key;
		final int hash;
		volatile V value;
		final HashEntry<K, V> next;

		HashEntry(K key, int hash, HashEntry<K, V> next, V value) {
			this.key = key;
			this.hash = hash;
			this.next = next;
			this.value = value;
		}

		@SuppressWarnings("unchecked")
		static final <K, V> HashEntry<K, V>[] newArray(int i) {
			return new HashEntry[i];
		}
	}

	/**
	 * segment段
	 */
	static final class Segment<K, V> extends ReentrantLock implements Serializable {
		/*
		 * Segments maintain a table of entry lists that are ALWAYS kept in a consistent state, so
		 * can be read without locking. Next fields of nodes are immutable (final). All list
		 * additions are performed at the front of each bin. This makes it easy to check changes,
		 * and also fast to traverse. When nodes would otherwise be changed, new nodes are created
		 * to replace them. This works well for hash tables since the bin lists tend to be short.
		 * (The average length is less than two for the default load factor threshold.)
		 * 
		 * Read operations can thus proceed without locking, but rely on selected uses of volatiles
		 * to ensure that completed write operations performed by other threads are noticed. For
		 * most purposes, the "count" field, tracking the number of elements, serves as that
		 * volatile variable ensuring visibility. This is convenient because this field needs to be
		 * read in many read operations anyway:
		 * 
		 * - All (unsynchronized) read operations must first read the "count" field, and should not
		 * look at table entries if it is 0.
		 * 
		 * - All (synchronized) write operations should write to the "count" field after
		 * structurally changing any bin. The operations must not take any action that could even
		 * momentarily cause a concurrent read operation to see inconsistent data. This is made
		 * easier by the nature of the read operations in Map. For example, no operation can reveal
		 * that the table has grown but the threshold has not yet been updated, so there are no
		 * atomicity requirements for this with respect to reads.
		 * 
		 * As a guide, all critical volatile reads and writes to the count field are marked in code
		 * comments.
		 */

		private static final long serialVersionUID = 2249069246763182397L;

		/**
		 * 当前segment中元素个数
		 */
		transient volatile int count;

		/**
		 * 计算size和检查containsValue时，如果modCount改变，需要重试。
		 * 
		 * 更改table大小的次数
		 */
		transient int modCount;

		/**
		 * rehash的阈值
		 */
		transient int threshold;

		/**
		 * 每个segment的table
		 */
		transient volatile HashEntry<K, V>[] table;

		/**
		 * hashtable的负载因子
		 */
		final float loadFactor;

		Segment(int initialCapacity, float lf) {
			loadFactor = lf;
			setTable(HashEntry.<K, V> newArray(initialCapacity));
		}

		@SuppressWarnings("unchecked")
		static final <K, V> Segment<K, V>[] newArray(int i) {
			return new Segment[i];
		}

		void setTable(HashEntry<K, V>[] newTable) {
			threshold = (int) (newTable.length * loadFactor);
			table = newTable;
		}

		/**
		 * 获取当前hash所在hash桶的第一个元素
		 */
		HashEntry<K, V> getFirst(int hash) {
			HashEntry<K, V>[] tab = table;
			System.out.println("table[" + (hash & (tab.length - 1)) + "]");
			return tab[hash & (tab.length - 1)];
		}

		/**
		 */
		V readValueUnderLock(HashEntry<K, V> e) {
			lock();
			try {
				return e.value;
			} finally {
				unlock();
			}
		}

		/**
		 * 遍历链表查找key
		 */
		V get(Object key, int hash) {
			int i = 0;
			if (count != 0) { // read-volatile
				HashEntry<K, V> e = getFirst(hash);
				while (e != null) {// 遍历链表查找key
					if (e.hash == hash && key.equals(e.key)) {
						V v = e.value;
						if (v != null) {
							System.out.println("i=" + i);
							return v;
						}
						return readValueUnderLock(e); // recheck
					}
					e = e.next;
					i++;
				}
			}
			return null;
		}

		/**
		 * 循环遍历单链表查找元素是否存在
		 */
		boolean containsKey(Object key, int hash) {
			if (count != 0) { // read-volatile
				HashEntry<K, V> e = getFirst(hash);
				while (e != null) {// 循环遍历单链表查找元素是否存在。
					if (e.hash == hash && key.equals(e.key))
						return true;
					e = e.next;
				}
			}
			return false;
		}

		/**
		 * 遍历hashtable数组，遍历单链表，查找是否存在该value值。
		 */
		boolean containsValue(Object value) {
			if (count != 0) { // read-volatile
				HashEntry<K, V>[] tab = table;
				int len = tab.length;
				for (int i = 0; i < len; i++) {
					for (HashEntry<K, V> e = tab[i]; e != null; e = e.next) {
						V v = e.value;
						if (v == null) // recheck
							v = readValueUnderLock(e);
						if (value.equals(v))
							return true;
					}
				}
			}
			return false;
		}

		boolean replace(K key, int hash, V oldValue, V newValue) {
			lock();
			try {
				HashEntry<K, V> e = getFirst(hash);
				while (e != null && (e.hash != hash || !key.equals(e.key)))
					e = e.next;

				boolean replaced = false;
				if (e != null && oldValue.equals(e.value)) {
					replaced = true;
					e.value = newValue;
				}
				return replaced;
			} finally {
				unlock();
			}
		}

		V replace(K key, int hash, V newValue) {
			lock();
			try {
				HashEntry<K, V> e = getFirst(hash);
				while (e != null && (e.hash != hash || !key.equals(e.key)))
					// 遍历单链表查找元素
					e = e.next;

				V oldValue = null;
				if (e != null) {
					oldValue = e.value;
					e.value = newValue;
				}
				return oldValue;
			} finally {
				unlock();
			}
		}

		/**
		 * 某个segment中存入数据
		 */
		V put(K key, int hash, V value, boolean onlyIfAbsent) {
			lock();
			try {
				int c = count;
				if (c++ > threshold) // ensure capacity
					rehash();// 元素个数大于阈值，rehash扩大hashtable
				HashEntry<K, V>[] tab = table;
				int index = hash & (tab.length - 1);// 找到元素应该存放的hash桶
				HashEntry<K, V> first = tab[index];
				System.out.println("table[" + index + "]");
				HashEntry<K, V> e = first;
				while (e != null && (e.hash != hash || !key.equals(e.key)))
					// 遍历单链表中的每一个元素
					e = e.next;

				V oldValue;
				if (e != null) {// 如果key存在，则覆盖
					oldValue = e.value;
					if (!onlyIfAbsent)
						e.value = value;
				} else { // 如果key不存在，头插入法，加入链表中
					oldValue = null;
					++modCount;
					tab[index] = new HashEntry<K, V>(key, hash, first, value);
					count = c; // write-volatile
				}
				return oldValue;
			} finally {
				unlock();
			}
		}

		void rehash() {
			HashEntry<K, V>[] oldTable = table;
			int oldCapacity = oldTable.length;
			if (oldCapacity >= MAXIMUM_CAPACITY)
				return;

			/*
			 * Reclassify nodes in each list to new Map. Because we are using power-of-two
			 * expansion, the elements from each bin must either stay at same index, or move with a
			 * power of two offset. We eliminate unnecessary node creation by catching cases where
			 * old nodes can be reused because their next fields won't change. Statistically, at the
			 * default threshold, only about one-sixth of them need cloning when a table doubles.
			 * The nodes they replace will be garbage collectable as soon as they are no longer
			 * referenced by any reader thread that may be in the midst of traversing table right
			 * now.
			 */

			HashEntry<K, V>[] newTable = HashEntry.newArray(oldCapacity << 1);
			threshold = (int) (newTable.length * loadFactor);
			int sizeMask = newTable.length - 1;
			for (int i = 0; i < oldCapacity; i++) {
				// We need to guarantee that any existing reads of old Map can
				// proceed. So we cannot yet null out each bin.
				HashEntry<K, V> e = oldTable[i];

				if (e != null) {
					HashEntry<K, V> next = e.next;
					int idx = e.hash & sizeMask;

					// Single node on list
					if (next == null)
						newTable[idx] = e;// rehash之前的hash不冲突元素，rehash之后也不会冲突，所以直接赋值即可。

					else {
						// Reuse trailing consecutive sequence at same slot
						HashEntry<K, V> lastRun = e;
						int lastIdx = idx;
						for (HashEntry<K, V> last = next; last != null; last = last.next) {
							int k = last.hash & sizeMask;
							if (k != lastIdx) {
								lastIdx = k;
								lastRun = last;
								// System.out.println("rehash lastRun:" + lastRun.key);
							}
						}
						System.out.println("rehash: " + lastRun.key + " new hash: " + lastIdx);
						newTable[lastIdx] = lastRun;// hash值一致的，直接放到指定hash桶。rehash之前的hash不冲突元素，rehash之后也不会冲突，所以直接赋值即可。

						// Clone all remaining nodes
						for (HashEntry<K, V> p = e; p != lastRun; p = p.next) {// 从前向后遍历单链表中的元素，hash后存放到指定位置。
							int k = p.hash & sizeMask;
							System.out.println(p.key + " new hash: " + k);
							HashEntry<K, V> n = newTable[k];
							newTable[k] = new HashEntry<K, V>(p.key, p.hash, n, p.value);
						}
					}
				}
			}
			table = newTable;
		}

		/**
		 * 删除指定key-value对
		 */
		V remove(Object key, int hash, Object value) {
			lock();
			try {
				int c = count - 1;
				HashEntry<K, V>[] tab = table;
				int index = hash & (tab.length - 1);
				HashEntry<K, V> first = tab[index];
				System.out.println("table[" + index + "]");
				HashEntry<K, V> e = first;
				while (e != null && (e.hash != hash || !key.equals(e.key)))
					// 遍历单链表查找元素
					e = e.next;

				V oldValue = null;
				if (e != null) {
					V v = e.value;
					if (value == null || value.equals(v)) {
						oldValue = v;
						// All entries following removed node can stay
						// in list, but all preceding ones need to be
						// cloned.
						++modCount;
						HashEntry<K, V> newFirst = e.next;
						for (HashEntry<K, V> p = first; p != e; p = p.next)
							// 1->2->3->4->5->6 ==>>remove4之后==>> 3->2->1->5->6
							// 复制被删除节点的所有前驱节点，使用头插法插入链表
							newFirst = new HashEntry<K, V>(p.key, p.hash, newFirst, p.value);
						tab[index] = newFirst;
						count = c; // write-volatile
					}
				}
				return oldValue;
			} finally {
				unlock();
			}
		}

		/**
		 * 清空当前segment的table数组
		 */
		void clear() {
			if (count != 0) {
				lock();
				try {
					HashEntry<K, V>[] tab = table;
					for (int i = 0; i < tab.length; i++)
						tab[i] = null;
					++modCount;
					count = 0; // write-volatile
				} finally {
					unlock();
				}
			}
		}
	}

	public ConcurrentHashMap(int initialCapacity, float loadFactor, int concurrencyLevel) {
		if (!(loadFactor > 0) || initialCapacity < 0 || concurrencyLevel <= 0)
			throw new IllegalArgumentException();

		if (concurrencyLevel > MAX_SEGMENTS)
			concurrencyLevel = MAX_SEGMENTS;// 分段数

		int sshift = 0;// Segment占用的位数
		int ssize = 1;// Segment分段数
		while (ssize < concurrencyLevel) {
			++sshift;
			ssize <<= 1;// 确保分段数是2^n
		}
		segmentShift = 32 - sshift;//
		segmentMask = ssize - 1;// 分段掩码
		this.segments = Segment.newArray(ssize);// Segment[] segments=new Segment[ssize];

		if (initialCapacity > MAXIMUM_CAPACITY)
			initialCapacity = MAXIMUM_CAPACITY;
		int c = initialCapacity / ssize;// c是每段的大小
		if (c * ssize < initialCapacity)
			++c;
		int cap = 1;// cap是分段大小，保证是2^n
		while (cap < c)
			cap <<= 1;

		for (int i = 0; i < this.segments.length; ++i)
			this.segments[i] = new Segment<K, V>(cap, loadFactor);// 初始化每一个segment。 HashEntry[]
																	// tabble= new HashEntry[cap];

	}

	public void print() {
		for (int i = 0; i < this.segments.length; i++) {
			// System.out.println(segments[i]);
			System.out.println("--------------------------------------------------------");
			System.out.println("segments[" + i + "]:");
			System.out.println("		table");

			HashEntry[] table = segments[i].table;
			for (int j = 0; j < table.length; j++) {
				HashEntry e = table[j];
				System.out.print("			[" + j + "]-->");

				while (e != null) {// 遍历链表查找key
					System.out.print("|" + e.key + "|" + e.value + "|");
					System.out.print("-->");

					e = e.next;
				}
				System.out.println("^");
			}
		}
	}

	public ConcurrentHashMap(int initialCapacity, float loadFactor) {
		this(initialCapacity, loadFactor, DEFAULT_CONCURRENCY_LEVEL);
	}

	public ConcurrentHashMap(int initialCapacity) {
		this(initialCapacity, DEFAULT_LOAD_FACTOR, DEFAULT_CONCURRENCY_LEVEL);
	}

	public ConcurrentHashMap() {
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR, DEFAULT_CONCURRENCY_LEVEL);
	}

	public ConcurrentHashMap(Map<? extends K, ? extends V> m) {
		this(Math.max((int) (m.size() / DEFAULT_LOAD_FACTOR) + 1, DEFAULT_INITIAL_CAPACITY), DEFAULT_LOAD_FACTOR, DEFAULT_CONCURRENCY_LEVEL);
		putAll(m);
	}

	/**
	 * 判断是否为空
	 */
	public boolean isEmpty() {
		final Segment<K, V>[] segments = this.segments;
		/*
		 * We keep track of per-segment modCounts to avoid ABA problems in which an element in one
		 * segment was added and in another removed during traversal, in which case the table was
		 * never actually empty at any point. Note the similar use of modCounts in the size() and
		 * containsValue() methods, which are the only other methods also susceptible to ABA
		 * problems.
		 */
		int[] mc = new int[segments.length];
		int mcsum = 0;
		for (int i = 0; i < segments.length; ++i) {
			if (segments[i].count != 0)
				return false;
			else
				mcsum += mc[i] = segments[i].modCount;
		}
		// If mcsum happens to be zero, then we know we got a snapshot
		// before any modifications at all were made. This is
		// probably common enough to bother tracking.
		if (mcsum != 0) {
			for (int i = 0; i < segments.length; ++i) {
				if (segments[i].count != 0 || mc[i] != segments[i].modCount)
					return false;
			}
		}
		return true;
	}

	/**
	 * map中元素个数
	 * 
	 * 1.无锁计算两次，如果有改动则2
	 * 
	 * 2.加锁计算
	 */
	public int size() {
		final Segment<K, V>[] segments = this.segments;
		long sum = 0;
		long check = 0;
		int[] mc = new int[segments.length];
		// Try a few times to get accurate count. On failure due to
		// continuous async changes in table, resort to locking.
		for (int k = 0; k < RETRIES_BEFORE_LOCK; ++k) {
			check = 0;
			sum = 0;
			int mcsum = 0;
			for (int i = 0; i < segments.length; ++i) {
				sum += segments[i].count;
				mcsum += mc[i] = segments[i].modCount;
			}
			if (mcsum != 0) {
				for (int i = 0; i < segments.length; ++i) {
					check += segments[i].count;
					if (mc[i] != segments[i].modCount) {
						check = -1; // force retry
						break;
					}
				}
			}
			if (check == sum)
				break;
		}
		if (check != sum) { // Resort to locking all segments
			sum = 0;
			for (int i = 0; i < segments.length; ++i)
				segments[i].lock();
			for (int i = 0; i < segments.length; ++i)
				sum += segments[i].count;
			for (int i = 0; i < segments.length; ++i)
				segments[i].unlock();
		}
		if (sum > Integer.MAX_VALUE)
			return Integer.MAX_VALUE;
		else
			return (int) sum;
	}

	/**
	 * 获取指定key
	 */
	public V get(Object key) {
		int hash = hash(key.hashCode());
		return segmentFor(hash).get(key, hash);
	}

	/**
	 * 是否包含指定key
	 */
	public boolean containsKey(Object key) {
		int hash = hash(key.hashCode());
		return segmentFor(hash).containsKey(key, hash);
	}

	/**
	 * 判断map是否包含value值。 1. 首先，无锁查找，重试一次 2. 如果没找到，所有segment加锁，再查找一次。
	 */
	public boolean containsValue(Object value) {
		if (value == null)
			throw new NullPointerException();

		// See explanation of modCount use above

		final Segment<K, V>[] segments = this.segments;
		int[] mc = new int[segments.length];

		// Try a few times without locking
		for (int k = 0; k < RETRIES_BEFORE_LOCK; ++k) {
			int sum = 0;
			int mcsum = 0;
			for (int i = 0; i < segments.length; ++i) {
				int c = segments[i].count;
				mcsum += mc[i] = segments[i].modCount;
				if (segments[i].containsValue(value))
					return true;
			}
			boolean cleanSweep = true;
			if (mcsum != 0) {
				for (int i = 0; i < segments.length; ++i) {
					int c = segments[i].count;
					if (mc[i] != segments[i].modCount) {
						cleanSweep = false;
						break;
					}
				}
			}
			if (cleanSweep)
				return false;
		}
		// Resort to locking all segments
		for (int i = 0; i < segments.length; ++i)
			segments[i].lock();
		boolean found = false;
		try {
			for (int i = 0; i < segments.length; ++i) {
				if (segments[i].containsValue(value)) {
					found = true;
					break;
				}
			}
		} finally {
			for (int i = 0; i < segments.length; ++i)
				segments[i].unlock();
		}
		return found;
	}

	/**
	 * 是否包括指定value
	 */
	public boolean contains(Object value) {
		return containsValue(value);
	}

	/**
	 * 存储数据,存在时，会覆盖value
	 */
	public V put(K key, V value) {
		if (value == null)
			throw new NullPointerException();
		int hash = hash(key.hashCode());// 获取hash值
		return segmentFor(hash).put(key, hash, value, false);// 向某个segment中存入数据
	}

	/**
	 * 存储数据，不存在才会存入，存在则无变化
	 */
	public V putIfAbsent(K key, V value) {
		if (value == null)
			throw new NullPointerException();
		int hash = hash(key.hashCode());
		return segmentFor(hash).put(key, hash, value, true);
	}

	/**
	 * Copies all of the mappings from the specified map to this one. These mappings replace any
	 * mappings that this map had for any of the keys currently in the specified map.
	 *
	 * @param m
	 *            mappings to be stored in this map
	 */
	public void putAll(Map<? extends K, ? extends V> m) {
		for (Map.Entry<? extends K, ? extends V> e : m.entrySet())
			put(e.getKey(), e.getValue());
	}

	/**
	 * 删除key
	 */
	public V remove(Object key) {
		int hash = hash(key.hashCode());
		return segmentFor(hash).remove(key, hash, null);
	}

	/**
	 * 删除指定key-value对
	 */
	public boolean remove(Object key, Object value) {
		int hash = hash(key.hashCode());
		if (value == null)
			return false;
		return segmentFor(hash).remove(key, hash, value) != null;
	}

	/**
	 * 替换指定key-value对的value值
	 */
	public boolean replace(K key, V oldValue, V newValue) {
		if (oldValue == null || newValue == null)
			throw new NullPointerException();
		int hash = hash(key.hashCode());
		return segmentFor(hash).replace(key, hash, oldValue, newValue);
	}

	/**
	 * 
	 * 替换指定key的value值
	 */
	public V replace(K key, V value) {
		if (value == null)
			throw new NullPointerException();
		int hash = hash(key.hashCode());
		return segmentFor(hash).replace(key, hash, value);
	}

	/**
	 * 删除map的所有数据
	 */
	public void clear() {
		for (int i = 0; i < segments.length; ++i)
			segments[i].clear();
	}

	/**
	 * Returns a {@link Set} view of the keys contained in this map. The set is backed by the map,
	 * so changes to the map are reflected in the set, and vice-versa. The set supports element
	 * removal, which removes the corresponding mapping from this map, via the
	 * <tt>Iterator.remove</tt>, <tt>Set.remove</tt>, <tt>removeAll</tt>, <tt>retainAll</tt>, and
	 * <tt>clear</tt> operations. It does not support the <tt>add</tt> or <tt>addAll</tt>
	 * operations.
	 *
	 * <p>
	 * The view's <tt>iterator</tt> is a "weakly consistent" iterator that will never throw
	 * {@link ConcurrentModificationException}, and guarantees to traverse elements as they existed
	 * upon construction of the iterator, and may (but is not guaranteed to) reflect any
	 * modifications subsequent to construction.
	 */
	public Set<K> keySet() {
		Set<K> ks = keySet;
		return (ks != null) ? ks : (keySet = new KeySet());
	}

	/**
	 * Returns a {@link Collection} view of the values contained in this map. The collection is
	 * backed by the map, so changes to the map are reflected in the collection, and vice-versa. The
	 * collection supports element removal, which removes the corresponding mapping from this map,
	 * via the <tt>Iterator.remove</tt>, <tt>Collection.remove</tt>, <tt>removeAll</tt>,
	 * <tt>retainAll</tt>, and <tt>clear</tt> operations. It does not support the <tt>add</tt> or
	 * <tt>addAll</tt> operations.
	 *
	 * <p>
	 * The view's <tt>iterator</tt> is a "weakly consistent" iterator that will never throw
	 * {@link ConcurrentModificationException}, and guarantees to traverse elements as they existed
	 * upon construction of the iterator, and may (but is not guaranteed to) reflect any
	 * modifications subsequent to construction.
	 */
	public Collection<V> values() {
		Collection<V> vs = values;
		return (vs != null) ? vs : (values = new Values());
	}

	/**
	 * Returns a {@link Set} view of the mappings contained in this map. The set is backed by the
	 * map, so changes to the map are reflected in the set, and vice-versa. The set supports element
	 * removal, which removes the corresponding mapping from the map, via the
	 * <tt>Iterator.remove</tt>, <tt>Set.remove</tt>, <tt>removeAll</tt>, <tt>retainAll</tt>, and
	 * <tt>clear</tt> operations. It does not support the <tt>add</tt> or <tt>addAll</tt>
	 * operations.
	 *
	 * <p>
	 * The view's <tt>iterator</tt> is a "weakly consistent" iterator that will never throw
	 * {@link ConcurrentModificationException}, and guarantees to traverse elements as they existed
	 * upon construction of the iterator, and may (but is not guaranteed to) reflect any
	 * modifications subsequent to construction.
	 */
	public Set<Map.Entry<K, V>> entrySet() {
		Set<Map.Entry<K, V>> es = entrySet;
		return (es != null) ? es : (entrySet = new EntrySet());
	}

	/**
	 * 返回table中的key枚举
	 */
	public Enumeration<K> keys() {
		return new KeyIterator();
	}

	/**
	 *
	 * 返回table中的value枚举
	 */
	public Enumeration<V> elements() {
		return new ValueIterator();
	}

	/* ---------------- Iterator Support -------------- */

	abstract class HashIterator {
		int nextSegmentIndex;
		int nextTableIndex;
		HashEntry<K, V>[] currentTable;
		HashEntry<K, V> nextEntry;
		HashEntry<K, V> lastReturned;

		HashIterator() {
			System.out.println("HashIterator.....");
			nextSegmentIndex = segments.length - 1;
			nextTableIndex = -1;
			advance();
		}

		public boolean hasMoreElements() {
			return hasNext();
		}

		final void advance() {
			if (nextEntry != null && (nextEntry = nextEntry.next) != null)
				return;

			while (nextTableIndex >= 0) {
				if ((nextEntry = currentTable[nextTableIndex--]) != null)
					return;
			}

			/**
			 * <pre>
			 * 从后向前遍历segment和table，找到第一个元素。
			 * nextSegmentIndex为有元素的segment-1， 
			 * nextEntry有元素的table，
			 * nextTableIndex设置为table的最后一个元素的位置-1。
			 */
			while (nextSegmentIndex >= 0) {
				Segment<K, V> seg = segments[nextSegmentIndex--];
				if (seg.count != 0) {
					currentTable = seg.table;
					for (int j = currentTable.length - 1; j >= 0; --j) {
						if ((nextEntry = currentTable[j]) != null) {
							nextTableIndex = j - 1;
							return;
						}
					}
				}
			}
		}

		public boolean hasNext() {
			return nextEntry != null;
		}

		HashEntry<K, V> nextEntry() {
			if (nextEntry == null)
				throw new NoSuchElementException();
			lastReturned = nextEntry;
			advance();
			return lastReturned;
		}

		public void remove() {
			if (lastReturned == null)
				throw new IllegalStateException();
			ConcurrentHashMap.this.remove(lastReturned.key);
			lastReturned = null;
		}
	}

	final class KeyIterator extends HashIterator implements Iterator<K>, Enumeration<K> {
		public K next() {
			return super.nextEntry().key;
		}

		public K nextElement() {
			return super.nextEntry().key;
		}
	}

	final class ValueIterator extends HashIterator implements Iterator<V>, Enumeration<V> {
		public V next() {
			return super.nextEntry().value;
		}

		public V nextElement() {
			return super.nextEntry().value;
		}
	}

	/**
	 * Custom Entry class used by EntryIterator.next(), that relays setValue changes to the
	 * underlying map.
	 */
	final class WriteThroughEntry extends AbstractMap.SimpleEntry<K, V> {
		WriteThroughEntry(K k, V v) {
			super(k, v);
		}

		/**
		 * Set our entry's value and write through to the map. The value to return is somewhat
		 * arbitrary here. Since a WriteThroughEntry does not necessarily track asynchronous
		 * changes, the most recent "previous" value could be different from what we return (or
		 * could even have been removed in which case the put will re-establish). We do not and
		 * cannot guarantee more.
		 */
		public V setValue(V value) {
			if (value == null)
				throw new NullPointerException();
			V v = super.setValue(value);
			ConcurrentHashMap.this.put(getKey(), value);
			return v;
		}
	}

	final class EntryIterator extends HashIterator implements Iterator<Entry<K, V>> {
		public Map.Entry<K, V> next() {
			HashEntry<K, V> e = super.nextEntry();
			return new WriteThroughEntry(e.key, e.value);
		}
	}

	final class KeySet extends AbstractSet<K> {
		public Iterator<K> iterator() {
			return new KeyIterator();
		}

		public int size() {
			return ConcurrentHashMap.this.size();
		}

		public boolean contains(Object o) {
			return ConcurrentHashMap.this.containsKey(o);
		}

		public boolean remove(Object o) {
			return ConcurrentHashMap.this.remove(o) != null;
		}

		public void clear() {
			ConcurrentHashMap.this.clear();
		}
	}

	final class Values extends AbstractCollection<V> {
		public Iterator<V> iterator() {
			return new ValueIterator();
		}

		public int size() {
			return ConcurrentHashMap.this.size();
		}

		public boolean contains(Object o) {
			return ConcurrentHashMap.this.containsValue(o);
		}

		public void clear() {
			ConcurrentHashMap.this.clear();
		}
	}

	final class EntrySet extends AbstractSet<Map.Entry<K, V>> {
		public Iterator<Map.Entry<K, V>> iterator() {
			System.out.println("EntrySet...");
			return new EntryIterator();
		}

		public boolean contains(Object o) {
			if (!(o instanceof Map.Entry))
				return false;
			Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
			V v = ConcurrentHashMap.this.get(e.getKey());
			return v != null && v.equals(e.getValue());
		}

		public boolean remove(Object o) {
			if (!(o instanceof Map.Entry))
				return false;
			Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
			return ConcurrentHashMap.this.remove(e.getKey(), e.getValue());
		}

		public int size() {
			return ConcurrentHashMap.this.size();
		}

		public void clear() {
			ConcurrentHashMap.this.clear();
		}
	}

	/* ---------------- Serialization Support -------------- */

	/**
	 * Save the state of the <tt>ConcurrentHashMap</tt> instance to a stream (i.e., serialize it).
	 * 
	 * @param s
	 *            the stream
	 * @serialData the key (Object) and value (Object) for each key-value mapping, followed by a
	 *             null pair. The key-value mappings are emitted in no particular order.
	 */
	private void writeObject(java.io.ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();

		for (int k = 0; k < segments.length; ++k) {
			Segment<K, V> seg = segments[k];
			seg.lock();
			try {
				HashEntry<K, V>[] tab = seg.table;
				for (int i = 0; i < tab.length; ++i) {
					for (HashEntry<K, V> e = tab[i]; e != null; e = e.next) {
						s.writeObject(e.key);
						s.writeObject(e.value);
					}
				}
			} finally {
				seg.unlock();
			}
		}
		s.writeObject(null);
		s.writeObject(null);
	}

	/**
	 * Reconstitute the <tt>ConcurrentHashMap</tt> instance from a stream (i.e., deserialize it).
	 * 
	 * @param s
	 *            the stream
	 */
	private void readObject(java.io.ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();

		// Initialize each segment to be minimally sized, and let grow.
		for (int i = 0; i < segments.length; ++i) {
			segments[i].setTable(new HashEntry[1]);
		}

		// Read the keys and values, and put the mappings in the table
		for (;;) {
			K key = (K) s.readObject();
			V value = (V) s.readObject();
			if (key == null)
				break;
			put(key, value);
		}
	}
}
