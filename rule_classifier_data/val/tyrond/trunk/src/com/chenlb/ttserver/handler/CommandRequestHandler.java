package com.chenlb.ttserver.handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CommandRequestHandler implements RequestHandler {

	protected byte[] command;
	
	public CommandRequestHandler(byte[] command) {
		super();
		this.command = command;
	}

	public void writeToRequest(ByteArrayOutputStream wbos) throws IOException {
		wbos.write(command);
		writeExtToRequest(wbos);
	}

	protected void writeExtToRequest(ByteArrayOutputStream wbos) throws IOException {
		
	}
}
