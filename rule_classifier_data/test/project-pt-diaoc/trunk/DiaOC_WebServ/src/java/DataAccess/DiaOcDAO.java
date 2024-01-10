/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DataAccess;

import Entities.DIAOC;
import Entities.DIACHI;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import jdbc.SQLConnection;

/**
 *
 * @author VooKa
 */
public class DiaOcDAO {
    
    // Tạo mới một MaDiaOc
    public String GetNewID()throws Exception{
        SQLConnection con = new SQLConnection();
        String s = con.SCallFunction("{call DIAOC_CreateID(?)}");
        return s;
    }
    
    // Trả về toàn bộ bảng DIAOC
    public ArrayList<DIAOC> GetAllRows()throws Exception{
        SQLConnection con = new SQLConnection();
        String sql="select * from DIAOC";
        ResultSet rs = con.executeQuery(sql);
        ArrayList<DIAOC> lst = new ArrayList<DIAOC>();
        DIAOC d = null;
        DiaChiDAO dbDiaChi = new DiaChiDAO();
        while(rs.next())
        {
            String maDiaOc = rs.getString("MaDiaOC");
            String maLoaiDiaOc = rs.getString("MaLoaiDiaOc");
            DIACHI dc = dbDiaChi.GetRowByID(rs.getString("MaDiaChi"));
            Float dienTichDat = rs.getFloat("DienTichDat");
            Float dienTichXayDung = rs.getFloat("DienTichXayDung");
            String huong = rs.getString("Huong");
            String viTri = rs.getString("ViTri");
            String moTa = rs.getString("MoTa");
            String trangThai = rs.getString("TrangThai");
            Float giaBan = rs.getFloat("GiaBan");
            d = new DIAOC(maDiaOc, maLoaiDiaOc, dc, dienTichDat,
                    dienTichXayDung, huong, viTri, moTa, trangThai, giaBan);
            lst.add(d);
        }
        return lst;
    }    
    
    // Thêm một đối tượng DIAOC
    public Boolean Insert(DIAOC d)throws Exception{
        Boolean result=true;
        DiaChiDAO dbDiaChi = new DiaChiDAO();
        if (dbDiaChi.ValidationID(d.getDiaChiDiaOc().getMaDiaChi()) || d.getDiaChiDiaOc().getMaDiaChi().equals(""))
            d.getDiaChiDiaOc().setMaDiaChi(dbDiaChi.GetNewID());
        if (dbDiaChi.Insert(d.getDiaChiDiaOc())==false)
            return false;
        
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call DIAOC_Insert(?,?,?,?,?,?,?,?,?,?)}");
        cs.setString(1, d.getMaDiaOc());
        cs.setString(2, d.getMaLoaiDiaOc());
        cs.setString(3, d.getDiaChiDiaOc().getMaDiaChi()); 
        cs.setFloat(4, d.getDienTichDat());
        cs.setFloat(5, d.getDienTichXayDung());
        cs.setString(6, d.getHuong());
        cs.setString(7, d.getViTri());
        cs.setString(8, d.getMoTa());
        cs.setString(9, d.getTrangThai());
        cs.setFloat(10, d.getGiaBan());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    
    // Sửa một đối tượng DIAOC
    public Boolean Update(DIAOC d)throws Exception{
        Boolean result=true;
        DiaChiDAO dbDiaChi = new DiaChiDAO();
        if (dbDiaChi.Update(d.getDiaChiDiaOc())==false)
            return false;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call DIAOC_Update(?,?,?,?,?,?,?,?,?,?)}");
        cs.setString(1, d.getMaDiaOc());
        cs.setString(2, d.getMaLoaiDiaOc());
        cs.setString(3, d.getDiaChiDiaOc().getMaDiaChi()); 
        cs.setFloat(4, d.getDienTichDat());
        cs.setFloat(5, d.getDienTichXayDung());
        cs.setString(6, d.getHuong());
        cs.setString(7, d.getViTri());
        cs.setString(8, d.getMoTa());
        cs.setString(9, d.getTrangThai());
        cs.setFloat(10, d.getGiaBan());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String ma)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call DIAOC_Delete(?)}");
        cs.setString(1, ma);
        int n = cs.executeUpdate();
        if(n == 0)
            result=false;
        //con.close();
        return result;
    }
    
    // Trả về một đối tượng DIAOC có MaDiaOc = ma
    public DIAOC GetRowByID(String ma)throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call DIAOC_SelectByID(?)}");
        cs.setString(1, ma);
        ResultSet rs = cs.executeQuery();
        DIAOC d = new DIAOC();
        DiaChiDAO dbDiaChi = new DiaChiDAO();
        while(rs.next())
        {
            d.setMaDiaOc(rs.getString("MaDiaOC"));
            d.setMaLoaiDiaOc(rs.getString("MaLoaiDiaOC"));
            d.setDiaChiDiaOc(dbDiaChi.GetRowByID(rs.getString("MaDiaChi")));
            d.setDienTichDat(rs.getFloat("DienTichDat"));
            d.setDienTichXayDung(rs.getFloat("DienTichXayDung"));
            d.setHuong(rs.getString("Huong"));
            d.setViTri(rs.getString("ViTri"));
            d.setMoTa(rs.getString("MoTa"));
            d.setTrangThai(rs.getString("TrangThai"));
            d.setGiaBan(rs.getFloat("GiaBan"));
        }
        return d;
    }
    
    // Kiểm tra xem một MaDiaOc có tồn tại trong bảng hay chưa
    public boolean ValidationID(String MaDiaOc) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call DIAOC_CheckExist(?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
        cs.setString(2, MaDiaOc);
        cs.execute();        
        int n = cs.getInt(1);
        if(n != 0)
            return true;
        return false;
    }
    
    // Kiểm tra xem một MaLoaiDiaOc có tồn tại trong bảng hay không
    public boolean IsLoaiDiaOcExist(String MaLoaiDiaOc) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call DIAOC_IsLoaiDiaOcExist(?)}");
        cs.registerOutParameter(1,java.sql.Types.INTEGER);
        cs.setString(2, MaLoaiDiaOc);
        cs.execute();        
        int n = cs.getInt(1);
        if(n!=0)
            return true;
        return false;
    }
    
    // Trả về những đối tượng DIAOC theo MaLoaiDiaOc
    public ArrayList<DIAOC> GetRowsByMaLoaiDiaOc(String maLoaiDiaOc)throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call DIAOC_GetDIAOCByMaLoaiDiaOc(?)}");
        cs.setString(1, maLoaiDiaOc);
        ResultSet rs = cs.executeQuery();
        ArrayList<DIAOC> lst = new ArrayList<DIAOC>();
        DiaChiDAO dbDiaChi = new DiaChiDAO();
        while(rs.next())
        {
            String maDiaOc = rs.getString("MaDiaOC");
            String maLoaiDiaOcX = rs.getString("MaLoaiDiaOc");
            DIACHI dc = dbDiaChi.GetRowByID(rs.getString("MaDiaChi"));
            Float dienTichDat = rs.getFloat("DienTichDat");
            Float dienTichXayDung = rs.getFloat("DienTichXayDung");
            String huong = rs.getString("Huong");
            String viTri = rs.getString("ViTri");
            String moTa = rs.getString("MoTa");
            String trangThai = rs.getString("TrangThai");
            Float giaBan = rs.getFloat("GiaBan");
            DIAOC _d = new DIAOC(maDiaOc, maLoaiDiaOcX, dc, dienTichDat,
                    dienTichXayDung, huong, viTri, moTa, trangThai, giaBan);
            lst.add(_d);
        }
        return lst;
    }
    
    // Trả về những đối tượng DIAOC theo TrangThai
    public ArrayList<DIAOC> GetRowsByTrangThai(String trangThai)throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call DIAOC_GetDIAOCByTrangThai(?)}");
        cs.setNString(1, trangThai);
        ResultSet rs = cs.executeQuery();
        ArrayList<DIAOC> lst = new ArrayList<DIAOC>();
        DiaChiDAO dbDiaChi = new DiaChiDAO();
        while(rs.next())
        {
            String maDiaOc = rs.getString("MaDiaOC");
            String maLoaiDiaOc = rs.getString("MaLoaiDiaOc");
            DIACHI dc = dbDiaChi.GetRowByID(rs.getString("MaDiaChi"));
            Float dienTichDat = rs.getFloat("DienTichDat");
            Float dienTichXayDung = rs.getFloat("DienTichXayDung");
            String huong = rs.getString("Huong");
            String viTri = rs.getString("ViTri");
            String moTa = rs.getString("MoTa");
            String trangThaiDiaOc = rs.getString("TrangThai");
            Float giaBan = rs.getFloat("GiaBan");
            DIAOC _d = new DIAOC(maDiaOc, maLoaiDiaOc, dc, dienTichDat,
                    dienTichXayDung, huong, viTri, moTa, trangThaiDiaOc, giaBan);
            lst.add(_d);
        }
        return lst;
    }
    
    // Trả về MaND tương ứng với MaDiaOc (Từ TOBUOM)
    public String GetMaND_ToBuom_From_MaDiaOc(String maDiaOc) throws Exception{
        String maND = "";
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call DIAOC_GetMaND_FromToBuom_ByMaDiaOc(?)}");
        cs.setString(1, maDiaOc);
        ResultSet rs = cs.executeQuery();
        while(rs.next()){
            maND = rs.getString("MaND");
        }
        return maND;
    }
    
    // Trả về MaND tương ứng với MaDiaOc (Từ QC_BAO)
    public String GetMaND_QCBao_From_MaDiaOc(String maDiaOc) throws Exception{
        String maND = "";
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call DIAOC_GetMaND_FromQCBao_ByMaDiaOc(?)}");
        cs.setString(1, maDiaOc);
        ResultSet rs = cs.executeQuery();
        while(rs.next()){
            maND = rs.getString("MaND");
        }
        return maND;
    }
    
    // Trả về MaND tương ứng với MaDiaOc (Từ QC_BANG)
    public String GetMaND_QCBang_From_MaDiaOc(String maDiaOc) throws Exception{
        String maND = "";
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call DIAOC_GetMaND_FromQCBang_ByMaDiaOc(?)}");
        cs.setString(1, maDiaOc);
        ResultSet rs = cs.executeQuery();
        while(rs.next()){
            maND = rs.getString("MaND");
        }
        return maND;
    }
}
