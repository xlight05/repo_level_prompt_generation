/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RSCall;

import Entities.DUONG;
import Entities.PHUONG;
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
public final class PhuongRSc {
    
    String mHost = Utilities.Utilities.host;
    String mPort = Utilities.Utilities.port;
    final Client client = ClientBuilder.newClient();
    final String URI = "http://"+mHost+":"+mPort+"/DiaOC_WebServ/Resources/PHUONG/";
    WebTarget PHUONG_Resource;
    
    // Mảng quản lý toàn bộ bảng PHUONG
    public ArrayList<PHUONG> mainList = new ArrayList<PHUONG>();
    
    // Constructor
    public PhuongRSc() throws Exception{
        PHUONG_Resource = client.target(URI);
        mainList = this.GetAllRows();
    }

    // Trả về toàn bộ bảng PHUONG
    public ArrayList<PHUONG> GetAllRows() throws Exception {
        try {
            ArrayList<PHUONG> l = PHUONG_Resource.path("GetAllRows").
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<PHUONG>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Thêm một đối tượng
    public String Insert(String tenPhuong)throws Exception{
        try {
            String s = PHUONG_Resource.path("Insert").queryParam("tenPhuong", tenPhuong).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        } 
    }
    
    // Sửa một đối tượng
    public String Update(String maPhuong, String tenPhuong) throws Exception{
        try {
            String s = PHUONG_Resource.path("Update").queryParam("maPhuong", maPhuong).
                    queryParam("tenPhuong", tenPhuong).request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Xóa một đối tượng PHUONG
    public Boolean Delete(String maPhuong)throws Exception{
        try {
            String s = PHUONG_Resource.path("Delete").queryParam("maPhuong", maPhuong).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }

    // Trả về một record PHUONG co MaPhuong = maPhuong
    public PHUONG GetRowByID(String maPhuong)throws Exception{
        try {
            PHUONG obj = PHUONG_Resource.path("GetRowByID").queryParam("maPhuong", maPhuong).
                    request(MediaType.APPLICATION_XML).get(PHUONG.class);
            return obj;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    public boolean ValidationID(String maPhuong) throws Exception {
        try {
            String s = PHUONG_Resource.path("ValidationID").queryParam("maPhuong", maPhuong).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
}
