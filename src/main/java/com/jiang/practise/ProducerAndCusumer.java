package com.jiang.practise;

import java.util.concurrent.ArrayBlockingQueue;

public class ProducerAndCusumer {

	public static Integer blockingQueueLength = 1;
	public static ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(blockingQueueLength);
	
	
	public static void main(String[] args){
	/*		Thread t_P1 = new Producer();
			t_P1.start();
			Thread t_C1 = new Cusumer();
			t_C1.start();*/
			Thread t_P2 = new Producer(2);
			t_P2.start();
			Thread t_C2 = new Cusumer(2);
			t_C2.start();
			Thread t_P3 = new Producer(3);	
			t_P3.start();
			Thread t_C3 = new Cusumer(3);
			t_C3.start();
	}
}
