/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DataAccess;

import Entities.*;
import java.sql.CallableStatement;
import jdbc.SQLConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author VooKa
 */
public class BaoDAO {
    
    // Tạo mới một MaBao
     public String GetNewID() throws Exception{
        SQLConnection con = new SQLConnection();
        String s = con.SCallFunction("{call BAO_CreateID(?)}");
        return s;
        
     }
     
     // Trả về toàn bộ bảng BAO
     public ArrayList<BAO> GetAllRows() throws Exception{
        ArrayList<BAO> lst = new ArrayList<BAO>();
        String sql = "select * from BAO";
        SQLConnection con = new SQLConnection();
        ResultSet rs = con.executeQuery(sql);
        BAO b = null;
        while(rs.next())
        {
            b = new BAO(rs.getString("MaBao"),rs.getString("TenBao"),rs.getInt("ThuPhatHanh"));
            lst.add(b);
        }
        return lst;
     }
     
     // Thêm một đối tượng
     public Boolean Insert(BAO b)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call BAO_Insert(?,?,?)}");
        cs.setString(1, b.getMaBao());
        cs.setString(2, b.getTenBao());
        cs.setInt(3, b.getThuPhatHanh());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
     }
     
     // Sửa một đối tượng
     public Boolean Update(BAO b)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call BAO_Update(?,?,?)}");
        cs.setString(1, b.getMaBao());
        cs.setString(2, b.getTenBao());
        cs.setInt(3, b.getThuPhatHanh());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
     }
     
     // Xóa một đối tượng
     public Boolean Delete(String mabao)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call BAO_Delete(?)}");
        cs.setString(1, mabao);
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
     }
     
     // Trả về một đối tượng BAO có MaBao = mabao
     public BAO GetRowByID(String mabao) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call BAO_SelectByID(?)}");
        cs.setString(1, mabao);
        BAO b = new BAO();
        ResultSet rs = cs.executeQuery();
        while(rs.next())
        {
            b.setMaBao(rs.getString("MaBao"));
            b.setTenBao(rs.getString("TenBao"));
            b.setThuPhatHanh(rs.getInt("ThuPhatHanh"));
        }
        return b;
     }
     
     // Hàm kiểm tra MaBao đã tồn tại hay chưa
    public boolean ValidationID(String mabao) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call BAO_CheckExist(?)}");
        cs.registerOutParameter(1,java.sql.Types.INTEGER);
        cs.setString(2, mabao);
        cs.execute();        
        int n = cs.getInt(1);
        if(n != 0)
            return true;
        return false;
    }
    
    // Trả về ThuPhatHanh tương ứng với TenBao
     public int GetNgayPHByTenBao(String tenBao) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call BAO_SelectByTen(?)}");
        cs.setString(1, tenBao);
        ResultSet rs = cs.executeQuery();
        int s = 0;
        while(rs.next())
        {
            s = rs.getInt("ThuPhatHanh");
        }
        return s;
     }
}
