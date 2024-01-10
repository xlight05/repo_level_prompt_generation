package com.chenlb.ttserver.handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.chenlb.ttserver.Utils;

public class MKeyRequestHandler extends CommandRequestHandler {

	private String[] keys;
	public MKeyRequestHandler(byte[] command, String[] keys) {
		super(command);
		this.keys = keys;
	}

	protected void writeExtToRequest(ByteArrayOutputStream wbos) throws IOException {
		wbos.write(Utils.intToBytes(keys.length));
		
		for(String key : keys) {
			byte[] ks = key.getBytes(KEY_CHARSET);
			wbos.write(Utils.intToBytes(ks.length));
			wbos.write(ks);
		}
	}

	
}
