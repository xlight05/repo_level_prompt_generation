/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

import Entities.QUAN;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import jdbc.SQLConnection;

/**
 *
 * @author Nixforest
 */
public class QuanDAO {
    // Tạo mới một MaQuan
     public String GetNewID() throws Exception{
        SQLConnection con = new SQLConnection();
        String s = con.SCallFunction("{call QUAN_CreateID(?)}");
        return s;
     }
     
     // Trả về toàn bộ bảng QUAN
     public ArrayList<QUAN> GetAllRows() throws Exception{
        ArrayList<QUAN> lst = new ArrayList<QUAN>();
        String sql = "select * from QUAN";
        SQLConnection con = new SQLConnection();
        ResultSet rs = con.executeQuery(sql);
        QUAN quan = null;
        while(rs.next())
        {
            quan = new QUAN(rs.getString("MaQuan"), rs.getString("TenQuan"));
            lst.add(quan);
        }
        return lst;
     }
     
     // Thêm một đối tượng
     public Boolean Insert(QUAN b)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call QUAN_Insert(?,?)}");
        cs.setString(1, b.getMaQuan());
        cs.setString(2, b.getTenQuan());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        con.close();
        return result;
     }
     
     // Sửa một đối tượng
     public Boolean Update(QUAN b)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call QUAN_Update(?,?)}");
        cs.setString(1, b.getMaQuan());
        cs.setString(2, b.getTenQuan());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        con.close();
        return result;
     }
     
     // Xóa một đối tượng
     public Boolean Delete(String maQuan)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call QUAN_Delete(?)}");
        cs.setString(1, maQuan);
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        con.close();
        return result;
     }
     
     // Trả về một đối tượng QUAN có MaQuan = maQuan
     public QUAN GetRowByID(String maQuan) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call QUAN_SelectByID(?)}");
        cs.setString(1, maQuan);
        QUAN b = new QUAN();
        ResultSet rs = cs.executeQuery();
        while(rs.next())
        {
            b.setMaQuan(rs.getString("MaQuan"));
            b.setTenQuan(rs.getString("TenQuan"));
        }
        return b;
     }
     
     // Hàm kiểm tra MaQuan đã tồn tại hay chưa
    public boolean ValidationID(String maQuan) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call QUAN_CheckExist(?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
        cs.setString(2, maQuan);
        cs.execute();        
        int n = cs.getInt(1);
        if(n != 0)
            return true;
        return false;
    }
}
