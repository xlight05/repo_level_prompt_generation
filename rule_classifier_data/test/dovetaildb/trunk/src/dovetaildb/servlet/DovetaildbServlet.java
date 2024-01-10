package dovetaildb.servlet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;


import dovetaildb.api.ApiException;
import dovetaildb.api.ApiService;
import dovetaildb.dbrepository.DbRepository;
import dovetaildb.dbrepository.FileDbRepository;
import dovetaildb.dbrepository.ParsedRequest;
import dovetaildb.util.Dirty;
import dovetaildb.util.Util;

public class DovetaildbServlet extends javax.servlet.http.HttpServlet 
implements javax.servlet.Servlet, Dirty.Listener {

	private static final long serialVersionUID = -8232011496563375902L;
	final int MAX_RESPONSE_LOG_LENGTH = 1024;

	protected DbRepository repo;
	protected AtomicBoolean isDirty = new AtomicBoolean(false);
	
	public DbRepository getRepo() { return repo; }
	public void setRepo(DbRepository repo) {
		this.repo = repo;
		Util.logger.log(Level.CONFIG, "Loaded metadata "+repo);
		repo.setDirtyListener(this);
	}

	public DovetaildbServlet() { super(); }

	@Override
	public void init() {
		String logLevelStr = getServletConfig().getInitParameter("logLevel");
		if (logLevelStr != null) {
			Level logLevel = Level.parse(logLevelStr);
			Util.logger.setLevel(logLevel);
			Util.logger.log(Level.INFO, "Logging at level: "+logLevel);
		}
		String headerFile = getServletConfig().getInitParameter("headerFile");
		if (headerFile == null) {
			throw new RuntimeException("No \"headerFile\" servlet init parameter provided");
		}
		setRepo(FileDbRepository.load(headerFile));
	}

	protected void handle(String url, boolean insertOrUpdate, HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		ParsedRequest req;
		Object ret;
		Map<String, String> params = new HashMap<String,String>();
		try {
			for(Map.Entry entry: (Set<Map.Entry>)request.getParameterMap().entrySet()) {
				String key = (String) entry.getKey();
				String[] vals = (String[])entry.getValue();
				if (vals.length > 1) throw new ApiException("DuplicateParamter","Parameter \""+key+"\" was duplicated");
				params.put(key, vals[0]);
			}
			req = new ParsedRequest(url, insertOrUpdate, request, response, params);
			ret = handle(req, params, response);
			boolean wasDirty = isDirty.getAndSet(false);
			if (wasDirty) {
				repo.force();
				if (Util.logger.isLoggable(Level.FINE)) {
					Util.logger.log(Level.FINE, "Metadata written: "+repo);
				}
			}
		} catch (ApiException e) {
			e.printStackTrace();
			String msg = "Error: "+e.exName+" "+e.getMessage();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.setContentType("text/plain");
			response.getWriter().write(msg);
			response.getWriter().flush();
			return;
		} catch (HttpReturnThrowable e) {
			e.respond(response);
			return;
		}
		shipResponse(request, response, ret, params, url);
	}
	
	public final static Map<String, String> EMPTY_OPTS = Collections.unmodifiableMap(new HashMap<String, String>());
	
	public Object handle(ParsedRequest req) {
		return handle(req, EMPTY_OPTS, null);
	}
	public Object handle(ParsedRequest req, Map<String, String> params, HttpServletResponse response) {
		Object returnVal = null;
		boolean normalFinish = false;
		ApiService api = repo.newSession(req.getDb(), req);
		if (Util.logger.isLoggable(Level.FINEST)) {
			Util.logger.log(Level.FINEST, "ApiService given: "+api);
		}
		if (api == null) {
			throw new ApiException("PermissionDenied","Permission denied");
		}
		try {
			switch(req.getAction()) {
			case call:
				List<Object> args = (List<Object>)Util.jsonDecode(req.getArguments());
				Object[] parsedArgs = new Object[args.size()+1];
				parsedArgs[0] = api;
				int idx=1;
				for(Object arg : args) {
					parsedArgs[idx++] = arg;
				}
				returnVal = repo.invokeFunction(req.getDb(), req.getFunctionName(), parsedArgs);
				break;
			case execute:
				returnVal = repo.executeCode(api, req.getDb(), req.getCode());
				break;
			case query:
				Object query = req.getQuery();
				returnVal = api.query(req.getBagName(), query, req.getOptionsAsMap());
				break;
			case put:
				Object entry = req.getEntry();
				api.put(req.getBagName(), (Map<String,Object>)entry);
				break;
			case remove:
				api.remove(req.getBagName(), req.getId());
				break;
			default:
				throw new RuntimeException();
			}
			api.commit();
			normalFinish = true;
		} finally {
			if (! normalFinish) api.rollback();
		}
		return returnVal;
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handle(request.getPathInfo()+"/remove", false,  request, response);
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handle(request.getPathInfo()+"/put", true, request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handle(request.getPathInfo(), false, request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handle(request.getPathInfo(), false, request, response);
	}
	public static JSONObject toJsonError(Exception e) {
		Throwable cause = e.getCause();
		if (cause == null) cause = e;
		JSONObject error = new JSONObject();
		if (e instanceof ApiException) {
			ApiException apiException = (ApiException)e; 
			error.put("name", apiException.exName);
			error.put("message", apiException.getMessage());
			if (apiException.stackTrace != null)
				error.put("stacktrace", apiException.stackTrace);
		} else {
			error.put("name", e.getClass().toString());
			error.put("message", e.toString());
		}
		org.json.simple.JSONArray jsonTrace = new org.json.simple.JSONArray();
		for(StackTraceElement elem: cause.getStackTrace()) {
			jsonTrace.add(elem.toString());
		}
		error.put("traceback", jsonTrace);
		return error;
	}

	protected void shipResponse(HttpServletRequest request, HttpServletResponse response, Object results, Map<String, String> params, String url) throws ServletException, IOException {
		long t0 = System.currentTimeMillis();
		Map<String,Object> jsonResponse = new HashMap<String,Object>();
		jsonResponse.put("version","1.1");
		String txnId = params.get("reqid");
		if (txnId != null) {
			params.remove("reqid");
			jsonResponse.put("id", txnId);
		}
		try {
			jsonResponse.put("result", results);
		} catch(Exception e) {
			JSONObject error = toJsonError(e);
			jsonResponse.put("error", error);
		}
		ServletOutputStream os = response.getOutputStream();
		os.flush();
		final Writer coreWriter = new BufferedWriter(new OutputStreamWriter(os));
		Writer wtr = coreWriter;
		String jsonCallback = (String) params.get("callback");
		if (jsonCallback != null) {
			params.remove("callback");
			wtr.write(jsonCallback);
			wtr.write("(");
			response.setContentType("text/javascript");
		} else {
			response.setContentType("application/json");
		}
		if (! Util.logger.isLoggable(Level.FINER)) {
			Util.jsonEncode(wtr, jsonResponse);
		} else {
			final StringBuffer logBuffer = new StringBuffer();
			wtr = new Writer() {
				public void close() throws IOException {coreWriter.close();}
				public void flush() throws IOException {coreWriter.flush();}
				public void write(char[] arg0, int arg1, int arg2) throws IOException {
					coreWriter.write(arg0, arg1, arg2);
					if (logBuffer.length() < MAX_RESPONSE_LOG_LENGTH) {
						logBuffer.append(arg0, arg1, arg2);
					}
				}
			};
			Util.jsonEncode(wtr, jsonResponse);
			String ret = (logBuffer.length() > MAX_RESPONSE_LOG_LENGTH) ? 
					logBuffer.substring(0,MAX_RESPONSE_LOG_LENGTH)+"..." : logBuffer.toString();
			t0 = System.currentTimeMillis() - t0;
			Util.logger.log(Level.FINER, url+" params="+request.getParameterMap()+" ms="+t0+" result="+ret);
		}
		if (jsonCallback != null) {
			wtr.write(")");
		}
		wtr.flush();
	}

	@Override
	public void destroy() {
		if (repo != null)
			repo.close();
	}
	public void setDirty() {
		this.isDirty.set(true);
	}

}