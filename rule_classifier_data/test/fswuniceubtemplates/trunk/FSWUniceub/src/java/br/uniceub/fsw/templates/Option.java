/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uniceub.fsw.templates;

/**
 *
 * @author Tiago
 */
class Option {

    String nome = "";
    Object valor = "";
    boolean selected = false;
    boolean disabled = false;

    protected Option(String nome, Object valor, boolean selected) {
        this.nome = nome;
        this.valor = valor;
        this.selected = selected;
    }

    protected String getOption() {
        return "<option value='" + valor.toString() + "' " + (selected ? "selected" : "") + " " + (disabled ? "disabled='disabled'" : "") + ">" + nome.toString() + "</option>";
    }

    protected String getCheckBox(String id) {
        return "<input type='checkbox' id='" + id + "' name='" + id + "' value='" + valor + "' " + (disabled ? "disabled='disabled' " : " ") + (selected ? "checked " : " ") + "><label for='" + id + "'>" + nome + "</label>";
    }
    protected String getRadio(String id){
        return "<input type='radio' id='" + id + valor + "' name='" + id + "' value='" + valor + "'> <label for='" + id + valor + "'>" + nome + "</label>";
    }

    protected Option setDisabled(boolean disabled) {
        this.disabled = disabled;
        return this;
    }
}
