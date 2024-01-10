/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExceptionMessage;

/**
 *
 * @author Nixforest
 */
public class KyHieuDaTonTai extends Exception{
    @Override
    public String getMessage(){
        return "Ký hiệu đã tồn tại!";
    }
}
