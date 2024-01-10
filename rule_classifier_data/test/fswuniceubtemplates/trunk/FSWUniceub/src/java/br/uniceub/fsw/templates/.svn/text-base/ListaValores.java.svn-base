/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uniceub.fsw.templates;

import java.util.LinkedList;

/**
 *
 * @author Tiago
 */
public class ListaValores {

    private LinkedList<Option> options;
    private String id = "", classes = "";

    /**
     * Construtor padrao
     * @param nome das radio buttons
     * @param classes da lista
     */
    public ListaValores(String id, String classes) {
        this.id = id;
        this.classes = classes;
        options = new LinkedList<Option>();
    }

    /**
     * Adiciona um linha a lista de valores
     * @param valor
     * @param label 
     */
    public void addLinha(Object valor, String label) {
        options.add(new Option(label, valor, false));
    }

    /**
     * Gera o html interno ao dialogo da LV apartir da lista de opicoes
     * e preenche o campo de filtro com o filto usado.
     * @param lista
     * @param filtro
     * @return 
     */
    private String getHTML(LinkedList<Option> lista, String filtro) {
        StringBuilder sb = new StringBuilder();
        sb.append("<form class='lvForm'>\n");
        sb.append("<label for='filtroLV'>Filtro: </label><input type='text' class='filtroLV' name='filtroLV' value='" + filtro + "'>\n");
        sb.append("<a class='filtrarLV'>Filtrar</a>");
        if (!lista.isEmpty()) {
            sb.append("<fieldset><legend>Selecione</legend>");
            sb.append("<ul class='" + classes + "'>\n");
            for (Option option : lista) {
                sb.append("\t<li>" + option.getRadio(id) + "</li>\n");
            }
            sb.append("</ul>\n");
            sb.append("</fieldset>");
        }
        sb.append("</form>\n");
        return sb.toString();
    }

    /**
     * Gera o html interno ao dialogo apartir do filtro, este pode ser
     * nullo ou em branco para mostar todas as opcoes cadastradas.
     * @param filtro
     * @return 
     */
    public String getHTML(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            return getHTML(options, "");
        }
        LinkedList<Option> lista = new LinkedList<Option>();
        String match = "(?i).*" + filtro + ".*";

        for (Option option : options) {
            if (option.nome.matches(match)) {
                lista.add(option);
            }
        }
        return getHTML(lista, filtro);
    }
}
