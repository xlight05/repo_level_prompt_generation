/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Business;

import DataAccess.GiaTien_QC_BangDAO;
import DataAccess.LoaiBangDAO;
import DataAccess.QCBangDAO;
import java.util.ArrayList;
import Entities.GIATIEN_QC_BANG;

/**
 *
 * @author VooKa
 */
public class GiaTienQCBangLogic {
    GiaTien_QC_BangDAO db = new GiaTien_QC_BangDAO();
    LoaiBangDAO dbLoaiBang = new LoaiBangDAO();
    QCBangDAO dbQCBang = new QCBangDAO();
    
    // Mảng quản lý toàn bộ bảng
    public ArrayList<GIATIEN_QC_BANG> mainList = new ArrayList<GIATIEN_QC_BANG>();
    
    // Constructor
    public GiaTienQCBangLogic() throws Exception{
        mainList = GetAllRows();
    }
    
    // Trả về toàn bộ bảng GIATIEN_QC_BANG
    public ArrayList<GIATIEN_QC_BANG> GetAllRows()throws Exception{
        return db.GetAllRows();
    }
    
    // Thêm một đối tượng
    public String Insert(String maLoaiBang, String moTa, Float kichCo, String dvt, Float giaTien)throws Exception{
        if (kichCo > 0 && giaTien >= 0) {                   // Kiểm tra sự hợp lệ của KichCo và GiaTien
            if (dbLoaiBang.ValidationID(maLoaiBang)) {       // Kiểm tra sự hợp lệ của MaLoaiBang
                String maGiaTien = db.GetNewID();
                GIATIEN_QC_BANG g = new GIATIEN_QC_BANG(maGiaTien, maLoaiBang, moTa, kichCo, dvt, giaTien);
                if(db.Insert(g)){
                    return maGiaTien;
                }else throw new ExceptionMessage.ExecuteFail("GIATIEN_QCG/Insert \n");
            }
            else throw new ExceptionMessage.MaLoaiBangKhongTonTai();
        }
        else throw new ExceptionMessage.GiaTienKhongHopLe();
    }
    
    // Sửa một đối tượng
    public String Update(String maGiaTien, String maLoaiBang, String moTa, Float kichCo, String dvt, Float giaTien)throws Exception{
        if (kichCo > 0 && giaTien >= 0) {                   // Kiểm tra sự hợp lệ của KichCo và GiaTien
            if (dbLoaiBang.ValidationID(maLoaiBang)) {       // Kiểm tra sự hợp lệ của MaLoaiBang
                if (ValidationID(maGiaTien)) {                  // Kiểm tra sự hợp lệ của MaGiaTien
                    GIATIEN_QC_BANG g = new GIATIEN_QC_BANG(maGiaTien, maLoaiBang, moTa, kichCo, dvt, giaTien);
                    if(db.Update(g)){
                        return maGiaTien;
                    }else throw new ExceptionMessage.ExecuteFail("GIATIEN_QCG/Update \n");
                }
                else throw new ExceptionMessage.MaGiaTienKhongHopLe();
            }
            else throw new ExceptionMessage.MaLoaiBangKhongTonTai();
        }
        else throw new ExceptionMessage.GiaTienKhongHopLe();
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String maGiaTien)throws Exception{
        if (ValidationID(maGiaTien)) {                             // Kiểm tra sự hợp lệ của MaGiaTien
            if (!dbQCBang.IsMaGiaTien_BangExist(maGiaTien)) {      // Kiểm tra xem MaGiaTien có bị tham chiếu hay không?
                return db.Delete(maGiaTien);
            }
            else throw new ExceptionMessage.MaThamChieuKhongTonTai();
        }
        else throw new ExceptionMessage.MaGiaTienKhongHopLe();
    }
    
    // Trả về một đối tượng GIATIEN_QC_BANG có MaGiaTien = maGiaTien
    public GIATIEN_QC_BANG GetRowByID(String maGiaTien)throws Exception{
        if (ValidationID(maGiaTien)) {                  // Kiểm tra sự hợp lệ của MaGiaTien
            return db.GetRowByID(maGiaTien);
        }
        else throw new ExceptionMessage.MaGiaTienKhongHopLe();
    }
    
    // Kiểm tra MaGiaTien có tồn tại trong bảng hay chưa
    public Boolean ValidationID(String maGiaTien) throws Exception{
        return db.ValidationID(maGiaTien);
    }
    
    // Kiểm tra MaLoaiBang có tồn tại trong bảng GIATIEN_QC_BANG hay không?
    public Boolean IsMaLoaiBangExist(String maLoaiBang) throws Exception{
        return db.IsMaLoaiBangExist(maLoaiBang);
    }
    
    // Trả về một đối tượng LoaiBang có MaLoaiBang = maLoaiBang
    public String GetLoaiBang(String maLoaiBang)throws Exception{
        return db.GetLoaiBang(maLoaiBang);
    }
}
