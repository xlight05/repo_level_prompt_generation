/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExceptionMessage;

/**
 *
 * @author Nixforest
 */
public class ExecuteFail extends Exception{
    
    String erPath = ""; 
    public ExecuteFail(String s){
        super("Lỗi xử lý Database! :" + s);
        this.erPath = s;
    }
    @Override
    public String getMessage(){
        return "Lỗi xử lý Database! :" + erPath;
    }
}
