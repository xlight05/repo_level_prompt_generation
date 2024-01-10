/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DataAccess;

import Entities.QC_BANG;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import jdbc.SQLConnection;

/**
 *
 * @author VooKa
 */
public class QCBangDAO {
    
    // Tạo mới một MaQCBang
    public String GetNewID()throws Exception{
        SQLConnection con = new SQLConnection();
        String s = con.SCallFunction("{call QCBANG_CreateID(?)}");
        return s;
    }
    
    // Trả về toàn bộ bảng QC_BANG
    public ArrayList<QC_BANG> GetAllRows()throws Exception{
        ArrayList<QC_BANG> lst  = new ArrayList<QC_BANG>();
        QC_BANG q = null;
        SQLConnection con = new SQLConnection();
        String sql = "select * from QC_BANG";
        ResultSet rs = con.executeQuery(sql);
        while(rs.next())
        {
            q = new QC_BANG(rs.getString("MaQCBang"), rs.getString("MaND"),
                    rs.getString("MaPhieuDangKy"), rs.getString("MaGiaTien"));
            lst.add(q);
        }
        return lst;
    }
    
    // Thêm một đối tượng
    public Boolean Insert(QC_BANG q)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call QCBANG_Insert(?,?,?,?)}");
        cs.setString(1, q.getMaQCBang());
        cs.setString(2, q.getMaND());
        cs.setString(3, q.getMaPhieuDangKy());
        cs.setString(4, q.getMaGiaTien());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    
    // Sửa một đối tượng
    public Boolean Update(QC_BANG q)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call QCBANG_Update(?,?,?,?)}");
        cs.setString(1, q.getMaQCBang());
        cs.setString(2, q.getMaND());
        cs.setString(3, q.getMaPhieuDangKy());
        cs.setString(4, q.getMaGiaTien());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String ma)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call QCBANG_Delete(?)}");
        cs.setString(1, ma);
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    
    // Trả về một đối tượng QC_BANG có MaQCBang = ma
    public QC_BANG GetRowByID(String ma)throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call QCBANG_SelectByID(?)}");
        cs.setString(1, ma);
        ResultSet rs = cs.executeQuery();
        QC_BANG q = new QC_BANG();
        while(rs.next())
        {
            q.setMaQCBang(rs.getString("MaQCBang"));
            q.setMaND(rs.getString("MaND"));
            q.setMaPhieuDangKy(rs.getString("MaPhieuDangKy"));
            q.setMaGiaTien(rs.getString("MaGiaTien"));
        }
        return q;
    }
    
    // Hàm kiểm tra MaQCBang đã tồn tại hay chưa
    public boolean ValidationID(String maQCBang) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call QCBANG_CheckExist(?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
        cs.setString(2, maQCBang);
        int n = cs.executeUpdate();
        if(n != 0)
            return true;
        return false;
    }
    
    // Kiểm tra MaND có tồn tại trong bảng QC_BANG hay không?
    public boolean IsMaND_BangExist(String maND) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call QCBANG_IsMaND_BangExist(?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
        cs.setString(2, maND);
        cs.execute();
        int n = cs.getInt(1);
        if(n != 0)
            return true;
        return false;
    }
    
    // Kiểm tra MaPhieuDangKy có tồn tại trong bảng QC_BANG hay không?
    public boolean IsMaPhieuDK_BangExist(String maPhieuDK) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call QCBANG_IsMaPhieuDK_BangExist(?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
        cs.setString(2, maPhieuDK);
        cs.execute();        
        int n = cs.getInt(1);
        if(n != 0)
            return true;
        return false;
    }
    
    // Kiểm tra MaGiaTien có tồn tại trong bảng QC_BANG hay không?
    public boolean IsMaGiaTien_BangExist(String maGiaTien) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call QCBANG_IsMaGiaTien_BangExist(?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
        cs.setString(2, maGiaTien);
        cs.execute();        
        int n = cs.getInt(1);
        if(n != 0)
            return true;
        return false;
    }
    
    // Trả về một đối tượng QC_BANG có MaPhieuDangKy = maPhieuDK
    public QC_BANG GetQCBangByMaPhieuDK(String maPhieuDK)throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call QCBANG_GetQCBang_ByMaPhieuDK(?)}");
        cs.setString(1, maPhieuDK);
        ResultSet rs = cs.executeQuery();
        QC_BANG q = new QC_BANG();
        while(rs.next())
        {
            q.setMaQCBang(rs.getString("MaQCBang"));
            q.setMaND(rs.getString("MaND"));
            q.setMaPhieuDangKy(rs.getString("MaPhieuDangKy"));
            q.setMaGiaTien(rs.getString("MaGiaTien"));
        }
        return q;
    }
}
