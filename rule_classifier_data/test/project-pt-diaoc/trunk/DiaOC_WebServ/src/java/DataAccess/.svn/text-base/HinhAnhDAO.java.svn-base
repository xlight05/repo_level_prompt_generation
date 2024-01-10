/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DataAccess;

import Entities.HINHANH;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import jdbc.SQLConnection;

/**
 *
 * @author VooKa
 */
public class HinhAnhDAO {
    
    // Tạo mới một MaHinhAnh
    public int GetNewID()throws Exception{
        SQLConnection con = new SQLConnection();
        int s = con.ICallFunction("{call HINHANH_CreateID(?)}");
        return s;
    }
    
    // Trả về toàn bộ bảng HINHANH
    public ArrayList<HINHANH> GetAllRows()throws Exception{
        ArrayList<HINHANH> lst = new ArrayList<HINHANH>();
        SQLConnection con = new SQLConnection();
        String sql = "select * from HINHANH";
        ResultSet rs = con.executeQuery(sql);
        HINHANH h = null;
        while(rs.next())
        {
            h = new HINHANH(rs.getInt("MaHinhAnh"),rs.getString("Anh"),
                    rs.getString("MoTa"),rs.getDate("ThoiGianChupAnh"), rs.getString("MaND"));
            lst.add(h);
        }
        return lst;
    }
    
    // Thêm một đối tượng
    public Boolean Insert(HINHANH h) throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call HINHANH_Insert(?,?,?,?,?)}");
        cs.setInt(1, h.getMaHinhAnh());
        cs.setString(2,h.getAnh());
        cs.setString(3, h.getMoTa());
        Date d = h.getThoiGianChupAnh();
        cs.setString(4, d.toString());
        cs.setString(5, h.getMaND());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    
    // Sửa một đối tượng
    public Boolean Update(HINHANH h)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call HINHANH_Update(?,?,?,?,?)}");
        cs.setInt(1, h.getMaHinhAnh());
        cs.setString(2,h.getAnh());
        cs.setString(3, h.getMoTa());
        Date d = h.getThoiGianChupAnh();
        cs.setString(4, d.toString());
        cs.setString(5, h.getMaND());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    
    // Xóa một đối tượng
    public Boolean Delete(int ma)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call HINHANH_Delete(?)}");
        cs.setInt(1, ma);
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    
    // Trả về một đối tượng HINHANH có MaHinhAnh = ma
    public HINHANH GetRowByID(int ma)throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call HINHANH_SelectByID(?)}");
        cs.setInt(1, ma);
        ResultSet rs = cs.executeQuery();
        HINHANH h = new HINHANH();
        while(rs.next())
        {
            h.setMaHinhAnh(rs.getInt("MaHinhAnh"));
            h.setAnh(rs.getString("Anh"));
            h.setMoTa(rs.getString("MoTa"));
            h.setThoiGianChupAnh(rs.getDate("ThoiGianChupAnh"));
            h.setMaND(rs.getString("MaND"));
        }
        return h;
    }
    
    // Kiểm tra xem một MaHinhAnh có tồn tại trong bảng hay chưa
    public boolean ValidationID(int maHinhAnh) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call HINHANH_CheckExist(?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
        cs.setInt(2, maHinhAnh);
        cs.execute();        
        int n = cs.getInt(1);
        return n != 0;
    }
    
    //Kiểm tra xem một MaND có tồn tại trong bảng HINHANH hay không
    public boolean IsMaND_HinhAnhExist(String maND) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call HINHANH_IsMaND_HinhAnhExist(?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
        cs.setString(2, maND);
        cs.execute();        
        int n = cs.getInt(1);
        return n != 0;
    }
    
    // Trả về HINHANH theo MaND
    public ArrayList<HINHANH> SelectHinhAnhByMaND(String maND)throws Exception{
        ArrayList<HINHANH> lst = new ArrayList<HINHANH>();
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call HINHANH_SelectHinhAnh_ByMaND(?)}");
        cs.setString(1, maND);
        ResultSet rs = cs.executeQuery();
        HINHANH h = null;
        while(rs.next())
        {
            h = new HINHANH(rs.getInt("MaHinhAnh"),rs.getString("Anh"),
                    rs.getString("MoTa"),rs.getDate("ThoiGianChupAnh"), rs.getString("MaND"));
            lst.add(h);
        }
        return lst;
    }
}

