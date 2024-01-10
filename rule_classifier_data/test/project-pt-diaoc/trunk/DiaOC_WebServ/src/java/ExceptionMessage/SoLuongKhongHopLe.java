/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExceptionMessage;

/**
 *
 * @author Nixforest
 */
public class SoLuongKhongHopLe extends Exception{
    
    @Override
    public String getMessage(){
        return "Số lượng phải lớn hơn mức tối thiểu là 50";
    }
}
