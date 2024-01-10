package jata.message;

import java.util.*;


public class MessageQueue {
	protected Queue<Message> msgQ;
	public MessageQueue(){
		msgQ= new LinkedList<Message>();
	}
	public synchronized void offer(Message msg){
		msgQ.offer(msg);
	}
	public synchronized Message poll(){
		return msgQ.poll();
	}
	public synchronized Message peek(){
		return msgQ.peek();
	}
	public synchronized boolean isEmpty(){
		return msgQ.isEmpty();
	}
}
