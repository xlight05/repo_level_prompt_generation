package com.itzg.quidsee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itzg.quidsee.proxy.MacOsProxyConfigurator;
import com.itzg.quidsee.proxy.ProxyConfigurator;

public class PacServer {
	String installed = null;
	
	private Logger logger = Logger.getLogger(PacServer.class.getName());
	
	private File pacFile;

	private String savedValue;
	
	private ProxyConfigurator proxyConfigurator;

	public void start() {
	}
	
	public void stop() {
	}
	
	public void installPac() {		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				uninstallPac();
			}
		});
		
		logger.info("Installing PAC...");
		String osName = System.getProperty("os.name");
		logger.info("OS name is "+osName);
		try {
			createPacFile();
			if (osName.startsWith("Windows")) {
				installPacForWindows();
			}
			else if (osName.equals("Mac OS X")) {
				proxyConfigurator = new MacOsProxyConfigurator();
				proxyConfigurator.setAutoConfigUrl(pacFile.toURI().toString());
				installed = "MacOS";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void installPacForWindows() {
		try {
			String ourPacUrl = pacFile.toURI().toString();
			
			ProcessBuilder pb = new ProcessBuilder("lib-win32/winproxycfg.exe", "set", ourPacUrl);
			pb.redirectErrorStream(true);
			Process process = pb.start();
			InputStream procOut = process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(procOut));
			try {
				String line = reader.readLine();
				if (line != null) {
					savedValue = line;
				}
				else {
					logger.log(Level.WARNING, "Did not get any output from proxy configuration app");
				}
				while (reader.readLine() != null) {}
			} finally {
				reader.close();
			}

			int exitStatus = process.waitFor();
			if (exitStatus != 0) {
				logger.log(Level.SEVERE, "Proxy setting failed ({0})", exitStatus);
			}
			installed = "Windows";
			logger.info("Installed PAC for Windows");
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Failed to setup PAC file", e);
		} catch (NoSuchFieldError e) {
			logger.log(Level.SEVERE, "JNI invocation failed", e);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createPacFile() throws IOException, FileNotFoundException {
		pacFile = File.createTempFile("quidsee-", ".pac");
		InputStream in = PacServer.class.getClassLoader().getResourceAsStream("default.pac");
		OutputStream out = new FileOutputStream(pacFile);
		try {
			int count;
			byte[] buffer = new byte[500];
			while ((count = in.read(buffer)) != -1) {
				out.write(buffer, 0, count);
			}
		} finally {
			in.close();
			out.close();
		}
	}

	public void uninstallPac() {
		logger.info("Uninstalling PAC");
		// revert system changes made by install
		if (installed != null) {
			if (installed.equals("Windows")) {
				uninstallPacForWindows();
			}
			else if (installed.equals("MacOS")) {
				proxyConfigurator.restoreAutoConfigUrl();
			}
		}
		if (pacFile != null) {
			pacFile.delete();
		}
	}

	private void uninstallPacForWindows() {
		if (savedValue != null) {
			ProcessBuilder pb = new ProcessBuilder("lib-win32/winproxycfg.exe", "restore", savedValue);
			try {
				Process process = pb.start();
				int exitStatus = process.waitFor();
				if (exitStatus != 0) {
					logger.log(Level.SEVERE, "Proxy restoration failed ({0})", exitStatus);
				}
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Unable to build process for restoring proxy setting");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		logger.info("Uninstalled PAC for Windows");
	}

}
