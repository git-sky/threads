package cn.com.sky.threads.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestBigFile2 {

	// public static String fname = "e:\\b.log";
	public static int SLINE = 10;

	interface DataHandler {
		void doHandler(String[] data);
	}

	interface ErrorHandler {
		void doHandler(Throwable t);

		public static final ErrorHandler PRINTER = new ErrorHandler() {
			public void doHandler(Throwable t) {
				t.printStackTrace();
			}
		};
	}

	/**
	 * 核心类，大文件数据处理器
	 */
	class BigFileProcessor {
		/**
		 * 致命毒药，用于干掉处理数据的线程。
		 */
		public final Object POISON = new Object();
		private BlockingQueue<Object> queue = new ArrayBlockingQueue<Object>(64);
		private ErrorHandler errorHandler = ErrorHandler.PRINTER;
		/**
		 * 用于终止读取线程，非强制终止。
		 */
		private volatile boolean running = false;
		/**
		 * 数据读取线程
		 */
		private Thread fileReader;
		/**
		 * 数据处理线程
		 */
		private Thread[] proccessors;

		public BigFileProcessor(final File file, final DataHandler handler) {
			fileReader = new Thread(new Runnable() {
				public void run() {
					try {
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(new FileInputStream(file)));
						String line = null;
						ArrayList<String> cache = new ArrayList<String>();
						long lines = 0;
						while (running && (line = reader.readLine()) != null) {
							lines++;
							if (lines == SLINE) {
								lines = 0;
								String[] data = cache.toArray(new String[cache
										.size()]);
								cache.clear();
								queue.put(data);
							} else {
								cache.add(line);
							}
						}
					} catch (Throwable t) {
						errorHandler.doHandler(t);
					} finally {
						try {
							queue.put(POISON);
						} catch (InterruptedException e) {
							errorHandler.doHandler(e);
						}
					}
				}
			}, "reader_thread");
			// 默认创建的线程数，与CPU处理的内核数相同，楼主可以自行更改。
			System.out.println(" 默认创建的线程数，与CPU处理的内核数相同="
					+ Runtime.getRuntime().availableProcessors());
			proccessors = new Thread[Runtime.getRuntime().availableProcessors()];
			Runnable worker = new Runnable() {
				public void run() {
					try {
						for (;;) {
							Object obj = queue.take();
							if (obj == POISON) {
								queue.put(obj);
								break;
							} else {
								String[] data = (String[]) obj;
								// handler.doHandler(data);
								String name = Thread.currentThread().getName();
								BufferedWriter bufWriter = new BufferedWriter(new FileWriter("e:\\c.log",true));  
								for (String s : data) {
//									System.out.println(name + "====" + s);
									bufWriter.write(s);
									bufWriter.newLine();
								}
							}
						}
					} catch (Throwable t) {
						errorHandler.doHandler(t);
					}
				}
			};
			for (int i = 0; i < proccessors.length; i++) {
				proccessors[i] = new Thread(worker, "proccessor-thread_" + i);
			}
		}

		public void setErrorHandler(ErrorHandler errorHandler) {
			this.errorHandler = errorHandler;
		}

		/**
		 * 开启处理过程
		 */
		public synchronized void start() {
			if (running)
				return;
			running = true;
			fileReader.start();
			for (int i = 0; i < proccessors.length; i++) {
				proccessors[i].start();
			}
		}

		/**
		 * 中断处理过程，非强制中断
		 */
		public synchronized void shutdown() {
			if (running) {
				running = false;
			}
		}

		/**
		 * 试图等待整个处理过程完毕
		 */
		public void join() {
			try {
				fileReader.join();
			} catch (InterruptedException e) {
				errorHandler.doHandler(e);
			}
			for (int i = 0; i < proccessors.length; i++) {
				try {
					proccessors[i].join();
				} catch (InterruptedException e) {
					errorHandler.doHandler(e);
				}
			}
		}
	}

	/**
	 * 测试用例，教你怎样使用这些代码。
	 * 
	 * @throws IOException
	 */
	public void testcase() {
		File file = new File("e:\\b.log");

		DataHandler dataHandler = new DataHandler() {
			public void doHandler(String[] data) {
				for (String s : data) {
					// logger.info(s);
					// System.out.println(s);
				}
			}
		};
		BigFileProcessor processor = new BigFileProcessor(file, dataHandler);
		processor.start();
		processor.join();
	}

	public static void main(String[] args) {
		long a = System.currentTimeMillis();
		System.out.println();
		TestBigFile2 instance = new TestBigFile2();
		instance.testcase();
		System.out.println(System.currentTimeMillis() - a);
	}

}
