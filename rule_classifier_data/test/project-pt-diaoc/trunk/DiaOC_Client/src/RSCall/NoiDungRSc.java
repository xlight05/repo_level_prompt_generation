/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RSCall;

import java.util.ArrayList;
import Entities.NOIDUNGQC;
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
public final class NoiDungRSc {
    
    String mHost = Utilities.Utilities.host;
    String mPort = Utilities.Utilities.port;
    final Client client = ClientBuilder.newClient();
    final String URI = "http://"+mHost+":"+mPort+"/DiaOC_WebServ/Resources/NOIDUNGQC/";
    WebTarget NOIDUNG_Resource;
    
    // Mảng quản lý toàn bộ các đối tượng trong bảng NOIDUNGQC
    public ArrayList<NOIDUNGQC> mainList = new ArrayList<NOIDUNGQC>();
    
    // Constructor
    public NoiDungRSc() throws Exception{
        NOIDUNG_Resource = client.target(URI);
        mainList = this.GetAllRows();
    }
    
    // Trả về toàn bộ bảng NOIDUNGQC
    public ArrayList<NOIDUNGQC> GetAllRows()throws Exception{
        try {
            ArrayList<NOIDUNGQC> l = NOIDUNG_Resource.path("GetAllRows").
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<NOIDUNGQC>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Thêm một đối tượng
    public String Insert(String thongTin, String moTa)throws Exception{
        try {
            String s = NOIDUNG_Resource.path("Insert").queryParam("thongTin", thongTin).
                    queryParam("moTa", moTa).request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        } 
    }
    
    // Sửa một đối tượng
    public String Update(String maND, String thongTin, String moTa)throws Exception{
        try {
            String s = NOIDUNG_Resource.path("Update").queryParam("maND", maND).
                    queryParam("thongTin", thongTin).queryParam("moTa", moTa).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String maND)throws Exception{
        try {
            String s = NOIDUNG_Resource.path("Delete").queryParam("maND", maND).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Trả về một đối tượng NOIDUNGQC có MaND = maND
    public NOIDUNGQC GetRowByID(String maND)throws Exception{
        try {
            NOIDUNGQC obj = NOIDUNG_Resource.path("GetRowByID").queryParam("maND", maND).
                    request(MediaType.APPLICATION_XML).get(NOIDUNGQC.class);
            return obj;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Kiểm tra một MaND đã tồn tại trong bảng NOIDUNGQC hay chưa
    public boolean ValidationID(String maND) throws Exception{
        try {
            String s = NOIDUNG_Resource.path("ValidationID").queryParam("maND", maND).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
}
