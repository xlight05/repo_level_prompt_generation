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
public class CheckList {

    private LinkedList<Option> options = new LinkedList<Option>();
    private String id = "", classes = "";

    /**
     * Construtor unico
     * @param id da checkbox
     * @param classes da lista
     */
    public CheckList(String id, String classes) {
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
     * Adiciona a lista uma checkbox
     * @param nome Label, texto ao lado da chack box
     * @param valor valor da checkbox
     * @param selected true se for para estar marcado
     */
    public void addCheckbox(String nome, Object valor, boolean selected) {
        addOption(new Option(nome, valor, selected));
    }

    /**
     * Adiciona a lista uma checkbox quer nao pode ser usada
     * @param nome Label, texto ao lado da chack box
     * @param valor valor da checkbox
     * @param selected true se for para estar marcado
     */
    public void addCheckboxDisabled(String nome, String valor, boolean selected) {
        addOption(new Option(nome, valor, selected).setDisabled(true));
    }

    /**
     * Gera o html apartir dos dados previamente adidionados
     * @return 
     */
    public String getHTML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<ul class='" + classes + "'>\n");
        for (Option option : options) {
            sb.append("\t<li>" + option.getCheckBox(id) + "</li>\n");
        }
        sb.append("</ul>\n");
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
