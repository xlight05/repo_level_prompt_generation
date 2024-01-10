package com.chenlb.ttserver.handler;

import java.io.IOException;

import com.chenlb.ttserver.Utils;

public class IntValueResponseHandler implements ResponseHandler {

	public Object readFormResponse(byte[] resp) throws IOException {
		if(resp.length < 5) {
			return 0;
		}
		Integer num = Utils.byteToInt(resp, 1, 4);
		return num;
	}

}
