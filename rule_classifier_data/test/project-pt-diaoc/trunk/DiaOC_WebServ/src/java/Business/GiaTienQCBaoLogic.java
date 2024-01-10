/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Business;

import DataAccess.GiaTien_QC_BaoDAO;
import DataAccess.QCBaoDAO;
import Entities.GIATIEN_QC_BAO;
import java.util.ArrayList;
/**
 *
 * @author VooKa
 */
public class GiaTienQCBaoLogic {
    GiaTien_QC_BaoDAO db = new GiaTien_QC_BaoDAO();
    QCBaoDAO dbQCBao = new QCBaoDAO();
    
    // Mảng quản lý toàn bộ bảng GIATIEN_QC_BAO
    public ArrayList<GIATIEN_QC_BAO> mainList = new ArrayList<GIATIEN_QC_BAO>();
    
    // Constructor
    public GiaTienQCBaoLogic() throws Exception{
        mainList = this.GetAllRows();
    }
    
    // Trả về toàn bộ bảng 
    public ArrayList<GIATIEN_QC_BAO> GetAllRows()throws Exception{
        return db.GetAllRows();
    }
    
    // Thêm một đối tượng
    public String Insert(String viTri, String khoIn, String mauSac, String ghiChu, Float giaTien)throws Exception{
        if (giaTien >= 0) {                                         // Kiểm tra GiaTien phải không nhỏ hơn 0
            String maGiaTien = db.GetNewID();
            GIATIEN_QC_BAO g = new GIATIEN_QC_BAO(maGiaTien, viTri, khoIn, mauSac, ghiChu, giaTien);
            if(db.Insert(g)){
                return maGiaTien;
            }else throw new ExceptionMessage.ExecuteFail("GIATIEN_QCB/Insert \n");
        }
        else throw new ExceptionMessage.GiaTienKhongHopLe();
    }
    
    // Sửa một đối tượng
    public String Update(String maGiaTien, String viTri, String khoIn, String mauSac, String ghiChu, Float giaTien)throws Exception{
        if (giaTien >= 0) {                                         // Kiểm tra GiaTien phải không nhỏ hơn 0
            if (ValidationID(maGiaTien)) {                          // Kiểm tra sự hợp lệ của MaGiaTien
                GIATIEN_QC_BAO g = new GIATIEN_QC_BAO(maGiaTien, viTri, khoIn, mauSac, ghiChu, giaTien);
                if(db.Update(g)){
                    return maGiaTien;
                }else throw new ExceptionMessage.ExecuteFail("GIATIEN_QCB/Update \n");
            }
            else throw new ExceptionMessage.MaGiaTienKhongHopLe();
        }
        else throw new ExceptionMessage.GiaTienKhongHopLe();
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String maGiaTien)throws Exception{
        if (ValidationID(maGiaTien)) {                             // Kiểm tra sự hợp lệ của MaGiaTien
            if (!dbQCBao.IsMaGiaTien_BaoExist(maGiaTien)) {   // Kiểm tra xem MaGiaTien có bị tham chiếu hay không?
                return db.Delete(maGiaTien);
            }
            else throw new ExceptionMessage.MaThamChieuKhongTonTai();
        }
        else throw new ExceptionMessage.MaGiaTienKhongHopLe();
    }
    
    // Trả về một đối tượng GIATIEN_QC_BAO có MaGiaTien = maGiaTien
    public GIATIEN_QC_BAO GetRowByID(String maGiaTien)throws Exception{
        if (ValidationID(maGiaTien)) {                          // Kiểm tra sự hợp lệ của MaGiaTien
            return db.GetRowByID(maGiaTien);
        }
        else throw new ExceptionMessage.MaGiaTienKhongHopLe();
    }
    
    // Kiểm tra MaGiaTien có tồn tại trong bàn hay chưa
    public Boolean ValidationID(String maGiaTien) throws Exception{
        return db.ValidationID(maGiaTien);
    }
}