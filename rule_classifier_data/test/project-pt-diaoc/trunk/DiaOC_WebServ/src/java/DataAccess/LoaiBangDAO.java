/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DataAccess;

import Entities.LOAIBANG;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import jdbc.SQLConnection;

/**
 *
 * @author VooKa
 */
public class LoaiBangDAO {
    
    // Tạo mới một MaLoaiBang
    public String GetNewID()throws Exception{
        SQLConnection con = new SQLConnection();
        String s = con.SCallFunction("{call LOAIBANG_CreateID(?)}");
        return s;
    }
    
    // Trả về toàn bộ bảng LOAIBANG
    public ArrayList<LOAIBANG> GetAllRows()throws Exception{
        ArrayList<LOAIBANG> lst = new ArrayList<LOAIBANG>();
        SQLConnection con = new SQLConnection();
        String sql = "select * from LOAIBANG";
        ResultSet rs = con.executeQuery(sql);
        LOAIBANG l = null;
        while(rs.next())
        {
            l = new LOAIBANG(rs.getString("MaLoaiBang"),rs.getString("LoaiBang"));
            lst.add(l);
        }
        return lst;
    }
    
    // Thêm một đối tượng
    public Boolean Insert(LOAIBANG l)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call LOAIBANG_Insert(?,?)}");
        cs.setString(1, l.getMaLoaiBang());
        cs.setString(2, l.getLoaiBang());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    
    // Sửa một đối tượng
    public Boolean Update(LOAIBANG l)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call LOAIBANG_Update(?,?)}");
        cs.setString(1, l.getMaLoaiBang());
        cs.setString(2, l.getLoaiBang());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String ma)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call LOAIBANG_Delete(?)}");
        cs.setString(1, ma);
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    
    // Trả về một đối tượng LOAIBANG có MaLoaiBang = ma
    public LOAIBANG GetRowByID(String ma)throws Exception{
        LOAIBANG loaiBang = new LOAIBANG();
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call LOAIBANG_SelectByID(?)}");
        cs.setString(1, ma);
        ResultSet rs = cs.executeQuery();
        while(rs.next())
        {
            loaiBang.setMaLoaiBang(rs.getString("MaLoaiBang"));
            loaiBang.setLoaiBang(rs.getString("LoaiBang"));
        }
        return loaiBang;
    }
    
    // Kiểm tra một MaLoaiBang đã tồn tại trong bảng hay chưa
    public boolean ValidationID(String maLoaiBang) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call LOAIBANG_CheckExist(?)}");
        cs.registerOutParameter(1,java.sql.Types.INTEGER);
        cs.setString(2, maLoaiBang);
        cs.execute();        
        int n = cs.getInt(1);
        if(n != 0)
            return true;
        return false;
    }
}
