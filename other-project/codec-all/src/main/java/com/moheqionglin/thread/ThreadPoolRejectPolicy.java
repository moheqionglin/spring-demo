package com.moheqionglin.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-12 23:58
 */
public class ThreadPoolRejectPolicy {

	/**
	 * task给线程池执行
	 * */
	public static final  RejectedExecutionHandler threadPoolRunsPolicy = new RejectedExecutionHandler() {
		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			try {
				System.out.println("rejected handler put " + ((Task)r).jobid);
				executor.getQueue().put(r);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " abort ");
		}
	};

	/**
	 *
	 * 给主线程执行
	 *
	 *
	 * */
	public static final RejectedExecutionHandler callerRunsPolicy = new ThreadPoolExecutor.CallerRunsPolicy();
	/**
	 * 抛异常策略
	 *
	 * 给主线程跑一个异常，主线程会因为异常退出。 除非自己捕获这个异常
	 * */
	public static final RejectedExecutionHandler abortPolicy = new ThreadPoolExecutor.AbortPolicy();
	/**
	 * 丢弃策略
	 *
	 * 执行结果
	 * pool-1-thread-1 finish job-0
	 * pool-1-thread-1 finish job-1
	 * pool-1-thread-1 finish job-2
	 * */
	public static final RejectedExecutionHandler discardPolicy = new ThreadPoolExecutor.DiscardPolicy();

	/**
	 * 丢弃最老的策略
	 * 执行结果
	 * pool-1-thread-1 finish job-0
	 * pool-1-thread-1 finish job-8
	 * pool-1-thread-1 finish job-9
	 */

	public static final RejectedExecutionHandler discardOldestPolicy = new ThreadPoolExecutor.DiscardOldestPolicy();


	private static void sleep() {
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MICROSECONDS,
				new ArrayBlockingQueue<>(2), callerRunsPolicy);

		for (int i = 0; i < 10; i++) {
			threadPoolExecutor.submit(new Task("job-" + i));
			System.out.println("submit runnable success");
		}
		threadPoolExecutor.shutdown();

	}

	static class Task implements Runnable{
		String jobid = "";
		public Task(String jobid) {
			this.jobid = jobid;
		}

		@Override
		public void run() {
			sleep();
			System.out.println(Thread.currentThread().getName() + " finish " + jobid);
		}
	}
}