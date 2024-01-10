/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Business;

import DataAccess.DangNhapDAO;
import java.util.ArrayList;
import Entities.DANGNHAP;
import ExceptionMessage.UserNameTonTai;
/**
 *
 * @author VooKa
 */
public class DangNhapLogic {
    private final DangNhapDAO db = new DangNhapDAO();


    public ArrayList<DANGNHAP> GetAllRows()throws Exception{
        return db.GetAllRows();
    }

    public Boolean Insert(String user, String pass)throws Exception{
        if(!IsExistUserName_DangNhap(user))
        {
            return db.Insert(db.GetNewID(), user, pass);
        }
        else throw  new UserNameTonTai();
    }

    public Boolean  Update(int id, String user, String pass)throws Exception{
        return db.Update(id, user, pass);
    }

    public Boolean Delete(int id)throws Exception{
        return db.Delete(id);
    }

    public Boolean CheckDangNhap(String user,String id)throws Exception{
        return db.CheckDangNhap(user, id);
    }

    public Boolean IsExistUserName_DangNhap(String user)throws Exception{
        return db.IsExistUserName_DangNhap(user);
    }

    public Boolean ChangePassword(int id, String pass)throws Exception{
        return db.ChangePassword(id, pass);
    }
}
