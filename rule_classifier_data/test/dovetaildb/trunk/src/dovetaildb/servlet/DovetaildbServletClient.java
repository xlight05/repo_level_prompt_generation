package dovetaildb.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import dovetaildb.util.Util;

public abstract class DovetaildbServletClient {

	protected String urlBase;
	protected String fixedQueryString;
	
	public String getUrlBase() {
		return urlBase;
	}

	public void setUrlBase(String urlBase) {
		this.urlBase = urlBase;
	}

	public String getFixedQueryString() {
		return fixedQueryString;
	}

	public void setFixedQueryString(String fixedQueryString) {
		this.fixedQueryString = fixedQueryString;
	}

	public DovetaildbServletClient(String urlBase) {
		this.urlBase = urlBase;
	}
	
	public void setAccessKey(String accesskey) {
		try {
			fixedQueryString = "accesskey="+URLEncoder.encode(accesskey,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected Object repoRequest(String method, Object[] args) {
	    StringBuffer code = new StringBuffer("repo."+method+"(");
	    boolean useComma = false;
	    for(Object arg : args) {
		    if (useComma) {
		    	code.append(',');
		    }
		    useComma = true;
		    code.append(Util.jsonEncode(arg));
	    }
    	code.append(')');
		String body = execute(code.toString());
	    Object ret = Util.jsonDecode(body);
		return ((Map)ret).get("result");
	}
	
	public String execute(String code) {
		try {
			URL url = new URL(urlBase + "/_meta/execute");
		    HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
	    	byte[] bytes = (fixedQueryString+"&code="+URLEncoder.encode(code.toString(), "UTF-8")).getBytes("UTF-8");
		    urlConn.setFixedLengthStreamingMode(bytes.length);
		    urlConn.setDoInput(true);
		    urlConn.setDoOutput(true);
		    urlConn.setUseCaches(false);
		    urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		    urlConn.setRequestProperty("Connection", "close");
//	    	urlConn.setRequestProperty("Content-Length", Integer.toString(bytes.length));
	    	OutputStream out = urlConn.getOutputStream();
	    	out.write(bytes);
		    out.flush();
		    out.close();
		    // Get response data.
		    try {
		    	String body = Util.readFully(urlConn.getInputStream());
		    	return body;
		    } catch(IOException e) {
			    InputStream errorStream = urlConn.getErrorStream();
			    if (errorStream != null) {
			    	throw new RuntimeException(Util.readFully(errorStream));
			    } else {
			    	throw e;
			    }
		    }
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
