/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Business;

import DataAccess.DiaOcDAO;
import java.util.ArrayList;
import DataAccess.LoaiDiaOcDAO;
import Entities.LOAIDIAOC;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author VooKa
 */
public final class LoaiDiaOCLogic {    
    LoaiDiaOcDAO db = new LoaiDiaOcDAO();
    DiaOcDAO dbDiaOc = new DiaOcDAO();
    
    // Mảng quản lý các đối tượng LOAIDIAOC
    public ArrayList<LOAIDIAOC> mainList = new ArrayList<LOAIDIAOC>();
    
    // Constructor
    public LoaiDiaOCLogic(){
        try {
            mainList = this.GetAllRows();
        } catch (Exception ex) {
            Logger.getLogger(LoaiDiaOCLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Trả về toàn bộ bảng LOAIDIAOC
    public ArrayList<LOAIDIAOC> GetAllRows()throws Exception{
        return db.GetAllRows();
    }
    
    // Thêm một đối tượng
    public String Insert(String tenLoaiDiaOc, String kyHieu)throws Exception{
        if (!IsKyHieuExist(kyHieu)) {
            String maLoaiDiaOc = db.GetNewID();
            LOAIDIAOC l = new LOAIDIAOC(maLoaiDiaOc, tenLoaiDiaOc, kyHieu);
            if(db.Insert(l)){
                return maLoaiDiaOc;
            }else throw new ExceptionMessage.ExecuteFail("LOAIDIAOC/Insert \n");
        }
        else throw new ExceptionMessage.KyHieuDaTonTai();
    }
    
    // Sửa một đối tượng
    public String Update(String maLoaiDiaOc, String tenLoaiDiaOc, String kyHieu)throws Exception{
        if (ValidationID(maLoaiDiaOc)) {
            if (!IsKyHieuExist(kyHieu)) {
                LOAIDIAOC l = new LOAIDIAOC(maLoaiDiaOc, tenLoaiDiaOc, kyHieu);
                if(db.Update(l)){
                    return maLoaiDiaOc;
                }else throw new ExceptionMessage.ExecuteFail("LOAIDIAOC/Update \n");
            }else throw new ExceptionMessage.KyHieuDaTonTai();
        }else throw new ExceptionMessage.MaLoaiDiaOcKhongTonTai();
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String maLoaiDiaOc)throws Exception{
        if (ValidationID(maLoaiDiaOc)) {                             // Kiểm tra sự hợp lệ của MaLoaiDiaOc
            if (!dbDiaOc.IsLoaiDiaOcExist(maLoaiDiaOc)) {             // Kiểm tra sự tồn tại của MaLoaiDiaOc trong bảng DIAOC
                return db.Delete(maLoaiDiaOc);
            }
            else throw new ExceptionMessage.MaThamChieuDiaOc_LoaiDiaOc();  // Không thể xóa một record đang được tham chiếu đến
        }
        else throw new ExceptionMessage.MaLoaiDiaOcKhongTonTai();
    }
    
    // Trả về một LOAIDIAOC có MaLoaiDiaOc = maLoaiDiaOc
    public LOAIDIAOC GetRowByID(String maLoaiDiaOc)throws Exception{
        if (ValidationID(maLoaiDiaOc)) {
            return db.GetRowByID(maLoaiDiaOc);
        }
        else throw new ExceptionMessage.MaLoaiDiaOcKhongTonTai();
    }
    
    // Hàm kiểm tra xem một MaLoaiDiaOc đã tồn tại hay chưa
    public Boolean ValidationID(String maLoaiDiaOc) throws Exception{
        return db.ValidationID(maLoaiDiaOc);
    }
    
    // Trả về TenLoaiDiaOc theo MaLoaiDiaOc
    public String GetNameByID(String maLoaiDiaOc)throws Exception{
        return db.GetNameByID(maLoaiDiaOc);
    }
    
    // Kiểm tra xem một KyHieu có tồn tại trong bảng hay không
    public Boolean IsKyHieuExist(String kyHieu) throws Exception{
        return db.IsKyHieuExist(kyHieu);
    }
}
