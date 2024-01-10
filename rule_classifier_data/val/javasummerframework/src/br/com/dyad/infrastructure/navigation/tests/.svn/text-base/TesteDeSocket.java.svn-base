package br.com.dyad.infrastructure.navigation.tests;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class TesteDeSocket extends Thread{
		
	@SuppressWarnings("unchecked")
	public static void main (String[] args) {
		
		int tempoEmSegundos = 5;
		
		TesteDeSocket teste = new TesteDeSocket();		
		try {
			while ( true ){
				teste.run();
				teste.sleep(1000 * tempoEmSegundos );
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void run() {
		HashMap ipsEPortas = new HashMap();
		ipsEPortas.put("10.85.8.220", new String[]{ "82", "83", "84" } );
		ipsEPortas.put("201.20.2.229", new String[]{ "82", "83", "84" } );

		
		Set<String> ips = ipsEPortas.keySet();
		for (Iterator iterator = ips.iterator(); iterator.hasNext();) {
			String ip = (String) iterator.next();
			
			String[] portas = (String[])ipsEPortas.get(ip);
			for (int i = 0; i < portas.length; i++) {
				Date now = new Date();
				SimpleDateFormat day = new SimpleDateFormat("dd/MM/yyyy");  				  
				SimpleDateFormat hour = new SimpleDateFormat("hh:mm:ss");  				  

				String resultTime = day.format(now) + " " + hour.format(now);	
				 
				try {
					URL u = new URL("http://" + ip + ":" + portas[i] );
					HttpURLConnection con = (HttpURLConnection)u.openConnection();
					con.setRequestMethod("GET");
					con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.0; de-DE; rv:1.7.5) Gecko/20041122 Firefox/1.0");
					
					int code = con.getResponseCode();
				    if ( code != 200 ){
						//throw new Exception("(" + resultTime + ") Engine " + ip + " está inacessível. Código do erro: " + code );
				    	System.err.println("(" + resultTime + ") ERROR: " + ip + ":" + portas[i] + ". Code: " + code );
				    } else {				    	
				    	System.out.println("(" + resultTime + ") OK: " + ip + ":" + portas[i] );
				    }
				} catch (Exception e) {
					//e.printStackTrace();
					System.err.println("(" + resultTime + ") ERROR: " + ip + ":" + portas[i] );
				}			
			}
		}
	}
} 
