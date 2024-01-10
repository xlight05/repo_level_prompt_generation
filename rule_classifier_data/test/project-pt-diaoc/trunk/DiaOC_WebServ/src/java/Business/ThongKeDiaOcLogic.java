/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Business;

import DataAccess.ThongKeDiaOcDAO;
import Entities.THONGKEDIAOC;
import java.util.ArrayList;
/**
 *
 * @author VooKa
 */
public class ThongKeDiaOcLogic {

    ThongKeDiaOcDAO db = new ThongKeDiaOcDAO();

    //liệt kê tất cả
    public ArrayList<THONGKEDIAOC> GetAllRows()throws Exception{
        return db.GetAllRows();
    }

    //liệt kê theo mã khách hàng
    public ArrayList<THONGKEDIAOC> GetRowsByMaKH(String maKH)throws Exception{
        return db.GetRowsByMaKH(maKH);
    }

    //liệt kê theo họ tên
    public ArrayList<THONGKEDIAOC> GetRowsByHoTen(String hoTen)throws Exception{
        return db.GetRowsByHoTen(hoTen);
    }

    //liệt kê theo trạng thái
    public ArrayList<THONGKEDIAOC> GetRowsByTrangThai(String trangThai)throws Exception{
        return db.GetRowsByTrangThai(trangThai);
    }
}
