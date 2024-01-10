/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uniceub.fsw.framework;

import java.util.*;

/**
 *
 * @author Tiago
 */
class Migalha {

    private String link;
    private String nome;
    private static List<Migalha> itens = new LinkedList<Migalha>();

    /**
     * Adiciona uma miganha a lista que ira para a pagina
     * @param m 
     */
    public static void addMigalha(Migalha m) {
        itens.add(m);
    }

    /**
     * Retorna o html pronto pra ser inserido a pagina
     * @return 
     */
    public static String getMigalhasHTML() {
        StringBuilder html = new StringBuilder();
        html.append("<div id='breadcrumbs'><p>\n");
        String m = "";

        for (Migalha item : itens) {
            m = "";
            boolean b = false;
            if (item.link != null && !"".equals(item.link)) {
                m = "<a href=" + item.link + ">";
                b = true;
            }
            m += item.nome;
            if (b) {
                m += "</a>";
            }
            html.append(m);
        }
        html.append("</p></div>");
        String s = html.toString();
        itens.clear();
        return s;
    }
    /**
     * Contrutor simples para migalhas nao links, ou seja, deve ser a ultima aser inserida, referente a pagina atual
     * @param nome 
     */
    public Migalha(String nome) {
        this.nome = nome;
    }
    /**
     * Contrutor simples para migalhas
     * @param nome 
     */
    public Migalha(String nome, String link) {
        this.nome = nome;
        this.link = link;
    }
}
