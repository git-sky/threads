package cn.com.sky.thread_juc.thread_pool.future;

import java.util.concurrent.atomic.AtomicReference;

/**
 * lock-free的无界stack （Treiber算法）
 * <p>
 * push 查看当前的head指针，并构建一个新的节点，如果head在这个过程中没有发生改变，再用原子地改变head；如果CAS失败了，说明有别的线程在“插队”，过程就会重新开始。pop类似。
 * <p>
 * 选择在链表首部进行push和pop比较取巧，此时只需要CAS一个head指针即可；如果更新发生在链表尾部，则有两个指针需要更新，一个是tail，一个是tail.next，但无法对两个指针进行原子性的CAS，此时需要找到一种方式用两次CAS更新两个指针，但又能保证数据结构的一致性。
 * <p>
 * <p>
 * FutureTask使用的类似的结构。
 */
public class ConcurrentStack<E> {

    AtomicReference<Node<E>> head = new AtomicReference<Node<E>>();

    // push和pop都发生在head处
    public void push(E item) {
        Node<E> newHead = new Node<E>(item);
        Node<E> oldHead;

        do {
            oldHead = head.get();
            newHead.next = oldHead;
        } while (!head.compareAndSet(oldHead, newHead));

    }

    public E pop() {
        Node<E> oldHead;
        Node<E> newHead;

        do {
            oldHead = head.get();
            if (oldHead == null)
                return null;
            newHead = oldHead.next;
        } while (!head.compareAndSet(oldHead, newHead));

        return oldHead.item;
    }

    static class Node<E> {
        final E item;
        Node<E> next;

        public Node(E item) {
            this.item = item;
        }
    }

}
