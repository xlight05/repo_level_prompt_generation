/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DataAccess;

import Entities.LOAIDIAOC;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import jdbc.SQLConnection;

/**
 *
 * @author VooKa
 */
public class LoaiDiaOcDAO {
    
    // Tạo mới một MaLoaiDiaOc
    public String GetNewID()throws Exception{
        SQLConnection con = new SQLConnection();
        String s = con.SCallFunction("{call LOAIDIAOC_CreateID(?)}");
        return s;
    }
    
    // Trả về toàn bộ bảng LOAIDIAOC
    public ArrayList<LOAIDIAOC> GetAllRows()throws Exception{
        SQLConnection con = new SQLConnection();
        String sql = "select * from LOAIDIAOC";
        ResultSet rs = con.executeQuery(sql);
        ArrayList<LOAIDIAOC> lst = new ArrayList<LOAIDIAOC>();
        LOAIDIAOC l = null;
        while(rs.next())
        {
            l = new LOAIDIAOC(rs.getString("MaLoaiDiaOC"),rs.getString("TenLoaiDiaOC"), rs.getString("KyHieu"));
            lst.add(l);
        }
        return lst;
    }
    
    // Thêm một đối tượng
    public Boolean Insert(LOAIDIAOC l)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call LOAIDIAOC_Insert(?,?,?)}");
        cs.setString(1, l.getMaLoaiDiaOc());
        cs.setString(2, l.getTenLoaiDiaOc());
        cs.setString(3, l.getKyHieu());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    
    // Sửa một đối tượng
    public Boolean Update(LOAIDIAOC l)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call LOAIDIAOC_Update(?,?,?)}");
        cs.setString(1, l.getMaLoaiDiaOc());
        cs.setString(2, l.getTenLoaiDiaOc());
        cs.setString(3, l.getKyHieu());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String ma)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call LOAIDIAOC_Delete(?)}");
        cs.setString(1, ma);
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    
    // Trả về một LOAIDIAOC có MaLoaiDiaOc = ma
    public LOAIDIAOC GetRowByID(String ma)throws Exception{
        LOAIDIAOC l = new LOAIDIAOC();
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call LOAIDIAOC_SelectByID(?)}");
        cs.setString(1, ma);
        ResultSet rs = cs.executeQuery();
        while(rs.next())
        {
            l.setMaLoaiDiaOc(rs.getString("MaLoaiDiaOC"));
            l.setTenLoaiDiaOc(rs.getString("TenLoaiDiaOc"));
            l.setKyHieu(rs.getString("KyHieu"));
        }
        return l;
    }
    
    // Hàm kiểm tra xem một MaLoaiDiaOc đã tồn tại hay chưa
    public boolean ValidationID(String ma) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call LOAIDIAOC_CheckExist(?)}");
        cs.registerOutParameter(1,java.sql.Types.INTEGER);
        cs.setString(2, ma);
        cs.execute();        
        int n = cs.getInt(1);
        if(n != 0)
            return true;
        return false;
    }
    
    // Trả về TenLoaiDiaOc theo MaLoaiDiaOc
    public String GetNameByID(String maLoaiDiaOc)throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call LOAIDIAOC_GetNameByID(?)}");
        cs.setString(1, maLoaiDiaOc);
        ResultSet rs = cs.executeQuery();
        String s = "";
        while(rs.next())
        {
            s = rs.getString("TenLoaiDiaOc");
        }
        return s;
    }
    
    // Kiểm tra xem một KyHieu có tồn tại trong bảng hay không
    public boolean IsKyHieuExist(String kyHieu) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call LOAIDIAOC_CheckKyHieu(?)}");
        cs.registerOutParameter(1,java.sql.Types.INTEGER);
        cs.setString(2, kyHieu);
        cs.execute();        
        int n = cs.getInt(1);
        if(n != 0)
            return true;
        return false;
    }
}