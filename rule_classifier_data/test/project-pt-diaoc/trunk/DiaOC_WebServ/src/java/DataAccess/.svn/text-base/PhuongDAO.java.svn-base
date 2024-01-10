/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

import Entities.PHUONG;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import jdbc.SQLConnection;

/**
 *
 * @author Nixforest
 */
public class PhuongDAO {
    // Tạo mới một MaPhuong
     public String GetNewID() throws Exception{
        SQLConnection con = new SQLConnection();
        String s = con.SCallFunction("{call PHUONG_CreateID(?)}");
        return s;
     }
     
     // Trả về toàn bộ bảng PHUONG
     public ArrayList<PHUONG> GetAllRows() throws Exception{
        ArrayList<PHUONG> lst = new ArrayList<PHUONG>();
        String sql = "select * from PHUONG";
        SQLConnection con = new SQLConnection();
        ResultSet rs = con.executeQuery(sql);
        PHUONG phuong = null;
        while(rs.next())
        {
            phuong = new PHUONG(rs.getString("MaPhuong"), rs.getString("TenPhuong"));
            lst.add(phuong);
        }
        return lst;
     }
     
     // Thêm một đối tượng
     public Boolean Insert(PHUONG b)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call PHUONG_Insert(?,?)}");
        cs.setString(1, b.getMaPhuong());
        cs.setString(2, b.getTenPhuong());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        con.close();
        return result;
     }
     
     // Sửa một đối tượng
     public Boolean Update(PHUONG b)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call PHUONG_Update(?,?)}");
        cs.setString(1, b.getMaPhuong());
        cs.setString(2, b.getTenPhuong());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        con.close();
        return result;
     }
     
     // Xóa một đối tượng
     public Boolean Delete(String maPhuong)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call PHUONG_Delete(?)}");
        cs.setString(1, maPhuong);
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        con.close();
        return result;
     }
     
     // Trả về một đối tượng PHUONG có MaPhuong = maPhuong
     public PHUONG GetRowByID(String maPhuong) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call PHUONG_SelectByID(?)}");
        cs.setString(1, maPhuong);
        PHUONG b = new PHUONG();
        ResultSet rs = cs.executeQuery();
        while(rs.next())
        {
            b.setMaPhuong(rs.getString("MaPhuong"));
            b.setTenPhuong(rs.getString("TenPhuong"));
        }
        return b;
     }
     
     // Hàm kiểm tra MaPhuong đã tồn tại hay chưa
    public boolean ValidationID(String maPhuong) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call PHUONG_CheckExist(?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
        cs.setString(2, maPhuong);
        cs.execute();        
        int n = cs.getInt(1);
        if(n != 0)
            return true;
        return false;
    }
}
