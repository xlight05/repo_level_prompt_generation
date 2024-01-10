/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExceptionMessage;

/**
 *
 * @author Nixforest
 */
public class MaThamChieu_QCBao_PhieuDK extends Exception{
    
    @Override
    public String getMessage(){
        return "Mã Phiếu đăng ký đang tồn tại trong bảng Quảng cáo Báo!";
    }
}
