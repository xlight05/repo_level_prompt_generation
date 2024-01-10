/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RSCall;

import Entities.TOBUOM;
import java.util.ArrayList;
import Entities.TOBUOM;
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
public final class ToBuomRSc {
    
    String mHost = Utilities.Utilities.host;
    String mPort = Utilities.Utilities.port;
    final Client client = ClientBuilder.newClient();
    final String URI = "http://"+mHost+":"+mPort+"/DiaOC_WebServ/Resources/TOBUOM/";
    WebTarget TOBUOM_Resource;
    
    // Mảng quản lý toàn bộ bảng TOBUOM    
    public ArrayList<TOBUOM> mainList = new ArrayList<TOBUOM>();
    
    // Constructor
    public ToBuomRSc() throws Exception{
        TOBUOM_Resource = client.target(URI);
        mainList = GetAllRows();
    }
    
    // Trả về toàn bộ bảng
    public ArrayList<TOBUOM> GetAllRows()throws Exception{
        try {
            ArrayList<TOBUOM> l = TOBUOM_Resource.path("GetAllRows").
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<TOBUOM>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Thêm một đối tượng
    public String Insert(String maND, String maPhieuDK, String maGiaTien)throws Exception{
        try {
            String s = TOBUOM_Resource.path("Insert").queryParam("maND", maND).
                    queryParam("maPhieuDK", maPhieuDK).queryParam("maGiaTien", maGiaTien).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        } 
    }
    
    // Sửa một đối tượng
    public String Update(String maToBuom, String maND,
            String maPhieuDK, String maGiaTien)throws Exception{
        try {
            String s = TOBUOM_Resource.path("Update").queryParam("maToBuom", maToBuom).
                    queryParam("maND", maND).queryParam("maPhieuDK", maPhieuDK).
                    queryParam("maGiaTien", maGiaTien).request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        } 
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String maToBuom)throws Exception{
        try {
            String s = TOBUOM_Resource.path("Delete").queryParam("maToBuom", maToBuom).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Trả về một đối tượng TOBUOM có MaToBuom = maToBuom
    public TOBUOM GetRowByID(String maToBuom)throws Exception{
        try {
            TOBUOM obj = TOBUOM_Resource.path("GetRowByID").queryParam("maToBuom", maToBuom).
                    request(MediaType.APPLICATION_XML).get(TOBUOM.class);
            return obj;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Kiểm tra một MaToBuom đã tồn tại trong bảng hay chưa
    public boolean ValidationID(String maToBuom) throws Exception{
        try {
            String s = TOBUOM_Resource.path("ValidationID").queryParam("maToBuom", maToBuom).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Kiểm tra MaND có tồn tại trong bảng TOBUOM hay không?
    public boolean IsMaND_ToBuomExist(String maND) throws Exception{
        try {
            String s = TOBUOM_Resource.path("IsMaND_ToBuomExist").queryParam("maND", maND).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Kiểm tra MaPhieuDangKy có tồn tại trong bảng TOBUOM hay không?
    public boolean IsMaPhieuDK_ToBuomExist(String maPhieuDK) throws Exception{
        try {
            String s = TOBUOM_Resource.path("IsMaPhieuDK_ToBuomExist").
                    queryParam("maPhieuDK", maPhieuDK).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Kiểm tra MaGiaTien có tồn tại trong bảng TOBUOM hay không?
    public boolean IsMaGiaTien_ToBuomExist(String maGiaTien) throws Exception{
        try {
            String s = TOBUOM_Resource.path("IsMaGiaTien_ToBuomExist").
                    queryParam("maGiaTien", maGiaTien).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Trả về một đối tượng TOBUOM có MaPhieuDK = maPhieuDK
    public TOBUOM GetToBuomByMaPhieuDK(String maPhieuDK)throws Exception{
        try {
            TOBUOM obj = TOBUOM_Resource.path("GetToBuomByMaPhieuDK").
                    queryParam("maPhieuDK", maPhieuDK).
                    request(MediaType.APPLICATION_XML).get(TOBUOM.class);
            return obj;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
}
