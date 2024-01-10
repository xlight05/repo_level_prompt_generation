/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

import Entities.DIACHI;
import Entities.KHACHHANG;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import jdbc.SQLConnection;
/**
 *
 * @author Nixforest
 */
public class KhachHangDAO {
    
    // Tạo một MaKH mới
    public String GetNewID()throws Exception{
        SQLConnection con = new SQLConnection();
        String s = con.SCallFunction("{call KHACHHANG_CreateID(?)}");
        return s;
    }
    
    // Trả về toàn bộ bảng KHACHHANG
    public ArrayList<KHACHHANG> GetAllRows()throws Exception{
        SQLConnection con = new SQLConnection();
        String sql = "select * from KHACHHANG";
        ResultSet rs = con.executeQuery(sql);
        ArrayList<KHACHHANG> lst = new ArrayList<KHACHHANG>();
        KHACHHANG k = null;
        DiaChiDAO dbDiaChi = new DiaChiDAO();
        while(rs.next())
        {
            String maKH = rs.getString("MaKH");
            String hoTen = rs.getString("HoTen");
            DIACHI dc = dbDiaChi.GetRowByID(rs.getString("MaDiaChi"));
            String soDT = rs.getString("SoDT");
            String email = rs.getString("Email");
            k = new KHACHHANG(maKH, hoTen, dc, soDT, email);
            lst.add(k);
        }
        return lst;
    }
    
    // Thêm một KHACHHANG
    public Boolean Insert(KHACHHANG k)throws Exception{
        Boolean result = true;
        DiaChiDAO dbDiaChi = new DiaChiDAO();
        if (dbDiaChi.ValidationID(k.getDiaChiKH().getMaDiaChi()) || k.getDiaChiKH().getMaDiaChi().equals(""))
            k.getDiaChiKH().setMaDiaChi(dbDiaChi.GetNewID());
        if (dbDiaChi.Insert(k.getDiaChiKH())==false)
            return false;
        
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call KHACHHANG_Insert(?,?,?,?,?)}");
        cs.setString(1, k.getMaKH());
        cs.setString(2, k.getHoTen());
        cs.setString(3, k.getDiaChiKH().getMaDiaChi());
        cs.setString(4, k.getSoDT());
        cs.setString(5, k.getEmail());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    
    // Sửa một đối tượng
    public Boolean Update(KHACHHANG k)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        DiaChiDAO dbDiaChi = new DiaChiDAO();
        if (dbDiaChi.Update(k.getDiaChiKH())==false)
            return false;
        CallableStatement cs = con.prepareCall("{call KHACHHANG_Update(?,?,?,?,?)}");
        cs.setString(1, k.getMaKH());
        cs.setString(2, k.getHoTen());
        cs.setString(3, k.getDiaChiKH().getMaDiaChi());
        cs.setString(4, k.getSoDT());
        cs.setString(5, k.getEmail());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String ma)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call KHACHHANG_Delete(?)}");
        cs.setString(1, ma);
        int n = cs.executeUpdate();
        if(n == 0)
            result=false;
        return result;
    }
    
    // Trả về một KHACHHANG có MaKH = ma
    public KHACHHANG GetRowByID(String ma)throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call KHACHHANG_SelectByID(?)}");
        cs.setString(1, ma);
        ResultSet rs = cs.executeQuery();
        KHACHHANG k = new KHACHHANG();
        DiaChiDAO dbDiaChi = new DiaChiDAO();
        while(rs.next())
        {
            k.setMaKH(rs.getString("MaKH"));
            k.setHoTen(rs.getString("HoTen"));
            DIACHI dc = dbDiaChi.GetRowByID(rs.getString("MaDiaChi"));
            k.setDiaChiKH(dc);
            k.setSoDT(rs.getString("SoDT"));
            k.setEmail(rs.getString("Email"));
        }
        return k;
    }
    
    // Hàm kiểm tra MaKH đã tồn tại hay chưa
    public boolean ValidationID(String maKH) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call KHACHHANG_CheckExist(?)}");
        cs.registerOutParameter(1,java.sql.Types.INTEGER);
        cs.setString(2, maKH);
        cs.execute();        
        int n = cs.getInt(1);
        if(n != 0)
            return true;
        return false;
    }
    
    // Trả về những đối tượng KHACHHANG theo HoTen
    public ArrayList<KHACHHANG> GetRowsByName(String hoTen)throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call KHACHHANG_SelectByName(?)}");
        cs.setString(1, hoTen);
        ResultSet rs = cs.executeQuery();
        ArrayList<KHACHHANG> lst = new ArrayList<KHACHHANG>();
        KHACHHANG kh = null;
        DiaChiDAO dbDiaChi = new DiaChiDAO();
        while(rs.next())
        {
            String maKH = rs.getString("MaKH");
            String hoTenKH = rs.getString("HoTen");
            DIACHI dc = dbDiaChi.GetRowByID(rs.getString("MaDiaChi"));
            String soDT = rs.getString("SoDT");
            String email = rs.getString("Email");
            kh = new KHACHHANG(maKH, hoTenKH, dc, soDT, email);
            lst.add(kh);
        }
        return lst;
    }
    
    // Trả về những đối tượng KHACHHANG theo Email
    public ArrayList<KHACHHANG> GetRowsByEmail(String email)throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call KHACHHANG_SelectByEmail(?)}");
        cs.setString(1, email);
        ResultSet rs = cs.executeQuery();
        ArrayList<KHACHHANG> lst = new ArrayList<KHACHHANG>();
        KHACHHANG kh = null;
        DiaChiDAO dbDiaChi = new DiaChiDAO();
        while(rs.next())
        {
            String maKH = rs.getString("MaKH");
            String hoTen = rs.getString("HoTen");
            DIACHI dc = dbDiaChi.GetRowByID(rs.getString("MaDiaChi"));
            String soDT = rs.getString("SoDT");
            String emailKH = rs.getString("Email");
            kh = new KHACHHANG(maKH, hoTen, dc, soDT, emailKH);
            lst.add(kh);
        }
        return lst;
    }
}
