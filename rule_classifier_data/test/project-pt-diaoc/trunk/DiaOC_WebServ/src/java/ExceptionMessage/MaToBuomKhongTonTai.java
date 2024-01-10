/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExceptionMessage;

/**
 *
 * @author Nixforest
 */
public class MaToBuomKhongTonTai extends Exception{
    
    @Override
    public String getMessage(){
        return "Mã Tờ Bướm không tồn tại!";
    }
}
