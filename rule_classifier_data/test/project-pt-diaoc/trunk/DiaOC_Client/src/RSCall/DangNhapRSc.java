/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RSCall;

import java.util.ArrayList;
import Entities.DANGNHAP;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
/**
 *
 * @author VooKa
 */
public class DangNhapRSc {
    
    String mHost = Utilities.Utilities.host;
    String mPort = Utilities.Utilities.port;
    final Client client = ClientBuilder.newClient();
    final String URI = "http://"+mHost+":"+mPort+"/DiaOC_WebServ/Resources/DANGNHAP/";
    WebTarget DANGNHAP_Resource = client.target(URI);

    
    public ArrayList<DANGNHAP> GetAllRows()throws Exception{
        try {
            ArrayList<DANGNHAP> l = DANGNHAP_Resource.path("GetAllRows").
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<DANGNHAP>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }

    public boolean Insert(String user, String pass)throws Exception{
        try {
            String s = DANGNHAP_Resource.path("Insert").queryParam("user", user).queryParam("pass", pass).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }

    public boolean Update(int id, String user, String pass)throws Exception{
        try {
            String s = DANGNHAP_Resource.path("Update").queryParam("id", id).queryParam("user", user).
                    queryParam("pass", pass).request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }

    public boolean Delete(int id)throws Exception{
        try {
            String s = DANGNHAP_Resource.path("Delete").queryParam("id", id).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }

    public boolean CheckDangNhap(String user,String pass)throws Exception{
        try {
            String s = DANGNHAP_Resource.path("CheckDangNhap").queryParam("user", user).
                    queryParam("pass", pass).request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }

    public boolean IsExistUserName_DangNhap(String user)throws Exception{
        try {
            String s = DANGNHAP_Resource.path("IsExistUserName_DangNhap").queryParam("user", user).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }

}
