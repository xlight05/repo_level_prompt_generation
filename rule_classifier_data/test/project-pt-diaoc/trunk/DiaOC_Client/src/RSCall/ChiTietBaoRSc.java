/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RSCall;

import Entities.CHITIETBAO;
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
public final class ChiTietBaoRSc {
    
    String mHost = Utilities.Utilities.host;
    String mPort = Utilities.Utilities.port;
    final Client client = ClientBuilder.newClient();
    final String URI = "http://"+mHost+":"+mPort+"/DiaOC_WebServ/Resources/CHITIETBAO/";
    WebTarget CHITIETBAOBAO_Resource;
    
    // Mảng quản lý các record CHITIETBAO
    public ArrayList<CHITIETBAO> mainList = new ArrayList<CHITIETBAO>();
    
    // Constructor
    public ChiTietBaoRSc() throws Exception{
        CHITIETBAOBAO_Resource = client.target(URI);
        mainList = this.GetAllRows();
    }
    
    // Trả về toàn bộ bảng CHITIETBAO
    public ArrayList<CHITIETBAO> GetAllRows() throws Exception{
        try {
            ArrayList<CHITIETBAO> l = CHITIETBAOBAO_Resource.path("GetAllRows").
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<CHITIETBAO>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Thêm một đối tượng
    public String Insert(String maQCBao, String maBao)throws Exception{
        try {
            String s = CHITIETBAOBAO_Resource.path("Insert").queryParam("maQCBao", maQCBao).
                    queryParam("maBao", maBao).request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }              
    }
    
    // Sửa một đối tượng
    public String Update(String maCT, String maQCBao, String maBao) throws Exception{
        try {
            String s = CHITIETBAOBAO_Resource.path("Update").queryParam("maCT", maCT).queryParam("maQCBao", maQCBao).
                    queryParam("maBao", maBao).request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Xóa một đối tượng CHITIETBAO
    public Boolean Delete(String maCT)throws Exception{
        try {
            String s = CHITIETBAOBAO_Resource.path("Delete").queryParam("maCT", maCT).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Trả về một record Bao co MaCT = maCT
    public CHITIETBAO GetRowByID(String maCT)throws Exception{
        try {
            CHITIETBAO ct = CHITIETBAOBAO_Resource.path("GetRowByID").queryParam("maCT", maCT).
                    request(MediaType.APPLICATION_XML).get(CHITIETBAO.class);
            return ct;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Kiểm tra xem MaBao đã tồn tại trong table BAO hay chưa
    public boolean ValidationID(String maCT) throws Exception{
        try {
            String s = CHITIETBAOBAO_Resource.path("ValidationID").queryParam("maCT", maCT).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            if (s.trim().equals("true"))
                return true;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
        return false;
    }
    
    // Hàm kiểm tra MaBao có tồn tại hay không
    public boolean IsMaBaoExist(String maBao) throws Exception{
        try {
            String s = CHITIETBAOBAO_Resource.path("IsMaBaoExist").queryParam("maBao", maBao).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            if (s.trim().equals("true"))
                return true;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
        return false;
    }
    
    // Hàm kiểm tra MaQCBao có tồn tại hay không
    public boolean IsMaQCBaoExist(String maQCBao) throws Exception{
        try {
            String s = CHITIETBAOBAO_Resource.path("IsMaQCBaoExist").queryParam("maQCBao", maQCBao).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            if (s.trim().equals("true"))
                return true;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
        return false;
    }
    
    // Trả về những MaBao trong bảng CHITIETBAO có cùng MaQCBao
    public ArrayList<String> GetMaBaoByMaQCBao(String maQCBao)throws Exception{
        try {
            ArrayList<String> l = CHITIETBAOBAO_Resource.path("GetMaBaoByMaQCBao").
                    queryParam("maQCBao", maQCBao).request(MediaType.APPLICATION_XML).
                    get(new  GenericType<ArrayList<String>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
}
