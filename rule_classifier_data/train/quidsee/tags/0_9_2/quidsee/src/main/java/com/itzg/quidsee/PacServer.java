package com.itzg.quidsee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PacServer {
	String installed = null;
	
	private Logger logger = Logger.getLogger(PacServer.class.getName());
	
	private File pacFile;

	private String savedValue;

	public void start() {
	}
	
	public void stop() {
	}
	
	public void installPac() {
		logger.info("Installing PAC...");
		String osName = System.getProperty("os.name");
		logger.info("OS name is "+osName);
		if (osName.startsWith("Windows")) {
			installPacForWindows();
		}
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				uninstallPac();
			}
		});
	}
	
	private void installPacForWindows() {
		try {
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

	public void uninstallPac() {
		logger.info("Uninstalling PAC");
		// revert system changes made by install
		if (installed != null) {
			if (installed.equals("Windows")) {
				uninstallPacForWindows();
			}
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

		pacFile.delete();
		logger.info("Uninstalled PAC for Windows");
	}

}
