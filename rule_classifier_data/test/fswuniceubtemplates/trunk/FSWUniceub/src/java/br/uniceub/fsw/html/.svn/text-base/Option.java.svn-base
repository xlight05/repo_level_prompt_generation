/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uniceub.fsw.html;

/**
 *
 * @author Gilberto Hiragi
 */
public class Option {

    private Integer idt;
    private String nme;
    private boolean marca;
    private String value;

    public Option(Integer idt, String nme, boolean marca) {
        this.idt = idt;
        this.nme = nme;
        this.marca = marca;
    }

    public Option(Integer idt, String nme) {
        this.idt = idt;
        this.nme = nme;
        this.marca = false;
    }
    
    public Option(String value, String nme, boolean marca) {
        this.value = value;
        this.nme = nme;
        this.marca = marca;
    }

    public Option(String value, String nme) {
        this.value = value;
        this.nme = nme;
    }

    public String getOptionComboString() {
        return "<OPTION VALUE='" + this.value + "'"
                + (marca ? "SELECTED" : "")
                + ">" + this.nme + "</OPTION>\r\n";
    }

    public String getOptionCombo() {
        return "<OPTION VALUE='" + this.idt.toString() + "'"
                + (marca ? "SELECTED" : "")
                + ">" + this.nme + "</OPTION>\r\n";
    }

    public String getOptionList() {
        return "<OPTION VALUE='" + this.idt.toString() + "'"
                + (marca ? "SELECTED" : "")
                + ">" + this.nme + "</OPTION>\r\n";
    }

    public String getOptionRadio(String nome, String classe) {
        return "<INPUT TYPE='RADIO' NAME='" + nome + "Agrupa' " + "ID='" + nome + this.idt + "' "
                + (marca ? "CHECKED" : "") + " CLASS='" + classe + "'"
                + " value='" + this.idt + "' onclick='document.getElementById(\"" + nome + "\").value=\"" + this.idt + "\"; document.getElementById(\"" + nome + "Nme\").value=\"" + this.nme + "\";'/>&nbsp;" +
                "<A HREF='#' onclick='document.getElementById(\"" + nome + "\").value=\"" + this.idt + "\";  document.getElementById(\"" + nome + "Nme\").value=\"" + this.nme + "\"; document.getElementById(\"" + nome + this.idt + "\").checked=true;'>"+this.nme + "</A><BR>\r\n";
    }

    public String getOptionCheck(String nome, String classe) {
        return "<INPUT TYPE='CHECKBOX' NAME='" + nome + "' ID=" + nome + " "
                + (marca ? "CHECKED" : "") + " CLASS='" + classe + "'"
                + " VALUE='" + this.idt + "'/>&nbsp;" + this.nme + "<BR>\r\n";
    }
}
