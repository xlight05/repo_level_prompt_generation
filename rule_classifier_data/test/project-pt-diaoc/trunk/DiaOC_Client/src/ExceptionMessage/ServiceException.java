/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ExceptionMessage;

public class ServiceException extends Exception {
 
    private static final long serialVersionUID = 1L;
 
    private String messages;
 
    public ServiceException(String mes) {
        super();
        this.messages = mes;
    }
 
    public String getMessages() {
        return messages;
    }
}
