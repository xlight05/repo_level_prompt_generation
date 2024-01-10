/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uniceub.fsw.html;

/**
 *
 * @author Gilberto Hiragi
 */
public class TableHeader {

    private String col;

    public TableHeader(String conteudo) {
        this.col = "\n\t\t<TH>" + conteudo + "</TH>\r\n";
    }

    public TableHeader(String conteudo, Integer colspan) {
        this.col = "\n\t\t<TH COLSPAN='" + colspan.toString() + "'>" + conteudo + "</TH>\r\n";
    }

    public TableHeader(String conteudo, String estilo, Integer colspan) {
        this.col = "\n\t\t<TH COLSPAN='" + colspan.toString() + "' STYLE='" + estilo + "'>" + conteudo + "</TH>\r\n";
    }

    public TableHeader(String conteudo, String estilo) {
        this.col = "\n\t\t<TH STYLE='" + estilo + "'>" + conteudo + "</TH>\r\n";
    }

    public String getHTML() {
        return this.col;
    }
}
