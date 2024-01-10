/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Business;

import DataAccess.*;
import java.util.ArrayList;
import Entities.*;
/**
 *
 * @author VooKa
 */
public class KhachHangLogic {
    KhachHangDAO db = new KhachHangDAO();
    PhieuDangKyDAO dbPhieuDangKy = new PhieuDangKyDAO();
    DiaChiDAO dbDiaChi = new DiaChiDAO();
    
    // Mảng quản lý các đối tượng KHACHHANG
    public ArrayList<KHACHHANG> mainList = new ArrayList<KHACHHANG> ();
    
    // Constructor
    public KhachHangLogic() throws Exception{
        mainList = GetAllRows();
    }
    
    // Trả về toàn bộ bảng KHACHHANG
    public ArrayList<KHACHHANG> GetAllRows()throws Exception{
        return db.GetAllRows();
    }
    
    
    public String Insert(String hoTen, String maThanhPho, String maQuan, String maPhuong,
            String maDuong, String diaChiCT, String soDT, String email)throws Exception{
        String maKH = db.GetNewID();
        String maDiaChi = dbDiaChi.GetNewID();
        DIACHI dc = new DIACHI(maDiaChi, maThanhPho, maQuan, maPhuong, maDuong, diaChiCT);
        KHACHHANG k = new KHACHHANG(maKH, hoTen, dc, soDT, email);
        if (db.Insert(k)) {
            return maKH;
        }else throw new ExceptionMessage.ExecuteFail("KHACHHANG/Insert \n");
    }
    
    // Sửa một đối tượng
    public String Update(String maKH, String hoTen, String maThanhPho, String maQuan, String maPhuong,
            String maDuong, String diaChiCT, String soDT, String email)throws Exception{
        if (ValidationID(maKH)) { // Kiểm tra sự hợp lệ của MaKH
            
            KHACHHANG k = db.GetRowByID(maKH);
            String maDiaChi = k.getDiaChiKH().getMaDiaChi();
            DIACHI dc = new DIACHI(maDiaChi, maThanhPho, maQuan, maPhuong, maDuong, diaChiCT);
            k.setDiaChiKH(dc);
            k.setHoTen(hoTen);
            k.setSoDT(soDT);
            k.setEmail(email);
            if (db.Update(k)) {
                return maKH;
            }else throw new ExceptionMessage.ExecuteFail("KHACHHANG/Update \n");
        }
        else throw new ExceptionMessage.MaKHKhongTonTai();
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String maKH)throws Exception{
        if (ValidationID(maKH)) {                                     // Kiểm tra sự hợp lệ của MaKH
            if (!dbPhieuDangKy.IsKhachHangExist(maKH)) {               // Kiểm tra xem MaKH có bị tham chiếu ko
                return db.Delete(maKH);
            }
            else throw new ExceptionMessage.MaThamChieuKhongTonTai();
        }
        else throw new ExceptionMessage.MaKHKhongTonTai();
    }
    
    //Trả về một đối tượng KHACHHANG có MaKH = maKH
    public KHACHHANG GetRowByID(String maKH)throws Exception{
        if (ValidationID(maKH)) {
            return db.GetRowByID(maKH);
        }
        else throw new ExceptionMessage.MaKHKhongTonTai();
    }
    
    // Hàm kiểm tra MaKH đã tồn tại hay chưa
    public Boolean ValidationID(String maKH) throws Exception{
        return db.ValidationID(maKH);
    }
    
    // Trả về những đối tượng KHACHHANG theo HoTen
    public ArrayList<KHACHHANG> GetRowsByName(String hoTen)throws Exception{
        return db.GetRowsByName(hoTen);
    }
    
    // Trả về những đối tượng KHACHHANG theo Email
    public ArrayList<KHACHHANG> GetRowsByEmail(String email)throws Exception{//cái này thêm nè
        return db.GetRowsByEmail(email);
    }
}
