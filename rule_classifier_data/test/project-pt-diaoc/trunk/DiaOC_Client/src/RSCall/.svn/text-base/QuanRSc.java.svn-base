/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RSCall;

import Entities.DUONG;
import Entities.QUAN;
import java.util.ArrayList;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Nixforest
 */
public final class QuanRSc {
    
    String mHost = Utilities.Utilities.host;
    String mPort = Utilities.Utilities.port;
    final Client client = ClientBuilder.newClient();
    final String URI = "http://"+mHost+":"+mPort+"/DiaOC_WebServ/Resources/QUAN/";
    WebTarget QUAN_Resource;
    
    // Mảng quản lý toàn bộ bảng QUAN
    public ArrayList<QUAN> mainList = new ArrayList<QUAN>();
    
    // Constructor
    public QuanRSc() throws Exception{
        QUAN_Resource = client.target(URI);
        mainList = this.GetAllRows();
    }

    // Trả về toàn bộ bảng QUAN
    public ArrayList<QUAN> GetAllRows() throws Exception {
        try {
            ArrayList<QUAN> l = QUAN_Resource.path("GetAllRows").
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<QUAN>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Thêm một đối tượng
    public String Insert(String tenQuan)throws Exception{
        try {
            String s = QUAN_Resource.path("Insert").queryParam("tenQuan", tenQuan).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        } 
    }
    
    // Sửa một đối tượng
    public String Update(String maQuan, String tenQuan) throws Exception{
        try {
            String s = QUAN_Resource.path("Update").queryParam("maQuan", maQuan).
                    queryParam("tenQuan", tenQuan).request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Xóa một đối tượng QUAN
    public Boolean Delete(String maQuan)throws Exception{
        try {
            String s = QUAN_Resource.path("Delete").queryParam("maQuan", maQuan).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }

    // Trả về một record ThanhPho co MaThanhPho = maThanhPho
    public QUAN GetRowByID(String maQuan)throws Exception{
        try {
            QUAN obj = QUAN_Resource.path("GetRowByID").queryParam("maQuan", maQuan).
                    request(MediaType.APPLICATION_XML).get(QUAN.class);
            return obj;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    public boolean ValidationID(String maQuan) throws Exception {
        try {
            String s = QUAN_Resource.path("ValidationID").queryParam("maQuan", maQuan).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
}
