/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DataAccess;

import Entities.QC_BAO;
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
public class QCBaoDAO {
    
    // Tạo mới một MaQCBao
    public String GetNewID()throws Exception{
        SQLConnection con = new SQLConnection();
        String s = con.SCallFunction("{call QCBAO_CreateID(?)}");
        return s;
    }
    
    // Trả về toàn bộ bảng QC_BAO
    public ArrayList<QC_BAO> GetAllRows()throws Exception{
        ArrayList<QC_BAO> lst = new ArrayList<QC_BAO>();
        QC_BAO q = null;
        SQLConnection con = new SQLConnection();
        String sql = "select * from QC_BAO";
        ResultSet rs = con.executeQuery(sql);
        while(rs.next())
        {
            q = new QC_BAO(rs.getString("MaQCBao"), rs.getDate("NgayBatDauPhatHanh"),
                    rs.getBoolean("CoHinhAnh"), rs.getString("MaND"),
                    rs.getString("MaPhieuDangKy"), rs.getString("MaGiaTien"));
            lst.add(q);
        }
        return lst;
    }
    
    // Thêm một đối tượng
    public Boolean Insert(QC_BAO q)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call QCBAO_Insert(?,?,?,?,?,?)}");
        cs.setString(1, q.getMaQCBao());
        Date d = q.getNgayBatDauPhatHanh();
        cs.setString(2, d.toString());
        cs.setBoolean(3, q.getCoHinhAnh());
        cs.setString(4, q.getMaND());
        cs.setString(5, q.getMaPhieuDangKy());
        cs.setString(6, q.getMaGiaTien());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    
    // Sửa một đối tượng
    public Boolean Update(QC_BAO q)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call QCBAO_Update(?,?,?,?,?,?)}");
        cs.setString(1, q.getMaQCBao());
        Date d = q.getNgayBatDauPhatHanh();
        cs.setString(2, d.toString());
        cs.setBoolean(3, q.getCoHinhAnh());
        cs.setString(4, q.getMaND());
        cs.setString(5, q.getMaPhieuDangKy());
        cs.setString(6, q.getMaGiaTien());
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
        
    // Xóa một đối tượng
    public Boolean Delete(String ma)throws Exception{
        Boolean result = true;
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call QCBAO_Delete(?)}");
        cs.setString(1, ma);
        int n = cs.executeUpdate();
        if(n == 0)
            result = false;
        return result;
    }
    
    // Trả về một đối tượng QC_BAO có MaQCBao = ma
    public QC_BAO GetRowByID(String ma)throws Exception{
        QC_BAO q = new QC_BAO();
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call QCBAO_SelectByID(?)}");
        cs.setString(1, ma);
        ResultSet rs = cs.executeQuery();
        while(rs.next())
        {
            q.setMaQCBao(rs.getString("MaQCBao"));
            q.setNgayBatDauPhatHanh(rs.getDate("NgayBatDauPhatHanh"));
            q.setCoHinhAnh(rs.getBoolean("CoHinhAnh"));
            q.setMaND(rs.getString("MaND"));
            q.setMaPhieuDangKy(rs.getString("MaPhieuDangKy"));
            q.setMaGiaTien(rs.getString("MaGiaTien"));
        }
        return q;
    }
    
    // Hàm kiểm tra MaQCBao đã tồn tại hay chưa
    public boolean ValidationID(String maQCBao) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call QCBAO_CheckExist(?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
        cs.setString(2, maQCBao);
        cs.execute();        
        int n = cs.getInt(1);
        if(n != 0)
            return true;
        return false;
    }
    
    // Kiểm tra MaND có tồn tại trong bảng QC_BAO hay không?
    public boolean IsMaND_BaoExist(String maND) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call QCBAO_IsMaND_BaoExist(?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
        cs.setString(2, maND);
        cs.execute();        
        int n = cs.getInt(1);
        if(n != 0)
            return true;
        return false;
    }
    
    // Kiểm tra MaPhieuDangKy có tồn tại trong bảng QC_BAO hay không?
    public boolean IsMaPhieuDK_BaoExist(String maPhieuDK) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call QCBAO_IsMaPhieuDK_BaoExist(?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
        cs.setString(2, maPhieuDK);
        cs.execute();        
        int n = cs.getInt(1);
        if(n != 0)
            return true;
        return false;
    }
    
    // Kiểm tra MaGiaTien có tồn tại trong bảng QC_BAO hay không?
    public boolean IsMaGiaTien_BaoExist(String maGiaTien) throws Exception{
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{?=call QCBAO_IsMaGiaTien_BaoExist(?)}");
        cs.registerOutParameter(1, java.sql.Types.INTEGER);
        cs.setString(2, maGiaTien);
        cs.execute();        
        int n = cs.getInt(1);
        if(n != 0)
            return true;
        return false;
    }
    
    // Trả về một đối tượng QC_BAO có MaPhieuDK = maPhieuDK
    public QC_BAO GetQCBaoByMaPhieuDK(String maPhieuDK)throws Exception{
        QC_BAO q = new QC_BAO();
        Connection con = new SQLConnection().GetConnect();
        CallableStatement cs = con.prepareCall("{call QCBAO_GetQCBao_ByMaPhieuDK(?)}");
        cs.setString(1, maPhieuDK);
        ResultSet rs = cs.executeQuery();
        while(rs.next())
        {
            q.setMaQCBao(rs.getString("MaQCBao"));
            q.setNgayBatDauPhatHanh(rs.getDate("NgayBatDauPhatHanh"));
            q.setCoHinhAnh(rs.getBoolean("CoHinhAnh"));
            q.setMaND(rs.getString("MaND"));
            q.setMaPhieuDangKy(rs.getString("MaPhieuDangKy"));
            q.setMaGiaTien(rs.getString("MaGiaTien"));
        }
        return q;
    }
}
