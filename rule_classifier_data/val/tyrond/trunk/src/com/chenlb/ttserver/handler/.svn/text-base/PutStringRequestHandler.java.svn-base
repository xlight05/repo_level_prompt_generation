package com.chenlb.ttserver.handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.chenlb.ttserver.Utils;

public class PutStringRequestHandler extends CommandRequestHandler {

	protected String key;
	protected String value;
	public PutStringRequestHandler(byte[] command, String key, String value) {
		super(command);
		this.key = key;
		this.value = value;
	}

	protected void writeExtToRequest(ByteArrayOutputStream wbos) throws IOException {
		byte[] ks = key.getBytes(KEY_CHARSET);
		wbos.write(Utils.intToBytes(ks.length));	//key len
		
		byte[] vs = value.getBytes(VALUE_CHARSET);
		wbos.write(Utils.intToBytes(vs.length));	//vaule len
		
		wbos.write(ks);	//key byte
		wbos.write(vs);	//vaule byte
	}

}
