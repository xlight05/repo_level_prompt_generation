/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import DataAccess.DiaChiDAO;
import DataAccess.PhuongDAO;
import Entities.PHUONG;
import java.util.ArrayList;

/**
 *
 * @author Nixforest
 */
public class PhuongLogic {
    PhuongDAO db = new PhuongDAO();
    DiaChiDAO dbDiaChi = new DiaChiDAO();
    
    // Mảng quản lý toàn bộ bảng PHUONG
    public ArrayList<PHUONG> mainList = new ArrayList<PHUONG>();
    
    // Constructor
    public PhuongLogic() throws Exception{
        mainList = this.GetAllRows();
    }

    // Trả về toàn bộ bảng PHUONG
    public ArrayList<PHUONG> GetAllRows() throws Exception {
        return db.GetAllRows();
    }
    
    // Thêm một đối tượng
    public String Insert(String tenPhuong)throws Exception{
        String maPhuong = db.GetNewID();
        PHUONG t = new PHUONG(maPhuong, tenPhuong);
        if (db.Insert(t)) {
            return maPhuong;
        }else throw new ExceptionMessage.ExecuteFail("PHUONG/Insert \n");
    }
    
    // Sửa một đối tượng
    public String Update(String maPhuong, String tenPhuong) throws Exception{
        if (this.ValidationID(maPhuong)) {
            PHUONG t = new PHUONG(maPhuong, tenPhuong);
            if (db.Update(t)) {
                return maPhuong;
            }else throw new ExceptionMessage.ExecuteFail("PHUONG/Update \n");
        }else throw new ExceptionMessage.MaPhuongKhongHopLe();
    }
    
    // Xóa một đối tượng PHUONG
    public Boolean Delete(String maPhuong)throws Exception{
        if (ValidationID(maPhuong)) {                                  // Kiểm tra sự hợp lệ của maPhuong
            if(!dbDiaChi.IsPhuongExist(maPhuong))
                return db.Delete(maPhuong);
            else throw new ExceptionMessage.MaThamChieuDiaChi_Phuong();
        }else throw new ExceptionMessage.MaPhuongKhongHopLe();
    }

    // Trả về một record PHUONG co MaPhuong = maPhuong
    public PHUONG GetRowByID(String maPhuong)throws Exception{
        if (ValidationID(maPhuong)) {                             // Kiểm tra sự hợp lệ của maPhuong
            return db.GetRowByID(maPhuong);
        }
        else throw new ExceptionMessage.MaPhuongKhongHopLe();
    }
    public Boolean ValidationID(String maPhuong) throws Exception {
        return db.ValidationID(maPhuong);
    }
}
