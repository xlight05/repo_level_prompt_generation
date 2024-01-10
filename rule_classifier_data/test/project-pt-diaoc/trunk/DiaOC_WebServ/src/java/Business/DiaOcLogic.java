/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Business;

import DataAccess.*;
import Entities.DIAOC;
import Entities.DIACHI;
import java.util.ArrayList;

/**
 *
 * @author VooKa
 */
public class DiaOcLogic {    
    DiaOcDAO db = new DiaOcDAO();
    LoaiDiaOcDAO dbLoaiDiaOc = new LoaiDiaOcDAO();
    
    PhieuDangKyDAO dbPhieuDangKy = new PhieuDangKyDAO();
    
    DiaChiDAO dbDiaChi = new DiaChiDAO();
    
    // Mảng quản lý các đối tượng DIAOC
    public ArrayList<DIAOC> mainList = new ArrayList<DIAOC> ();
    // Constructor
    public DiaOcLogic() throws Exception{
        mainList = GetAllRows();
    }
    
    // Trả về toàn bộ bảng DIAOC
    public ArrayList<DIAOC> GetAllRows()throws Exception{
        return db.GetAllRows();
    }
    
    // Thêm một đối tượng DIAOC
    public String Insert(String maLoaiDiaOc, String maThanhPho, String maQuan,
            String maPhuong, String maDuong, String DiaChiCT, Float dienTichDat,
            Float dienTichXayDung, String huong, String viTri,
            String moTa, String trangThai, Float giaBan)throws Exception{
        if (dienTichDat > 0 && dienTichXayDung > 0) {                           // Kiểm tra sự hợp lệ của tham số diện tích
            if (giaBan > 0) {                                                   // Kiểm tra sự hợp lệ của Giá Bán
                if (dbLoaiDiaOc.ValidationID(maLoaiDiaOc)) {                     // Kiểm tra sự hợp lệ của MaLoaiDiaOc
                    String maDiaChi = dbDiaChi.GetNewID();
                    DIACHI dc = new DIACHI(maDiaChi, maThanhPho, maQuan, maPhuong, maDuong, DiaChiCT);
                    String maDiaOc = db.GetNewID();
                    DIAOC d = new DIAOC(maDiaOc, maLoaiDiaOc, dc, dienTichDat,
                            dienTichXayDung, huong, viTri, moTa, trangThai, giaBan);
                    if (db.Insert(d)) {
                        return maDiaChi;
                    }else throw new ExceptionMessage.ExecuteFail("DIAOC/Insert \n");
                }
                else throw new ExceptionMessage.MaLoaiDiaOcKhongTonTai();
            }
            else throw new ExceptionMessage.GiaTienKhongHopLe();
        }
        else throw new ExceptionMessage.DienTichKhongHopLe();
    }
    
    // Sửa một đối tượng
    public String Update(String maDiaOc,String maLoaiDiaOc, String maThanhPho, String maQuan,
            String maPhuong, String maDuong, String DiaChiCT, Float dienTichDat,
            Float dienTichXayDung, String huong, String viTri,
            String moTa, String trangThai, Float giaBan)throws Exception{
        if (dienTichDat > 0 && dienTichXayDung > 0) {                           // Kiểm tra sự hợp lệ của tham số diện tích
            if (giaBan > 0) {                                                   // Kiểm tra sự hợp lệ của Giá Bán
                if (dbLoaiDiaOc.ValidationID(maLoaiDiaOc)) {                     // Kiểm tra sự hợp lệ của MaLoaiDiaOc
                    if (ValidationID(maDiaOc)) {                                // Kiểm tra sự hợp lệ của MaDiaOc
                        DIAOC d = db.GetRowByID(maDiaOc);
                        String maDiaChi = d.getDiaChiDiaOc().getMaDiaChi();
                        DIACHI dc = new DIACHI(maDiaChi, maThanhPho, maQuan, maPhuong, maDuong, DiaChiCT);
                        
                        d.setMaLoaiDiaOc(maLoaiDiaOc);
                        d.setDiaChiDiaOc(dc);
                        d.setDienTichDat(dienTichDat);
                        d.setDienTichXayDung(dienTichXayDung);
                        d.setHuong(huong);
                        d.setViTri(viTri);
                        d.setMoTa(moTa);
                        d.setTrangThai(trangThai);
                        d.setGiaBan(giaBan);
                        
                        if (db.Update(d)) {
                            return maDiaOc;
                        }else throw new ExceptionMessage.ExecuteFail("DIAOC/Update \n");
                    }
                    else throw new ExceptionMessage.MaDiaOcKhongTonTai();
                }
                else throw new ExceptionMessage.MaLoaiDiaOcKhongTonTai();
            }
            else throw new ExceptionMessage.GiaTienKhongHopLe();
        }
        else throw new ExceptionMessage.DienTichKhongHopLe();
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String maDiaOc)throws Exception{
        if (ValidationID(maDiaOc)) {                             // Kiểm tra sự hợp lệ của MaDiaOc
            if (!dbPhieuDangKy.IsDiaOcExist(maDiaOc)) {           // Kiểm tra xem MaDiaOc có bị tham chiếu ko
                return db.Delete(maDiaOc);
            }
            else throw new ExceptionMessage.MaThamChieuChiTietBao_Bao();
        }
        else throw new ExceptionMessage.MaDiaOcKhongTonTai();        
    }
    
    // Trả về một đối tượng DIAOC có MaDicOc = maDiaOc
    public DIAOC GetRowByID(String maDiaOc)throws Exception{
        if (ValidationID(maDiaOc)) {                             // Kiểm tra sự hợp lệ của MaDiaOc
            return db.GetRowByID(maDiaOc);
        }
        else throw new ExceptionMessage.MaDiaOcKhongTonTai();
    }
    
    // Kiểm tra xem một MaDiaOc có tồn tại trong bảng hay chưa
    public Boolean ValidationID(String maDiaOc) throws Exception{
        return db.ValidationID(maDiaOc);
    }
    
    // Kiểm tra xem một MaLoaiDiaOc có tồn tại trong bảng hay không
    public Boolean IsLoaiDiaOcExist(String maLoaiDiaOc) throws Exception{
        return db.IsLoaiDiaOcExist(maLoaiDiaOc);
    }
    
    // Trả về những đối tượng DIAOC theo MaLoaiDiaOc
    public ArrayList<DIAOC> GetRowsByMaLoaiDiaOc(String maLoaiDiaOc)throws Exception{
        return db.GetRowsByMaLoaiDiaOc(maLoaiDiaOc);
    }
    
    // Trả về những đối tượng DIAOC theo TrangThai
    public ArrayList<DIAOC> GetRowsByTrangThai(String trangThai)throws Exception{
        return db.GetRowsByTrangThai(trangThai);
    }
    
    // Trả về MaND tương ứng với MaDiaOc (Từ TOBUOM)
    public String GetMaND_ToBuom_From_MaDiaOc(String maDiaOc) throws Exception{
        return db.GetMaND_ToBuom_From_MaDiaOc(maDiaOc);
    }
    
    // Trả về MaND tương ứng với MaDiaOc (Từ QC_BAO)
    public String GetMaND_QCBao_From_MaDiaOc(String maDiaOc) throws Exception{
        return db.GetMaND_QCBao_From_MaDiaOc(maDiaOc);
    }
    
    // Trả về MaND tương ứng với MaDiaOc (Từ QC_BANG)
    public String GetMaND_QCBang_From_MaDiaOc(String maDiaOc) throws Exception{
        return db.GetMaND_QCBang_From_MaDiaOc(maDiaOc);
    }    
}