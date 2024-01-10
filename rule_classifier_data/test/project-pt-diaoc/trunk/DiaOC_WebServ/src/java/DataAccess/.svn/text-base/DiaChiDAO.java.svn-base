/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 * @author Wjnd_Field
 */
public class DiaChiDAO {
    // Tạo mới một MaDiaChi
     public String GetNewID() throws Exception{
        SQLConnection con = new SQLConnection();
        String s = con.SCallFunction("{call DIACHI_CreateID(?)}");
        return s;
        
     }
     
     // Trả về toàn bộ bảng DIACHI
     public ArrayList<DIACHI> GetAllRows() throws Exception{
        ArrayList<DIACHI> lst = new ArrayList<DIACHI>();
        String sql = "select * from DIACHI";
        SQLConnection con = new SQLConnection();
        ResultSet rs = con.executeQuery(sql);
        DIACHI dc = null;
        while(rs.next())
        {
            dc = new DIACHI(rs.getString("MaDiaChi"), rs.getString("MaThanhPho"), rs.getString("MaQuan"), 
                    rs.getString("MaPhuong"), rs.getString("MaDuong"), rs.getString("DiaChiCT"));
            lst.add(dc);
        }
        return lst;
     }
     
     // Thêm một đối tượng
     public Boolean Insert(DIACHI dc)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call DIACHI_Insert(?,?,?,?,?,?)}");
        cs.setString(1, dc.getMaDiaChi());
        cs.setString(2, dc.getMaThanhPho());
        cs.setString(3, dc.getMaQuan());
        cs.setString(4, dc.getMaPhuong());
        cs.setString(5, dc.getMaDuong());
        cs.setString(6, dc.getDiaChiCT());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
     }
     
     // Sửa một đối tượng
     public Boolean Update(DIACHI dc)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call DIACHI_Update(?,?,?,?,?,?)}");
        cs.setString(1, dc.getMaDiaChi());
        cs.setString(2, dc.getMaThanhPho());
        cs.setString(3, dc.getMaQuan());
        cs.setString(4, dc.getMaPhuong());
        cs.setString(5, dc.getMaDuong());
        cs.setString(6, dc.getDiaChiCT());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
     }
     
     // Xóa một đối tượng
     public Boolean Delete(String maDiaChi)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call DIACHI_Delete(?)}");
        cs.setString(1, maDiaChi);
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
     }
     
     // Trả về một đối tượng BAO có MaBao = mabao
     public DIACHI GetRowByID(String maDiaChi) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call DIACHI_SelectByID(?)}");
        cs.setString(1, maDiaChi);
        DIACHI dc = new DIACHI();
        ResultSet rs = cs.executeQuery();
        while(rs.next())
        {
            dc.setMaDiaChi(rs.getString("MaDiaChi"));
            dc.setMaThanhPho(rs.getString("MaThanhPho"));
            dc.setMaQuan(rs.getString("MaQuan"));
            dc.setMaPhuong(rs.getString("MaPhuong"));
            dc.setMaDuong(rs.getString("MaDuong"));
            dc.setDiaChiCT(rs.getString("DiaChiCT"));
        }
        return dc;
     }
     
     // Hàm kiểm tra MaBao đã tồn tại hay chưa
    public boolean ValidationID(String maDiaChi) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call DIACHI_CheckExist(?)}");
        cs.registerOutParameter(1,java.sql.Types.INTEGER);
        cs.setString(2, maDiaChi);
        cs.execute();        
        int n = cs.getInt(1);
        return n != 0;
    }

    // Kiểm tra xem một MaThanhPho có tồn tại trong bảng hay không?
    public boolean IsThanhPhoExist(String maThanhPho) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call DIACHI_IsThanhPhoExist(?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
        cs.setString(2, maThanhPho);
        cs.execute();        
        int n = cs.getInt(1);
        return n != 0;
    }
    
    // Kiểm tra xem một MaThanhPho có tồn tại trong bảng hay không?
    public boolean IsQuanExist(String maQuan) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call DIACHI_IsQuanExist(?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
        cs.setString(2, maQuan);
        cs.execute();        
        int n = cs.getInt(1);
        return n != 0;
    }
    
    // Kiểm tra xem một MaThanhPho có tồn tại trong bảng hay không?
    public boolean IsPhuongExist(String maPhuong) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call DIACHI_IsPhuongExist(?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
        cs.setString(2, maPhuong);
        cs.execute();        
        int n = cs.getInt(1);
        return n != 0;
    }
    
        // Kiểm tra xem một MaThanhPho có tồn tại trong bảng hay không?
    public boolean IsDuongExist(String maDuong) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call DIACHI_IsDuongExist(?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
        cs.setString(2, maDuong);
        cs.execute();        
        int n = cs.getInt(1);
        return n != 0;
    }
}
