/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DataAccess;

import Entities.NOIDUNGQC;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import jdbc.SQLConnection;

/**
 *
 * @author VooKa
 */
public class NoiDungQCDAO {
    
    // Tạo mới một MaND
    public String GetNewID()throws Exception{
        SQLConnection con = new SQLConnection();
        String s = con.SCallFunction("{call NOIDUNGQC_CreateID(?)}");
        return s;
    }
    
    // Trả về toàn bộ bảng NOIDUNGQC
    public ArrayList<NOIDUNGQC> GetAllRows()throws Exception{
        ArrayList<NOIDUNGQC> lst = new ArrayList<NOIDUNGQC>();
        NOIDUNGQC n = null;
        SQLConnection con = new SQLConnection();
        String sql = "select * from NOIDUNGQC";
        ResultSet rs = con.executeQuery(sql);
        while(rs.next())
        {
            n = new NOIDUNGQC(rs.getString("MaND"),rs.getString("ThongTinDiaOC"),rs.getString("MoTaTomTat"));
            lst.add(n);
        }
        return lst;
    }
    
    // Thêm một đối tượng
    public Boolean Insert(NOIDUNGQC n)throws Exception{
        Boolean result=true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call NOIDUNGQC_Insert(?,?,?)}");
        cs.setString(1, n.getMaND());
        cs.setString(2, n.getThongTinDiaOc());
        cs.setString(3, n.getMoTaTomTat());
        int i = cs.executeUpdate();
        if(i == 0)
            result = false;
         return result;
    }
    
    // Sửa một đối tượng
    public Boolean Update(NOIDUNGQC n)throws Exception{
        Boolean result=true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call NOIDUNGQC_Update(?,?,?)}");
        cs.setString(1, n.getMaND());
        cs.setString(2, n.getThongTinDiaOc());
        cs.setString(3, n.getMoTaTomTat());
        int i = cs.executeUpdate();
        if(i == 0)
            result = false;
         return result;
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String ma)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call NOIDUNGQC_Delete(?)}");
        cs.setString(1, ma);
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    
    // Trả về một đối tượng NOIDUNGQC có MaND = ma
    public NOIDUNGQC GetRowByID(String ma)throws Exception{
        NOIDUNGQC n = new NOIDUNGQC();
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call NOIDUNGQC_SelectByID(?)}");
        cs.setString(1, ma);
        ResultSet rs = cs.executeQuery();
        while(rs.next())
        {
            n.setMaND(rs.getString("MaND"));
            n.setThongTinDiaOc(rs.getString("ThongTinDiaOC"));
            n.setMoTaTomTat(rs.getString("MoTaTomTat"));
        }
        return n;
    }
    
    // Kiểm tra một MaND đã tồn tại trong bảng NOIDUNGQC hay chưa
    public boolean ValidationID(String maND) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement c = con.prepareCall("{?=call NOIDUNGQC_CheckExist(?)}");
        c.registerOutParameter(1, java.sql.Types.INTEGER);
        c.setString(2, maND);
        c.execute();        
        int n = c.getInt(1);
        if (n != 0) {
            return true;
        }
        else return false;
    }    
}