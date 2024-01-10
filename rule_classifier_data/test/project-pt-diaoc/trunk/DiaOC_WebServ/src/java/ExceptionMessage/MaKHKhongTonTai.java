/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExceptionMessage;

/**
 *
 * @author Nixforest
 */
public class MaKHKhongTonTai extends Exception{
    
    @Override
    public String getMessage(){
        return "Mã Khách hàng không tồn tại!";
    }
}
