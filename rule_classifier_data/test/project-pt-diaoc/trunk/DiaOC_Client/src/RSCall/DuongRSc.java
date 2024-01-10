/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RSCall;

import Entities.DUONG;
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
public final class DuongRSc {
    
    String mHost = Utilities.Utilities.host;
    String mPort = Utilities.Utilities.port;
    final Client client = ClientBuilder.newClient();
    final String URI = "http://"+mHost+":"+mPort+"/DiaOC_WebServ/Resources/DUONG/";
    WebTarget DUONG_Resource;
    
    // Mảng quản lý toàn bộ bảng DUONG
    public ArrayList<DUONG> mainList = new ArrayList<DUONG>();
    
    // Constructor
    public DuongRSc() throws Exception{
        DUONG_Resource = client.target(URI);
        mainList = this.GetAllRows();
    }

    // Trả về toàn bộ bảng DUONG
    public ArrayList<DUONG> GetAllRows() throws Exception {
        try {
            ArrayList<DUONG> l = DUONG_Resource.path("GetAllRows").
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<DUONG>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Thêm một đối tượng
    public String Insert(String tenDuong)throws Exception{
        try {
            String s = DUONG_Resource.path("Insert").queryParam("tenDuong", tenDuong).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        } 
    }
    
    // Sửa một đối tượng
    public String Update(String maDuong, String tenDuong) throws Exception{
        try {
            String s = DUONG_Resource.path("Update").queryParam("maDuong", maDuong).
                    queryParam("tenDuong", tenDuong).request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Xóa một đối tượng DUONG
    public Boolean Delete(String maDuong)throws Exception{
        try {
            String s = DUONG_Resource.path("Delete").queryParam("maDuong", maDuong).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }

    // Trả về một record DUONG co MaDuong = maDuong
    public DUONG GetRowByID(String maDuong)throws Exception{
        try {
            DUONG obj = DUONG_Resource.path("GetRowByID").queryParam("maDuong", maDuong).
                    request(MediaType.APPLICATION_XML).get(DUONG.class);
            return obj;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    public boolean ValidationID(String maDuong) throws Exception {
        try {
            String s = DUONG_Resource.path("ValidationID").queryParam("maDuong", maDuong).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
}
