package com.chenlb.ttserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.chenlb.ttserver.handler.CommandRequestHandler;
import com.chenlb.ttserver.handler.IntValueResponseHandler;
import com.chenlb.ttserver.handler.KeyOnlyRequestHandler;
import com.chenlb.ttserver.handler.LongValueResponseHandler;
import com.chenlb.ttserver.handler.MKeyRequestHandler;
import com.chenlb.ttserver.handler.PutObjectRequestHandler;
import com.chenlb.ttserver.handler.PutStringRequestHandler;
import com.chenlb.ttserver.handler.RequestHandler;
import com.chenlb.ttserver.handler.ResponseHandler;
import com.chenlb.ttserver.handler.StatusOnlyResponseHandler;
import com.chenlb.ttserver.handler.StringValueResponseHandler;

/**
 * TTServer Java Client.
 * 
 * @author chenlb 2009-6-17 下午12:58:21
 */
public class TTServerClient {
	private static final Logger logger = Logger.getLogger(TTServerClient.class.getName());
	
	private Socket server;
	private ResponseHandler statusResp;
	
	/**
	 * socket 超时为 30s
	 * @throws IOException
	 */
	public TTServerClient(String host, int port) throws IOException {
		this(host, port, 30000);
	}
	
	/**
	 * 
	 * @param socketTimeout socket 超时
	 */
	public TTServerClient(String host, int port, int socketTimeout) throws IOException {
		server = new Socket(host, port);
		server.setSoTimeout(socketTimeout);
		statusResp = new StatusOnlyResponseHandler();
	}
	
	/**
	 * 请求 ttserver 模板。
	 * @param req 请求 handler
	 * @param resp 为 null 返回不用解析响应。
	 * @return 返回 resp 的返回，如果出错返回 null。
	 * @author chenlb 2009-7-17 上午11:47:52
	 */
	public Object request(RequestHandler req, ResponseHandler resp) {
		
		try {
			ByteArrayOutputStream wbos = new ByteArrayOutputStream();
			req.writeToRequest(wbos);
			
			//send to server stream handler
			OutputStream out = server.getOutputStream();
			
			wbos.writeTo(out);
			wbos.close();
		} catch (IOException e) {
			logger.log(Level.WARNING, "write bytes data I/O error!", e);
			return null; 
		}
		
		if(resp == null) {	//无返回的
			return null;
		}
		
		try {
			ByteArrayOutputStream rbos = new ByteArrayOutputStream();
			InputStream in = server.getInputStream();
			byte[] respBuff = new byte[8192];
			int bN = respBuff.length;
			//读取响应的字节流
			while(bN >= respBuff.length && (bN=in.read(respBuff)) > 0) {
				rbos.write(respBuff, 0, bN);
			}
			
			if(rbos.size() < 1) {
				throw new IOException("TTServer no response!");
			}
			
			respBuff = rbos.toByteArray();
			
			byte status = respBuff[0];
			if(status == 0) {
				
				return resp.readFormResponse(respBuff);
				
			} else {
				if(logger.isLoggable(Level.FINER)) {
					logger.finer("no data response!");
				}
			}
		} catch (IOException e) {
			logger.log(Level.WARNING, "read bytes data I/O error!", e);
		}
		
		return null;
	}
	
	public boolean requestBoolean(RequestHandler req, ResponseHandler resp) {
		Boolean isOk = null;
		
		isOk = (Boolean) request(req, resp);
		
		return isOk != null && isOk;
	}
	
	/**
	 * 请求，用 StatusResponseHandler 处理响应。
	 * @param req
	 * @return 是否成功
	 * @author chenlb 2009-6-17 下午06:14:49
	 */
	public boolean request(RequestHandler req) {
		return requestBoolean(req, statusResp);
	}
	
	/**
	 * 根据 key 去取对象，如果取得的内容不能反系列化，尝试转换为 String
	 */
	public Object get(String key) {
		
		Object obj = request(new KeyOnlyRequestHandler(Command.GET, key), new IntValueResponseHandler() {

			public Object readFormResponse(byte[] resp) throws IOException {
				Object obj = null;
				int len = (Integer) super.readFormResponse(resp);
				
				if(len > 0 && len < resp.length) {
					try {
						obj = Utils.readObject(resp, 5, len);
					} catch (ReadObjectException e) {
						logger.log(Level.WARNING, "unserializable error! try read as string");
						obj = new String(resp, 5, Math.min(resp.length-5, len), RequestHandler.VALUE_CHARSET);
					}
				}
				return obj;
			}
			
		});
		
		return obj;
	}
	
	/**
	 * 取 string 值。
	 */
	public String getString(String key) {
		
		Object obj = request(new KeyOnlyRequestHandler(Command.GET, key), new IntValueResponseHandler() {

			public Object readFormResponse(byte[] resp) throws IOException {
				Object obj = null;
				int len = (Integer) super.readFormResponse(resp);
				if(len > 0 && len < resp.length) {
					obj = new String(resp, 5, Math.min(resp.length-5, len), RequestHandler.VALUE_CHARSET);
				}
				return obj;
			}
			
		});
		
		if(obj == null) {
			return null;
		}
		
		return obj.toString();
	}
	
	
	/**
	 * 取多个 obj 值。
	 */
	public Map<String, Object> mget(final String ... keys) {
		@SuppressWarnings("unchecked")
		Map<String, Object> mv = (Map<String, Object>) request(new MKeyRequestHandler(Command.MGET, keys), new IntValueResponseHandler() {

			public Object readFormResponse(byte[] resp) throws IOException {
				int vn = (Integer) super.readFormResponse(resp);
				if(vn < 1) {
					return null;
				}
				int idx = 5;
				Map<String, Object> mv = new LinkedHashMap<String, Object>();
				for(int i=0; i<vn; i++) {
					int kLen = Utils.byteToInt(resp, idx, 4);
					int vLen = Utils.byteToInt(resp, idx+4, 4);
					idx += 8;
					
					String key = new String(resp, idx, kLen, RequestHandler.KEY_CHARSET);
					idx += kLen;
					
					try {
						Object obj = Utils.readObject(resp, idx, vLen);
						mv.put(key, obj);
					} catch (ReadObjectException e) {
						logger.log(Level.WARNING, "unserializable error!", e);
					}
					
					idx += vLen;	//下一次的开始
				}
				
				return mv;
			}
			
		});
		
		return mv;
	}
	
	/**
	 * 取多个 string 值。
	 */
	public Map<String, String> mgetString(final String ... keys) {
		@SuppressWarnings("unchecked")
		Map<String, String> mv = (Map<String, String>) request(new MKeyRequestHandler(Command.MGET, keys), new IntValueResponseHandler() {

			public Object readFormResponse(byte[] resp) throws IOException {
				int vn = (Integer) super.readFormResponse(resp);
				if(vn < 1) {
					return null;
				}
				int idx = 5;
				Map<String, String> mv = new LinkedHashMap<String, String>();
				for(int i=0; i<vn; i++) {
					int kLen = Utils.byteToInt(resp, idx, 4);
					int vLen = Utils.byteToInt(resp, idx+4, 4);
					idx += 8;
					
					String key = new String(resp, idx, kLen, RequestHandler.KEY_CHARSET);
					idx += kLen;
					
					String obj = new String(resp, idx, vLen, RequestHandler.VALUE_CHARSET);
					mv.put(key, obj);
					
					idx += vLen;	//下一次的开始
				}
				
				return mv;
			}
			
		});
		
		return mv;
	}
	
	/**
	 * 写一个 object.
	 */
	public boolean put(String key, Object value) {
		return request(new PutObjectRequestHandler(Command.PUT, key, value));
	}
	
	public boolean putString(String key, String value) {
		return request(new PutStringRequestHandler(Command.PUT, key, value));
	}
	
	/**
	 * 没有才添加
	 * @return true 之前没有，并且添加了。
	 * @author chenlb 2009-6-17 下午05:48:56
	 */
	public boolean putkeep(String key, Object value) {
		return request(new PutObjectRequestHandler(Command.PUTKEEP, key, value)); 
	}
	
	public boolean putkeepString(String key, String value) {
		return request(new PutStringRequestHandler(Command.PUTKEEP, key, value));
	}
	
	/**
	 * 追加内容
	 */
	public boolean putcat(String key, String value) {
		return request(new PutStringRequestHandler(Command.PUTCAT, key, value)); 
	}
	
	/**
	 * 在后面添加后，再从最后面向前取 width 个字节保存下来。
	 * @author chenlb 2009-6-17 下午05:32:42
	 */
	public boolean putshl(final String key, final String value, final int width) {
		return request(new CommandRequestHandler(Command.PUTSHL) {

			protected void writeExtToRequest(ByteArrayOutputStream wbos) throws IOException {
				
				byte[] ks = key.getBytes(RequestHandler.KEY_CHARSET);
				wbos.write(Utils.intToBytes(ks.length));	//key len
				
				byte[] vs = value.getBytes(RequestHandler.VALUE_CHARSET);
				
				wbos.write(Utils.intToBytes(vs.length));	//vaule len
				
				wbos.write(Utils.intToBytes(width));
				
				wbos.write(ks);	//key byte
				wbos.write(vs);	//vaule byte
			}
			
		}); 
	}
	
	/**
	 * 不返回状态地添加。
	 * @author chenlb 2009-7-8 下午06:10:43
	 */
	public void putnr(String key, Object value) {
		requestBoolean(new PutObjectRequestHandler(Command.PUTNR, key, value), null); 
	}
	
	public void putnrString(String key, String value) {
		requestBoolean(new PutStringRequestHandler(Command.PUTNR, key, value), null); 
	}
	
	/**
	 * 删除
	 * @author chenlb 2009-7-8 下午06:11:15
	 */
	public boolean out(String key) {
	
		return request(new KeyOnlyRequestHandler(Command.OUT, key));
	}
	
	/**
	 * 为 key 增加 n 数量。
	 * @param n 增量
	 * @return 增加的值。
	 */
	public Integer addInt(final String key, final int n) {
		Integer num = (Integer) request(new CommandRequestHandler(Command.ADDINT) {

			protected void writeExtToRequest(ByteArrayOutputStream wbos) throws IOException {
				byte[] ks = key.getBytes(RequestHandler.KEY_CHARSET);
				wbos.write(Utils.intToBytes(ks.length));
				
				wbos.write(Utils.intToBytes(n));
				
				wbos.write(ks);	//key value
			}
			
		}, new IntValueResponseHandler());
		
		return num !=null ? num : 0;
	}
	
	public boolean putInt(String key, int n) {
		Integer on = getInt(key);
		Integer num = addInt(key, n-on);
		return num != null && n == num;
	}
	
	public Integer getInt(String key) {
		return addInt(key, 0);
	}
	
	/**
	 * 返回 key 命中的值的大小（字节）
	 * @return 没命中返回 0
	 * @author chenlb 2009-6-17 下午06:07:31
	 */
	public int vsiz(String key) {
		Integer num = (Integer) request(new KeyOnlyRequestHandler(Command.VSIZ, key), new IntValueResponseHandler());
		
		return num != null ? num : 0;
	}
	
	/**
	 * 遍历前的初始化，游标回到起始位置。
	 * @return
	 * @author chenlb 2009-7-8 下午06:12:45
	 */
	public boolean iterinit() {
		return request(new CommandRequestHandler(Command.ITERINIT));
	}
	
	/**
	 * 按游标取 key。完成会，游标指到下一位置。
	 * @return key
	 * @author chenlb 2009-7-8 下午06:13:45
	 */
	public String iternext() {
		String key = null;
		
		key = (String) request(new CommandRequestHandler(Command.ITERNEXT), new StringValueResponseHandler());
		
		return key;
	}
	
	/**
	 * @return tt server 的记录数。
	 * @author chenlb 2009-6-16 下午02:10:24
	 */
	public long rnum() {
		Long ln = (Long) request(new CommandRequestHandler(Command.RNUM), new LongValueResponseHandler());
		
		if(ln == null) {
			return 0;
		}
		
		return ln;
	}
	
	/**
	 * @return tt server 占用的空间大小(字节)。
	 * @author chenlb 2009-6-16 下午02:17:13
	 */
	public long size() {
		Long ln = (Long) request(new CommandRequestHandler(Command.SIZE), new LongValueResponseHandler());
		
		if(ln == null) {
			return 0;
		}
		
		return ln;
	}
	
	/**
	 * ttserver 的统计信息
	 */
	public String stat() {
		String stats = (String) request(new CommandRequestHandler(Command.STAT), new StringValueResponseHandler());
		
		return stats;
	}
	
	public void close() {
		try {
			server.close();
		} catch (IOException e) {
			
		}
	}
}
