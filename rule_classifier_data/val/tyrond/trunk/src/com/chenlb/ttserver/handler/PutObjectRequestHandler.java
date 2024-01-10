package com.chenlb.ttserver.handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.chenlb.ttserver.Utils;

public class PutObjectRequestHandler extends CommandRequestHandler {

	protected String key;
	protected Object value;
	public PutObjectRequestHandler(byte[] command, String key, Object value) {
		super(command);
		this.key = key;
		this.value = value;
	}

	protected void writeExtToRequest(ByteArrayOutputStream wbos) throws IOException {
		
		byte[] ks = key.getBytes(KEY_CHARSET);
		wbos.write(Utils.intToBytes(ks.length));	//key len
		
		ByteArrayOutputStream obos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(obos);
		oos.writeObject(value);
		
		wbos.write(Utils.intToBytes(obos.size()));	//vaule len
		
		wbos.write(ks);	//key byte
		wbos.write(obos.toByteArray());	//vaule byte
	}
}
