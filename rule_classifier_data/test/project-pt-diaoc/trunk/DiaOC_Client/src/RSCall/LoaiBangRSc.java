/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RSCall;

import java.util.ArrayList;
import Entities.LOAIBANG;
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
public final class LoaiBangRSc {

    String mHost = Utilities.Utilities.host;
    String mPort = Utilities.Utilities.port;
    final Client client = ClientBuilder.newClient();
    final String URI = "http://"+mHost+":"+mPort+"/DiaOC_WebServ/Resources/LOAIBANG/";
    WebTarget LOAIBANG_Resource;
    
    // Mảng quản lý toàn bộ bảng LOAIBANG
    public ArrayList<LOAIBANG> mainList = new ArrayList<LOAIBANG>();
    
    // Constructor
    public LoaiBangRSc() throws Exception{
        LOAIBANG_Resource = client.target(URI);
        mainList = GetAllRows();
    }
    
    // Trả về toàn bộ bảng LOAIBANG
    public ArrayList<LOAIBANG> GetAllRows()throws Exception{
        try {
            ArrayList<LOAIBANG> l = LOAIBANG_Resource.path("GetAllRows").
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<LOAIBANG>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Thêm mới một đối tượng
    public String Insert(String loaiBang)throws Exception{
        try {
            String s = LOAIBANG_Resource.path("Insert").queryParam("loaiBang", loaiBang).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        } 
    }
    
    // Sửa một đối tượng
    public String Update(String maLoaiBang, String loaiBang)throws Exception{
        try {
            String s = LOAIBANG_Resource.path("Update").queryParam("maLoaiBang", maLoaiBang).
                    queryParam("loaiBang", loaiBang).request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String maLoaiBang)throws Exception{
        try {
            String s = LOAIBANG_Resource.path("Delete").queryParam("maLoaiBang", maLoaiBang).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Trả về một đối tượng LOAIBANG có MaLoaiBang = maLoaiBang
    public LOAIBANG GetRowByID(String maLoaiBang)throws Exception{
       try {
            LOAIBANG dg = LOAIBANG_Resource.path("GetRowByID").queryParam("maLoaiBang", maLoaiBang).
                    request(MediaType.APPLICATION_XML).get(LOAIBANG.class);
            return dg;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Kiểm tra một MaLoaiBang đã tồn tại trong bảng hay chưa
    public boolean ValidationID(String maLoaiBang) throws Exception{
        try {
            String s = LOAIBANG_Resource.path("ValidationID").queryParam("maLoaiBang", maLoaiBang).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
}
