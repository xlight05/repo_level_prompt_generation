/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RSCall;

import Entities.BAO;
import java.util.ArrayList;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
/**
 *
 * @author VooKa
 */
public final class BaoRSc {
    
    String mHost = Utilities.Utilities.host;
    String mPort = Utilities.Utilities.port;
    final Client client = ClientBuilder.newClient();
    final String URI = "http://"+mHost+":"+mPort+"/DiaOC_WebServ/Resources/BAO/";
    WebTarget BAO_Resource;
    
    public ArrayList<BAO> mainList = new ArrayList<BAO>();
    
    // Constructor
    public BaoRSc() throws Exception{
        BAO_Resource = client.target(URI);
        mainList = this.GetAllRows();
    }
    
    // Trả về toàn bộ bảng BAO
    public ArrayList<BAO> GetAllRows() throws Exception{
        try {
            ArrayList<BAO> l = BAO_Resource.path("GetAllRows").
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<BAO>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Thêm một đối tượng
    public String Insert(String tenBao, int thuPhatHanh)throws Exception{
        try {
            String s = BAO_Resource.path("Insert").queryParam("TenBao", tenBao).queryParam("Thu", thuPhatHanh).
                request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Sửa một đối tượng
    public String Update(String maBao, String tenBao, int thuPhatHanh) throws Exception{
        try {
            String s = BAO_Resource.path("Update").queryParam("MaBao", maBao).queryParam("TenBao", tenBao).
                    queryParam("Thu", thuPhatHanh).request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Xóa một đối tượng BAO
    public Boolean Delete(String maBao)throws Exception{
        try {
            String s = BAO_Resource.path("Delete").queryParam("MaBao", maBao).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            if (s.trim().equals("true"))
                return true;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
        return false;
    }
    
    // Trả về một record Bao co MaBao = maBao
    public BAO GetRowByID(String maBao)throws Exception{
        try {
            BAO b = BAO_Resource.path("GetRowByID").queryParam("MaBao", maBao).
                    request(MediaType.APPLICATION_XML).get(BAO.class);
            return b;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Kiểm tra xem MaBao đã tồn tại trong table BAO hay chưa
    public Boolean ValidationID(String maBao) throws Exception{
        try {
            String s = BAO_Resource.path("ValidationID").queryParam("MaBao", maBao).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            if (s.trim().equals("true"))
                return true;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
        return false;
    }
    
    // Trả về ThuPhatHanh tương ứng với TenBao
     public int GetNgayPHByTenBao(String tenBao) throws Exception{
        try {
            String s = BAO_Resource.path("GetNgayPHByTenBao").queryParam("TenBao", tenBao).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            int i = Integer.parseInt(s);
            return i;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
     }
}
