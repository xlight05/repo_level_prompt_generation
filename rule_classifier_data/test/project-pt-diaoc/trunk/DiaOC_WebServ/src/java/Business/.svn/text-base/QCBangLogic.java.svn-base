/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Business;

import DataAccess.GiaTien_QC_BangDAO;
import DataAccess.LoaiBangDAO;
import DataAccess.NoiDungQCDAO;
import DataAccess.PhieuDangKyDAO;
import java.util.ArrayList;
import DataAccess.QCBangDAO;
import Entities.QC_BANG;
/**
 *
 * @author VooKa
 */
public class QCBangLogic {
    QCBangDAO db = new QCBangDAO();
    NoiDungQCDAO dbNoiDungQC = new NoiDungQCDAO();
    LoaiBangDAO dbLoaiBang = new LoaiBangDAO();
    PhieuDangKyDAO dbPhieuDangKy = new PhieuDangKyDAO();
    GiaTien_QC_BangDAO dbGiaTienQCBang = new GiaTien_QC_BangDAO();
    
    // Mảng quản lý toàn bộ bảng
    public ArrayList<QC_BANG> mainList = new ArrayList<QC_BANG>();
    
    // Constructor 
    public QCBangLogic() throws Exception{
        mainList = GetAllRows();
    }
    
    // Trả về toàn bộ bảng QC_BANG
    public ArrayList<QC_BANG> GetAllRows()throws Exception{
        return db.GetAllRows();
    }
    
    // Thêm một đối tượng
    public String Insert(String maND, String maPhieuDK, String maGiaTien)throws Exception{
        if (dbNoiDungQC.ValidationID(maND)) {                       // Kiểm tra sự hợp lệ của MaND
            if (dbPhieuDangKy.ValidationID(maPhieuDK)) {            // Kiểm tra sự hợp lệ của MaPhieuDangKy
                if (dbGiaTienQCBang.ValidationID(maGiaTien)) {      // Kiểm tra sự hợp lệ của MaGiaTien
                    String maQCBang = db.GetNewID();
                    QC_BANG q = new QC_BANG(maQCBang, maND, maPhieuDK, maGiaTien);
                    if(db.Insert(q)){
                        return maQCBang;
                    }else throw new ExceptionMessage.ExecuteFail("QCBANG/Insert \n");
                }else throw new ExceptionMessage.MaGiaTienKhongHopLe();
            }else throw new ExceptionMessage.MaPhieuDKKhongTonTai();
        }else throw new ExceptionMessage.MaNDQCKhongTonTai(); 
    }
    
    // Sửa một đối tượng
    public String Update(String maQCBang, String maND,
            String maPhieuDK, String maGiaTien)throws Exception{
        if (dbNoiDungQC.ValidationID(maND)) {                       // Kiểm tra sự hợp lệ của MaND
            if (dbPhieuDangKy.ValidationID(maPhieuDK)) {            // Kiểm tra sự hợp lệ của MaPhieuDangKy
                if (dbGiaTienQCBang.ValidationID(maGiaTien)) {      // Kiểm tra sự hợp lệ của MaGiaTien
                    if (ValidationID(maQCBang)) {                   // Kiểm tra sự hợp lệ của MaQCBang
                        QC_BANG q = new QC_BANG(maQCBang, maND, maPhieuDK, maGiaTien);
                        if(db.Update(q)){
                            return maQCBang;
                        }else throw new ExceptionMessage.ExecuteFail("QCBANG/Update \n");
                    }else throw new ExceptionMessage.MaQCBangKhongTonTai();
                }else throw new ExceptionMessage.MaGiaTienKhongHopLe();
            }else throw new ExceptionMessage.MaPhieuDKKhongTonTai();
        }else throw new ExceptionMessage.MaNDQCKhongTonTai(); 
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String maQCBang)throws Exception{
        if (ValidationID(maQCBang)) {           // Kiểm tra sự hợp lệ của MaQCBang
            return db.Delete(maQCBang);
        }
        else throw new ExceptionMessage.MaQCBangKhongTonTai();
    }
    public QC_BANG GetRowByID(String maQCBang)throws Exception{
        if (ValidationID(maQCBang)) {           // Kiểm tra sự hợp lệ của MaQCBang
            return db.GetRowByID(maQCBang);
        }
        else throw new ExceptionMessage.MaQCBangKhongTonTai();
    }
    
    // Hàm kiểm tra MaQCBang đã tồn tại hay chưa
    public Boolean ValidationID(String maQCBang) throws Exception{
        return db.ValidationID(maQCBang);
    }
    
    // Kiểm tra MaND có tồn tại trong bảng QC_BANG hay không?
    public Boolean IsMaND_BangExist(String maND) throws Exception{
        return db.IsMaND_BangExist(maND);
    }
    
    // Kiểm tra MaPhieuDangKy có tồn tại trong bảng QC_BANG hay không?
    public Boolean IsMaPhieuDK_BangExist(String maPhieuDK) throws Exception{
        return db.IsMaPhieuDK_BangExist(maPhieuDK);
    }
    
    // Kiểm tra MaGiaTien có tồn tại trong bảng QC_BANG hay không?
    public Boolean IsMaGiaTien_BangExist(String maGiaTien) throws Exception{
        return db.IsMaGiaTien_BangExist(maGiaTien);
    }
    
    // Trả về một đối tượng QC_BANG có MaPhieuDangKy = maPhieuDK
    public QC_BANG GetQCBangByMaPhieuDK(String maPhieuDK)throws Exception{
        if (this.IsMaPhieuDK_BangExist(maPhieuDK)) {
            return db.GetQCBangByMaPhieuDK(maPhieuDK);
        }else throw new ExceptionMessage.MaPhieuDKKhongTonTai();
    }
}
