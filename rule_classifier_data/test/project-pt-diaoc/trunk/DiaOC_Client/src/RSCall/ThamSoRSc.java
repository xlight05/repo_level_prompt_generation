/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RSCall;

import Entities.THAMSO;
import java.util.ArrayList;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
/**
 *
 * @author Nixforest
 */
public class ThamSoRSc {
    
    String mHost = Utilities.Utilities.host;
    String mPort = Utilities.Utilities.port;
    final Client client = ClientBuilder.newClient();
    final String URI = "http://"+mHost+":"+mPort+"/DiaOC_WebServ/Resources/THAMSO/";
    WebTarget THAMSO_Resource = client.target(URI);
    
    // Trả về toàn bộ bảng THAMSO
    public ArrayList<Entities.THAMSO> GetAllRows() throws Exception{
        try {
            ArrayList<THAMSO> l = THAMSO_Resource.path("GetAllRows").
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<THAMSO>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Thêm Tham số
    public Boolean Insert(String tenThamSo, Float giaTri, String giaiThich) throws Exception{
        try {
            String s = THAMSO_Resource.path("Insert").queryParam("tenThamSo", tenThamSo).
                    queryParam("giaTri", giaTri).queryParam("giaiThich", giaiThich).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        } 
    }
    // Sửa tham số
    public Boolean Update(String tenThamSo, Float giaTri, String giaiThich) throws Exception{
        try {
            String s = THAMSO_Resource.path("Update").queryParam("tenThamSo", tenThamSo).
                    queryParam("giaTri", giaTri).queryParam("giaiThich", giaiThich).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Xóa Tham số
    public Boolean Delete(String tenThamSo) throws Exception{
        try {
            String s = THAMSO_Resource.path("Delete").queryParam("tenThamSo", tenThamSo).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Lấy Tham số theo tên
    public THAMSO GetRowByID(String tenThamSo) throws Exception{
        try {
            THAMSO obj = THAMSO_Resource.path("GetRowByID").queryParam("tenThamSo", tenThamSo).
                    request(MediaType.APPLICATION_XML).get(THAMSO.class);
            return obj;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Trả về một đối tượng THAMSO có TenThamSo = tenThamSo
    public THAMSO GetThamSoByTen(String tenThamSo)throws Exception{
        try {
            THAMSO obj = THAMSO_Resource.path("GetThamSoByTen").queryParam("tenThamSo", tenThamSo).
                    request(MediaType.APPLICATION_XML).get(THAMSO.class);
            return obj;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
}
