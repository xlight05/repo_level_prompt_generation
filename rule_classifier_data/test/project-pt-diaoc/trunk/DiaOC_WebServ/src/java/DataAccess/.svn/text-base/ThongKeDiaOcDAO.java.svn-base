/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DataAccess;

import Entities.DIACHI;
import Entities.THONGKEDIAOC;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import jdbc.SQLConnection;
/**
 *
 * @author VooKa
 */
public class ThongKeDiaOcDAO {
    //liệt kê tất cả
    public ArrayList<THONGKEDIAOC> GetAllRows()throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call THONGKEDIAOC}");
        ArrayList<THONGKEDIAOC> lst = new ArrayList<THONGKEDIAOC>();
        ResultSet rs = cs.executeQuery();
        THONGKEDIAOC _t = null;
        DiaChiDAO dbDiaChi = new DiaChiDAO();
        while(rs.next())
        {
            DIACHI dc = dbDiaChi.GetRowByID(rs.getString("MaDiaChi"));
            _t = new THONGKEDIAOC(rs.getString("MaKH"), rs.getNString("HoTen"),
                    rs.getString("MaDiaOc"), rs.getNString("TrangThai"),
                    rs.getFloat("GiaBan"), dc);
            lst.add(_t);
        }
        return lst;
    }

    //liệt kê theo mã khách hàng
    public ArrayList<THONGKEDIAOC> GetRowsByMaKH(String ma)throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call THONGKEDIAOC_ByMaKH(?)}");
        cs.setString(1, ma);
        ArrayList<THONGKEDIAOC> lst = new ArrayList<THONGKEDIAOC>();
        THONGKEDIAOC _t = null;
        ResultSet rs = cs.executeQuery();
        DiaChiDAO dbDiaChi = new DiaChiDAO();
        while(rs.next())
        {
            DIACHI dc = dbDiaChi.GetRowByID(rs.getString("MaDiaChi"));
            _t = new THONGKEDIAOC(rs.getString("MaKH"), rs.getNString("HoTen"),
                    rs.getString("MaDiaOc"), rs.getNString("TrangThai"),
                    rs.getFloat("GiaBan"), dc);
            lst.add(_t);
        }
        return lst;
    }

    //liệt kê theo tên khách hàng
    public ArrayList<THONGKEDIAOC> GetRowsByHoTen(String ten)throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call THONGKEDIAOC_ByHoTen(?)}");
        cs.setNString(1, ten);
        ArrayList<THONGKEDIAOC> lst = new ArrayList<THONGKEDIAOC>();
        THONGKEDIAOC _t = null;
        ResultSet rs = cs.executeQuery();
        DiaChiDAO dbDiaChi = new DiaChiDAO();
        while(rs.next())
        {
            DIACHI dc = dbDiaChi.GetRowByID(rs.getString("MaDiaChi"));
            _t = new THONGKEDIAOC(rs.getString("MaKH"), rs.getNString("HoTen"),
                    rs.getString("MaDiaOc"), rs.getNString("TrangThai"),
                    rs.getFloat("GiaBan"), dc);
            lst.add(_t);
        }
        return lst;
    }

    //liệt kê theo trạng thái địa ốc
    public ArrayList<THONGKEDIAOC> GetRowsByTrangThai(String trangThai)throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call THONGKEDIAOC_ByTrangThai(?)}");
        cs.setNString(1, trangThai);
        ArrayList<THONGKEDIAOC> lst = new ArrayList<THONGKEDIAOC>();
        THONGKEDIAOC _t = null;
        ResultSet rs = cs.executeQuery();
        DiaChiDAO dbDiaChi = new DiaChiDAO();
        while(rs.next())
        {
            DIACHI dc = dbDiaChi.GetRowByID(rs.getString("MaDiaChi"));
            _t = new THONGKEDIAOC(rs.getString("MaKH"), rs.getNString("HoTen"),
                    rs.getString("MaDiaOc"), rs.getNString("TrangThai"),
                    rs.getFloat("GiaBan"), dc);
            lst.add(_t);
        }
        return lst;
    }
}
