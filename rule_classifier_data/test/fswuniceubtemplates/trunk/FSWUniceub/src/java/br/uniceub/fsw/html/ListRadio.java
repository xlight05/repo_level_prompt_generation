/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uniceub.fsw.html;

/**
 *
 * @author Gilberto Hiragi
 */
public class ListRadio {

    private StringBuffer sb;
    private String nome;
    private String classe;

    public ListRadio(String nome) {
        this.nome = nome;
        this.classe = "";
        this.sb = new StringBuffer();
    }

    public ListRadio(String nome, String classe) {
        this.nome = nome;
        this.classe = classe;
        this.sb = new StringBuffer();
    }

    public void addOption(Option opt) {
        sb.append(opt.getOptionRadio(this.nome, this.classe));
    }

    public String getHTML() {
        this.sb.append("<INPUT TYPE='HIDDEN' id='");
        this.sb.append(nome);
        this.sb.append("' value='0'/>");
        this.sb.append("<INPUT TYPE='HIDDEN' id='");
        this.sb.append(nome);
        this.sb.append("Nme");
        this.sb.append("' value='0'/>");
        return this.sb.toString();
    }
}
