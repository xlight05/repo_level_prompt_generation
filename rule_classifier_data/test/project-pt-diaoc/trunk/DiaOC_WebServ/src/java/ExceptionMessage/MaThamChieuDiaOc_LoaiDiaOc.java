/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExceptionMessage;

/**
 *
 * @author Nixforest
 */
public class MaThamChieuDiaOc_LoaiDiaOc extends Exception{
    
    @Override
    public String getMessage(){
        return "Tồn tại một Địa ốc tham chiếu đến Loại Địa ốc này!";
    }
}
