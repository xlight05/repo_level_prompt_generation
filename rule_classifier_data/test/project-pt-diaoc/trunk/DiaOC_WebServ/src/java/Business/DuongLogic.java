/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import DataAccess.DiaChiDAO;
import DataAccess.DuongDAO;
import Entities.DUONG;
import java.util.ArrayList;

/**
 *
 * @author Nixforest
 */
public class DuongLogic {
    DuongDAO db = new DuongDAO();
    DiaChiDAO dbDiaChi = new DiaChiDAO();
    
    // Mảng quản lý toàn bộ bảng DUONG
    public ArrayList<DUONG> mainList = new ArrayList<DUONG>();
    
    // Constructor
    public DuongLogic() throws Exception{
        mainList = this.GetAllRows();
    }

    // Trả về toàn bộ bảng DUONG
    public ArrayList<DUONG> GetAllRows() throws Exception {
        return db.GetAllRows();
    }
    
    // Thêm một đối tượng
    public String Insert(String tenDuong)throws Exception{
        String maDuong = db.GetNewID();
        DUONG t = new DUONG(maDuong, tenDuong);
        if (db.Insert(t)) {
            return maDuong;
        }else throw new ExceptionMessage.ExecuteFail("DUONG/Insert \n");
    }
    
    // Sửa một đối tượng
    public String Update(String maDuong, String tenDuong) throws Exception{
        if (this.ValidationID(maDuong)) {
            DUONG t = new DUONG(maDuong, tenDuong);
            if (db.Update(t)) {
                return maDuong;
            }else throw new ExceptionMessage.ExecuteFail("Update/Insert \n");
        }else throw new ExceptionMessage.MaDuongKhongHopLe();
    }
    
    // Xóa một đối tượng DUONG
    public Boolean Delete(String maDuong)throws Exception{
        if (ValidationID(maDuong)) {                                  // Kiểm tra sự hợp lệ của maDuong
            if(!dbDiaChi.IsDuongExist(maDuong))
                return db.Delete(maDuong);
            else throw new ExceptionMessage.MaThamChieuDiaChi_Duong();
        }else throw new ExceptionMessage.MaDuongKhongHopLe();
    }

    // Trả về một record DUONG co MaDuong = maDuong
    public DUONG GetRowByID(String maDuong)throws Exception{
        if (ValidationID(maDuong)) {                             // Kiểm tra sự hợp lệ của maDuong
            return db.GetRowByID(maDuong);
        }
        else throw new ExceptionMessage.MaDuongKhongHopLe();
    }
    public Boolean ValidationID(String maDuong) throws Exception {
        return db.ValidationID(maDuong);
    }
}
