/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Business;

import DataAccess.DiaOcDAO;
import DataAccess.KhachHangDAO;
import java.util.ArrayList;
import DataAccess.PhieuDangKyDAO;
import DataAccess.QCBangDAO;
import DataAccess.QCBaoDAO;
import DataAccess.ToBuomDAO;
import Entities.PHIEUDANGKY;
import java.sql.Date;

/**
 *
 * @author VooKa
 */
public class PhieuDangKyLogic {
    PhieuDangKyDAO db = new PhieuDangKyDAO();
    DiaOcDAO dbDiaOc = new DiaOcDAO();
    KhachHangDAO dbKhachHang = new KhachHangDAO();
    QCBaoDAO dbQCBao = new QCBaoDAO();
    QCBangDAO dbQCBang = new QCBangDAO();
    ToBuomDAO dbToBuom = new ToBuomDAO();
    
    // Mảng quản lý toàn bộ các đối tượng trong bảng
    public ArrayList<PHIEUDANGKY> mainList = new ArrayList<PHIEUDANGKY>();
    
    // Constructor
    public PhieuDangKyLogic() throws Exception{
        mainList = this.GetAllRows();
    }
    
    // Trả về toàn bộ bảng PHIEUDANGKY
    public ArrayList<PHIEUDANGKY> GetAllRows()throws Exception{
        return db.GetAllRows();
    }
    
    // Thêm một đối tượng
    public String Insert(String maDiaOc, Date tuNgay, Date denNgay,
            Float soTien, Date thoiGianHenChupAnh, String maKH)throws Exception{
        if (denNgay.compareTo(tuNgay)!= -1) {                       // Kiểm tra denngay >= tungay
            if (dbDiaOc.ValidationID(maDiaOc)) {                     // Kiểm tra MaDiaOc hợp lệ
                if (dbKhachHang.ValidationID(maKH)) {                // Kiểm tra MaKH hợp lệ
                    String maPhieuDangKy = db.GetNewID();
                    PHIEUDANGKY p = new PHIEUDANGKY(maPhieuDangKy, maDiaOc, tuNgay, 
                            denNgay, soTien, thoiGianHenChupAnh, maKH);
                    if(db.Insert(p))
                        return maPhieuDangKy;
                    else throw new ExceptionMessage.ExecuteFail("PHIEUDANGKY/Insert \n");
                }else throw new ExceptionMessage.MaKHKhongTonTai();
            }else throw new ExceptionMessage.MaDiaOcKhongTonTai();
        }else throw new ExceptionMessage.NgayKhongHopLe();
    }
    
    // Sửa một đối tượng
    public String Update(String maPhieuDangKy, String maDiaOc, Date tuNgay, Date denNgay,
            Float soTien, Date thoiGianHenChupAnh, String maKH)throws Exception{        
        if (denNgay.compareTo(tuNgay)!= -1) {                       // Kiểm tra denngay >= tungay
            if (ValidationID(maPhieuDangKy)) {                      // Kiểm tra MaPhieuDangKy hợp lệ
                if (dbDiaOc.ValidationID(maDiaOc)) {                     // Kiểm tra MaDiaOc hợp lệ
                    if (dbKhachHang.ValidationID(maKH)) {                // Kiểm tra MaKH hợp lệ
                        PHIEUDANGKY p = new PHIEUDANGKY(maPhieuDangKy, maDiaOc, tuNgay, denNgay, soTien, thoiGianHenChupAnh, maKH);
                        if(db.Update(p))
                            return maPhieuDangKy;
                        else throw new ExceptionMessage.ExecuteFail("PHIEUDANGKY/Update \n");
                    }else throw new ExceptionMessage.MaKHKhongTonTai();
                }else throw new ExceptionMessage.MaDiaOcKhongTonTai();
            }else throw new ExceptionMessage.MaPhieuDKKhongTonTai();
        }else throw new ExceptionMessage.NgayKhongHopLe();
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String maPhieuDangKy)throws Exception{
        if (ValidationID(maPhieuDangKy)) {                                 // Kiểm tra MaPhieuDangKy hợp lệ
            if (!dbQCBao.IsMaPhieuDK_BaoExist(maPhieuDangKy)) {            // Kiểm tra xem MaPhieuDK có bị tham chiếu từ QC_BAO không
                if (!dbToBuom.IsMaPhieuDK_ToBuomExist(maPhieuDangKy)) {    // Kiểm tra xem MaPhieuDK có bị tham chiếu từ TOBUOM không
                    if (!dbQCBang.IsMaPhieuDK_BangExist(maPhieuDangKy)) {  // Kiểm tra xem MaPhieuDK có bị tham chiếu từ QC_BANG không
                        return db.Delete(maPhieuDangKy);
                    }else throw new ExceptionMessage.MaThamChieu_QCBang_PhieuDK();
                }else throw new ExceptionMessage.MaThamChieu_ToBuom_PhieuDK();
            }else throw new ExceptionMessage.MaThamChieu_QCBao_PhieuDK();
        }else throw new ExceptionMessage.MaPhieuDKKhongTonTai();
    }
    
    // Trả về một đối tượng PHIEUDANGKY có MaPhieuDangKy = maPhieuDangKy
    public PHIEUDANGKY GetRowByID(String maPhieuDangKy)throws Exception{
        if (ValidationID(maPhieuDangKy)) {                      // Kiểm tra MaPhieuDangKy hợp lệ
            return db.GetRowByID(maPhieuDangKy);
        }
        else throw new ExceptionMessage.MaPhieuDKKhongTonTai();
    }
    
    // Kiểm tra xem một MaPhieuDangKy có tồn tại trong bảng hay chưa
    public Boolean ValidationID(String maPhieuDangKy) throws Exception{
        return db.ValidationID(maPhieuDangKy);
    }
    
    // Kiểm tra xem một MaDiaOc có tồn tại trong bảng hay không?
    public Boolean IsDiaOcExist(String maDiaOc) throws Exception{
        return db.IsDiaOcExist(maDiaOc);
    }
    
    // Kiểm tra xem một MaKH có tồn tại trong bảng hay không?
    public Boolean IsKhachHangExist(String maKH) throws Exception{
        return db.IsKhachHangExist(maKH);
    }
    
    // Trả về một đối tượng PHIEUDANGKY có MaDiaOc = maDiaOc
    public PHIEUDANGKY GetPhieuDKByMaDiaOc(String maDiaOc)throws Exception{
        if (this.IsDiaOcExist(maDiaOc)) {
            return db.GetPhieuDKByMaDiaOc(maDiaOc);
        }else throw new ExceptionMessage.MaDiaOcKhongTonTai();
    }
    
    // Trả về các đối tượng PHIEUDANGKY có MaDiaOc = maDiaOc
    public ArrayList<PHIEUDANGKY> GetAllPhieuDKByMaDiaOc(String maDiaOc)throws Exception{
        if (this.IsDiaOcExist(maDiaOc)) {
            return db.GetAllPhieuDKByMaDiaOc(maDiaOc);
        }else throw new ExceptionMessage.MaDiaOcKhongTonTai();
    }
}
