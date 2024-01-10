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
public class ComboBox {

    private LinkedList<Option> options = new LinkedList<Option>();
    private String id = "", classes = "";

    /**
     * Construtor unico
     * @param id do select
     * @param classes do select
     */
    public ComboBox(String id, String classes) {
        this.id = id;
        this.classes = classes;
    }

    /**
     * Adidiona um option 
     * @param option 
     */
    private void addOption(Option option) {
        this.options.add(option);
    }

    /**
     * Adiciona a lista uma opcao
     * @param nome texto da opcao
     * @param valor valor da opcao
     * @param selected true se for para estar selecionado
     */
    public void addOption(String nome, Object valor, boolean selected) {
        addOption(new Option(nome, valor, selected));
    }

    /**
     * Adiciona a lista uma opcao quer nao pode ser usada
     * @param nome texto da opcao
     * @param valor valor da opcao
     * @param selected true se for para estar selecionado
     */
    public void addOptionDisabled(String nome, String valor, boolean selected) {
        addOption(new Option(nome, valor, selected).setDisabled(true));
    }

    /**
     * Gera o html apartir dos dados previamente adidionados
     * @return 
     */
    public String getHTML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<select id='" + id + "' name='" + id + "' class='" + classes + "'>\n");
        for (Option option : options) {
            sb.append("\t").append(option.getOption()).append("\n");
        }
        sb.append("</select>\n");
        return sb.toString();
    }

    /**
     * Equivale ao getHTML()
     * @return 
     */
    @Override
    public String toString() {
        return getHTML();
    }
}
