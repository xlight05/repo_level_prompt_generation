/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RSCall;

import java.util.ArrayList;
import Entities.PHIEUDANGKY;
import java.sql.Date;
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
public final class PhieuDangKyRSc {
    
    String mHost = Utilities.Utilities.host;
    String mPort = Utilities.Utilities.port;
    final Client client = ClientBuilder.newClient();
    final String URI = "http://"+mHost+":"+mPort+"/DiaOC_WebServ/Resources/PHIEUDANGKY/";
    WebTarget PHIEUDANGKY_Resource;
    
    // Mảng quản lý toàn bộ các đối tượng trong bảng
    public ArrayList<PHIEUDANGKY> mainList = new ArrayList<PHIEUDANGKY>();
    
    // Constructor
    public PhieuDangKyRSc() throws Exception{
        PHIEUDANGKY_Resource = client.target(URI);
        mainList = this.GetAllRows();
    }
    
    // Trả về toàn bộ bảng PHIEUDANGKY
    public ArrayList<PHIEUDANGKY> GetAllRows()throws Exception{
        try {
            ArrayList<PHIEUDANGKY> l = PHIEUDANGKY_Resource.path("GetAllRows").
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<PHIEUDANGKY>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Thêm một đối tượng
    public String Insert(String maDiaOc, Date tuNgay, Date denNgay,
            Float soTien, Date thoiGianHenChupAnh, String maKH)throws Exception{
        try {
            String s = PHIEUDANGKY_Resource.path("Insert").queryParam("maDiaOc", maDiaOc).
                    queryParam("tuNgay", tuNgay.getTime()).queryParam("denNgay", denNgay.getTime()).
                    queryParam("maPhuong", soTien).queryParam("maDuong", soTien).
                    queryParam("thoiGianHenChupAnh", thoiGianHenChupAnh.getTime()).
                    queryParam("maKH", maKH).request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        } 
    }
    
    // Sửa một đối tượng
    public String Update(String maPhieuDangKy, String maDiaOc, Date tuNgay, Date denNgay,
            Float soTien, Date thoiGianHenChupAnh, String maKH)throws Exception{        
        try {
            String s = PHIEUDANGKY_Resource.path("Update").
                    queryParam("maPhieuDangKy", maPhieuDangKy).queryParam("maDiaOc", maDiaOc).
                    queryParam("tuNgay", tuNgay.getTime()).queryParam("denNgay", denNgay.getTime()).
                    queryParam("maPhuong", soTien).queryParam("maDuong", soTien).
                    queryParam("thoiGianHenChupAnh", thoiGianHenChupAnh.getTime()).
                    queryParam("maKH", maKH).request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String maPhieuDangKy)throws Exception{
        try {
            String s = PHIEUDANGKY_Resource.path("Delete").
                    queryParam("maPhieuDangKy", maPhieuDangKy).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Trả về một đối tượng PHIEUDANGKY có MaPhieuDangKy = maPhieuDangKy
    public PHIEUDANGKY GetRowByID(String maPhieuDangKy)throws Exception{
        try {
            PHIEUDANGKY obj = PHIEUDANGKY_Resource.path("GetRowByID").
                    queryParam("maPhieuDangKy", maPhieuDangKy).
                    request(MediaType.APPLICATION_XML).get(PHIEUDANGKY.class);
            return obj;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Kiểm tra xem một MaPhieuDangKy có tồn tại trong bảng hay chưa
    public boolean ValidationID(String maPhieuDangKy) throws Exception{
        try {
            String s = PHIEUDANGKY_Resource.path("ValidationID").
                    queryParam("maPhieuDangKy", maPhieuDangKy).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Kiểm tra xem một MaDiaOc có tồn tại trong bảng hay không?
    public boolean IsDiaOcExist(String maDiaOc) throws Exception{
        try {
            String s = PHIEUDANGKY_Resource.path("IsDiaOcExist").
                    queryParam("maDiaOc", maDiaOc).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Kiểm tra xem một MaKH có tồn tại trong bảng hay không?
    public boolean IsKhachHangExist(String maKH) throws Exception{
        try {
            String s = PHIEUDANGKY_Resource.path("IsKhachHangExist").
                    queryParam("maKH", maKH).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Trả về một đối tượng PHIEUDANGKY có MaDiaOc = maDiaOc
    public PHIEUDANGKY GetPhieuDKByMaDiaOc(String maDiaOc)throws Exception{
        try {
            PHIEUDANGKY obj = PHIEUDANGKY_Resource.path("GetPhieuDKByMaDiaOc").
                    queryParam("maDiaOc", maDiaOc).
                    request(MediaType.APPLICATION_XML).get(PHIEUDANGKY.class);
            return obj;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Trả về các đối tượng PHIEUDANGKY có MaDiaOc = maDiaOc
    public ArrayList<PHIEUDANGKY> GetAllPhieuDKByMaDiaOc(String maDiaOc)throws Exception{
        try {
            ArrayList<PHIEUDANGKY> l = PHIEUDANGKY_Resource.path("GetAllPhieuDKByMaDiaOc").
                    queryParam("maDiaOc", maDiaOc).request(MediaType.APPLICATION_XML).
                    get(new  GenericType<ArrayList<PHIEUDANGKY>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
}
