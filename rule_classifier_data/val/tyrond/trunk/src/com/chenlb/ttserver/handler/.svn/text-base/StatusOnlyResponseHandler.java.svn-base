package com.chenlb.ttserver.handler;

import java.io.IOException;

/**
 * 仅仅是成功与否的状态响应处理。
 * 
 * @author chenlb 2009-6-16 下午02:07:07
 */
public class StatusOnlyResponseHandler implements ResponseHandler {
	//private static final Logger logger = Logger.getLogger(StatusOnlyResponseHandler.class.getName());

	public Object readFormResponse(byte[] resp) throws IOException {
		
		return resp[0] == 0;
	}
		
	
}
