/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DataAccess;

import Entities.THAMSO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import jdbc.SQLConnection;

/**
 *
 * @author VooKa
 */
public class ThamSoDAO {
    public ArrayList<THAMSO> GetAllRows()throws Exception{
        SQLConnection con = new SQLConnection();
        String sql = "select * from THAMSO";
        ResultSet rs = con.executeQuery(sql);
        ArrayList<THAMSO> lst = new ArrayList<THAMSO>();
        THAMSO t = null;
        while(rs.next())
        {
            t = new THAMSO(rs.getString("TenThamSo"), rs.getFloat("GiaTri"),
                    rs.getString("GiaiThich"));
            lst.add(t);
        }
        return lst;
    }
    public Boolean Insert(THAMSO t)throws Exception{
        Boolean result=true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call THAMSO_insert(?,?,?)}");
        cs.setString(1, t.getTenThamSo());
        cs.setFloat(2, t.getGiaTri());
        cs.setString(3, t.getGiaiThich());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    public Boolean Update(THAMSO t)throws Exception{
        Boolean result=true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call THAMSO_Update(?,?,?)}");
        cs.setString(1, t.getTenThamSo());
        cs.setFloat(2, t.getGiaTri());
        cs.setString(3, t.getGiaiThich());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    public Boolean Delete(String ten)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call THAMSO_Delete(?)}");
        cs.setString(1, ten);
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    public THAMSO GetRowByID(String ten)throws Exception{
        THAMSO t = new THAMSO();
        SQLConnection con = new SQLConnection();
        String sql = "select * from THAMSO where TenThamSo="+ten;
        ResultSet rs = con.executeQuery(sql);
        while(rs.next())
        {
            t.setTenThamSo(ten);
            t.setGiaTri(rs.getFloat("GiaTri"));
            t.setGiaiThich(rs.getString("GiaiThich"));
        }
        return t;
    }
    
    // Trả về một đối tượng THAMSO có TenThamSo = tenThamSo
    public THAMSO GetThamSoByTen(String tenThamSo)throws Exception{
        THAMSO t = new THAMSO();
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call THAMSO_GetThamSo_ByTen(?)}");
        cs.setString(1, tenThamSo);
        ResultSet rs = cs.executeQuery();
        while(rs.next())
        {
            t.setTenThamSo(tenThamSo);
            t.setGiaTri(rs.getFloat("GiaTri"));
            t.setGiaiThich(rs.getString("GiaiThich"));
        }
        return t;
    }
}
