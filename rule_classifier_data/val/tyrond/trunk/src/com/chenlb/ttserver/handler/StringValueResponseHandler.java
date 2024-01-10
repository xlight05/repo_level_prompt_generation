package com.chenlb.ttserver.handler;

import java.io.IOException;

import com.chenlb.ttserver.Utils;

public class StringValueResponseHandler implements ResponseHandler {
	
	private String charset;
	
	public StringValueResponseHandler() {
		this(RequestHandler.VALUE_CHARSET);
	}

	public StringValueResponseHandler(String charset) {
		super();
		this.charset = charset;
	}



	public Object readFormResponse(byte[] resp) throws IOException {
		Object obj = null;
		int len = Utils.byteToInt(resp, 1, 4);

		obj = new String(resp, 1+4, Math.min(resp.length-5, len), charset);
		return obj;
	}

}
