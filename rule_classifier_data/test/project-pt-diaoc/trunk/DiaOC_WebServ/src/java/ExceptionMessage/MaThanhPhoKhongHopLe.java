/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExceptionMessage;

/**
 *
 * @author Nixforest
 */
public class MaThanhPhoKhongHopLe extends Exception{
    
    @Override
    public String getMessage(){
        return "Mã Thành Phố không hợp lệ!";
    }
}
