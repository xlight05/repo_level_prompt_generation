/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

import Entities.THANHPHO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import jdbc.SQLConnection;

/**
 *
 * @author Nixforest
 */
public class ThanhPhoDAO {
    // Tạo mới một MaThanhPho
     public String GetNewID() throws Exception{
        SQLConnection con = new SQLConnection();
        String s = con.SCallFunction("{call THANHPHO_CreateID(?)}");
        return s;
     }
     
     // Trả về toàn bộ bảng THANHPHO
     public ArrayList<THANHPHO> GetAllRows() throws Exception{
        ArrayList<THANHPHO> lst = new ArrayList<THANHPHO>();
        String sql = "select * from THANHPHO";
        SQLConnection con = new SQLConnection();
        ResultSet rs = con.executeQuery(sql);
        THANHPHO thanhpho = null;
        while(rs.next())
        {
            thanhpho = new THANHPHO(rs.getString("MaThanhPho"), rs.getString("TenThanhPho"));
            lst.add(thanhpho);
        }
        return lst;
     }
     
     // Thêm một đối tượng
     public Boolean Insert(THANHPHO b)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call THANHPHO_Insert(?,?)}");
        cs.setString(1, b.getMaThanhPho());
        cs.setString(2, b.getTenThanhPho());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        con.close();
        return result;
     }
     
     // Sửa một đối tượng
     public Boolean Update(THANHPHO b)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call THANHPHO_Update(?,?)}");
        cs.setString(1, b.getMaThanhPho());
        cs.setString(2, b.getTenThanhPho());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        con.close();
        return result;
     }
     
     // Xóa một đối tượng
     public Boolean Delete(String maThanhPho)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call THANHPHO_Delete(?)}");
        cs.setString(1, maThanhPho);
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        con.close();
        return result;
     }
     
     // Trả về một đối tượng THANHPHO có MaThanhPho = maThanhPho
     public THANHPHO GetRowByID(String maThanhPho) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call THANHPHO_SelectByID(?)}");
        cs.setString(1, maThanhPho);
        THANHPHO b = new THANHPHO();
        ResultSet rs = cs.executeQuery();
        while(rs.next())
        {
            b.setMaThanhPho(rs.getString("MaThanhPho"));
            b.setTenThanhPho(rs.getString("TenThanhPho"));
        }
        return b;
     }
     
     // Hàm kiểm tra MaThanhPho đã tồn tại hay chưa
    public boolean ValidationID(String maThanhPho) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call THANHPHO_CheckExist(?)}");
        cs.registerOutParameter(1,java.sql.Types.INTEGER);
        cs.setString(2, maThanhPho);
        cs.execute();        
        int n = cs.getInt(1);
        if(n != 0)
            return true;
        return false;
    }
}
