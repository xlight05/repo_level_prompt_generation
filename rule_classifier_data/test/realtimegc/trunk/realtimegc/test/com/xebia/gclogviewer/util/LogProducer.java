package com.xebia.gclogviewer.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

public class LogProducer {
	private String fileName;
	private String text;
	private volatile AtomicBoolean running=new AtomicBoolean();

	public LogProducer(String fileName) {
		super();
		this.fileName = fileName;
	}

	public void start() throws FileNotFoundException {
		final PrintWriter writer = new PrintWriter(new FileOutputStream(fileName));
		running.set(true);
		new Thread() {
			public void run() {
				while (running.get()) {
					writer.print(text);
					writer.flush();
					double wait=Math.random() * 2000 + 1000;
					
					try {
						Thread.sleep((long)wait );
					} catch (InterruptedException e) {
					}
				}
			};
		}.start();

	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isRunning() {
		return running.get();
	}

	public void setRunning(boolean running) {
		this.running.set(running);
	}

}
