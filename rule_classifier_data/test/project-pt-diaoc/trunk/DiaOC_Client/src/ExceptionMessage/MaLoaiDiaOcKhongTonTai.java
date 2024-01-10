/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExceptionMessage;

/**
 *
 * @author Nixforest
 */
public class MaLoaiDiaOcKhongTonTai extends Exception{
    public String toString(){
        return "Mã Loại địa ốc không tồn tại!";
    }
}
