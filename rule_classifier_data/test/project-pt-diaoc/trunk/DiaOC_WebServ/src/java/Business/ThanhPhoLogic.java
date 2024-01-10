/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import DataAccess.DiaChiDAO;
import DataAccess.ThanhPhoDAO;
import Entities.THANHPHO;
import java.util.ArrayList;

/**
 *
 * @author Nixforest
 */
public class ThanhPhoLogic {
    ThanhPhoDAO db = new ThanhPhoDAO();
    DiaChiDAO dbDiaChi = new DiaChiDAO();
    
    // Mảng quản lý toàn bộ bảng THANHPHO
    public ArrayList<THANHPHO> mainList = new ArrayList<THANHPHO>();
    
    // Constructor
    public ThanhPhoLogic() throws Exception{
        mainList = this.GetAllRows();
    }

    // Trả về toàn bộ bảng THANHPHO
    public ArrayList<THANHPHO> GetAllRows() throws Exception {
        return db.GetAllRows();
    }
    
    // Thêm một đối tượng
    public String Insert(String tenThanhPho)throws Exception{
        String maThanhPho = db.GetNewID();
        THANHPHO t = new THANHPHO(maThanhPho, tenThanhPho);
        if (db.Insert(t)) {
            return maThanhPho;
        }else throw new ExceptionMessage.ExecuteFail("THANHPHO/Insert \n");
    }
    
    // Sửa một đối tượng
    public String Update(String maThanhPho, String tenThanhPho) throws Exception{
        if (this.ValidationID(maThanhPho)) {
            THANHPHO t = new THANHPHO(maThanhPho, tenThanhPho);
            if (db.Update(t)) {
                return maThanhPho;
            }else throw new ExceptionMessage.ExecuteFail("THANHPHO/Update \n");
        }else throw new ExceptionMessage.MaThanhPhoKhongHopLe();
    }
    
    // Xóa một đối tượng THANHPHO
    public Boolean Delete(String maThanhPho)throws Exception{
        if (ValidationID(maThanhPho)) {                                  // Kiểm tra sự hợp lệ của MaThanhPho
            if(!dbDiaChi.IsThanhPhoExist(maThanhPho))
                return db.Delete(maThanhPho);
            else throw new ExceptionMessage.MaThamChieuDiaChi_ThanhPho();
        }else throw new ExceptionMessage.MaThanhPhoKhongHopLe();
    }

    // Trả về một record ThanhPho co MaThanhPho = maThanhPho
    public THANHPHO GetRowByID(String maThanhPho)throws Exception{
        if (ValidationID(maThanhPho)) {                             // Kiểm tra sự hợp lệ của MaThanhPho
            return db.GetRowByID(maThanhPho);
        }
        else throw new ExceptionMessage.MaThanhPhoKhongHopLe();
    }
    public Boolean ValidationID(String maThanhPho) throws Exception {
        return db.ValidationID(maThanhPho);
    }
}
