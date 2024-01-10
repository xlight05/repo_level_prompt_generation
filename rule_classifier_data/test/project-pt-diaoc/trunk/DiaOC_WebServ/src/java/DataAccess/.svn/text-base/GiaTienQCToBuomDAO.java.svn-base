/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DataAccess;

import Entities.GIATIEN_TOBUOM;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import jdbc.SQLConnection;

/**
 *
 * @author VooKa
 */
public class GiaTienQCToBuomDAO {
    
    // Tạo mới một MaGiaTien
    public String GetNewID()throws Exception{
       SQLConnection con = new SQLConnection();
        String s = con.SCallFunction("{call GIATIEN_QCT_CreateID(?)}");
        return s;
    }
    
    // Trả về toàn bộ bảng
    public ArrayList<GIATIEN_TOBUOM> GetAllRows()throws Exception{
        SQLConnection con = new SQLConnection();
        String sql = "select * from GIATIEN_QCT";
        ArrayList<GIATIEN_TOBUOM> lst = new ArrayList<GIATIEN_TOBUOM>();
        GIATIEN_TOBUOM g = null;
        ResultSet rs = con.executeQuery(sql);
        while(rs.next())
        {
            g = new GIATIEN_TOBUOM(rs.getString("MaGiaTien"),rs.getInt("SoLuong"),rs.getFloat("GiaTien"));
            lst.add(g);
        }
        return lst;
    }
    
    // Thêm một đối tượng
    public Boolean Insert(GIATIEN_TOBUOM g) throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call GIATIEN_QCT_Insert(?,?,?)}");
        cs.setString(1, g.getMaGiaTien());
        cs.setInt(2, g.getSoLuong());
        cs.setFloat(3, g.getGiaTien());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    
    // Sửa một đối tượng
    public Boolean Update(GIATIEN_TOBUOM g)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call GIATIEN_QCT_Update(?,?,?)}");
        cs.setString(1, g.getMaGiaTien());
        cs.setInt(2, g.getSoLuong());
        cs.setFloat(3, g.getGiaTien());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String ma)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call GIATIEN_QCT_Delete(?)}");
        cs.setString(1, ma);
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;

        return result;
    }
    
    // Trả về một đối tượng GIATIEN_TOBUOM có MaGiaTien = ma
    public GIATIEN_TOBUOM GetRowByID(String ma)throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call GIATIEN_QCT_SelectByID(?)}");
        cs.setString(1, ma);
        GIATIEN_TOBUOM g = new GIATIEN_TOBUOM();
        ResultSet rs = cs.executeQuery();
        while(rs.next())
        {
            g.setMaGiaTien(rs.getString("MaGiaTien"));
            g.setSoLuong(rs.getInt("SoLuong"));
            g.setGiaTien(rs.getFloat("GiaTien"));
        }
        return g;
    }
    
    // Hàm kiểm tra MaGiaTien đã tồn tại hay chưa
    public boolean ValidationID(String maGiaTien) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call GIATIEN_QCT_CheckExist(?)}");
        cs.registerOutParameter(1,java.sql.Types.INTEGER);
        cs.setString(2, maGiaTien);
        cs.execute();        
        int n = cs.getInt(1);
        if(n != 0)
            return true;
        return false;
    }
    
    // Trả về GiaTien tương ứng với Soluong
    public float GetGiaBySoLuong(int soLuong)throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call GIATIEN_QCT_GetGia_BySoLuong(?)}");
        cs.setInt(1, soLuong);
        ResultSet rs = cs.executeQuery();
        float t = (float) 0.0;
        while(rs.next())
        {
            t = rs.getFloat("GiaTien");
        }
        return t;
    }
    
    // Trả về MaGiaTien tương ứng với Soluong
    public String GetIDBySoLuong(int soLuong)throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call GIATIEN_QCT_GetID_BySoLuong(?)}");
        cs.setInt(1, soLuong);
        ResultSet rs = cs.executeQuery();
        String t = "";
        while(rs.next())
        {
            t = rs.getString("MaGiaTien");
        }
        return t;
    }
}
