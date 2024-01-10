/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExceptionMessage;

/**
 *
 * @author Nixforest
 */
public class NgayKhongHopLe extends Exception{
    public String toString(){
        return "Ngày nhập không hợp lệ, yêu cầu định dạng yyyy-mm-dd!";
    }
}
