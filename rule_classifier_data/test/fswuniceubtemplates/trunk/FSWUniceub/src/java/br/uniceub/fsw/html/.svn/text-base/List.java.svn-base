/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uniceub.fsw.html;

/**
 *
 * @author Gilberto Hiragi
 */
public class List {
    private StringBuffer sb;
    private String       nome;
    private String       classe;
    private int          linhas;
    private boolean      multiplo;

    public List(String nome, String classe, int linhas, boolean multiplo) {
        this.nome = nome;
        this.classe = classe;
        this.linhas=linhas;
        this.multiplo=multiplo;
    }

    public void addOption(Option opt) {
        sb.append(opt.getOptionList());
    }

    public String getHTML() {
        return "<SELECT ID='" + this.nome + "' NAME='" + this.nome +
                "' CLASS='" + this.classe + "' SIZE='" + linhas + "'" +
                (this.multiplo?"MULTIPLE='MULTIPLE'":"") + ">\r\n" +
                sb.toString() +
                "</SELECT>\r\n";
    }
}
