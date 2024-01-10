/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Business;

import DataAccess.GiaTienQCToBuomDAO;
import DataAccess.ThamSoDAO;
import DataAccess.ToBuomDAO;
import Entities.GIATIEN_TOBUOM;
import java.util.ArrayList;
/**
 *
 * @author VooKa
 */
public class GiaTienQCToBuomLogic {
    GiaTienQCToBuomDAO db = new GiaTienQCToBuomDAO();
    ToBuomDAO dbToBuom = new ToBuomDAO();
    DataAccess.ThamSoDAO dbThamSo = new ThamSoDAO();
    
    // Mảng quản lý toàn bộ bảng GIATIEN_TOBUOM
    public ArrayList<GIATIEN_TOBUOM> mainList = new ArrayList<GIATIEN_TOBUOM>();
    
    // Constructor
    public GiaTienQCToBuomLogic() throws Exception{
        mainList = this.GetAllRows();
    }
    
    // Trả về toàn bộ bảng
    public ArrayList<GIATIEN_TOBUOM> GetAllRows()throws Exception{
        return db.GetAllRows();
    }
    
    // Thêm một đối tượng
    public String Insert(int soLuong, Float giaTien)throws Exception{
        if (giaTien >= 0) {                                 // Kiểm tra sự hợp lệ của GiaTien
            if (soLuong >= dbThamSo.GetThamSoByTen("SoLuongPhatHanhToiThieu").getGiaTri()) {                             // Kiểm tra sự hợp lệ của SoLuong                
                String maGiaTien = db.GetNewID();
                GIATIEN_TOBUOM g = new GIATIEN_TOBUOM(maGiaTien, soLuong, giaTien);
                if(db.Insert(g)){
                    return maGiaTien;
                }else throw new ExceptionMessage.ExecuteFail("GIATIEN_QCT/Insert \n");
            }
            else throw new ExceptionMessage.SoLuongKhongHopLe();
        }
        else throw new ExceptionMessage.GiaTienKhongHopLe();
        
    }
    
    // Sửa một đối tượng
    public String Update(String maGiaTien, int soLuong, Float giaTien)throws Exception{
        if (giaTien >= 0) {                                 // Kiểm tra sự hợp lệ của GiaTien
            if (soLuong >= dbThamSo.GetThamSoByTen("SoLuongPhatHanhToiThieu").getGiaTri()) {        // Kiểm tra sự hợp lệ của SoLuong
                if (ValidationID(maGiaTien)) {              // Kiểm tra sự hợp lệ của MaGiaTien
                    GIATIEN_TOBUOM g = new GIATIEN_TOBUOM(maGiaTien, soLuong, giaTien);
                    if(db.Update(g)){
                        return maGiaTien;
                    }else throw new ExceptionMessage.ExecuteFail("GIATIEN_QCT/Update \n");
                }
                else throw new ExceptionMessage.MaGiaTienKhongHopLe();
            }
            else throw new ExceptionMessage.SoLuongKhongHopLe();
        }
        else throw new ExceptionMessage.GiaTienKhongHopLe();
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String maGiaTien)throws Exception{
        if (ValidationID(maGiaTien)) {                             // Kiểm tra sự hợp lệ của MaGiaTien
            if (!dbToBuom.IsMaGiaTien_ToBuomExist(maGiaTien)) {     // Kiểm tra xem MaGiaTien có bị tham chiếu hay không?
                return db.Delete(maGiaTien);
            }
            else throw new ExceptionMessage.MaThamChieuKhongTonTai();
        }
        else throw new ExceptionMessage.MaGiaTienKhongHopLe();
    }
    
    // Trả về một đối tượng GIATIEN_TOBUOM có MaGiaTien = maGiaTien
    public GIATIEN_TOBUOM GetRowByID(String maGiaTien)throws Exception{
        if (ValidationID(maGiaTien)) {              // Kiểm tra sự hợp lệ của MaGiaTien
            return db.GetRowByID(maGiaTien);
        }
        else throw new ExceptionMessage.MaGiaTienKhongHopLe();
    }
    
    // Hàm kiểm tra MaGiaTien đã tồn tại hay chưa
    public Boolean ValidationID(String maGiaTien) throws Exception{
        return db.ValidationID(maGiaTien);
    }
    
    // Trả về GiaTien tương ứng với Soluong
    public Float GetGiaBySoLuong(int soLuong)throws Exception{
        return db.GetGiaBySoLuong(soLuong);
    }
    
    // Trả về MaGiaTien tương ứng với Soluong
    public String GetIDBySoLuong(int soLuong)throws Exception{
        return db.GetIDBySoLuong(soLuong);
    }
}
