/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RSCall;

import Entities.GIATIEN_QC_BAO;
import java.util.ArrayList;
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
public final class GiaTienQCBaoRSc {
    
    String mHost = Utilities.Utilities.host;
    String mPort = Utilities.Utilities.port;
    final Client client = ClientBuilder.newClient();
    final String URI = "http://"+mHost+":"+mPort+"/DiaOC_WebServ/Resources/GIATIENBAO/";
    WebTarget GTBAO_Resource;
    
    // Mảng quản lý toàn bộ bảng GIATIEN_QC_BAO
    public ArrayList<GIATIEN_QC_BAO> mainList = new ArrayList<GIATIEN_QC_BAO>();
    
    // Constructor
    public GiaTienQCBaoRSc() throws Exception{
        GTBAO_Resource = client.target(URI);
        mainList = this.GetAllRows();
    }
    
    // Trả về toàn bộ bảng 
    public ArrayList<GIATIEN_QC_BAO> GetAllRows()throws Exception{
        try {
            ArrayList<GIATIEN_QC_BAO> l = GTBAO_Resource.path("GetAllRows").
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<GIATIEN_QC_BAO>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Thêm một đối tượng
    public String Insert(String viTri, String khoIn, String mauSac, String ghiChu, Float giaTien)throws Exception{
        try {
            String s = GTBAO_Resource.path("Insert").queryParam("viTri", viTri).
                    queryParam("khoIn", khoIn).queryParam("mauSac", mauSac).
                    queryParam("ghiChu", ghiChu).queryParam("giaTien", giaTien).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Sửa một đối tượng
    public String Update(String maGiaTien, String viTri, String khoIn, String mauSac, String ghiChu, Float giaTien)throws Exception{
        try {
            String s = GTBAO_Resource.path("Update").
                    queryParam("maGiaTien", maGiaTien).queryParam("viTri", viTri).
                    queryParam("khoIn", khoIn).queryParam("mauSac", mauSac).
                    queryParam("ghiChu", ghiChu).queryParam("giaTien", giaTien).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String maGiaTien)throws Exception{
        try {
            String s = GTBAO_Resource.path("Delete").queryParam("maGiaTien", maGiaTien).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Trả về một đối tượng GIATIEN_QC_BAO có MaGiaTien = maGiaTien
    public GIATIEN_QC_BAO GetRowByID(String maGiaTien)throws Exception{
        try {
            GIATIEN_QC_BAO obj = GTBAO_Resource.path("GetRowByID").queryParam("maGiaTien", maGiaTien).
                    request(MediaType.APPLICATION_XML).get(GIATIEN_QC_BAO.class);
            return obj;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Kiểm tra MaGiaTien có tồn tại trong bàn hay chưa
    public boolean ValidationID(String maGiaTien) throws Exception{
        try {
            String s = GTBAO_Resource.path("ValidationID").queryParam("maGiaTien", maGiaTien).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
}