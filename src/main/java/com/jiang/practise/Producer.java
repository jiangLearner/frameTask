package com.jiang.practise;

public class Producer extends Thread{
	
	int t;
	
	public Producer(int i){ this.t = i;}
	
	@Override
	public void run(){
		synchronized (ProducerAndCusumer.blockingQueue) {
			int i=0;
			while(true){
				while(ProducerAndCusumer.blockingQueue.size()+1>ProducerAndCusumer.blockingQueueLength){
					System.out.println("线程"+t+"队列已满，溢出数据.等待");
					try {
						ProducerAndCusumer.blockingQueue.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					ProducerAndCusumer.blockingQueue.put(""+(i++));
					ProducerAndCusumer.blockingQueue.notifyAll();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
			}
			
		}
		
	}

}
