/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Business;

import DataAccess.GiaTienQCToBuomDAO;
import DataAccess.NoiDungQCDAO;
import DataAccess.PhieuDangKyDAO;
import java.util.ArrayList;
import Entities.TOBUOM;
import DataAccess.ToBuomDAO;
/**
 *
 * @author VooKa
 */
public class ToBuomLogic {
    ToBuomDAO db = new ToBuomDAO();
    NoiDungQCDAO dbNoiDungQC = new NoiDungQCDAO();
    PhieuDangKyDAO dbPhieuDangKy = new PhieuDangKyDAO();
    GiaTienQCToBuomDAO dbGiaTienToBuom = new GiaTienQCToBuomDAO();
            
    // Mảng quản lý toàn bộ bảng TOBUOM    
    public ArrayList<TOBUOM> mainList = new ArrayList<TOBUOM>();
    
    // Constructor
    public ToBuomLogic() throws Exception{
        mainList = GetAllRows();
    }
    
    // Trả về toàn bộ bảng
    public ArrayList<TOBUOM> GetAllRows()throws Exception{
        return db.GetAllRows();
    }
    
    // Thêm một đối tượng
    public String Insert(String maND, String maPhieuDK, String maGiaTien)throws Exception{
        if (dbNoiDungQC.ValidationID(maND)) {                        // Kiểm tra sự hợp lệ của MaND
            if (dbPhieuDangKy.ValidationID(maPhieuDK)) {             // Kiểm tra sự hợp lệ của MaPhieuDangKy
                if (dbGiaTienToBuom.ValidationID(maGiaTien)) {       // Kiểm tra sự hợp lệ của MaGiaTien
                    String maToBuom = db.GetNewID();
                    TOBUOM t = new TOBUOM(maToBuom, maND, maPhieuDK, maGiaTien);
                    if(db.Insert(t)){
                        return maToBuom;
                    }else throw new ExceptionMessage.ExecuteFail("TOBUOM/Insert \n");
                }else throw new ExceptionMessage.MaGiaTienKhongHopLe();
            }else throw new ExceptionMessage.MaPhieuDKKhongTonTai();
        }else throw new ExceptionMessage.MaNDQCKhongTonTai();
    }
    
    // Sửa một đối tượng
    public String Update(String maToBuom, String maND,
            String maPhieuDK, String maGiaTien)throws Exception{
        if (dbNoiDungQC.ValidationID(maND)) {                       // Kiểm tra sự hợp lệ của MaND
            if (dbPhieuDangKy.ValidationID(maPhieuDK)) {            // Kiểm tra sự hợp lệ của MaPhieuDangKy
                if (dbGiaTienToBuom.ValidationID(maGiaTien)) {      // Kiểm tra sự hợp lệ của MaGiaTien
                    if (ValidationID(maToBuom)) {                   // Kiểm tra sự hợp lệ của MaToBuom
                        TOBUOM t = new TOBUOM(maToBuom, maND, maPhieuDK, maGiaTien);
                        if(db.Update(t)){
                            return maToBuom;
                        }else throw new ExceptionMessage.ExecuteFail("TOBUOM/Update \n");
                    }else throw new ExceptionMessage.MaToBuomKhongTonTai();
                }else throw new ExceptionMessage.MaGiaTienKhongHopLe();
            }else throw new ExceptionMessage.MaPhieuDKKhongTonTai();
        }else throw new ExceptionMessage.MaNDQCKhongTonTai();
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String maToBuom)throws Exception{
        if (ValidationID(maToBuom)) {           // Kiểm tra sự hợp lệ của MaToBuom
            return db.Delete(maToBuom);
        }
        else throw new ExceptionMessage.MaToBuomKhongTonTai();
    }
    
    // Trả về một đối tượng TOBUOM có MaToBuom = maToBuom
    public TOBUOM GetRowByID(String maToBuom)throws Exception{
        if (ValidationID(maToBuom)) {           // Kiểm tra sự hợp lệ của MaToBuom
            return db.GetRowByID(maToBuom);
        }
        else throw new ExceptionMessage.MaToBuomKhongTonTai();
    }
    
    // Kiểm tra một MaToBuom đã tồn tại trong bảng hay chưa
    public Boolean ValidationID(String maToBuom) throws Exception{
        return db.ValidationID(maToBuom);
    }
    
    // Kiểm tra MaND có tồn tại trong bảng TOBUOM hay không?
    public Boolean IsMaND_ToBuomExist(String maND) throws Exception{
        return db.IsMaND_ToBuomExist(maND);
    }
    
    // Kiểm tra MaPhieuDangKy có tồn tại trong bảng TOBUOM hay không?
    public Boolean IsMaPhieuDK_ToBuomExist(String maPhieuDK) throws Exception{
        return db.IsMaPhieuDK_ToBuomExist(maPhieuDK);
    }
    
    // Kiểm tra MaGiaTien có tồn tại trong bảng TOBUOM hay không?
    public Boolean IsMaGiaTien_ToBuomExist(String maGiaTien) throws Exception{
        return db.IsMaGiaTien_ToBuomExist(maGiaTien);
    }
    
    // Trả về một đối tượng TOBUOM có MaPhieuDK = maPhieuDK
    public TOBUOM GetToBuomByMaPhieuDK(String maPhieuDK)throws Exception{
        if (this.IsMaPhieuDK_ToBuomExist(maPhieuDK)) {
            return db.GetToBuomByMaPhieuDK(maPhieuDK);
        }else throw new ExceptionMessage.MaPhieuDKKhongTonTai();
    }
}
