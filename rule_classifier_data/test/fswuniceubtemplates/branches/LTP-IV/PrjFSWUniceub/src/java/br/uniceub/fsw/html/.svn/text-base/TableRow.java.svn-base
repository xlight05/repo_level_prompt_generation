/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uniceub.fsw.html;

/**
 *
 * @author Gilberto Hiragi
 */
public class TableRow {

    private StringBuffer sb;
    private String estilo;

    public TableRow() {
        this.sb = new StringBuffer();
        this.estilo = "";
    }

    public TableRow(String estilo) {
        this.sb = new StringBuffer();
        this.estilo = estilo;
    }

    public void clear() {
        this.sb = new StringBuffer();
    }

    public void addTableCol(TableCol col) {
        this.sb.append(col.getHTML());
    }

    public void addTableHeader(TableHeader col) {
        this.sb.append(col.getHTML());
    }

    public String getHTML() {
        return "\t<TR style='" + this.estilo + "'>" + this.sb.toString() + "\t</TR>\r\n";
    }
}
