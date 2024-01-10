/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RSCall;


import java.util.ArrayList;
import Entities.QC_BAO;
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
public final class QCBaoRSc {
    
    String mHost = Utilities.Utilities.host;
    String mPort = Utilities.Utilities.port;
    final Client client = ClientBuilder.newClient();
    final String URI = "http://"+mHost+":"+mPort+"/DiaOC_WebServ/Resources/QCBAO/";
    WebTarget QCBAO_Resource;
    
    // Mảng quản lý toàn bộ bảng QC_BAO
    public ArrayList<QC_BAO> mainList = new ArrayList<QC_BAO>();
    
    // Constructor
    public QCBaoRSc() throws Exception{
        QCBAO_Resource = client.target(URI);
        mainList = GetAllRows();
    }
    
    // Trả về toàn bộ bảng QC_BAO
    public ArrayList<QC_BAO> GetAllRows()throws Exception{
        try {
            ArrayList<QC_BAO> l = QCBAO_Resource.path("GetAllRows").
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<QC_BAO>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Thêm một đối tượng
    public String Insert(Date ngayBatDauPhatHanh, Boolean hinhAnh,
        String maND, String maPhieuDK, String maGiaTien) throws Exception{
        try {
            String s = QCBAO_Resource.path("Insert").
                    queryParam("ngayBatDauPhatHanh", ngayBatDauPhatHanh.getTime()).
                    queryParam("hinhAnh", hinhAnh).queryParam("maND", maND).
                    queryParam("maPhieuDK", maPhieuDK).queryParam("maGiaTien", maGiaTien).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }      
    }
    
    // Sửa một đối tượng
    public String Update(String maQCBao, Date ngayBatDauPhatHanh, Boolean hinhAnh,
            String maND, String maPhieuDK, String maGiaTien)throws Exception{
        try {
            String s = QCBAO_Resource.path("Update").queryParam("maQCBao", maQCBao).
                    queryParam("ngayBatDauPhatHanh", ngayBatDauPhatHanh.getTime()).
                    queryParam("hinhAnh", hinhAnh).queryParam("maND", maND).
                    queryParam("maPhieuDK", maPhieuDK).queryParam("maGiaTien", maGiaTien).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        } 
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String maQCBao)throws Exception{
        try {
            String s = QCBAO_Resource.path("Delete").queryParam("maQCBao", maQCBao).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Trả về một đối tượng QC_BAO có MaQCBao = maQCBao
    public QC_BAO GetRowByID(String maQCBao)throws Exception{
        try {
            QC_BAO obj = QCBAO_Resource.path("GetRowByID").queryParam("maQCBao", maQCBao).
                    request(MediaType.APPLICATION_XML).get(QC_BAO.class);
            return obj;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Hàm kiểm tra MaQCBao đã tồn tại hay chưa
    public boolean ValidationID(String maQCBao) throws Exception{
        try {
            String s = QCBAO_Resource.path("ValidationID").queryParam("maQCBao", maQCBao).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Kiểm tra MaND có tồn tại trong bảng QC_BAO hay không?
    public boolean IsMaND_BaoExist(String maND) throws Exception{
        try {
            String s = QCBAO_Resource.path("IsMaND_BaoExist").queryParam("maND", maND).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Kiểm tra MaPhieuDangKy có tồn tại trong bảng QC_BAO hay không?
    public boolean IsMaPhieuDK_BaoExist(String maPhieuDK) throws Exception{
        try {
            String s = QCBAO_Resource.path("IsMaPhieuDK_BaoExist").
                    queryParam("maPhieuDK", maPhieuDK).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Kiểm tra MaGiaTien có tồn tại trong bảng QC_BAO hay không?
    public boolean IsMaGiaTien_BaoExist(String maGiaTien) throws Exception{
        try {
            String s = QCBAO_Resource.path("IsMaGiaTien_BaoExist").
                    queryParam("maGiaTien", maGiaTien).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Trả về một đối tượng QC_BAO có MaPhieuDK = maPhieuDK
    public QC_BAO GetQCBaoByMaPhieuDK(String maPhieuDK)throws Exception{
        try {
            QC_BAO obj = QCBAO_Resource.path("GetQCBaoByMaPhieuDK").
                    queryParam("maPhieuDK", maPhieuDK).
                    request(MediaType.APPLICATION_XML).get(QC_BAO.class);
            return obj;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
}
