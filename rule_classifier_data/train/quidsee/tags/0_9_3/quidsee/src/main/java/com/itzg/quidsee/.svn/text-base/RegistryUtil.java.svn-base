package com.itzg.quidsee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistryUtil {
	private static Logger logger = Logger.getLogger(RegistryUtil.class.getName());
	public interface LineConsumer {
		void consume(String line);
	}
	
	public static boolean regAdd(String registryKey, String valueName, String data) {
		ProcessBuilder pb = new ProcessBuilder("reg", "add", registryKey, "/v", valueName, "/d", data, "/f");
		pb.redirectErrorStream(true);
		try {
			Process process = pb.start();
			InputStream procOut = process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(procOut));
			try {
				String line;
				while ((line = reader.readLine()) != null) {
					if (line.contains("success")) {
						return true;
					}
				}
			} finally {
				reader.close();
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Unexpected error", e);
		}
		
		return false;
	}

	public static boolean regQuery(String registryKey, String valueName, LineConsumer consumer) {
		ProcessBuilder pb = new ProcessBuilder("reg", "query", registryKey, "/v", valueName);
		pb.redirectErrorStream(true);
		try {
			Process process = pb.start();
			InputStream procOut = process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(procOut));
			try {
				String line;
				while ((line = reader.readLine()) != null) {
					if (consumer != null) {
						consumer.consume(line);
					}
				}
				return true;
			}
			finally {
				reader.close();
				procOut.close();
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Unexpected error", e);
		}
		return false;
	}
	public static boolean regDelete(String registryKey, String valueName) {
		ProcessBuilder pb = new ProcessBuilder("reg", "delete", registryKey, "/v", valueName, "/f");
		pb.redirectErrorStream(true);
		try {
			Process process = pb.start();
			InputStream procOut = process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(procOut));
			try {
				String line;
				while ((line = reader.readLine()) != null) {
					if (line.contains("success")) {
						return true;
					}
				}
			} finally {
				reader.close();
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Unexpected error", e);
		}
		
		return false;
	}

}
