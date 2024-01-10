package com.chenlb.ttserver.handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.chenlb.ttserver.Utils;

/**
 * 仅仅发送 key 请求。
 * 
 * @author chenlb 2009-6-16 下午02:07:46
 */
public class KeyOnlyRequestHandler extends CommandRequestHandler {

	private String key;
	
	public KeyOnlyRequestHandler(byte[] command, String key) {
		super(command);
		this.key = key;
	}

	protected void writeExtToRequest(ByteArrayOutputStream wbos) throws IOException {
		
		byte[] ks = key.getBytes(KEY_CHARSET);
		wbos.write(Utils.intToBytes(ks.length));
		wbos.write(ks);
		
	}

}
