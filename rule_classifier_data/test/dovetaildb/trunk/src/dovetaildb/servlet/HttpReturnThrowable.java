package dovetaildb.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import dovetaildb.api.ApiException;



public class HttpReturnThrowable extends RuntimeException {

	private static final long serialVersionUID = 8830785031961251854L;
	
	int status;
	List<String> headers;
	String content;
	
	public HttpReturnThrowable(int status, List<String> headers, String content) {
		super(content);
		this.status = status;
		this.headers = headers;
		this.content = content;
	}
	
	public void respond(HttpServletResponse response) {
		for(String header : headers) {
			int colonPos = header.indexOf(":");
			if (colonPos == -1) throw new ApiException("InvalidHttpHeader", "Header must contain a colon character");
			response.addHeader(header.substring(0, colonPos), header.substring(colonPos+1));
		}
		try {
			response.getWriter().write(content);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		response.setStatus(status);
	}
}
