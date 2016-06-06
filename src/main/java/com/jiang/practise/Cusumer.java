package com.jiang.practise;

public class Cusumer extends Thread{

	int i;
	public Cusumer(int i){ this.i = i;}
	
	@Override
	public void run(){
		synchronized (ProducerAndCusumer.blockingQueue) {
			while(true){
				while(ProducerAndCusumer.blockingQueue.size()<=0){
					System.out.println("线程"+i+"队列无数据，等待数据");
					try {
						ProducerAndCusumer.blockingQueue.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println(ProducerAndCusumer.blockingQueue.poll());
				ProducerAndCusumer.blockingQueue.notify();
			}
			
		}
		
	}
}
