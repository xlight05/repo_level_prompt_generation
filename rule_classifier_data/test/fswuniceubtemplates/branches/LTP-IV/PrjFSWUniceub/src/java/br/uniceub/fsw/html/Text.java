/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uniceub.fsw.html;

public class Text {

    private String name;
    private String value;
    private int size;
    private int maxSize;

    public Text() {
    }

    public Text(String name, String value) {
        this.name = name;
        this.value = value;
        this.size = 20;
        this.maxSize = 20;
    }

    public Text(String name, String value, int size) {
        this.name = name;
        this.value = value;
        this.size = size;
        this.maxSize = 20;
    }

    public Text(String name, String value, int size, int maxSize) {
        this.name = name;
        this.value = value;
        this.size = size;
        this.maxSize = maxSize;
    }

    public String getHTML() {
        return "<INPUT TYPE='TEXT' ID='" + name + "' NAME='" + name + "' VALUE='" + value + "' SIZE='" + size + "' MAXLENGTH='" + maxSize + "'/>";
    }
}
