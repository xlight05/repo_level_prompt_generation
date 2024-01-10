/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import DataAccess.ThamSoDAO;
import Entities.THAMSO;
import java.util.ArrayList;

/**
 *
 * @author Nixforest
 */
public class ThamSoLogic {
    ThamSoDAO db = new ThamSoDAO();
    
    // Trả về toàn bộ bảng THAMSO
    public ArrayList<Entities.THAMSO> GetAllRows() throws Exception{
        return db.GetAllRows();
    }
    
    // Thêm Tham số
    public Boolean Insert(String tenThamSo, Float giaTri, String giaiThich) throws Exception{
        return db.Insert(new THAMSO(tenThamSo, giaTri, giaiThich));
    }
    // Sửa tham số
    public Boolean Update(String tenThamSo, Float giaTri, String giaiThich) throws Exception{
        return db.Update(new THAMSO(tenThamSo, giaTri, giaiThich));
    }
    
    // Xóa Tham số
    public Boolean Delete(String tenThamSo) throws Exception{
        return db.Delete(tenThamSo);
    }
    
    // Lấy Tham số theo tên
    public THAMSO GetRowByID(String tenThamSo) throws Exception{
        return db.GetRowByID(tenThamSo);
    }
    
    // Trả về một đối tượng THAMSO có TenThamSo = tenThamSo
    public THAMSO GetThamSoByTen(String tenThamSo)throws Exception{
        return db.GetThamSoByTen(tenThamSo);
    }
}
