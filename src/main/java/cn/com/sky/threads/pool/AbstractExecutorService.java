package cn.com.sky.threads.pool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Provides default implementations of {@link ExecutorService} execution methods. This class
 * implements the <tt>submit</tt>, <tt>invokeAny</tt> and <tt>invokeAll</tt> methods using a
 * {@link RunnableFuture} returned by <tt>newTaskFor</tt>, which defaults to the {@link FutureTask}
 * class provided in this package. For example, the implementation of <tt>submit(Runnable)</tt>
 * creates an associated <tt>RunnableFuture</tt> that is executed and returned. Subclasses may
 * override the <tt>newTaskFor</tt> methods to return <tt>RunnableFuture</tt> implementations other
 * than <tt>FutureTask</tt>.
 *
 * <p>
 * <b>Extension example</b>. Here is a sketch of a class that customizes {@link ThreadPoolExecutor}
 * to use a <tt>CustomTask</tt> class instead of the default <tt>FutureTask</tt>:
 * 
 * <pre>
 * {@code
 * public class CustomThreadPoolExecutor extends ThreadPoolExecutor {
 * 
 *   static class CustomTask<V> implements RunnableFuture<V> {...}
 * 
 *   protected <V> RunnableFuture<V> newTaskFor(Callable<V> c) {
 *       return new CustomTask<V>(c);
 *   }
 *   protected <V> RunnableFuture<V> newTaskFor(Runnable r, V v) {
 *       return new CustomTask<V>(r, v);
 *   }
 *   // ... add constructors, etc.
 * }}
 * </pre>
 *
 * @since 1.5
 * @author Doug Lea
 */
public abstract class AbstractExecutorService implements ExecutorService {

	/**
	 * Returns a <tt>RunnableFuture</tt> for the given runnable and default value.
	 *
	 * @param runnable
	 *            the runnable task being wrapped
	 * @param value
	 *            the default value for the returned future
	 * @return a <tt>RunnableFuture</tt> which when run will run the underlying runnable and which,
	 *         as a <tt>Future</tt>, will yield the given value as its result and provide for
	 *         cancellation of the underlying task.
	 * @since 1.6
	 */
	protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
		return new FutureTask<T>(runnable, value);
	}

	/**
	 * Returns a <tt>RunnableFuture</tt> for the given callable task.
	 *
	 * @param callable
	 *            the callable task being wrapped
	 * @return a <tt>RunnableFuture</tt> which when run will call the underlying callable and which,
	 *         as a <tt>Future</tt>, will yield the callable's result as its result and provide for
	 *         cancellation of the underlying task.
	 * @since 1.6
	 */
	protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
		return new FutureTask<T>(callable);
	}

	/**
	 * @throws RejectedExecutionException
	 *             {@inheritDoc}
	 * @throws NullPointerException
	 *             {@inheritDoc}
	 */
	public Future<?> submit(Runnable task) {
		if (task == null)
			throw new NullPointerException();
		RunnableFuture<Void> ftask = newTaskFor(task, null);
		execute(ftask);
		return ftask;
	}

	/**
	 * @throws RejectedExecutionException
	 *             {@inheritDoc}
	 * @throws NullPointerException
	 *             {@inheritDoc}
	 */
	public <T> Future<T> submit(Runnable task, T result) {
		if (task == null)
			throw new NullPointerException();
		RunnableFuture<T> ftask = newTaskFor(task, result);
		execute(ftask);
		return ftask;
	}

	/**
	 * @throws RejectedExecutionException
	 *             {@inheritDoc}
	 * @throws NullPointerException
	 *             {@inheritDoc}
	 */
	public <T> Future<T> submit(Callable<T> task) {
		if (task == null)
			throw new NullPointerException();
		RunnableFuture<T> ftask = newTaskFor(task);
		execute(ftask);
		return ftask;
	}

}
