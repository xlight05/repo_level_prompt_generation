/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import DataAccess.DiaChiDAO;
import DataAccess.QuanDAO;
import Entities.QUAN;
import java.util.ArrayList;

/**
 *
 * @author Nixforest
 */
public class QuanLogic {
    QuanDAO db = new QuanDAO();
    DiaChiDAO dbDiaChi = new DiaChiDAO();
    
    // Mảng quản lý toàn bộ bảng QUAN
    public ArrayList<QUAN> mainList = new ArrayList<QUAN>();
    
    // Constructor
    public QuanLogic() throws Exception{
        mainList = this.GetAllRows();
    }

    // Trả về toàn bộ bảng QUAN
    public ArrayList<QUAN> GetAllRows() throws Exception {
        return db.GetAllRows();
    }
    
    // Thêm một đối tượng
    public String Insert(String tenQuan)throws Exception{
        String maQuan = db.GetNewID();
        QUAN t = new QUAN(maQuan, tenQuan);
        if (db.Insert(t)) {
            return maQuan;
        }else throw new ExceptionMessage.ExecuteFail("QUAN/Insert \n");
    }
    
    // Sửa một đối tượng
    public String Update(String maQuan, String tenQuan) throws Exception{
        if (this.ValidationID(maQuan)) {
            QUAN t = new QUAN(maQuan, tenQuan);
            if (db.Update(t)) {
                return maQuan;
            }else throw new ExceptionMessage.ExecuteFail("QUAN/Update \n");
        }else throw new ExceptionMessage.MaQuanKhongHopLe();
    }
    
    // Xóa một đối tượng QUAN
    public Boolean Delete(String maQuan)throws Exception{
        if (ValidationID(maQuan)) {                                  // Kiểm tra sự hợp lệ của maQuan
            if(!dbDiaChi.IsQuanExist(maQuan))
                return db.Delete(maQuan);
            else throw new ExceptionMessage.MaThamChieuDiaChi_Quan();
        }else throw new ExceptionMessage.MaQuanKhongHopLe();
    }

    // Trả về một record ThanhPho co MaThanhPho = maThanhPho
    public QUAN GetRowByID(String maQuan)throws Exception{
        if (ValidationID(maQuan)) {                             // Kiểm tra sự hợp lệ của maQuan
            return db.GetRowByID(maQuan);
        }
        else throw new ExceptionMessage.MaQuanKhongHopLe();
    }
    public Boolean ValidationID(String maQuan) throws Exception {
        return db.ValidationID(maQuan);
    }
}
