/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DataAccess;

import Entities.GIATIEN_QC_BANG;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import jdbc.SQLConnection;

/**
 *
 * @author VooKa
 */
public class GiaTien_QC_BangDAO {
    
    // Tạo mới một MaGiaTien
    public String GetNewID()throws Exception{
        SQLConnection con = new SQLConnection();
        String s = con.SCallFunction("{call GIATIEN_QCG_CreateID(?)}");
        return s;
    }
    
    // Trả về toàn bộ bảng GIATIEN_QC_BANG
    public ArrayList<GIATIEN_QC_BANG> GetAllRows()throws Exception{
        SQLConnection con = new SQLConnection();
        String sql="select * from GIATIEN_QCG";
        ResultSet rs = con.executeQuery(sql);
        ArrayList<GIATIEN_QC_BANG> lst = new ArrayList<GIATIEN_QC_BANG>();
        GIATIEN_QC_BANG g = null;
        while(rs.next())
        {
            g = new GIATIEN_QC_BANG(rs.getString("MaGiaTien"), rs.getString("MaLoaiBang"),
                    rs.getString("MoTa"), rs.getFloat("KichCo"), rs.getString("DVT"),
                    rs.getFloat("GiaTien"));
            lst.add(g);
        }
        return lst;
    }
    
    // Thêm một đối tượng
    public Boolean Insert(GIATIEN_QC_BANG g)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call GIATIEN_QCG_Insert(?,?,?,?,?,?)}");
        cs.setString(1, g.getMaGiaTien());
        cs.setString(2, g.getMaLoaiBang());
        cs.setString(3, g.getMoTa());
        cs.setFloat(4, g.getKichCo());
        cs.setString(5, g.getDVT());
        cs.setFloat(6, g.getGiaTien());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        con.close();
        return result;
    }
    
    // Sửa một đối tượng
    public Boolean Update(GIATIEN_QC_BANG g)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call GIATIEN_QCG_Update(?,?,?,?,?,?)}");
        cs.setString(1, g.getMaGiaTien());
        cs.setString(2, g.getMaLoaiBang());
        cs.setString(3, g.getMoTa());
        cs.setFloat(4, g.getKichCo());
        cs.setString(5, g.getDVT());
        cs.setFloat(6, g.getGiaTien());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        con.close();
        return result;
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String ma)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call GIATIEN_QCG_Delete(?)}");
        cs.setString(1, ma);
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        con.close();
        return result;
    }
    
    // Trả về một đối tượng GIATIEN_QC_BANG có MaGiaTien = ma
    public GIATIEN_QC_BANG GetRowByID(String ma)throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call GIATIEN_QCG_SelectByID(?)}");
        cs.setString(1, ma);
        ResultSet rs = cs.executeQuery();
        GIATIEN_QC_BANG g = new GIATIEN_QC_BANG();
        while(rs.next())
        {
            g.setMaGiaTien(rs.getString("MaGiaTien"));
            g.setMaLoaiBang(rs.getString("MaLoaiBang"));
            g.setMoTa(rs.getString("MoTa"));
            g.setKichCo(rs.getFloat("KichCo"));
            g.setDVT(rs.getString("DVT"));
            g.setGiaTien(rs.getFloat("GiaTien"));
        }
        return g;
    }
    
    // Kiểm tra MaGiaTien có tồn tại trong bảng hay chưa
    public boolean ValidationID(String maGiaTien) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement c = con.prepareCall("{?=call GIATIEN_QCG_CheckExist(?)}");
        c.registerOutParameter(1, java.sql.Types.INTEGER);
        c.setString(2, maGiaTien);
        c.execute();        
        int n = c.getInt(1);
        return n != 0;
    }
    
    // Kiểm tra MaLoaiBang có tồn tại trong bảng GIATIEN_QC_BANG hay không?
    public boolean IsMaLoaiBangExist(String maLoaiBang) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call GIATIEN_QCG_IsMaLoaiBangExist(?)}");
        cs.registerOutParameter(1,java.sql.Types.INTEGER);
        cs.setString(2, maLoaiBang);
        cs.execute();        
        int n = cs.getInt(1);
        return n != 0;
    }
    
    // Trả về một đối tượng LoaiBang có MaLoaiBang = maLoaiBang
    public String GetLoaiBang(String maLoaiBang)throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call GIATIEN_QCG_GetLoaiBang(?)}");
        cs.setString(1, maLoaiBang);
        ResultSet rs = cs.executeQuery();
        String s = "";
        while(rs.next())
        {
            s = rs.getString("LoaiBang");
        }
        return s;
    }
}
