/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DataAccess;

import Entities.PHIEUDANGKY;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import jdbc.SQLConnection;

/**
 *
 * @author VooKa
 */
public class PhieuDangKyDAO {
    
    // Tạo mới một MaPhieuDangKy
    public String GetNewID()throws Exception{
        SQLConnection con = new SQLConnection();
        String s = con.SCallFunction("{call PHIEUDANGKY_CreateID(?)}");
        return s;
    }
    
    // Trả về toàn bộ bảng PHIEUDANGKY
    public ArrayList<PHIEUDANGKY> GetAllRows()throws Exception{
        ArrayList<PHIEUDANGKY> lst = new ArrayList<PHIEUDANGKY>();
        PHIEUDANGKY p = null;
        SQLConnection con = new SQLConnection();
        String sql = "select * from PHIEUDANGKY";
        ResultSet rs = con.executeQuery(sql);
        while(rs.next())
        {
            p = new PHIEUDANGKY(rs.getString("MaPhieuDangKy"),rs.getString("MaDiaOC"),
                    rs.getDate("TuNgay"),rs.getDate("DenNgay"),rs.getFloat("SoTien"),
                    rs.getDate("ThoiGianHenChupAnh"),rs.getString("MaKH"));
            lst.add(p);
        }
        return lst;
    }
    
    // Thêm một đối tượng
    public Boolean Insert(PHIEUDANGKY p)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call PHIEUDANGKY_Insert(?,?,?,?,?,?,?)}");
        cs.setString(1, p.getMaPhieuDangKy());
        cs.setString(2, p.getMaDiaOc());
        Date d1 = p.getTuNgay();
        cs.setString(3, d1.toString());
        Date d2 = p.getDenNgay();
        cs.setString(4, d2.toString());
        cs.setFloat(5, p.getSoTien());
        Date d = p.getThoiGianHenChupAnh();
        cs.setString(6, d.toString());
        cs.setString(7, p.getMaKH());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    
    // Sửa một đối tượng
    public Boolean Update(PHIEUDANGKY p)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call PHIEUDANGKY_Update(?,?,?,?,?,?,?)}");
        cs.setString(1, p.getMaPhieuDangKy());
        cs.setString(2, p.getMaDiaOc());
        Date d1 = p.getTuNgay();
        cs.setString(3, d1.toString());
        Date d2 = p.getDenNgay();
        cs.setString(4, d2.toString());
        cs.setFloat(5, p.getSoTien());
        Date d = p.getThoiGianHenChupAnh();
        cs.setString(6, d.toString());
        cs.setString(7, p.getMaKH());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String ma)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call PHIEUDANGKY_Delete(?)}");
        cs.setString(1, ma);
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    
    // Trả về một đối tượng PHIEUDANGKY có MaPhieuDangKy = ma
    public PHIEUDANGKY GetRowByID(String ma)throws Exception{
        PHIEUDANGKY p = new PHIEUDANGKY();
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call PHIEUDANGKY_SelectByID(?)}");
        cs.setString(1, ma);
        ResultSet rs = cs.executeQuery();
        while(rs.next())
        {
            p.setMaPhieuDangKy(rs.getString("MaPhieuDangKy"));
            p.setMaDiaOc(rs.getString("MaDiaOC"));
            p.setTuNgay(rs.getDate("TuNgay"));
            p.setDenNgay(rs.getDate("DenNgay"));
            p.setSoTien(rs.getFloat("SoTien"));
            p.setThoiGianHenChupAnh(rs.getDate("ThoiGianHenChupAnh"));
            p.setMaKH(rs.getString("MaKH"));
        }
        return p;
    }
    
    // Kiểm tra xem một MaPhieuDangKy có tồn tại trong bảng hay chưa
    public boolean ValidationID(String maPhieuDangKy) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call PHIEUDANGKY_CheckExist(?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
        cs.setString(2, maPhieuDangKy);
        cs.execute();        
        int n = cs.getInt(1);
        return n != 0;
    }
    
    // Kiểm tra xem một MaDiaOc có tồn tại trong bảng hay không?
    public boolean IsDiaOcExist(String maDiaOc) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call PHIEUDANGKY_IsDiaOcExist(?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
        cs.setString(2, maDiaOc);
        cs.execute();        
        int n = cs.getInt(1);
        return n != 0;
    }
    
    // Kiểm tra xem một MaKH có tồn tại trong bảng hay không?
    public boolean IsKhachHangExist(String maKH) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call PHIEUDANGKY_IsKhachHangExist(?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
        cs.setString(2, maKH);
        cs.execute();        
        int n = cs.getInt(1);
        return n != 0;
    }
    
    // Trả về một đối tượng PHIEUDANGKY có MaDiaOc = maDiaOc với Mã Phiếu đăng ký là lớn nhất
    public PHIEUDANGKY GetPhieuDKByMaDiaOc(String maDiaOc)throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call PHIEUDANGKY_GetPhieuDK_ByMaDiaOc(?)}");
        cs.setString(1, maDiaOc);
        ResultSet rs = cs.executeQuery();
        PHIEUDANGKY p = new PHIEUDANGKY();
        while(rs.next())
        {
            p.setMaPhieuDangKy(rs.getString("MaPhieuDangKy"));
            p.setMaDiaOc(rs.getString("MaDiaOC"));
            p.setTuNgay(rs.getDate("TuNgay"));
            p.setDenNgay(rs.getDate("DenNgay"));
            p.setSoTien(rs.getFloat("SoTien"));
            p.setThoiGianHenChupAnh(rs.getDate("ThoiGianHenChupAnh"));
            p.setMaKH(rs.getString("MaKH"));
        }
        return p;
    }
    
    // Trả về các đối tượng PHIEUDANGKY có MaDiaOc = maDiaOc
    public ArrayList<PHIEUDANGKY> GetAllPhieuDKByMaDiaOc(String maDiaOc)throws Exception{
        ArrayList<PHIEUDANGKY> px = new ArrayList<PHIEUDANGKY>();
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call PHIEUDANGKY_GetAllPhieuDK_ByMaDiaOc(?)}");
        cs.setString(1, maDiaOc);
        ResultSet rs = cs.executeQuery();
        PHIEUDANGKY p = new PHIEUDANGKY();
        while(rs.next())
        {
            p.setMaPhieuDangKy(rs.getString("MaPhieuDangKy"));
            p.setMaDiaOc(rs.getString("MaDiaOC"));
            p.setTuNgay(rs.getDate("TuNgay"));
            p.setDenNgay(rs.getDate("DenNgay"));
            p.setSoTien(rs.getFloat("SoTien"));
            p.setThoiGianHenChupAnh(rs.getDate("ThoiGianHenChupAnh"));
            p.setMaKH(rs.getString("MaKH"));
            px.add(p);
        }
        return px;
    }
}
