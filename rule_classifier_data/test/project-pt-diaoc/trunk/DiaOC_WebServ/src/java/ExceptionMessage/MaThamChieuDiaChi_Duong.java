/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ExceptionMessage;

/**
 *
 * @author Wjnd_Field
 */
public class MaThamChieuDiaChi_Duong extends Exception{
    
    @Override
    public String getMessage(){
        return "Tồn tại Địa chỉ trên đường này!";
    }
}
