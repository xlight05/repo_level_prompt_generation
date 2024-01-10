package com.chenlb.ttserver.handler;

import java.io.IOException;

public interface ResponseHandler {

	/**
	 * 从 resp 字节数组中解析对象。
	 * @param resp
	 * @return
	 * @throws IOException
	 * @author chenlb 2009-6-15 下午06:05:40
	 */
	Object readFormResponse(byte[] resp) throws IOException;
}
