/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

import Entities.CHITIETBAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import jdbc.SQLConnection;

/**
 *
 * @author Nixforest
 */
public class ChiTietBaoDAO {
    // Tạo mới một MaCT
     public String GetNewID() throws Exception{
        SQLConnection con = new SQLConnection();
        String s = con.SCallFunction("{call CHITIETBAO_CreateID(?)}");
        return s;
     }
     
     // Trả về toàn bộ bảng CHITIETBAO
     public ArrayList<CHITIETBAO> GetAllRows() throws Exception{
        ArrayList<CHITIETBAO> lst = new ArrayList<CHITIETBAO>();
        String sql = "select * from CHITIETBAO";
        SQLConnection con = new SQLConnection();
        ResultSet rs = con.executeQuery(sql);
        CHITIETBAO b = null;
        while(rs.next())
        {
            b = new CHITIETBAO(rs.getString("MaCT"), rs.getString("MaQCBao"), rs.getString("MaBao"));
            lst.add(b);
        }
        return lst;
     }
     
     // Thêm một đối tượng
     public Boolean Insert(CHITIETBAO b)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call CHITIETBAO_Insert(?,?,?)}");
        cs.setString(1,b.getMaCT());
        cs.setString(2, b.getMaQCBao());
        cs.setString(3, b.getMaBao());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        con.close();
        return result;
     }
     
     // Sửa một đối tượng
     public Boolean Update(CHITIETBAO b)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call CHITIETBAO_Update(?,?,?)}");
        cs.setString(1,b.getMaCT());
        cs.setString(2, b.getMaQCBao());
        cs.setString(3, b.getMaBao());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        con.close();
        return result;
     }
     
     // Xóa một đối tượng
     public Boolean Delete(String maCT)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call CHITIETBAO_Delete(?)}");
        cs.setString(1, maCT);
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        con.close();
        return result;
     }
     
     // Trả về một đối tượng CHITIETBAO có MaCT = maCT
     public CHITIETBAO GetRowByID(String maCT) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call CHITIETBAO_SelectByID(?)}");
        cs.setString(1, maCT);
        CHITIETBAO b = new CHITIETBAO();
        ResultSet rs = cs.executeQuery();
        while(rs.next())
        {
            b.setMaCT(rs.getString("MaCT"));
            b.setMaQCBao(rs.getString("MaQCBao"));
            b.setMaBao(rs.getString("MaBao"));
        }
        return b;
     }
     
     // Hàm kiểm tra MaCT đã tồn tại hay chưa
    public boolean ValidationID(String maCT) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call CHITIETBAO_CheckExist(?)}");
        cs.registerOutParameter(1,java.sql.Types.INTEGER);
        cs.setString(2, maCT);
        cs.execute();        
        int n = cs.getInt(1);
        if(n != 0)
            return true;
        return false;
    }
    
    // Hàm kiểm tra MaBao có tồn tại hay không
    public boolean IsMaBaoExist(String maBao) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call CHITIETBAO_IsMaBaoExist(?)}");
        cs.registerOutParameter(1,java.sql.Types.INTEGER);
        cs.setString(2, maBao);
        cs.execute();        
        int n = cs.getInt(1);
        if(n != 0)
            return true;
        return false;
    }   
    
    // Hàm kiểm tra MaQCBao có tồn tại hay không
    public boolean IsMaQCBaoExist(String maQCBao) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call CHITIETBAO_IsMaQCBaoExist(?)}");
        cs.registerOutParameter(1,java.sql.Types.INTEGER);
        cs.setString(2, maQCBao);
        cs.execute();        
        int n = cs.getInt(1);
        if(n != 0)
            return true;
        return false;
    }
    
    // Trả về MaBao trong bảng CHITIETBAO có cùng MaQCBao
    public ArrayList<String> GetMaBaoByMaQCBao(String maQCBao)throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call CHITIETBAO_GetMaBaoByMaQCBao(?)}");
        cs.setString(1, maQCBao);
        ResultSet rs = cs.executeQuery();
        ArrayList<String> lst = new ArrayList<String>();
        while(rs.next())
        {
            String s = rs.getString("MaBao");
            lst.add(s);
        }
        return lst;
    }
}
