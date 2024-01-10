/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Business;

import DataAccess.ThongKeThangDAO;
import java.util.ArrayList;
import Entities.THONGKETHANG;
/**
 *
 * @author VooKa
 */
public class ThongKeThangLogic {
    ThongKeThangDAO db = new ThongKeThangDAO();

    public ArrayList<THONGKETHANG> GetAllRows_Bang()throws Exception{
        return db.GetAllRows_Bang();
    }

    public ArrayList<THONGKETHANG> GetAllRows_Bao()throws Exception{
        return db.GetAllRows_Bao();
    }

    public ArrayList<THONGKETHANG> GetAllRows_ToBuom()throws Exception{
        return db.GetAllRows_ToBuom();
    }
}