/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExceptionMessage;

/**
 *
 * @author Nixforest
 */
public class MaBaoKhongTonTai extends Exception{
    @Override
    public String toString(){
        return "Mã Báo không tồn tại!";
    }
}
