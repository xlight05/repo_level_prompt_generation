/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RSCall;


import java.util.ArrayList;
import Entities.QC_BANG;
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
public final class QCBangRSc {
    
    String mHost = Utilities.Utilities.host;
    String mPort = Utilities.Utilities.port;
    final Client client = ClientBuilder.newClient();
    final String URI = "http://"+mHost+":"+mPort+"/DiaOC_WebServ/Resources/QCBANG/";
    WebTarget QCBANG_Resource;
    
    // Mảng quản lý toàn bộ bảng
    public ArrayList<QC_BANG> mainList = new ArrayList<QC_BANG>();
    
    // Constructor 
    public QCBangRSc() throws Exception{
        QCBANG_Resource = client.target(URI);
        mainList = GetAllRows();
    }
    
    // Trả về toàn bộ bảng QC_BANG
    public ArrayList<QC_BANG> GetAllRows()throws Exception{
        try {
            ArrayList<QC_BANG> l = QCBANG_Resource.path("GetAllRows").
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<QC_BANG>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Thêm một đối tượng
    public String Insert(String maND, String maPhieuDK, String maGiaTien)throws Exception{
        try {
            String s = QCBANG_Resource.path("Insert").queryParam("maND", maND).
                    queryParam("maPhieuDK", maPhieuDK).queryParam("maGiaTien", maGiaTien).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        } 
    }
    
    // Sửa một đối tượng
    public String Update(String maQCBang, String maND,
            String maPhieuDK, String maGiaTien)throws Exception{
        try {
            String s = QCBANG_Resource.path("Update").queryParam("maQCBang", maQCBang).
                    queryParam("maND", maND).queryParam("maPhieuDK", maPhieuDK).
                    queryParam("maGiaTien", maGiaTien).request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        } 
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String maQCBang)throws Exception{
        try {
            String s = QCBANG_Resource.path("Delete").queryParam("maQCBang", maQCBang).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    public QC_BANG GetRowByID(String maQCBang)throws Exception{
        try {
            QC_BANG obj = QCBANG_Resource.path("GetRowByID").queryParam("maQCBang", maQCBang).
                    request(MediaType.APPLICATION_XML).get(QC_BANG.class);
            return obj;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Hàm kiểm tra MaQCBang đã tồn tại hay chưa
    public boolean ValidationID(String maQCBang) throws Exception{
        try {
            String s = QCBANG_Resource.path("ValidationID").queryParam("maQCBang", maQCBang).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Kiểm tra MaND có tồn tại trong bảng QC_BANG hay không?
    public boolean IsMaND_BangExist(String maND) throws Exception{
        try {
            String s = QCBANG_Resource.path("IsMaND_BangExist").queryParam("maND", maND).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Kiểm tra MaPhieuDangKy có tồn tại trong bảng QC_BANG hay không?
    public boolean IsMaPhieuDK_BangExist(String maPhieuDK) throws Exception{
        try {
            String s = QCBANG_Resource.path("IsMaPhieuDK_BangExist").
                    queryParam("maPhieuDK", maPhieuDK).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Kiểm tra MaGiaTien có tồn tại trong bảng QC_BANG hay không?
    public boolean IsMaGiaTien_BangExist(String maGiaTien) throws Exception{
        try {
            String s = QCBANG_Resource.path("IsMaGiaTien_BangExist").
                    queryParam("maGiaTien", maGiaTien).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Trả về một đối tượng QC_BANG có MaPhieuDangKy = maPhieuDK
    public QC_BANG GetQCBangByMaPhieuDK(String maPhieuDK)throws Exception{
        try {
            QC_BANG obj = QCBANG_Resource.path("GetQCBangByMaPhieuDK").
                    queryParam("maPhieuDK", maPhieuDK).
                    request(MediaType.APPLICATION_XML).get(QC_BANG.class);
            return obj;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
}
