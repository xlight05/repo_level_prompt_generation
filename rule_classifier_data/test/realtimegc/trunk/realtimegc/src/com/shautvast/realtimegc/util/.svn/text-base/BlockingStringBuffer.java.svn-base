package com.shautvast.realtimegc.util;

/**
 * BlockingQueue containing characters
 * does not perform as well as juc blockingQueue...
 * @author sander
 * @deprecated
 */
public class BlockingStringBuffer {

	private char[] buffer;
	private int readPosition;
	private int writePosition;
	private int available;
	private int capacity;
	private boolean closed;

	public BlockingStringBuffer(int capacity) {
		buffer = new char[capacity];
		this.capacity = capacity;

	}

	public int available() {
		return available;
	}

	public char poll() {
		synchronized (buffer) {
			while (available == 0) {
				try {
					buffer.wait();
				} catch (InterruptedException e) {
				}
			}
			char c = buffer[readPosition++];
			if (readPosition == capacity) {
				readPosition = 0;
			}
			available--;
			buffer.notifyAll();
			return c;
		}
	}

	public int poll(char[] cbuf, int off, int len) {
		int i;
		for (i = 0; i < len; i++) {
			if ((cbuf[off + i] = poll()) == -1)
				break;
		}
		return i;
	}
	
	/**
	 * @param len
	 * @return the next len characters if available
	 */
	public char[] toCharArray(int len){
		char[] cb=new char[len];
		poll(cb,0,len);
		return cb;
	}
	
	public char[] toCharArray(){
		char[] cb=new char[available];
		poll(cb,0,available);
		return cb;
	}

	public void add(char c) {
		synchronized (buffer) {
			while (available == capacity) {
				try {
					buffer.wait();
				} catch (InterruptedException e) {
				}
			}
			buffer[writePosition++] = c;
			available++;
			if (writePosition == capacity)
				writePosition = 0;
			buffer.notifyAll();
		}

	}

	public void add(CharSequence chars) {
		for (int i = 0; i < chars.length(); i++) {
			add(chars.charAt(i));
		}
	}

	public void add(char[] chars) {
		for (int i = 0; i < chars.length; i++) {
			add(chars[i]);
		}
	}

	public int remainingCapacity() {
		return capacity - available;
	}

	public boolean isClosed() {
		return closed;
	}

	public void close() {
		this.closed = true;
	}
}
