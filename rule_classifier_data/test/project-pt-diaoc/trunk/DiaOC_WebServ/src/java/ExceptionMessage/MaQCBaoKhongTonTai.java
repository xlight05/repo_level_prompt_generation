/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExceptionMessage;

/**
 *
 * @author Nixforest
 */
public class MaQCBaoKhongTonTai extends Exception{
    
    @Override
    public String getMessage(){
        return "Mã Quảng cáo Báo không tồn tại!";
    }
}