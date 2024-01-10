package br.com.dyad.client;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class ClientServerException extends Exception implements IsSerializable {

	public static Integer UNEXPECTED_EXCEPTION = 1;
	public static Integer TREATED_EXCEPTION = 2;
	public static Integer INVALID_SESSION = 3;
	
	protected String detailedError;
	protected String fullStackTrace;
	protected Throwable cause = this;	
	protected Integer type = null; 
	
	public ClientServerException(){
		super();
	}
	
	public ClientServerException( String message){
		super(message);
	}
	
	public String getDetailedError() {
		return detailedError;
	}

	public void setDetailedError(String detailedError) {
		this.detailedError = detailedError;
	}

	public String getFullStackTrace() {
		return fullStackTrace;
	}
	
	@Override
	public String getMessage() {		
		return getDetailedError();
	}

	public void setFullStackTrace(String stackTrace) {
		this.fullStackTrace = stackTrace;
	}

	public static ClientServerException createException(String error) {
		ClientServerException e = new ClientServerException();
		e.setDetailedError(error);
		return e;
	}

	public static ClientServerException createException(Exception error, String stackTraceString ) {
		ClientServerException e = new ClientServerException(error.getMessage());		
		e.setCause(error.getCause());
		
		if ( error instanceof AppException || 
				( error instanceof ClientServerException && ((ClientServerException)error).getType() == ClientServerException.TREATED_EXCEPTION ) ){
			e.type = ClientServerException.TREATED_EXCEPTION;
		} else if (error instanceof ClientServerException && ((ClientServerException)error).getType() == ClientServerException.INVALID_SESSION) {
			e.type = ClientServerException.INVALID_SESSION;
	    } else{
			e.type = ClientServerException.UNEXPECTED_EXCEPTION;
		}
		
		String stack = "";
		if ( stackTraceString == null ){
			if (error.getStackTrace() != null){			
				for (StackTraceElement elem : error.getStackTrace()) {
					stack += "<br> File: " + elem.getFileName() +
					" Class: " + elem.getClassName() +
					" Method: " + elem.getMethodName() +
					" Line number: " + elem.getLineNumber();

				}
			}
		} else {
			stack = stackTraceString;
		}
			
		e.setFullStackTrace(stack);
		if ( e.getStackTrace() == null ){
			e.setStackTrace(error.getStackTrace());
		}
		e.setDetailedError(error.getMessage());	
		return e;
	}

	@Override
	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
