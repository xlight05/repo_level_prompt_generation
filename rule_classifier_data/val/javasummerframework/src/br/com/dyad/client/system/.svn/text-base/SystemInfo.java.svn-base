package br.com.dyad.client.system;


public class SystemInfo {
	
	private static SystemInfo systemInfo;	
	private String applicationPath;
	private String tempDir;

	public String getTempDir() {
		return tempDir;
	}

	public void setTempDir(String tempDir) {
		this.tempDir = tempDir;
	}	
	
	public String getApplicationPath() {
		return applicationPath;
	}

	public void setApplicationPath(String applicationPath) {
		this.applicationPath = applicationPath;
	}

	public static SystemInfo getInstance(){
		if (SystemInfo.systemInfo == null){			
			SystemInfo.systemInfo = new SystemInfo();
		}						
		
		return SystemInfo.systemInfo;
	}

}
