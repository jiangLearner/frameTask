package org.pactise.common;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlocakingQueue<E extends Object> {

	private final ArrayList<E> items ;
	
	private int takeIndex;
	private int putIndex;
	private int count;
	
	private Lock lock = new ReentrantLock();
	
	private Condition fullCondition = lock.newCondition();
	private Condition EmptyCondition = lock.newCondition();

	public BlocakingQueue(){
		items = new ArrayList<E>(100);
		takeIndex = -1;
		putIndex = 0;
		count = 0;
	}
	
	public BlocakingQueue(int capacity){
		
		items = new ArrayList<E>(capacity);
		takeIndex = -1;
		putIndex = 0;
		count = 0;
	}
	
	public void put(E x){
		lock.lock();
		try{
			while(count == items.size()){
				fullCondition.wait();
			}
			items.add(x);
			if(++putIndex == items.size()){
				putIndex = 0;
			}
			++count;
			EmptyCondition.signal();
		}catch (Exception e) {
			// TODO: handle exception
		}finally{
			lock.unlock();
		}
	}
	
	public E take(){
		lock.lock();
		try{
			while(count == 0){
				EmptyCondition.wait();
			}
			E result  = items.get(takeIndex--);
			--count;
			fullCondition.signal();
			return result;
		}catch(Exception e){
			
		}finally{
			lock.unlock();
		}
		return null;
		
	}
}
