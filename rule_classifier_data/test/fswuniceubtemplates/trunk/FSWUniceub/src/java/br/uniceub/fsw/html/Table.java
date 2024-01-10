package br.uniceub.fsw.html;

/**
 *
 * Esta classe tem por finalidade oferecer servicos para a criacao de
 * tabelas em HTML.
 *
 * @author Gilberto Hiragi
 * @version 1.5
 * @since Janeiro/2010
 * @see TableRow, TableHeader, TableCol
 * 
 */
public class Table {

    private StringBuffer sb;
    private String classe;
    private Integer width;
    private String id;
    private Integer border;

    public Table(){
        this.sb = new StringBuffer();
        this.id = "";
        this.classe = "";
        this.width = 95;
    }
    
    public Table(String id, String classe, Integer width) {
        this.sb = new StringBuffer();
        this.id = id;
        this.classe = classe;
        this.width = width;
    }

    /**
     * Este metodo retorna o HTML gerado a partir da montagemda tabela
     *
     * @return o html para a criacao da tabela
     */
    public String getHTML() {
        return "<TABLE border='" + this.border + "px' cellpadding='3px' id='" + id + "' width='" + this.width + "%' class='" + this.classe + "'>\r\n"
                + this.sb.toString()
                + "</TABLE>\r\n";
    }

    /**
     *
     * Adiciona uma nova linha "<TR>" a tabela
     *
     * @param row
     */
    public void addTableRow(TableRow row) {
        this.sb.append(row.getHTML());
    }

    /**
     *
     * Limpa a variavel global sb (StringBuffer), resetando assim as
     * configuracoes da tabela.
     *
     */
    public void clear() {
        this.sb = new StringBuffer();
    }

}
