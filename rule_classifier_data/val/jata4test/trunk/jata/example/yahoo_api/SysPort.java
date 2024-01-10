package jata.example.yahoo_api;

import java.io.*;
import java.net.*;

import jata.exception.JataException;
import jata.message.Message;
import jata.port.AdapterPackage;
import jata.port.SystemPort;

public class SysPort extends SystemPort{
	public SysPort() throws JataException{
		super();
		addInputType(Message.getICD(QueryMsg.class));
        addOutputType(Message.getICD(ResponseMsg.class));
        this.PortMode = SystemPort.AdapterMode;
	}
	
	public String get(String urlvalue) {
		String inputLine = "";
		String line2 = "";

		try {
			URL url = new URL(urlvalue);

			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			while ((line2 = in.readLine()) != null) {
				inputLine += line2;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(inputLine);

		return inputLine;
	}
	

	@Override
	protected AdapterPackage Adapter(AdapterPackage pkg) throws Exception {
		if (pkg.streamType.equals("java.lang.String"))
        {
			String url = (String)pkg.stream;
            
            return new AdapterPackage(get(url));
        }
        return null;
	}
}
