/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RSCall;

import Entities.HINHANH;
import java.sql.Date;
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
public final class HinhAnhRSc {
    
    String mHost = Utilities.Utilities.host;
    String mPort = Utilities.Utilities.port;
    final Client client = ClientBuilder.newClient();
    final String URI = "http://"+mHost+":"+mPort+"/DiaOC_WebServ/Resources/HINHANH/";
    WebTarget HINHANH_Resource;
    
    // Mảng quản lý toàn bộ các đối tượng trong bảng HINHANH
    public ArrayList<HINHANH> mainList = new ArrayList<HINHANH>();
    
    // Constructor
    public HinhAnhRSc() throws Exception{
        HINHANH_Resource = client.target(URI);
        mainList = this.GetAllRows();
    }
    
    // Trả về toàn bộ bảng HINHANH
    public ArrayList<HINHANH> GetAllRows()throws Exception{
        try {
            ArrayList<HINHANH> l = HINHANH_Resource.path("GetAllRows").
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<HINHANH>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Thêm một đối tượng
    public int Insert(String anh, String moTa, Date thoiGianChupAnh, String maND)throws Exception{
        try {
            String s = HINHANH_Resource.path("Insert").queryParam("anh", anh).
                    queryParam("moTa", moTa).queryParam("thoiGianChupAnh", thoiGianChupAnh.getTime()).
                    queryParam("maND", maND).request(MediaType.TEXT_PLAIN).get(String.class);   
            return Integer.parseInt(s);
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        } 
    }
    
    // Sửa một đối tượng
    public int Update(int maHinhAnh, String anh, String moTa, Date thoiGianChupAnh, String maND)throws Exception{
        try {
            String s = HINHANH_Resource.path("Update").
                    queryParam("maHinhAnh", maHinhAnh).queryParam("anh", anh).
                    queryParam("moTa", moTa).queryParam("thoiGianChupAnh", thoiGianChupAnh.getTime()).
                    queryParam("maND", maND).request(MediaType.TEXT_PLAIN).get(String.class);   
            return Integer.parseInt(s);
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Xóa một đối tượng
    public Boolean Delete(int maHinhAnh)throws Exception{
        try {
            String s = HINHANH_Resource.path("Delete").queryParam("maHinhAnh", maHinhAnh).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    public HINHANH GetRowByID(int maHinhAnh)throws Exception{
        try {
            HINHANH obj = HINHANH_Resource.path("GetRowByID").queryParam("maHinhAnh", maHinhAnh).
                    request(MediaType.APPLICATION_XML).get(HINHANH.class);
            return obj;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Kiểm tra xem một MaHinhAnh có tồn tại trong bảng hay chưa
    public boolean ValidationID(int maHinhAnh) throws Exception{
        try {
            String s = HINHANH_Resource.path("ValidationID").queryParam("maHinhAnh", maHinhAnh).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    //Kiểm tra xem một MaND có tồn tại trong bảng HINHANH hay không
    public boolean IsMaND_HinhAnhExist(String maND) throws Exception{
        try {
            String s = HINHANH_Resource.path("IsMaND_HinhAnhExist").queryParam("maND", maND).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Trả về Anh theo MaND
    public ArrayList<HINHANH> SelectHinhAnhByMaND(String maND)throws Exception{
        try {
            ArrayList<HINHANH> l = HINHANH_Resource.path("SelectHinhAnhByMaND").queryParam("maND", maND).
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<HINHANH>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
}
