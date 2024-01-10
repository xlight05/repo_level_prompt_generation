/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RSCall;

import java.util.ArrayList;
import Entities.LOAIDIAOC;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public final class LoaiDiaOCRSc {    

    String mHost = Utilities.Utilities.host;
    String mPort = Utilities.Utilities.port;
    final Client client = ClientBuilder.newClient();
    final String URI = "http://"+mHost+":"+mPort+"/DiaOC_WebServ/Resources/LOAIDIAOC/";
    WebTarget LOAIDIAOC_Resource;
    
    // Mảng quản lý các đối tượng LOAIDIAOC
    public ArrayList<LOAIDIAOC> mainList = new ArrayList<LOAIDIAOC>();
    
    // Constructor
    public LoaiDiaOCRSc(){
        try {
            LOAIDIAOC_Resource = client.target(URI);
            mainList = this.GetAllRows();
        } catch (Exception ex) {
            Logger.getLogger(LoaiDiaOCRSc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Trả về toàn bộ bảng LOAIDIAOC
    public ArrayList<LOAIDIAOC> GetAllRows()throws Exception{
        try {
            ArrayList<LOAIDIAOC> l = LOAIDIAOC_Resource.path("GetAllRows").
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<LOAIDIAOC>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Thêm một đối tượng
    public String Insert(String tenLoaiDiaOc, String kyHieu)throws Exception{
        try {
            String s = LOAIDIAOC_Resource.path("Insert").queryParam("tenLoaiDiaOc", tenLoaiDiaOc).
                    queryParam("kyHieu", kyHieu).request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        } 
    }
    
    // Sửa một đối tượng
    public String Update(String maLoaiDiaOc, String tenLoaiDiaOc, String kyHieu)throws Exception{
        try {
            String s = LOAIDIAOC_Resource.path("Update").queryParam("maLoaiDiaOc", maLoaiDiaOc).
                    queryParam("tenLoaiDiaOc", tenLoaiDiaOc).queryParam("kyHieu", kyHieu).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String maLoaiDiaOc)throws Exception{
        try {
            String s = LOAIDIAOC_Resource.path("Delete").
                    queryParam("maLoaiDiaOc", maLoaiDiaOc).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Trả về một LOAIDIAOC có MaLoaiDiaOc = maLoaiDiaOc
    public LOAIDIAOC GetRowByID(String maLoaiDiaOc)throws Exception{
        try {
            LOAIDIAOC obj = LOAIDIAOC_Resource.path("GetRowByID").
                    queryParam("maLoaiDiaOc", maLoaiDiaOc).
                    request(MediaType.APPLICATION_XML).get(LOAIDIAOC.class);
            return obj;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Hàm kiểm tra xem một MaLoaiDiaOc đã tồn tại hay chưa
    public boolean ValidationID(String maLoaiDiaOc) throws Exception{
        try {
            String s = LOAIDIAOC_Resource.path("ValidationID").
                    queryParam("maLoaiDiaOc", maLoaiDiaOc).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Trả về TenLoaiDiaOc theo MaLoaiDiaOc
    public String GetNameByID(String maLoaiDiaOc)throws Exception{
        try {
            String s = LOAIDIAOC_Resource.path("GetNameByID").
                    queryParam("maLoaiDiaOc", maLoaiDiaOc).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        } 
    }
    
    // Kiểm tra xem một KyHieu có tồn tại trong bảng hay không
    public boolean IsKyHieuExist(String kyHieu) throws Exception{
        try {
            String s = LOAIDIAOC_Resource.path("IsKyHieuExist").
                    queryParam("kyHieu", kyHieu).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
}
