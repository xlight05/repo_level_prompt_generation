/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DataAccess;

import Entities.DIACHI;
import Entities.THONGKETHANG;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import jdbc.SQLConnection;
/**
 *
 * @author VooKa
 */
public class ThongKeThangDAO {
    public ArrayList<THONGKETHANG> GetAllRows_Bang()throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call ThongKeKhachHangTheoThang_Bang}");
        ArrayList<THONGKETHANG> lst = new ArrayList<THONGKETHANG>();
        ResultSet rs = cs.executeQuery();
        THONGKETHANG _tk = null;
        DiaChiDAO dbDiaChi = new DiaChiDAO();
        while(rs.next())
        {
            String maKH = rs.getString("MaKH");
            String hoTen = rs.getString("HoTen");
            DIACHI dc = dbDiaChi.GetRowByID(rs.getString("MaDiaChi"));
            String soDT = rs.getString("SoDT");
            String email = rs.getString("Email");
            Date tuNgay = rs.getDate("TuNgay");
            _tk = new THONGKETHANG(maKH, hoTen, dc, soDT, email, tuNgay);
            lst.add(_tk);
        }
        return lst;
    }

    public ArrayList<THONGKETHANG> GetAllRows_Bao()throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call ThongKeKhachHangTheoThang_Bao}");
        ArrayList<THONGKETHANG> lst = new ArrayList<THONGKETHANG>();
        ResultSet rs = cs.executeQuery();
        THONGKETHANG _tk = null;
        DiaChiDAO dbDiaChi = new DiaChiDAO();
        while(rs.next())
        {
            String maKH = rs.getString("MaKH");
            String hoTen = rs.getString("HoTen");
            DIACHI dc = dbDiaChi.GetRowByID(rs.getString("MaDiaChi"));
            String soDT = rs.getString("SoDT");
            String email = rs.getString("Email");
            Date tuNgay = rs.getDate("TuNgay");
            _tk = new THONGKETHANG(maKH, hoTen, dc, soDT, email, tuNgay);
            lst.add(_tk);
        }
        return lst;
    }

    public ArrayList<THONGKETHANG> GetAllRows_ToBuom()throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call ThongKeKhachHangTheoThang_ToBuom}");
        ArrayList<THONGKETHANG> lst = new ArrayList<THONGKETHANG>();
        ResultSet rs = cs.executeQuery();
        THONGKETHANG _tk = null;
        DiaChiDAO dbDiaChi = new DiaChiDAO();
        while(rs.next())
        {
            String maKH = rs.getString("MaKH");
            String hoTen = rs.getString("HoTen");
            DIACHI dc = dbDiaChi.GetRowByID(rs.getString("MaDiaChi"));
            String soDT = rs.getString("SoDT");
            String email = rs.getString("Email");
            Date tuNgay = rs.getDate("TuNgay");
            _tk = new THONGKETHANG(maKH, hoTen, dc, soDT, email, tuNgay);
            lst.add(_tk);
        }
        return lst;
    }
}
