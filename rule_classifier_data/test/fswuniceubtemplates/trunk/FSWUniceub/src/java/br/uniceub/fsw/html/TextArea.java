/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uniceub.fsw.html;

public class TextArea {

    private String name;
    private String value;
    private int linhas;
    private int colunas;
    private String classe;

    public TextArea() {
    }

    public TextArea(String name, String value, int linhas, int colunas) {
        this.name = name;
        this.value = value;
        this.linhas = linhas;
        this.colunas = colunas;
        this.classe = "";
    }

    public TextArea(String name, String value, int linhas, int colunas, String classe) {
        this.name = name;
        this.value = value;
        this.linhas = linhas;
        this.colunas = colunas;
        this.classe = classe;
    }

    public String getHTML() {
        return "<TEXTAREA CLASS='" + classe + "' ID='" + name + "' NAME='" + name + "' ROWS='" + linhas + "' COLS='" + colunas + ">" + value + "</TEXTAREA>";
    }
}
