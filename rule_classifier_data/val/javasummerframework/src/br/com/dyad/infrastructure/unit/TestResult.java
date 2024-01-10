package br.com.dyad.infrastructure.unit;

public class TestResult {
	
	private String clazz;
	private String method;
	private boolean result;
	private Exception exception;
	
	public TestResult(String clazz, String method, boolean result, Exception exception) {
		this.clazz = clazz;
		this.method = method;
		this.result = result;
		this.exception = exception;
	}
		
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public boolean getResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}	
}
