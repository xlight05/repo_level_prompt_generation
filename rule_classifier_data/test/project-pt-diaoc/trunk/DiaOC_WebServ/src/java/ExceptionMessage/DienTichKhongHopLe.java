/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExceptionMessage;

/**
 *
 * @author Nixforest
 */
public class DienTichKhongHopLe extends Exception{
    @Override
    public String getMessage(){
        return java.util.ResourceBundle.getBundle("Presentation/" ).getString("eDienTichKhongHopLe");
    }
}
