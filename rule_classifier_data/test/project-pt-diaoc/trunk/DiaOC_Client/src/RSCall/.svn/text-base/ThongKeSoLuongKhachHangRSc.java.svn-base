/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RSCall;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;


public class ThongKeSoLuongKhachHangRSc {
    
    String mHost = Utilities.Utilities.host;
    String mPort = Utilities.Utilities.port;
    final Client client = ClientBuilder.newClient();
    final String URI = "http://"+mHost+":"+mPort+"/DiaOC_WebServ/Resources/THONGKESLKHACHHANG/";
    WebTarget THONGKESLKHACHHANG_Resource = client.target(URI);

    public int GetSoLuongKH_Bang()throws Exception{
        try {
            String s = THONGKESLKHACHHANG_Resource.path("GetSoLuongKH_Bang").
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return Integer.parseInt(s);
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }

    public int GetSoLuongKH_Bao()throws Exception{
        try {
            String s = THONGKESLKHACHHANG_Resource.path("GetSoLuongKH_Bao").
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return Integer.parseInt(s);
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }

    public int GetSoLuongKH_ToBuom()throws Exception{
        try {
            String s = THONGKESLKHACHHANG_Resource.path("GetSoLuongKH_ToBuom").
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return Integer.parseInt(s);
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
}
