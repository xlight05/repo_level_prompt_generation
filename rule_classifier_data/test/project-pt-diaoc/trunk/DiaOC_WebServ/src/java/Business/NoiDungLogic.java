/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Business;

import DataAccess.HinhAnhDAO;
import java.util.ArrayList;
import DataAccess.NoiDungQCDAO;
import DataAccess.QCBangDAO;
import DataAccess.QCBaoDAO;
import DataAccess.ToBuomDAO;
import Entities.NOIDUNGQC;
/**
 *
 * @author VooKa
 */
public class NoiDungLogic {
    NoiDungQCDAO db = new NoiDungQCDAO();
    HinhAnhDAO dbHinhAnh = new HinhAnhDAO();
    QCBaoDAO dbQCBao = new QCBaoDAO();
    ToBuomDAO dbToBuom = new ToBuomDAO();
    QCBangDAO dbQCBang = new QCBangDAO();
    
    // Mảng quản lý toàn bộ các đối tượng trong bảng NOIDUNGQC
    public ArrayList<NOIDUNGQC> mainList = new ArrayList<NOIDUNGQC>();
    
    // Constructor
    public NoiDungLogic() throws Exception{
        mainList = this.GetAllRows();
    }
    
    // Trả về toàn bộ bảng NOIDUNGQC
    public ArrayList<NOIDUNGQC> GetAllRows()throws Exception{
        return db.GetAllRows();
    }
    
    // Thêm một đối tượng
    public String Insert(String thongTin, String moTa)throws Exception{
        String maND = db.GetNewID();
        NOIDUNGQC n = new NOIDUNGQC(maND, thongTin, moTa);
        if (db.Insert(n)) {
            return maND;
        }else throw new ExceptionMessage.ExecuteFail("NOIDUNG/Insert \n");
    }
    
    // Sửa một đối tượng
    public String Update(String maND, String thongTin, String moTa)throws Exception{
        if (ValidationID(maND)) {                                   // Kiểm tra sự hợp lệ của MaND
            NOIDUNGQC n = new NOIDUNGQC(maND, thongTin, moTa);
            if (db.Update(n)) {
                return maND;
            }else throw new ExceptionMessage.ExecuteFail("NOIDUNG/Update \n");
        }
        else throw new ExceptionMessage.MaNDQCKhongTonTai();
    }
    
    // Xóa một đối tượng
    public Boolean Delete(String maND)throws Exception{
        if (ValidationID(maND)) {                                      // Kiểm tra sự hợp lệ của MaND
            if (!dbQCBao.IsMaND_BaoExist(maND)) {                      // Kiểm tra xem MaND có bị tham chiếu từ QC_BAO không
                if (!dbToBuom.IsMaND_ToBuomExist(maND)) {              // Kiểm tra xem MaND có bị tham chiếu từ TOBUOM không
                    if (!dbHinhAnh.IsMaND_HinhAnhExist(maND)) {        // Kiểm tra xem MaND có bị tham chiếu từ HINHANH không
                        if (!dbQCBang.IsMaND_BangExist(maND)) {
                            return db.Delete(maND);
                        }else throw new ExceptionMessage.MaThamChieu_QCBang_NoiDungQC();
                    }else throw new ExceptionMessage.MaThamChieu_HinhAnh_NoiDungQC();
                }else throw new ExceptionMessage.MaThamChieu_ToBuom_NoiDungQC();
            }else throw new ExceptionMessage.MaThamChieu_QCBao_NoiDungQC();
        }else throw new ExceptionMessage.MaNDQCKhongTonTai();
    }
    
    // Trả về một đối tượng NOIDUNGQC có MaND = maND
    public NOIDUNGQC GetRowByID(String maND)throws Exception{
        if (ValidationID(maND)) {
            return db.GetRowByID(maND);
        }
        else throw new ExceptionMessage.MaNDQCKhongTonTai();
    }
    
    // Kiểm tra một MaND đã tồn tại trong bảng NOIDUNGQC hay chưa
    public Boolean ValidationID(String maND) throws Exception{
        return db.ValidationID(maND);
    }
}
