/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uniceub.fsw.html;

/**
 *
 * @author Gilberto Hiragi
 */
public class ListCheck {
    private StringBuffer sb;
    private String nome;
    private String classe;

    public ListCheck(String nome, String classe) {
        this.nome = nome;
        this.classe = classe;
    }

    public void addOption(Option opt) {
        sb.append(opt.getOptionCheck(this.nome, this.classe));
    }

    public String getHTML() {
        return sb.toString();
    }
}
