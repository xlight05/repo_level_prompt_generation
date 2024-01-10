/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExceptionMessage;

/**
 *
 * @author Nixforest
 */
public class MaQCBangKhongTonTai extends Exception{
    
    @Override
    public String getMessage(){
        return "Mã Quảng cáo bảng không tồn tại!";
    }
}
