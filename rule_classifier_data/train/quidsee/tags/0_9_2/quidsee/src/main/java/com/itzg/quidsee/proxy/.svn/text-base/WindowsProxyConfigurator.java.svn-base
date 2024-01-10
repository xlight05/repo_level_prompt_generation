package com.itzg.quidsee.proxy;

import java.io.File;
import java.io.IOException;

public class WindowsProxyConfigurator extends ProxyConfigurator {
	static {
		// loads the platform specific lib
		try {
			System.out.println("The arch is "+System.getProperty("os.arch"));
			System.loadLibrary("proxyconfig");
			System.out.println("Loaded library");
		} catch (UnsatisfiedLinkError e) {
			// TODO Auto-generated catch block
			try {
				System.err.println("Couldn't find proxyconfig in "+new File(".").getCanonicalPath());
				e.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public int savedFlags;
	public String savedAutoConfigURL;


	public int getSavedFlags() {
		return savedFlags;
	}
	
	public String getSavedAutoConfigURL() {
		return savedAutoConfigURL;
	}
	
	@Override
	public String toString() {
		return "WindowsProxyConfigurator:[savedFlags="+savedFlags+",url="+savedAutoConfigURL+"]";
	}
	
	public native boolean setAutoConfigUrl(String url);

	@Override
	public native void restoreAutoConfigUrl();
}
