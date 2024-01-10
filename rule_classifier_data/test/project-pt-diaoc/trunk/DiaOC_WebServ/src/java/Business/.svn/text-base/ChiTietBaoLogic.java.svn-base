/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import DataAccess.BaoDAO;
import DataAccess.ChiTietBaoDAO;
import DataAccess.QCBaoDAO;
import Entities.CHITIETBAO;
import java.util.ArrayList;

/**
 *
 * @author Nixforest
 */
public final class ChiTietBaoLogic {
    ChiTietBaoDAO db = new ChiTietBaoDAO();
    QCBaoDAO dbQCBao = new QCBaoDAO();
    BaoDAO dbBao = new BaoDAO();
    
    // Mảng quản lý các record CHITIETBAO
    public ArrayList<CHITIETBAO> mainList = new ArrayList<CHITIETBAO>();
    
    // Constructor
    public ChiTietBaoLogic() throws Exception{
        mainList = this.GetAllRows();
    }
    
    // Trả về toàn bộ bảng CHITIETBAO
    public ArrayList<CHITIETBAO> GetAllRows() throws Exception{
        return db.GetAllRows();
    }
    
    // Thêm một đối tượng
    public String Insert(String maQCBao, String maBao)throws Exception{
        if (dbQCBao.ValidationID(maQCBao)) {                   // Kiểm tra sự hợp lệ của MaQCBao
            if (dbBao.ValidationID(maBao)) {                         // Kiểm tra sự hợp lệ của MaBao
                String maCT = db.GetNewID();
                CHITIETBAO c = new CHITIETBAO(maCT, maQCBao, maBao);
                if(db.Insert(c)){
                    return maCT;
                }else throw new ExceptionMessage.ExecuteFail("CHITIETBAO/Insert \n");
            }else throw new ExceptionMessage.MaBaoKhongTonTai();
        }else throw new ExceptionMessage.MaQCBaoKhongTonTai();              
    }
    
    // Sửa một đối tượng
    public String Update(String maCT, String maQCBao, String maBao) throws Exception{
        if (dbQCBao.ValidationID(maQCBao)) {                   // Kiểm tra sự hợp lệ của MaQCBao
            if (dbBao.ValidationID(maBao)) {                         // Kiểm tra sự hợp lệ của MaBao
                if (ValidationID(maCT)) {                           // Kiểm tra sự hợp lệ của MaCT
                    CHITIETBAO c = new CHITIETBAO(maCT, maQCBao, maBao);
                    if (db.Update(c)) {
                        return maCT;
                    }else throw new ExceptionMessage.ExecuteFail("CHITIETBAO/Update \n");
                }
                else throw new ExceptionMessage.MaCTKhongTonTai();
            }else throw new ExceptionMessage.MaBaoKhongTonTai();
        }else throw new ExceptionMessage.MaQCBaoKhongTonTai();
    }
    
    // Xóa một đối tượng CHITIETBAO
    public Boolean Delete(String maCT)throws Exception{
        if (ValidationID(maCT)) {                           // Kiểm tra sự hợp lệ của MaCT
            return db.Delete(maCT);
        }
        else 
            throw new ExceptionMessage.MaCTKhongTonTai();
    }
    
    // Trả về một record Bao co MaCT = maCT
    public CHITIETBAO GetRowByID(String maCT)throws Exception{
        if (ValidationID(maCT)) {                           // Kiểm tra sự hợp lệ của MaCT
            return db.GetRowByID(maCT);
        }
        else throw new ExceptionMessage.MaCTKhongTonTai();
    }
    
    // Kiểm tra xem MaBao đã tồn tại trong table BAO hay chưa
    public Boolean ValidationID(String maCT) throws Exception{
        return db.ValidationID(maCT);
    }
    
    // Hàm kiểm tra MaBao có tồn tại hay không
    public Boolean IsMaBaoExist(String maBao) throws Exception{
        return db.IsMaBaoExist(maBao);
    }
    
    // Hàm kiểm tra MaQCBao có tồn tại hay không
    public Boolean IsMaQCBaoExist(String maQCBao) throws Exception{
        return db.IsMaQCBaoExist(maQCBao);
    }
    
    // Trả về những MaBao trong bảng CHITIETBAO có cùng MaQCBao
    public ArrayList<String> GetMaBaoByMaQCBao(String maQCBao)throws Exception{
        if (this.IsMaQCBaoExist(maQCBao)) {
            return db.GetMaBaoByMaQCBao(maQCBao);
        }else throw new ExceptionMessage.MaQCBaoKhongTonTai();
    }
}
