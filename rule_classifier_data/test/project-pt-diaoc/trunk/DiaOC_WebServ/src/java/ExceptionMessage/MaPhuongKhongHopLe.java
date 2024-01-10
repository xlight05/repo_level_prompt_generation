/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExceptionMessage;

/**
 *
 * @author Nixforest
 */
public class MaPhuongKhongHopLe extends Exception{
    
    @Override
    public String getMessage(){
        return "Mã Phường không hợp lê!";
    }
}
