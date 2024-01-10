package com.itzg.quidsee.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MacOsProxyConfigurator extends ProxyConfigurator {
	private static Logger logger = Logger.getLogger(MacOsProxyConfigurator.class.getName());
	
	private String savedAutoProxyUrl;

	@Override
	public boolean setAutoConfigUrl(String url) {
		try {
			NetworkInterface usableInterface = null;
			InetAddress quibidsAddr = InetAddress.getByName("www.quibids.com");
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			while (networkInterfaces.hasMoreElements()) {
				NetworkInterface networkInterface = networkInterfaces.nextElement();
				try {
					if (quibidsAddr.isReachable(networkInterface, 100, 1000)) {
						usableInterface = networkInterface;
						break;
					}
					else if (!networkInterface.isLoopback() && networkInterface.isUp()) {
						if (usableInterface == null) {
							usableInterface = networkInterface;
						}
					}
				} catch (IOException e) {
				}
			}
			
			// Note: usableInterface.name is the low-level device name such as en1
			
			String networkServiceName = deriveNetworkServiceName(usableInterface.getName());
			
			System.out.println("Usable interface is "+usableInterface);
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private String deriveNetworkServiceName(String deviceName) {
		final Pattern serviceNamePattern = Pattern.compile("\\(\\d+\\)\\s+(.*)");
		final Pattern detailsPattern = Pattern.compile("\\(Hardware Port: .+?, Device: (.+?)\\)");
		
		ProcessBuilder pb = new ProcessBuilder("networksetup", "-listnetworkserviceorder");
		try {
			Process process = pb.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			try {
				String line;
				while ((line = reader.readLine()) != null) {
					Matcher serviceMatcher = serviceNamePattern.matcher(line);
					if (serviceMatcher.matches()) {
						line = reader.readLine();
						Matcher detailsMatcher = detailsPattern.matcher(line);
						if (detailsMatcher.matches()) {
							if (detailsMatcher.group(1).equals(deviceName)) {
								return serviceMatcher.group(1);
							}
						}
					}
				}
			}
			finally {
				reader.close();
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Unable to execute networksetup command", e);
		}
		
		return null;
	}

	@Override
	public void restoreAutoConfigUrl() {
		// TODO Auto-generated method stub

	}

}
