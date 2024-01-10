/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uniceub.fsw.html;

/**
 *
 * @author Hiragi
 */
public class Hidden {

    private String name;
    private String value;

    public Hidden() {
    }

    public Hidden(String name, String value) {
        this.name = name;
        this.value = value;
    }
     public Hidden(String name, Object value) {
        this.name = name;
        if (value != null){
            this.value = value.toString();
        }
    }

    public String getHTML(){
        return "<INPUT TYPE='HIDDEN' ID='" + name  + "' NAME='"+name+"' VALUE='"+value+"'/>";
    }


}
