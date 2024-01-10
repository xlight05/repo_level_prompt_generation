package com.chenlb.ttserver.handler;

import java.io.IOException;

import com.chenlb.ttserver.Utils;

public class LongValueResponseHandler implements ResponseHandler {

	public Object readFormResponse(byte[] resp) throws IOException {
		if(resp.length < 9) {
			return 0L;
		}
		Long num = Utils.byteToLong(resp, 1, 8);
		return num;
	}

}
