/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Business;

import DataAccess.BaoDAO;
import DataAccess.ChiTietBaoDAO;
import Entities.BAO;
import java.util.ArrayList;


public class BaoLogic {
    BaoDAO db = new BaoDAO();
    ChiTietBaoDAO dbChiTietBao = new ChiTietBaoDAO();
    
    // Mảng quản lý các record BAO
    public ArrayList<BAO> mainList = new ArrayList<BAO>();
    
    // Constructor
    public BaoLogic() throws Exception{
        mainList = this.GetAllRows();
    }
    
    // Trả về toàn bộ bảng BAO
    public ArrayList<BAO> GetAllRows() throws Exception{
        return db.GetAllRows();
    }
    
    // Thêm một đối tượng
    public String Insert(String tenBao, int thuPhatHanh)throws Exception{
        if (thuPhatHanh > 0 && thuPhatHanh < 8) {                       // Kiểm tra sự hợp lệ của ThuPhatHanh
            String maBao = db.GetNewID();
            BAO b = new BAO(maBao, tenBao, thuPhatHanh);
            if (db.Insert(b)) {
                return maBao;
            }else throw new ExceptionMessage.ExecuteFail("BAO/Insert");            
        }
        else throw new ExceptionMessage.ThuKoHopLe();        
    }
    
    // Sửa một đối tượng
    public String Update(String maBao, String tenBao, int thuPhatHanh) throws Exception{
        if (thuPhatHanh > 0 && thuPhatHanh < 8) {                       // Kiểm tra sự hợp lệ của ThuPhatHanh
            if (this.ValidationID(maBao)) {                             // Kiểm tra sự hợp lệ của MaBao
                BAO b = new BAO(maBao, tenBao, thuPhatHanh);
                if(db.Update(b)){
                    return maBao;
                }else throw new ExceptionMessage.ExecuteFail("BAO/Update");
            }
            else throw new ExceptionMessage.MaBaoKhongTonTai();
        }else throw new ExceptionMessage.ThuKoHopLe();
    }
    
    // Xóa một đối tượng BAO
    public Boolean Delete(String maBao)throws Exception{
        if (ValidationID(maBao)) {                                  // Kiểm tra sự hợp lệ của MaBao
            if (!dbChiTietBao.IsMaBaoExist(maBao)) {                 // Kiểm tra xem MaBao có bị tham chiếu hay ko
                return db.Delete(maBao);
            }
            else throw new ExceptionMessage.MaThamChieuChiTietBao_Bao();
        }else throw new ExceptionMessage.MaBaoKhongTonTai();
    }
    
    // Trả về một record Bao co MaBao = maBao
    public BAO GetRowByID(String maBao)throws Exception{
        if (ValidationID(maBao)) {                             // Kiểm tra sự hợp lệ của MaBao
            try{
                return db.GetRowByID(maBao);
            }catch(Exception e){
                throw new ExceptionMessage.ExecuteFail("BAO/GetRowByID \n"+e.getMessage());
            }
        }
        else throw new ExceptionMessage.MaBaoKhongTonTai();
    }
    
    // Kiểm tra xem MaBao đã tồn tại trong table BAO hay chưa
    public Boolean ValidationID(String maBao) throws Exception{
        try{
            return db.ValidationID(maBao);
        }catch(Exception e){
                throw new ExceptionMessage.ExecuteFail("BAO/ValidationID \n"+e.getMessage());
        }        
    }
    
    // Trả về ThuPhatHanh tương ứng với TenBao
     public int GetNgayPHByTenBao(String tenBao) throws Exception{
        try {
            return db.GetNgayPHByTenBao(tenBao);
        } catch(Exception e){
                throw new ExceptionMessage.ExecuteFail("BAO/GetNgayPHByTenBao \n"+e.getMessage());
        }
     }
}
