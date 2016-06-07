package org.pactise.common;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockTest {

	/**
	 * 写的时候其他写和读都不能操作，而读的时候，写不能操作，但其他线程的读操作都是可以正常执行的。
	 * ReadWriteLock严格区分了读写操作，如果读操作使用了写入锁，那么降低读操作的吞吐量，如果写操作使用了读取锁，那么就可能发生数据错误
	 */
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	public static void main(String[] args) {
		final ReentrantReadWriteLockTest lockTest = new ReentrantReadWriteLockTest();

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				lockTest.readDataFromFile1(Thread.currentThread());
			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				lockTest.readDataFromFile1(Thread.currentThread());
			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				lockTest.writeDataIntoFile1(Thread.currentThread());
			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				lockTest.writeDataIntoFile1(Thread.currentThread());
			}
		}).start();

	}

	public synchronized void readDataFromFile(Thread thread) throws InterruptedException {
		long start = System.currentTimeMillis();
		while (true) {
			Thread.sleep(10);
			System.out.println(thread.getName() + "正在进行读操作");
		}
	//	System.out.println(thread.getName() + "读操作完毕");
	}

	public synchronized void writeDataIntoFile(Thread thread) throws InterruptedException {
		long start = System.currentTimeMillis();
		while (true) {
			Thread.sleep(10);
			System.out.println(thread.getName() + "正在进行写操作");
		}
	//	System.out.println(thread.getName() + "写操作完毕");
	}
	
	
	public  void readDataFromFile1(Thread thread) {
		lock.readLock().lock();
		try{
		long start = System.currentTimeMillis();
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(thread.getName() + "正在进行读操作");
		}
		//System.out.println(thread.getName() + "读操作完毕");
		}finally{
			lock.readLock().unlock();
		}
	}

	public  void writeDataIntoFile1(Thread thread) {
		lock.writeLock().lock();
		try{
		long start = System.currentTimeMillis();
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(thread.getName() + "正在进行写操作");
		}
	//	System.out.println(thread.getName() + "写操作完毕");
		}finally{
			lock.writeLock().unlock();
		}
	}

}
