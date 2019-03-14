package com.ysl.myandroidbase.message;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageQueue {

	private BlockingQueue<String> queue = new ArrayBlockingQueue(1000);//消息队列

	/**
	 * 将记录消息入队
	 */
	public boolean doEnqueue(String message){
		try {
			System.out.println(">>>消息入队，message="+message);
			queue.add(message);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 将队列的首位出队
	 */
	public String doDequeue(){
		try {
			return queue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
