/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RSCall;

import Entities.GIATIEN_TOBUOM;
import java.util.ArrayList;
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
public final class GiaTienQCToBuomRSc {
    
    String mHost = Utilities.Utilities.host;
    String mPort = Utilities.Utilities.port;
    final Client client = ClientBuilder.newClient();
    final String URI = "http://"+mHost+":"+mPort+"/DiaOC_WebServ/Resources/GIATIENTOBUOM/";
    WebTarget GTTOBUOM_Resource;
    
    // Mảng quản lý toàn bộ bảng GIATIEN_TOBUOM
    public ArrayList<GIATIEN_TOBUOM> mainList = new ArrayList<GIATIEN_TOBUOM>();
    
    // Constructor
    public GiaTienQCToBuomRSc() throws Exception{
        GTTOBUOM_Resource = client.target(URI);
        mainList = this.GetAllRows();
    }
    
    // Trả về toàn bộ bảng
    public ArrayList<GIATIEN_TOBUOM> GetAllRows()throws Exception{
        try {
            ArrayList<GIATIEN_TOBUOM> l = GTTOBUOM_Resource.path("GetAllRows").
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<GIATIEN_TOBUOM>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Thêm một đối tượng
    public String Insert(int soLuong, Float giaTien)throws Exception{
        try {
            String s = GTTOBUOM_Resource.path("Insert").
                    queryParam("soLuong", soLuong).queryParam("giaTien", giaTien).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
        
    }
    
    // Sửa một đối tượng
    public String Update(String maGiaTien, int soLuong, Float giaTien)throws Exception{
        try {
            String s = GTTOBUOM_Resource.path("Update").queryParam("maGiaTien", maGiaTien).
                    queryParam("soLuong", soLuong).queryParam("giaTien", giaTien).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String maGiaTien)throws Exception{
        try {
            String s = GTTOBUOM_Resource.path("Delete").queryParam("maGiaTien", maGiaTien).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Trả về một đối tượng GIATIEN_TOBUOM có MaGiaTien = maGiaTien
    public GIATIEN_TOBUOM GetRowByID(String maGiaTien)throws Exception{
        try {
            GIATIEN_TOBUOM obj = GTTOBUOM_Resource.path("GetRowByID").queryParam("maGiaTien", maGiaTien).
                    request(MediaType.APPLICATION_XML).get(GIATIEN_TOBUOM.class);
            return obj;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Hàm kiểm tra MaGiaTien đã tồn tại hay chưa
    public boolean ValidationID(String maGiaTien) throws Exception{
        try {
            String s = GTTOBUOM_Resource.path("ValidationID").queryParam("maGiaTien", maGiaTien).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Trả về GiaTien tương ứng với Soluong
    public float GetGiaBySoLuong(int soLuong)throws Exception{
        try {
            String s = GTTOBUOM_Resource.path("GetGiaBySoLuong").queryParam("soLuong", soLuong).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return Float.parseFloat(s);
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Trả về MaGiaTien tương ứng với Soluong
    public String GetIDBySoLuong(int soLuong)throws Exception{
        try {
            String s = GTTOBUOM_Resource.path("GetIDBySoLuong").queryParam("soLuong", soLuong).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
}
