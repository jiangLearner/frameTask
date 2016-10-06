package com.jiang.practise.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ThreadLock implements Lock{

	private static Sync sync = new Sync(2);
	
	//定义类继承自同步队列
	private static final class Sync extends AbstractQueuedSynchronizer{
		Sync(int count){
			if(count <= 0){
				throw new IllegalArgumentException("count must lage than 0!");
			}
			//The synchronization state. 设置同步状态，如果状态<=0表示资源已经锁住
			setState(count);
		}
		
		//获取锁
		@Override
		protected int tryAcquireShared(int arg) {
			// TODO Auto-generated method stub
			for(;;){
				int current = getState();
				int newCount = current-arg;
				if(newCount<0||compareAndSetState(current, newCount)){
					return newCount;
				}
			}
		}
		
		@Override
		protected boolean tryReleaseShared(int arg) {
			// TODO Auto-generated method stub
			for(;;){
				int current = getState();
				int newCount = current+arg;
				if(compareAndSetState(current, newCount)){
					return true;
				}
			}
		}
	}
	
	@Override
	public void lock() {
		// TODO Auto-generated method stub
		sync.acquireShared(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean tryLock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unlock() {
		// TODO Auto-generated method stub
		sync.releaseShared(1);
	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

}
