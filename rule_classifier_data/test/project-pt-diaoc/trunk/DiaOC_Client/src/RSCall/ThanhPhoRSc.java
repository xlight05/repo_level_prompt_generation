/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RSCall;

import Entities.THANHPHO;
import Entities.THANHPHO;
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
public final class ThanhPhoRSc {
    
    String mHost = Utilities.Utilities.host;
    String mPort = Utilities.Utilities.port;
    final Client client = ClientBuilder.newClient();
    final String URI = "http://"+mHost+":"+mPort+"/DiaOC_WebServ/Resources/THANHPHO/";
    WebTarget THANHPHO_Resource;
    
    // Mảng quản lý toàn bộ bảng THANHPHO
    public ArrayList<THANHPHO> mainList = new ArrayList<THANHPHO>();
    
    // Constructor
    public ThanhPhoRSc() throws Exception{
        THANHPHO_Resource = client.target(URI);
        mainList = this.GetAllRows();
    }

    // Trả về toàn bộ bảng THANHPHO
    public ArrayList<THANHPHO> GetAllRows() throws Exception {
        try {
            ArrayList<THANHPHO> l = THANHPHO_Resource.path("GetAllRows").
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<THANHPHO>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Thêm một đối tượng
    public String Insert(String tenThanhPho)throws Exception{
        try {
            String s = THANHPHO_Resource.path("Insert").queryParam("tenThanhPho", tenThanhPho).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        } 
    }
    
    // Sửa một đối tượng
    public String Update(String maThanhPho, String tenThanhPho) throws Exception{
        try {
            String s = THANHPHO_Resource.path("Update").queryParam("maThanhPho", maThanhPho).
                    queryParam("tenThanhPho", tenThanhPho).request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Xóa một đối tượng THANHPHO
    public Boolean Delete(String maThanhPho)throws Exception{
        try {
            String s = THANHPHO_Resource.path("Delete").queryParam("maThanhPho", maThanhPho).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }

    // Trả về một record ThanhPho co MaThanhPho = maThanhPho
    public THANHPHO GetRowByID(String maThanhPho)throws Exception{
        try {
            THANHPHO obj = THANHPHO_Resource.path("GetRowByID").queryParam("maThanhPho", maThanhPho).
                    request(MediaType.APPLICATION_XML).get(THANHPHO.class);
            return obj;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    public boolean ValidationID(String maThanhPho) throws Exception {
        try {
            String s = THANHPHO_Resource.path("ValidationID").queryParam("maThanhPho", maThanhPho).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
}
