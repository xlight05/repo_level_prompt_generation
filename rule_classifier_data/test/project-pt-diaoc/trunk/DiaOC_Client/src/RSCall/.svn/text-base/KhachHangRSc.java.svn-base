/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RSCall;

import java.util.ArrayList;
import Entities.KHACHHANG;
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
public final class KhachHangRSc {
    
    String mHost = Utilities.Utilities.host;
    String mPort = Utilities.Utilities.port;
    final Client client = ClientBuilder.newClient();
    final String URI = "http://"+mHost+":"+mPort+"/DiaOC_WebServ/Resources/KHACHHANG/";
    WebTarget KHACHHANG_Resource;
    
    // Mảng quản lý các đối tượng KHACHHANG
    public ArrayList<KHACHHANG> mainList = new ArrayList<KHACHHANG> ();
    
    // Constructor
    public KhachHangRSc() throws Exception{
        KHACHHANG_Resource = client.target(URI);
        mainList = GetAllRows();
    }
    
    // Trả về toàn bộ bảng KHACHHANG
    public ArrayList<KHACHHANG> GetAllRows()throws Exception{
        try {
            ArrayList<KHACHHANG> l = KHACHHANG_Resource.path("GetAllRows").
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<KHACHHANG>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }

    public String Insert(String hoTen, String maThanhPho, String maQuan, String maPhuong,
            String maDuong, String diaChiCT, String soDT, String email)throws Exception{
        try {
            String s = KHACHHANG_Resource.path("Insert").queryParam("hoTen", hoTen).
                    queryParam("maThanhPho", maThanhPho).queryParam("maQuan", maQuan).
                    queryParam("maPhuong", maPhuong).queryParam("maDuong", maDuong).
                    queryParam("diaChiCT", diaChiCT).queryParam("soDT", soDT).
                    queryParam("email", email).request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        } 
    }
    
    // Sửa một đối tượng
    public String Update(String maKH, String hoTen, String maThanhPho, String maQuan, String maPhuong,
            String maDuong, String diaChiCT, String soDT, String email)throws Exception{
        try {
            String s = KHACHHANG_Resource.path("Update").queryParam("maKH", maKH).
                    queryParam("hoTen", hoTen).queryParam("maThanhPho", maThanhPho).
                    queryParam("maQuan", maQuan).queryParam("maPhuong", maPhuong).
                    queryParam("maDuong", maDuong).queryParam("diaChiCT", diaChiCT).
                    queryParam("soDT", soDT).queryParam("email", email).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        } 
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String maKH)throws Exception{
        try {
            String s = KHACHHANG_Resource.path("Delete").queryParam("maKH", maKH).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    //Trả về một đối tượng KHACHHANG có MaKH = maKH
    public KHACHHANG GetRowByID(String maKH)throws Exception{
        try {
            KHACHHANG obj = KHACHHANG_Resource.path("GetRowByID").queryParam("maKH", maKH).
                    request(MediaType.APPLICATION_XML).get(KHACHHANG.class);
            return obj;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Hàm kiểm tra MaKH đã tồn tại hay chưa
    public boolean ValidationID(String maKH) throws Exception{
        try {
            String s = KHACHHANG_Resource.path("ValidationID").queryParam("maKH", maKH).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Trả về những đối tượng KHACHHANG theo HoTen
    public ArrayList<KHACHHANG> GetRowsByName(String hoTen)throws Exception{
        try {
            ArrayList<KHACHHANG> l = KHACHHANG_Resource.path("GetRowsByName").queryParam("hoTen", hoTen).
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<KHACHHANG>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Trả về những đối tượng KHACHHANG theo Email
    public ArrayList<KHACHHANG> GetRowsByEmail(String email)throws Exception{//cái này thêm nè
        try {
            ArrayList<KHACHHANG> l = KHACHHANG_Resource.path("GetRowsByEmail").queryParam("email", email).
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<KHACHHANG>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
}
