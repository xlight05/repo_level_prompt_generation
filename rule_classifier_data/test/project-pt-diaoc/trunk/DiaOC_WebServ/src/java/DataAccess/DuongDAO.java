/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

import Entities.DUONG;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import jdbc.SQLConnection;

/**
 *
 * @author Nixforest
 */
public class DuongDAO {
    // Tạo mới một MaDuong
     public String GetNewID() throws Exception{
        SQLConnection con = new SQLConnection();
        String s = con.SCallFunction("{call DUONG_CreateID(?)}");
        return s;
     }
     
     // Trả về toàn bộ bảng PHUONG
     public ArrayList<DUONG> GetAllRows() throws Exception{
        ArrayList<DUONG> lst = new ArrayList<DUONG>();
        String sql = "select * from DUONG";
        SQLConnection con = new SQLConnection();
        ResultSet rs = con.executeQuery(sql);
        DUONG duong = null;
        while(rs.next())
        {
            duong = new DUONG(rs.getString("MaDuong"), rs.getString("TenDuong"));
            lst.add(duong);
        }
        return lst;
     }
     
     // Thêm một đối tượng
     public Boolean Insert(DUONG b)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call DUONG_Insert(?,?)}");
        cs.setString(1, b.getMaDuong());
        cs.setString(2, b.getTenDuong());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        con.close();
        return result;
     }
     
     // Sửa một đối tượng
     public Boolean Update(DUONG b)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call DUONG_Update(?,?)}");
        cs.setString(1, b.getMaDuong());
        cs.setString(2, b.getTenDuong());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        con.close();
        return result;
     }
     
     // Xóa một đối tượng
     public Boolean Delete(String maDuong)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call DUONG_Delete(?)}");
        cs.setString(1, maDuong);
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        con.close();
        return result;
     }
     
     // Trả về một đối tượng DUONG có MaDuong = maDuong
     public DUONG GetRowByID(String maDuong) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call DUONG_SelectByID(?)}");
        cs.setString(1, maDuong);
        DUONG b = new DUONG();
        ResultSet rs = cs.executeQuery();
        while(rs.next())
        {
            b.setMaDuong(rs.getString("MaDuong"));
            b.setTenDuong(rs.getString("TenDuong"));
        }
        return b;
     }
     
     // Hàm kiểm tra MaDuong đã tồn tại hay chưa
    public boolean ValidationID(String maDuong) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call DUONG_CheckExist(?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
        cs.setString(2, maDuong);
        cs.execute();        
        int n = cs.getInt(1);
        if(n != 0)
            return true;
        return false;
    }
}
