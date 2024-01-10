/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Business;

import DataAccess.ChiTietBaoDAO;
import DataAccess.GiaTien_QC_BaoDAO;
import DataAccess.NoiDungQCDAO;
import DataAccess.PhieuDangKyDAO;
import java.util.ArrayList;
import DataAccess.QCBaoDAO;
import Entities.QC_BAO;
import java.sql.Date;
/**
 *
 * @author VooKa
 */
public class QCBaoLogic {
    QCBaoDAO db = new QCBaoDAO();
    ChiTietBaoDAO dbChiTietBao = new ChiTietBaoDAO();
    NoiDungQCDAO dbNoiDungQC = new NoiDungQCDAO();
    PhieuDangKyDAO dbPhieuDangKy = new PhieuDangKyDAO();
    GiaTien_QC_BaoDAO dbGiaTienQCBao = new GiaTien_QC_BaoDAO();
    
    // Mảng quản lý toàn bộ bảng QC_BAO
    public ArrayList<QC_BAO> mainList = new ArrayList<QC_BAO>();
    
    // Constructor
    public QCBaoLogic() throws Exception{
        mainList = GetAllRows();
    }
    
    // Trả về toàn bộ bảng QC_BAO
    public ArrayList<QC_BAO> GetAllRows()throws Exception{
        return db.GetAllRows();
    }
    
    // Thêm một đối tượng
    public String Insert(Date ngayBatDauPhatHanh, Boolean hinhAnh,
        String maND, String maPhieuDK, String maGiaTien) throws Exception{
        if (dbNoiDungQC.ValidationID(maND)) {                       // Kiểm tra sự hợp lệ của MaND
            if (dbPhieuDangKy.ValidationID(maPhieuDK)) {            // Kiểm tra sự hợp lệ của MaPhieuDK
                if (dbGiaTienQCBao.ValidationID(maGiaTien)) {       // Kiểm tra sự hợp lệ của MaGiaTien
                    String maQCBao = db.GetNewID();
                    QC_BAO b = new QC_BAO(maQCBao, ngayBatDauPhatHanh, hinhAnh, maND, maPhieuDK, maGiaTien);
                    if(db.Insert(b))
                        return maQCBao;
                    else throw new ExceptionMessage.ExecuteFail("QCBAo/Insert \n");
                }else throw new ExceptionMessage.MaGiaTienKhongHopLe();
            }else throw new ExceptionMessage.MaPhieuDKKhongTonTai();
        }else throw new ExceptionMessage.MaNDQCKhongTonTai();      
    }
    
    // Sửa một đối tượng
    public String Update(String maQCBao, Date ngayBatDauPhatHanh, Boolean hinhAnh,
            String maND, String maPhieuDK, String maGiaTien)throws Exception{
        if (dbNoiDungQC.ValidationID(maND)) {                       // Kiểm tra sự hợp lệ của MaND
            if (dbPhieuDangKy.ValidationID(maPhieuDK)) {            // Kiểm tra sự hợp lệ của MaPhieuDK
                if (dbGiaTienQCBao.ValidationID(maGiaTien)) {       // Kiểm tra sự hợp lệ của MaGiaTien
                    if (ValidationID(maQCBao)) {                    // Kiểm tra sự hợp lệ của MaQCBao
                        QC_BAO b = new QC_BAO(maQCBao, ngayBatDauPhatHanh, hinhAnh, maND, maPhieuDK, maGiaTien);
                        if(db.Update(b))
                            return maQCBao;
                        else throw new ExceptionMessage.ExecuteFail("QCBAo/Update \n");
                    }else throw new ExceptionMessage.MaQCBaoKhongTonTai();
                }else throw new ExceptionMessage.MaGiaTienKhongHopLe();
            }else throw new ExceptionMessage.MaPhieuDKKhongTonTai();
        }else throw new ExceptionMessage.MaNDQCKhongTonTai();
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String maQCBao)throws Exception{
        if (ValidationID(maQCBao)) {                        // Kiểm tra sự hợp lệ của MaQCBao
            if (dbChiTietBao.IsMaQCBaoExist(maQCBao)) {     // Kiểm tra xem MaQCBao có bị tham chiếu trong CHITIETBAO không?
                return db.Delete(maQCBao);
            }else throw new ExceptionMessage.MaThamChieuKhongTonTai();
        }
        else throw new ExceptionMessage.MaQCBaoKhongTonTai();
    }
    
    // Trả về một đối tượng QC_BAO có MaQCBao = maQCBao
    public QC_BAO GetRowByID(String maQCBao)throws Exception{
        if (ValidationID(maQCBao)) {            // Kiểm tra sự hợp lệ của MaQCBao
            return db.GetRowByID(maQCBao);
        }
        else throw new ExceptionMessage.MaQCBaoKhongTonTai();
    }
    
    // Hàm kiểm tra MaQCBao đã tồn tại hay chưa
    public Boolean ValidationID(String maQCBao) throws Exception{
        return db.ValidationID(maQCBao);
    }
    
    // Kiểm tra MaND có tồn tại trong bảng QC_BAO hay không?
    public Boolean IsMaND_BaoExist(String maND) throws Exception{
        return db.IsMaND_BaoExist(maND);
    }
    
    // Kiểm tra MaPhieuDangKy có tồn tại trong bảng QC_BAO hay không?
    public Boolean IsMaPhieuDK_BaoExist(String maPhieuDK) throws Exception{
        return db.IsMaPhieuDK_BaoExist(maPhieuDK);
    }
    
    // Kiểm tra MaGiaTien có tồn tại trong bảng QC_BAO hay không?
    public Boolean IsMaGiaTien_BaoExist(String maGiaTien) throws Exception{
        return db.IsMaGiaTien_BaoExist(maGiaTien);
    }
    
    // Trả về một đối tượng QC_BAO có MaPhieuDK = maPhieuDK
    public QC_BAO GetQCBaoByMaPhieuDK(String maPhieuDK)throws Exception{
        if (this.IsMaPhieuDK_BaoExist(maPhieuDK)) {
            return db.GetQCBaoByMaPhieuDK(maPhieuDK);
        }else throw new ExceptionMessage.MaPhieuDKKhongTonTai();
    }
}
