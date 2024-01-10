/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RSCall;

import java.util.ArrayList;
import Entities.GIATIEN_QC_BANG;
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
public final class GiaTienQCBangRSc {
    
    String mHost = Utilities.Utilities.host;
    String mPort = Utilities.Utilities.port;
    final Client client = ClientBuilder.newClient();
    final String URI = "http://"+mHost+":"+mPort+"/DiaOC_WebServ/Resources/GIATIENBANG/";
    WebTarget GTBANG_Resource;
    
    // Mảng quản lý toàn bộ bảng
    public ArrayList<GIATIEN_QC_BANG> mainList = new ArrayList<GIATIEN_QC_BANG>();
    
    // Constructor
    public GiaTienQCBangRSc() throws Exception{
        GTBANG_Resource = client.target(URI);
        mainList = GetAllRows();
    }
    
    // Trả về toàn bộ bảng GIATIEN_QC_BANG
    public ArrayList<GIATIEN_QC_BANG> GetAllRows()throws Exception{
        try {
            ArrayList<GIATIEN_QC_BANG> l = GTBANG_Resource.path("GetAllRows").
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<GIATIEN_QC_BANG>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Thêm một đối tượng
    public String Insert(String maLoaiBang, String moTa, Float kichCo, String dvt, Float giaTien)throws Exception{
        try {
            String s = GTBANG_Resource.path("Insert").queryParam("maLoaiBang", maLoaiBang).
                    queryParam("moTa", moTa).queryParam("kichCo", kichCo).
                    queryParam("dvt", dvt).queryParam("giaTien", giaTien).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        } 
    }
    
    // Sửa một đối tượng
    public String Update(String maGiaTien, String maLoaiBang, String moTa, Float kichCo, String dvt, Float giaTien)throws Exception{
        try {
            String s = GTBANG_Resource.path("Update").queryParam("maGiaTien", maGiaTien).
                    queryParam("maLoaiBang", maLoaiBang).
                    queryParam("moTa", moTa).queryParam("kichCo", kichCo).
                    queryParam("dvt", dvt).queryParam("giaTien", giaTien).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String maGiaTien)throws Exception{
        try {
            String s = GTBANG_Resource.path("Delete").queryParam("maGiaTien", maGiaTien).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Trả về một đối tượng GIATIEN_QC_BANG có MaGiaTien = maGiaTien
    public GIATIEN_QC_BANG GetRowByID(String maGiaTien)throws Exception{
        try {
            GIATIEN_QC_BANG obj = GTBANG_Resource.path("GetRowByID").queryParam("maGiaTien", maGiaTien).
                    request(MediaType.APPLICATION_XML).get(GIATIEN_QC_BANG.class);
            return obj;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Kiểm tra MaGiaTien có tồn tại trong bảng hay chưa
    public boolean ValidationID(String maGiaTien) throws Exception{
        try {
            String s = GTBANG_Resource.path("ValidationID").queryParam("maGiaTien", maGiaTien).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Kiểm tra MaLoaiBang có tồn tại trong bảng GIATIEN_QC_BANG hay không?
    public boolean IsMaLoaiBangExist(String maLoaiBang) throws Exception{
        try {
            String s = GTBANG_Resource.path("IsMaLoaiBangExist").queryParam("maLoaiBang", maLoaiBang).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Trả về một đối tượng LoaiBang có MaLoaiBang = maLoaiBang
    public String GetLoaiBang(String maLoaiBang)throws Exception{
        try {
            String s = GTBANG_Resource.path("GetLoaiBang").queryParam("maLoaiBang", maLoaiBang).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
}
