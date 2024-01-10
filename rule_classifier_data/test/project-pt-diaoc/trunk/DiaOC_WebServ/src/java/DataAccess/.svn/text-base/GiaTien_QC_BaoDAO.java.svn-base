/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DataAccess;

import Entities.GIATIEN_QC_BAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import jdbc.SQLConnection;

/**
 *
 * @author VooKa
 */
public class GiaTien_QC_BaoDAO {
    
    // Tạo mới một MaGiaTien của QC_BAO
    public String GetNewID()throws Exception{
        SQLConnection con = new SQLConnection();
        String s = con.SCallFunction("{call GIATIEN_QCB_CreateID(?)}");
        return s;
    }
    
    // Trả về toàn bộ bảng 
    public ArrayList<GIATIEN_QC_BAO> GetAllRows()throws Exception{
        SQLConnection con = new SQLConnection();
        String sql = "select * from GIATIEN_QCB";
        ResultSet rs = con.executeQuery(sql);
        ArrayList<GIATIEN_QC_BAO> lst  = new ArrayList<GIATIEN_QC_BAO>();
        GIATIEN_QC_BAO g = null;
        while(rs.next())
        {
            g = new GIATIEN_QC_BAO(rs.getString("MaGiaTien"),rs.getString("ViTri"),rs.getString("KhoIn"),rs.getString("MauSac"),rs.getString("GhiChu"),rs.getFloat("GiaTien"));
            lst.add(g);
        }
        return lst;
    }
    
    // Thêm một đối tượng
    public Boolean Insert(GIATIEN_QC_BAO g)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call GIATIEN_QCB_Insert(?,?,?,?,?,?)}");
        cs.setString(1, g.getMaGiaTien());
        cs.setString(2, g.getViTri());
        cs.setString(3, g.getKhoIn());
        cs.setString(4, g.getMauSac());
        cs.setString(5, g.getGhiChu());
        cs.setFloat(6, g.getGiaTien());
        int n = cs.executeUpdate();
        if(n==0)
            result=false;
        return result;
    }
    
    // Sửa một đối tượng
    public Boolean Update(GIATIEN_QC_BAO g) throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call GIATIEN_QCB_Update(?,?,?,?,?,?)}");
        cs.setString(1, g.getMaGiaTien());
        cs.setString(2, g.getViTri());
        cs.setString(3, g.getKhoIn());
        cs.setString(4, g.getMauSac());
        cs.setString(5, g.getGhiChu());
        cs.setFloat(6, g.getGiaTien());
        int n = cs.executeUpdate();
        if(n==0)
            result=false;
        return result;
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String ma)throws Exception{
        Boolean result=true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call GIATIEN_QCB_Delete(?)}");
        cs.setString(1, ma);
        int n = cs.executeUpdate();
        if(n==0)
            result=false;
        return result;
    }
    
    // Trả về một đối tượng GIATIEN_QC_BAO có MaGiaTien = ma
    public GIATIEN_QC_BAO GetRowByID(String ma)throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call GIATIEN_QCB_SelectByID(?)}");
        cs.setString(1, ma);
        ResultSet rs = cs.executeQuery();
        GIATIEN_QC_BAO g = new GIATIEN_QC_BAO();
        while(rs.next())
        {
            g.setMaGiaTien(rs.getString("MaGiaTien"));
            g.setViTri(rs.getString("ViTri"));
            g.setKhoIn(rs.getString("KhoIn"));
            g.setMauSac(rs.getString("MauSac"));
            g.setGhiChu(rs.getString("GhiChu"));
            g.setGiaTien(rs.getFloat("GiaTien"));
        }
        return g;
    }
    
    // Kiểm tra MaGiaTien có tồn tại trong bàn hay chưa
    public boolean ValidationID(String maGiaTien) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement c = con.prepareCall("{?=call GIATIEN_QCB_CheckExist(?)}");
        c.registerOutParameter(1, java.sql.Types.INTEGER);
        c.setString(2, maGiaTien);
        c.execute();        
        int n = c.getInt(1);
        if (n != 0) {
            return true;
        }
        else return false;
    }
}
