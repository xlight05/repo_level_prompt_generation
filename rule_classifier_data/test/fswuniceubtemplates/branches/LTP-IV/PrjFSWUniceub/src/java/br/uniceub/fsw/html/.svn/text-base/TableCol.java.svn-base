/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uniceub.fsw.html;

/**
 *
 * @author Gilberto Hiragi
 */
public class TableCol {
    private String col;

    public TableCol(String conteudo){
        this.col="\n\t\t<TD>" + conteudo + "</TD>\r\n";
    }

    public TableCol(String conteudo, String estilo){
        this.col="\n\t\t<TD style='" + estilo + "'>" + conteudo + "</TD>\r\n";
    }

    public TableCol(String conteudo, Integer colspan){
        this.col="\n\t\t<TD colspan='"+colspan.toString()+"'>" + conteudo + "</TD>\r\n";
    }

    public TableCol(String conteudo, String estilo, Integer colspan){
        this.col="\n\t\t<TD colspan='"+colspan.toString()+"' style='" + estilo + "'>" + conteudo + "</TD>\r\n";
    }
    
    public String getHTML(){
        return this.col;
    }


}