/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExceptionMessage;

/**
 *
 * @author Nixforest
 */
public class MaGiaTienKhongHopLe extends Exception{
    
    @Override
    public String getMessage(){
        return "Mã Giá tiền không hợp lệ!";
    }
}
