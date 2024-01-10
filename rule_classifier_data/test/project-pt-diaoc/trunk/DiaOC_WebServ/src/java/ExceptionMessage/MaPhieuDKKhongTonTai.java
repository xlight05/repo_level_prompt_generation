/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExceptionMessage;

/**
 *
 * @author Nixforest
 */
public class MaPhieuDKKhongTonTai extends Exception{
    
    @Override
    public String getMessage(){
        return "Mã Phiếu đăng ký không tồn tại!";
    }
}
