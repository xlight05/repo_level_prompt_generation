/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExceptionMessage;

/**
 *
 * @author Nixforest
 */
public class MaDuongKhongHopLe extends Exception{
    
    @Override
    public String getMessage(){
        return "Mã Đường không hợp lệ!";
    }
}
