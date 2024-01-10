/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Business;

import DataAccess.HinhAnhDAO;
import DataAccess.NoiDungQCDAO;
import Entities.HINHANH;
import java.sql.Date;
import java.util.ArrayList;
/**
 *
 * @author VooKa
 */
public final class HinhAnhLogic {
    HinhAnhDAO db = new HinhAnhDAO();
    NoiDungQCDAO dbNoiDungQC = new NoiDungQCDAO();
    
    // Mảng quản lý toàn bộ các đối tượng trong bảng HINHANH
    public ArrayList<HINHANH> mainList = new ArrayList<HINHANH>();
    
    // Constructor
    public HinhAnhLogic() throws Exception{
        mainList = this.GetAllRows();
    }
    
    // Trả về toàn bộ bảng HINHANH
    public ArrayList<HINHANH> GetAllRows()throws Exception{
        return db.GetAllRows();
    }
    
    // Thêm một đối tượng
    public int Insert(String anh, String moTa, Date thoiGianChupAnh, String maND)throws Exception{
        if (dbNoiDungQC.ValidationID(maND)) {                          // Kiểm tra sự hợp lệ của MaND
            int maHinhAnh = db.GetNewID();
            HINHANH h = new HINHANH(maHinhAnh, anh, moTa, thoiGianChupAnh, maND);
            if(db.Insert(h)){
                return maHinhAnh;
            }else throw new ExceptionMessage.ExecuteFail("HINHANH/Insert \n");
        }
        else throw new ExceptionMessage.MaNDQCKhongTonTai();
    }
    
    // Sửa một đối tượng
    public int Update(int maHinhAnh, String anh, String moTa, Date thoiGianChupAnh, String maND)throws Exception{
        if (dbNoiDungQC.ValidationID(maND)) {                          // Kiểm tra sự hợp lệ của MaND
            if (ValidationID(maHinhAnh)) {
                HINHANH h = new HINHANH(maHinhAnh, anh, moTa, thoiGianChupAnh, maND);
                if(db.Update(h)){
                return maHinhAnh;
            }else throw new ExceptionMessage.ExecuteFail("HINHANH/Update \n");
            }
            else throw new ExceptionMessage.MaHinhAnhKhongTonTai();
        }
        else throw new ExceptionMessage.MaNDQCKhongTonTai();
    }
    
    // Xóa một đối tượng
    public Boolean Delete(int maHinhAnh)throws Exception{
        if (ValidationID(maHinhAnh)) {                 // Kiểm tra sự hợp lệ của MaHinhAnh
            return db.Delete(maHinhAnh);
        }
        else throw new ExceptionMessage.MaHinhAnhKhongTonTai();
    }
    
    public HINHANH GetRowByID(int maHinhAnh)throws Exception{
        if (ValidationID(maHinhAnh)) {                 // Kiểm tra sự hợp lệ của MaHinhAnh
            return db.GetRowByID(maHinhAnh);
        }
        else throw new ExceptionMessage.MaHinhAnhKhongTonTai();
    }
    
    // Kiểm tra xem một MaHinhAnh có tồn tại trong bảng hay chưa
    public Boolean ValidationID(int maHinhAnh) throws Exception{
        return db.ValidationID(maHinhAnh);
    }
    
    //Kiểm tra xem một MaND có tồn tại trong bảng HINHANH hay không
    public Boolean IsMaND_HinhAnhExist(String maND) throws Exception{
        return db.IsMaND_HinhAnhExist(maND);
    }
    
    // Trả về Anh theo MaND
    public ArrayList<HINHANH> SelectHinhAnhByMaND(String maND)throws Exception{
        if (IsMaND_HinhAnhExist(maND)) {
            return db.SelectHinhAnhByMaND(maND);
        }
        else throw new ExceptionMessage.MaNDQCKhongTonTai();
    }
}
