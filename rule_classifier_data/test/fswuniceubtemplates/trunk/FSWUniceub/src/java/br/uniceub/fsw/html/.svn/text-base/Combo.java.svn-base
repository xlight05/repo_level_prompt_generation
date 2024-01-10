/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uniceub.fsw.html;

/**
 *
 * @author Gilberto Hiragi
 */
public class Combo {

    private StringBuffer sb;
    private String nome;
    private String classe;
    private String extra;

    public Combo(String nome) {
        this.nome = nome;
        this.classe = "";
        this.extra = "";
        sb = new StringBuffer();
    }

    public Combo(String nome, String classe) {
        this.nome = nome;
        this.classe = classe;
        this.extra = "";
        sb = new StringBuffer();
    }

    public Combo(String nome, String classe, String extra) {
        this.nome = nome;
        this.classe = classe;
        this.extra = extra;
        sb = new StringBuffer();
    }

    public void addOption(Option opt) {
        sb.append(opt.getOptionCombo());
    }

    public void addOptionString(Option opt) {
        sb.append(opt.getOptionComboString());
    }

    public String getHTML() {
        return "<SELECT ID='" + this.nome + "' NAME='" + this.nome + "' CLASS='"
                + this.classe + "' size='1' " + this.extra + ">\r\n"
                + sb.toString()
                + "</SELECT>\r\n";
    }
}
