/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RSCall;

import Entities.DIAOC;
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
public final class DiaOcRSc {    
    
    String mHost = Utilities.Utilities.host;
    String mPort = Utilities.Utilities.port;
    final Client client = ClientBuilder.newClient();
    final String URI = "http://"+mHost+":"+mPort+"/DiaOC_WebServ/Resources/DIAOC/";
    WebTarget DIAOC_Resource;
    
    // Mảng quản lý các đối tượng DIAOC
    public ArrayList<DIAOC> mainList = new ArrayList<DIAOC> ();
    
    // Constructor
    public DiaOcRSc() throws Exception{
        DIAOC_Resource = client.target(URI);
        mainList = GetAllRows();
    }
    
    // Trả về toàn bộ bảng DIAOC
    public ArrayList<DIAOC> GetAllRows()throws Exception{
        try {
            ArrayList<DIAOC> l = DIAOC_Resource.path("GetAllRows").
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<DIAOC>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Thêm một đối tượng DIAOC
    public String Insert(String maLoaiDiaOc, String maThanhPho, String maQuan,
            String maPhuong, String maDuong, String DiaChiCT, Float dienTichDat,
            Float dienTichXayDung, String huong, String viTri,
            String moTa, String trangThai, Float giaBan)throws Exception{
        try {
            String s = DIAOC_Resource.path("Insert").queryParam("maLoaiDiaOc", maLoaiDiaOc).
                    queryParam("maThanhPho", maThanhPho).queryParam("maQuan", maQuan).
                    queryParam("maPhuong", maPhuong).queryParam("maDuong", maDuong).
                    queryParam("DiaChiCT", DiaChiCT).queryParam("dienTichDat", dienTichDat).
                    queryParam("dienTichXayDung", dienTichXayDung).queryParam("huong", huong).
                    queryParam("viTri", viTri).queryParam("moTa", moTa).
                    queryParam("trangThai", trangThai).queryParam("giaBan", giaBan).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        } 
    }
    
    // Sửa một đối tượng
    public String Update(String maDiaOc,String maLoaiDiaOc, String maThanhPho, String maQuan,
            String maPhuong, String maDuong, String DiaChiCT, Float dienTichDat,
            Float dienTichXayDung, String huong, String viTri,
            String moTa, String trangThai, Float giaBan)throws Exception{
        try {
            String s = DIAOC_Resource.path("Update").
                    queryParam("maDiaOc", maDiaOc).queryParam("maLoaiDiaOc", maLoaiDiaOc).
                    queryParam("maThanhPho", maThanhPho).queryParam("maQuan", maQuan).
                    queryParam("maPhuong", maPhuong).queryParam("maDuong", maDuong).
                    queryParam("DiaChiCT", DiaChiCT).queryParam("dienTichDat", dienTichDat).
                    queryParam("dienTichXayDung", dienTichXayDung).queryParam("huong", huong).
                    queryParam("viTri", viTri).queryParam("moTa", moTa).
                    queryParam("trangThai", trangThai).queryParam("giaBan", giaBan).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        } 
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String maDiaOc)throws Exception{
        try {
            String s = DIAOC_Resource.path("Delete").queryParam("maDiaOc", maDiaOc).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }       
    }
    
    // Trả về một đối tượng DIAOC có MaDicOc = maDiaOc
    public DIAOC GetRowByID(String maDiaOc)throws Exception{
        try {
            DIAOC d = DIAOC_Resource.path("GetRowByID").queryParam("maDiaOc", maDiaOc).
                    request(MediaType.APPLICATION_XML).get(DIAOC.class);
            return d;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Kiểm tra xem một MaDiaOc có tồn tại trong bảng hay chưa
    public boolean ValidationID(String maDiaOc) throws Exception{
        try {
            String s = DIAOC_Resource.path("ValidationID").queryParam("maDiaOc", maDiaOc).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Kiểm tra xem một MaLoaiDiaOc có tồn tại trong bảng hay không
    public boolean IsLoaiDiaOcExist(String maLoaiDiaOc) throws Exception{
        try {
            String s = DIAOC_Resource.path("IsLoaiDiaOcExist").queryParam("maLoaiDiaOc", maLoaiDiaOc).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s.trim().equals("true");
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Trả về những đối tượng DIAOC theo MaLoaiDiaOc
    public ArrayList<DIAOC> GetRowsByMaLoaiDiaOc(String maLoaiDiaOc)throws Exception{
        try {
            ArrayList<DIAOC> l = DIAOC_Resource.path("GetRowsByMaLoaiDiaOc").queryParam("maLoaiDiaOc", maLoaiDiaOc).
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<DIAOC>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Trả về những đối tượng DIAOC theo TrangThai
    public ArrayList<DIAOC> GetRowsByTrangThai(String trangThai)throws Exception{
        try {
            ArrayList<DIAOC> l = DIAOC_Resource.path("GetRowsByTrangThai").queryParam("trangThai", trangThai).
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<DIAOC>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
    
    // Trả về MaND tương ứng với MaDiaOc (Từ TOBUOM)
    public String GetMaND_ToBuom_From_MaDiaOc(String maDiaOc) throws Exception{
        try {
            String s = DIAOC_Resource.path("GetMaND_ToBuom_From_MaDiaOc").queryParam("maDiaOc", maDiaOc).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        } 
    }
    
    // Trả về MaND tương ứng với MaDiaOc (Từ QC_BAO)
    public String GetMaND_QCBao_From_MaDiaOc(String maDiaOc) throws Exception{
        try {
            String s = DIAOC_Resource.path("GetMaND_QCBao_From_MaDiaOc").queryParam("maDiaOc", maDiaOc).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        } 
    }
    
    // Trả về MaND tương ứng với MaDiaOc (Từ QC_BANG)
    public String GetMaND_QCBang_From_MaDiaOc(String maDiaOc) throws Exception{
        try {
            String s = DIAOC_Resource.path("GetMaND_QCBang_From_MaDiaOc").queryParam("maDiaOc", maDiaOc).
                    request(MediaType.TEXT_PLAIN).get(String.class);   
            return s;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        } 
    }    
    
    // Trả về MaND tương ứng với MaDiaOc
    public String GetMaND_From_MaDiaOc(String maDiaOc) throws Exception{
        String MaND = "";
        MaND = GetMaND_ToBuom_From_MaDiaOc(maDiaOc);
        if (MaND.equals("")) {
            MaND = GetMaND_QCBang_From_MaDiaOc(maDiaOc);
        }
        if (MaND.equals("")) {
            MaND = GetMaND_QCBao_From_MaDiaOc(maDiaOc);
        }
        return MaND;
    }
}