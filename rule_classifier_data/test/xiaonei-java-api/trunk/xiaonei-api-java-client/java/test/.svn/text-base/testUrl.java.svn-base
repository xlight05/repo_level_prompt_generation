import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class testUrl {
 public static void main(String args[]) throws IOException{
	 try {
		URL url =new URL("http://api.xiaonei.com/restserver.do");
		 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		 StringBuffer buffer=new StringBuffer();
//		 buffer.append("method=xiaonei.xnim.setStatus&session_key=Qzhoijs9icODtXAes16vJ1i4L81ya6mGknlL/Q8HqQRGeSsGevxlHN9Qj9VmBUMz-267018381, v=1.0, content=123(3)& api_key=ffa4add0b30141aa9eb9a8f1af34a8c3");
//		 buffer.append("&session_key=");
//		 System.out.println(URLEncoder.encode("fWa1W4s19txMEQiJoClkbHIsWdGTXGz5zEZc26\\BZufVEaokX8sLSgMolOn7YDc3h-66271","UTF-8"));
//		 buffer.append(URLEncoder.encode("ziBQFE4lwGsbhzu21gOsN16vACv4u99NSsubINtPy6HIwZhFPX7mNpWjInndEGWY-700001044","UTF8"));
//		 buffer.put();
		 conn.setRequestMethod("POST");
//		 conn.setRequestMethod("GET");
		 conn.setDoOutput(true);
		 conn.connect();
		 buffer.append("method=xiaonei.xnim.getStatus");
		 buffer.append("&session_key=7d6d5936b33b418bbffd1c35565e1412-66271");
		 buffer.append("&v=1.0");
		 buffer.append("&uid=66271");
		 buffer.append("&api_key=652d3bb7a3804f52a3f40cd29ea100a5");
		 buffer.append("&format=xml");
		 buffer.append("&content=TerryTsui");
		 buffer.append("&app_id=9999");
//		 v=1.0&uids1=700001044&uids2=700000524&api_key=106c6586fbfb499dbeb2a414650fd891&method=xiaonei.friends.areFriends&call_id=1236237668514&session_key=bQslLmfacU3zHcKkRYc%2BvnuXdMX%2BWYUQudUusoBQCjMG962TRxSYvo8Xtz5AMWV4-700001044&sig=554dd4c4c6dc43e7dd34d9ae2839ccc0
		 conn.getOutputStream().write(buffer.toString().getBytes());
		 System.out.println(conn.getInputStream());
		 BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		 String line;
	     boolean insideTagBody = false;
	      while ((line = in.readLine()) != null) {
	      System.out.println(line);
	      
	      }
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 }
 }
