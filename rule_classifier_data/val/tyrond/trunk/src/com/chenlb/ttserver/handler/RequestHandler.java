package com.chenlb.ttserver.handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface RequestHandler {

	String KEY_CHARSET = "UTF-8";
	String VALUE_CHARSET = "UTF-8";
	/**
	 * 向 whos 中写请求字节。
	 * @param wbos
	 * @throws IOException
	 * @author chenlb 2009-6-15 下午06:05:18
	 */
	void writeToRequest(ByteArrayOutputStream wbos) throws IOException;
}
