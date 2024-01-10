/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DataAccess;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import jdbc.SQLConnection;


/**
 *
 * @author VooKa
 */
public class ThongKeSoLuongKhachHangDAO {
    public int GetSoLuongKhachHang_Bang()throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call ThongKeSoLuongKhachHang_Bang(?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
        cs.execute();
        int n = cs.getInt(1);
        return n;
    }

    public int GetSoLuongKhachHang_Bao()throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call ThongKeSoLuongKhachHang_Bao(?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
        cs.execute();
        int n = cs.getInt(1);
        return n;
    }

    public int GetSoLuongKhachHang_ToBuom()throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call ThongKeSoLuongKhachHang_ToBuom(?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
        cs.execute();
        int n = cs.getInt(1);
        return n;
    }
}
