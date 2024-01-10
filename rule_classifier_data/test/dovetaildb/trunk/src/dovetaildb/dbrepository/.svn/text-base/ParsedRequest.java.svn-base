package dovetaildb.dbrepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import dovetaildb.Action;
import dovetaildb.api.ApiException;
import dovetaildb.util.Util;

public class ParsedRequest {
	private String db;
	private String bagName;
	private String id;
	private Object entry;
	private Action action;
	private String functionName;
	private String code;
	private Object query;
	private String options;
	private String arguments;
	private HttpServletRequest request;
	private Map<String, String> parameterMap;
	private HttpServletResponse response;

	
	static final Pattern slashRegexp = Pattern.compile("/+");
	static final Map<String,Action> cannonicalActionStrings;
	static {
		cannonicalActionStrings = new HashMap<String,Action>();
		cannonicalActionStrings.put("query",Action.query);
		cannonicalActionStrings.put("execute",Action.execute);
		cannonicalActionStrings.put("put",Action.put);
		cannonicalActionStrings.put("remove",Action.remove);
		cannonicalActionStrings.put("call",Action.call);
	}
	
	class MyReader extends StringReader {
		JSONParser parser;
		boolean atEnd = false;
		MyReader(String s) {
			super(s);
			parser = new JSONParser();
		}
		public String readUntil(char end) {
			if (atEnd) return null;
			try {
				int ch;
				StringBuffer buf = new StringBuffer();
				while(true) {
					ch = this.read();
					if (ch == end) break;
					if (ch == -1) {atEnd=true; break;}
					buf.append((char)ch);
				}
				return URLDecoder.decode(buf.toString(), "UTF-8");
			} catch(IOException e) {
				throw new RuntimeException(e);
			}
		}
		public String readAll() {
			if (atEnd) return null;
			try {
				int ch;
				StringBuffer buf = new StringBuffer();
				while(true) {
					ch = this.read();
					if (ch == -1) {atEnd=true; break;}
					buf.append((char)ch);
				}
				return URLDecoder.decode(buf.toString(), "UTF-8");
			} catch(IOException e) {
				throw new RuntimeException(e);
			}
		}
		public Object readItem() {
			try {
				try {
					Object ret = parser.parse(this);
					int ch = read();
					if (ch != -1 && ch != '/') {
						throw new ApiException("InvalidRequestUrl", "Url request is invalid");
					}
					return ret;
				} catch (ParseException e) {
					throw new ApiException("InvalidRequestUrl", "Url request is invalid");
				}
			} catch(IOException e) {
				throw new RuntimeException(e);
			}
		}
		public int peek() {
			if (atEnd) return -1;
			try {
				mark(1);
				char ch = (char)this.read();
				reset();
				return ch;
			} catch(IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public ParsedRequest(String url, boolean insertOrUpdate, HttpServletRequest request, HttpServletResponse response, Map<String, String> params) {
		parameterMap = params;
		MyReader reader = new MyReader(url);
		char firstChar = ' ';
		try {
			firstChar = (char)reader.read();
		} catch(IOException e) {}
		if (firstChar != '/') {
			throw new ApiException("InvalidRequestUrl", "Url request is invalid");
		}
		db = reader.readUntil('/');
		String actionString = reader.readUntil('/');
		action = cannonicalActionStrings.get(actionString);
		if (action == null) {
			throw new ApiException("UnsupportedAction", "Unsupported action: \""+actionString+"\"");
		}
		this.request = request;
		this.response = response;
		parseUsingMap(parameterMap);
	}
//	private Object produceValue(String specifier, int idx, Object cur) {
//		int firstClose = specifier.indexOf(']');
//		if (firstClose <= 0) {
//			if (firstClose == -1) {
//				throw new ApiException("InvalidRequestUrl", "Invalid parameter key: "+specifier);
//			} else {
//				// array
//				ArrayList<Object> array;
//				if (cur == null) {
//					cur = array = new ArrayList<Object>();
//				} else if (!(cur instanceof ArrayList)) {
//					throw new ApiException("InvalidRequestUrl", "Invalid parameter key: "+specifier);
//				} else {
//					array = (ArrayList<Object>) cur;
//				}
//			}
//		}
//		
//		String key = specifier.substring(0, firstClose);
//		
//	}
	
//	private Map<String,Object> deepMap(HttpServletRequest request2) {
//		Map<String,Object> root = Util.literalSMap();
//		Object cur;
//		for(Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
//			cur = root;
//			String specifier = e.nextElement();
//			int firstOpen = specifier.indexOf('[');
//			if (firstOpen == -1) {
//				root.put(specifier, request.getParameter(specifier));
//			} else {
//				String key = specifier.substring(0,firstOpen);
//				Object val = (root.containsKey(key)) ? root.get(key) : null;
//				Object result = produceValue(specifier, firstOpen, val);
//				if (! root.containsKey(key)) {
//					root.put(key, cur=)
//					cur = root.get(key);
//				}
//				cur = root.get();
//			}
//		}
//		return null;
//	}
	
	public static String chkGet(Map<String,String> map, String key) {
		String val = map.get(key);
		if (val == null) throw new ApiException("MissingParameter", "A parameter named \""+key+"\" is required in this call");
		return val;
	}

	private void parseUsingMap(Map map) {
		this.setRequest(request);
		switch(action) {
		case put:
		case remove:
		case query:
			bagName = chkGet(map,"bag");
			switch(action) {
			case query:
				String queryString = chkGet(map,"query");
				try {
					query = Util.jsonDecode(queryString);
				} catch(Util.JsonParseRuntimeException e) {
					throw new ApiException("InvalidQuery", "Query is not JSON decodable: "+ e.getMessage());
				}
				options = (String)map.get("options");
				break;
			case put:
				String entryString = chkGet(map,"entry");
				try {
					entry = Util.jsonDecode(entryString);
				} catch(Util.JsonParseRuntimeException e) {
					throw new ApiException("InvalidEntry", "Entry is not JSON decodable: "+ e.getMessage());
				}
				break;
			case remove:
				id = chkGet(map,"id");
				break;
			}
			break;
		case call:
			bagName = null;
			functionName = chkGet(map,"function");
			arguments = chkGet(map,"args");
			break;
		case execute:
			code = chkGet(map,"code");
			break;
		}
	}
	
	public ParsedRequest(String db, String action, String bag, String query, String id, String entry, String method, String args, String code) {
		parameterMap = new HashMap();
		this.db = db;
		this.action = cannonicalActionStrings.get(action);
		this.bagName = bag;
		if (query != null)
			this.query = Util.jsonDecode(query);
		this.id = id;
		if (entry != null)
			this.entry = Util.jsonDecode(entry);
		this.functionName = method;
		this.arguments = args;
		this.code = code;
	}

	public static ParsedRequest makePut(String db, String bag, String entry) {
		return new ParsedRequest(db, "put", bag, null, null, entry, null, null, null);
	}
	
	public static ParsedRequest makeRemove(String db, String bag, String id) {
		return new ParsedRequest(db, "remove", bag, null, id, null, null, null, null);
	}
	
	public static ParsedRequest makeQuery(String db, String bag, String query) {
		return new ParsedRequest(db, "query", bag, query, null, null, null, null, null);
	}
	
	public static ParsedRequest makeCall(String db, String method, String args) {
		return new ParsedRequest(db, "call", null, null, null, null, method, args, null);
	}
	
	public static ParsedRequest makeExecute(String db, String code) {
		return new ParsedRequest(db, "execute", null, null, null, null, null, null, code);
	}

	public void setDb(String db) {
		this.db = db;
	}

	public String getDb() {
		return db;
	}

	public void setBagName(String bagName) {
		this.bagName = bagName;
	}

	public String getBagName() {
		return bagName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public Object getEntry() {
		return entry;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Action getAction() {
		return action;
	}

	public String getActionString() {
		return action.toString();
	}

	public void setFunctionName(String methodName) {
		this.functionName = methodName;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Object getQuery() {
		return query;
	}

	public void setArguments(String arguments) {
		this.arguments = arguments;
	}

	public String getArguments() {
		return arguments;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public Object getParameter(String name) {
		return parameterMap.get(name);
	}

	public void setParameter(String name, String value) {
		parameterMap.put(name, value);
	}
	public Map<String, String> getParameterMap() {
		return parameterMap;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public Map<String, Object> getOptionsAsMap() {
		String optionStr = getOptions();
		if (optionStr == null) return Util.EMPTY_SMAP;
		return (Map<String, Object>) Util.jsonDecode(optionStr);
	}
}
