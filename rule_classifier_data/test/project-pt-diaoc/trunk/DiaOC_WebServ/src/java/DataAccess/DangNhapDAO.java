/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DataAccess;

import Entities.DANGNHAP;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import jdbc.SQLConnection;


/**
 *
 * @author VooKa
 */
public class DangNhapDAO {

    public int GetNewID()throws Exception{
        SQLConnection con = new SQLConnection();
        int n = con.ICallFunction("{call DANGNHAP_CreateID(?)}");
        return n;
    }

    public ArrayList<DANGNHAP> GetAllRows()throws Exception{
        String sql = "select * from DANGNHAP";
        SQLConnection con = new SQLConnection();
        ResultSet rs = con.executeQuery(sql);
        ArrayList<DANGNHAP> lst = new ArrayList<DANGNHAP>();
        DANGNHAP _d = null;
        while(rs.next())
        {
            _d = new DANGNHAP(rs.getInt("id"),rs.getString("username"),rs.getString("password"));
            lst.add(_d);
        }
        return lst;
    }

    public Boolean Insert(int id, String user, String pass)throws Exception{
        boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call DANGNHAP_Insert(?,?,?)}");
        cs.setInt(1, id);
        cs.setString(2, user);
        cs.setString(3, pass);
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }

    public Boolean Update(int id, String user, String pass)throws Exception{
        boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call DANGNHAP_Update(?,?,?)}");
        cs.setInt(1, id);
        cs.setString(2, user);
        cs.setString(3, pass);
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }

    public boolean Delete(int id)throws Exception{
        boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call DANGNHAP_Delete(?)}");
        cs.setInt(1, id);
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }

    public boolean  CheckDangNhap(String user, String pass)throws Exception{
        boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{? = call DANGNHAP_Check(?,?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
       
        cs.setString(2, user);
        cs.setString(3, pass);
        cs.execute();
        int n = cs.getInt(1);
        if(n == 0)
            result = false;
        return result;
    }

    public boolean IsExistUserName_DangNhap(String user)throws Exception{
        boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{? = call DANGNHAP_CheckExist(?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
        cs.setString(2, user);
        cs.execute();
        int n = cs.getInt(1);
        if(n == 0)
            result = false;
        return result;
    }

    public boolean  ChangePassword(int id, String pass)throws Exception{
        boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("call DANGNHAP_ChangePass(?,?)");
        cs.setInt(1, id);
        cs.setString(2, pass);
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
}
