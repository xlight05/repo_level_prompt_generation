/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RSCall;

import java.util.ArrayList;
import Entities.THONGKETHANG;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;


public class ThongKeThangRSc {

    String mHost = Utilities.Utilities.host;
    String mPort = Utilities.Utilities.port;
    final Client client = ClientBuilder.newClient();
    final String URI = "http://"+mHost+":"+mPort+"/DiaOC_WebServ/Resources/THONGKETHANG/";
    WebTarget THONGKETHANG_Resource = client.target(URI);
    
    public ArrayList<THONGKETHANG> GetAllRows_Bang()throws Exception{
        try {
            ArrayList<THONGKETHANG> l = THONGKETHANG_Resource.path("GetAllRows_Bang").
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<THONGKETHANG>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }

    public ArrayList<THONGKETHANG> GetAllRows_Bao()throws Exception{
        try {
            ArrayList<THONGKETHANG> l = THONGKETHANG_Resource.path("GetAllRows_Bao").
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<THONGKETHANG>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }

    public ArrayList<THONGKETHANG> GetAllRows_ToBuom()throws Exception{
        try {
            ArrayList<THONGKETHANG> l = THONGKETHANG_Resource.path("GetAllRows_ToBuom").
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<THONGKETHANG>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
}