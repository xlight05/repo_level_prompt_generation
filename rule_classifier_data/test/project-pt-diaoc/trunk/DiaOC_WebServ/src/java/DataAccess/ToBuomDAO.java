/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DataAccess;

import Entities.TOBUOM;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import jdbc.SQLConnection;

/**
 *
 * @author VooKa
 */
public class ToBuomDAO {
    
    // Tạo mới một MaToBuom
    public String GetNewID()throws Exception{
        SQLConnection con = new SQLConnection();
        String s= con.SCallFunction("{call TOBUOM_CreateID(?)}");
        return s;
    }
    
    // Trả về toàn bộ bảng
    public ArrayList<TOBUOM> GetAllRows()throws Exception{
        ArrayList<TOBUOM> lst = new ArrayList<TOBUOM>();
        TOBUOM t = null;
        SQLConnection con = new SQLConnection();
        String sql = "select * from TOBUOM";
        ResultSet rs = con.executeQuery(sql);
        while(rs.next())
        {
            t = new TOBUOM(rs.getString("MaToBuom"),rs.getString("MaND"),
                    rs.getString("MaPhieuDangKy"),rs.getString("MaGiaTien"));
            lst.add(t);
        }
        return lst;
    }
    
    // Thêm một đối tượng
    public Boolean Insert(TOBUOM t)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call TOBUOM_Insert(?,?,?,?)}");
        cs.setString(1, t.getMaToBuom());
        cs.setString(2, t.getMaND());
        cs.setString(3, t.getMaPhieuDangKy());
        cs.setString(4, t.getMaGiaTien());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    
    // Sửa một đối tượng
    public Boolean Update(TOBUOM t)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call TOBUOM_Update(?,?,?,?)}");
        cs.setString(1, t.getMaToBuom());
        cs.setString(2, t.getMaND());
        cs.setString(3, t.getMaPhieuDangKy());
        cs.setString(4, t.getMaGiaTien());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String ma)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call TOBUOM_Delete(?)}");
        cs.setString(1, ma);
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    
    // Trả về một đối tượng TOBUOM có MaToBuom = ma
    public TOBUOM GetRowByID(String ma)throws Exception{
        TOBUOM t = new TOBUOM();
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call TOBUOM_SelectByID(?)}");
        cs.setString(1, ma);
        ResultSet rs = cs.executeQuery();
        while(rs.next())
        {
            t.setMaToBuom(rs.getString("MaToBuom"));
            t.setMaND(rs.getString("MaND"));
            t.setMaPhieuDangKy(rs.getString("MaPhieuDangKy"));
            t.setMaGiaTien(rs.getString("MaGiaTien"));
        }
        return t;
    }
    
    // Kiểm tra một MaToBuom đã tồn tại trong bảng hay chưa
    public boolean ValidationID(String maToBuom) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call TOBUOM_CheckExist(?)}");
        cs.registerOutParameter(1,java.sql.Types.INTEGER);
        cs.setString(2, maToBuom);
        cs.execute();        
        int n = cs.getInt(1);
        if(n != 0)
            return true;
        return false;
    }
    
    // Kiểm tra MaND có tồn tại trong bảng TOBUOM hay không?
    public boolean IsMaND_ToBuomExist(String maND) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call TOBUOM_IsMaND_ToBuomExist(?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
        cs.setString(2, maND);
        cs.execute();        
        int n = cs.getInt(1);
        if(n != 0)
            return true;
        return false;
    }
    
    // Kiểm tra MaPhieuDangKy có tồn tại trong bảng TOBUOM hay không?
    public boolean IsMaPhieuDK_ToBuomExist(String maPhieuDK) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call TOBUOM_IsMaPhieuDK_ToBuomExist (?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
        cs.setString(2, maPhieuDK);
        cs.execute();        
        int n = cs.getInt(1);
        if(n != 0)
            return true;
        return false;
    }
    
    // Kiểm tra MaGiaTien có tồn tại trong bảng TOBUOM hay không?
    public boolean IsMaGiaTien_ToBuomExist(String maGiaTien) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call TOBUOM_IsMaGiaTien_ToBuomExist(?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
        cs.setString(2, maGiaTien);
        cs.execute();        
        int n = cs.getInt(1);
        if(n != 0)
            return true;
        return false;
    }
    
    // Trả về một đối tượng TOBUOM có MaPhieuDK = maPhieuDK
    public TOBUOM GetToBuomByMaPhieuDK(String maPhieuDK)throws Exception{
        TOBUOM t = new TOBUOM();
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call TOBUOM_GetToBuom_ByMaPhieuDK(?)}");
        cs.setString(1, maPhieuDK);
        ResultSet rs = cs.executeQuery();
        while(rs.next())
        {
            t.setMaToBuom(rs.getString("MaToBuom"));
            t.setMaND(rs.getString("MaND"));
            t.setMaPhieuDangKy(rs.getString("MaPhieuDangKy"));
            t.setMaGiaTien(rs.getString("MaGiaTien"));
        }
        return t;
    }
}
