/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Business;

import DataAccess.GiaTien_QC_BangDAO;
import java.util.ArrayList;
import Entities.LOAIBANG;
import DataAccess.LoaiBangDAO;
/**
 *
 * @author VooKa
 */
public final class LoaiBangLogic {
    LoaiBangDAO db = new LoaiBangDAO();
    GiaTien_QC_BangDAO dbGiaTienQCBang = new GiaTien_QC_BangDAO();
    
    // Mảng quản lý toàn bộ bảng LOAIBANG
    public ArrayList<LOAIBANG> mainList = new ArrayList<LOAIBANG>();
    
    // Constructor
    public LoaiBangLogic() throws Exception{
        mainList = GetAllRows();
    }
    
    // Trả về toàn bộ bảng LOAIBANG
    public ArrayList<LOAIBANG> GetAllRows()throws Exception{
        LoaiBangDAO _loaiBang = new LoaiBangDAO();
        return _loaiBang.GetAllRows();
    }
    
    // Thêm mới một đối tượng
    public String Insert(String loaiBang)throws Exception{
        String maLoaiBang = db.GetNewID();
        LOAIBANG l = new LOAIBANG(maLoaiBang, loaiBang);
        if(db.Insert(l)){
            return maLoaiBang;
        }else throw new ExceptionMessage.ExecuteFail("LOAIBANG/Insert \n");
    }
    
    // Sửa một đối tượng
    public String Update(String maLoaiBang, String loaiBang)throws Exception{
        if (ValidationID(maLoaiBang)) {                         // Kiểm tra sự hợp lệ của MaLoaiBang
            LOAIBANG l = new LOAIBANG(maLoaiBang, loaiBang);
            if(db.Update(l)){
                return maLoaiBang;
            }else throw new ExceptionMessage.ExecuteFail("LOAIBANG/Update \n");
        }
        else throw new ExceptionMessage.MaLoaiBangKhongTonTai();
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String maLoaiBang)throws Exception{
        if (ValidationID(maLoaiBang)) {                         // Kiểm tra sự hợp lệ của MaLoaiBang
            if (!dbGiaTienQCBang.IsMaLoaiBangExist(maLoaiBang)) {      // Kiểm tra MaLoaiBang có bị tham chiếu không?
                return db.Delete(maLoaiBang);
            }
            else throw new ExceptionMessage.MaThamChieuKhongTonTai();
        }
        else throw new ExceptionMessage.MaLoaiBangKhongTonTai();
    }
    
    // Trả về một đối tượng LOAIBANG có MaLoaiBang = maLoaiBang
    public LOAIBANG GetRowByID(String maLoaiBang)throws Exception{
        if (ValidationID(maLoaiBang)) {                         // Kiểm tra sự hợp lệ của MaLoaiBang
            return db.GetRowByID(maLoaiBang);
        }
        else throw new ExceptionMessage.MaLoaiBangKhongTonTai();
    }
    
    // Kiểm tra một MaLoaiBang đã tồn tại trong bảng hay chưa
    public Boolean ValidationID(String maLoaiBang) throws Exception{
        return db.ValidationID(maLoaiBang);
    }
}
