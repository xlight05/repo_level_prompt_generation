package br.uniceub.fsw.framework;

import java.util.*;

/**
 * Classe responsavel por gerenciar as Migalhas(breadcrumb) da pagina
 * Classe Restrita interna do framework
 * @author Tiago
 */
class Migalha {

    private List<Mig> itens = new LinkedList<Mig>();

    /**
     * Adiciona uma miganha a lista que ira para a pagina sem link
     * @param m 
     */
    public void add(String nome) {
        itens.add(new Mig(nome));
    }

    /**
     * Adiciona uma miganha a lista que ira para a pagina com link
     * @param m
     */
    public void add(String nome, String link) {
        itens.add(new Mig(nome, link));
    }

    /**
     * Retorna o html pronto pra ser inserido a pagina
     * a lista e esvasiada apos o uso desse metodo
     * @return 
     */
    public String getHTML() {
        StringBuilder html = new StringBuilder("<div id='breadcrumbs'><p>\n");
        for (Mig item : itens) {
            html.append(item.getHTML());
        }
        itens.clear();
        return html.append("</p></div>").toString();
    }

    /**
     * Invoca getHTML() internamente
     * @return
     */
    @Override
    public String toString() {
        return this.getHTML();
    }

    /**
     * Classe individual da Migalha
     */
    private class Mig {

        private String link;
        private String nome;

        /**
         * Contrutor simples para migalhas nao links, ou seja, deve ser a ultima aser inserida, referente a pagina atual
         * @param nome
         */
        public Mig(String nome) {
            this.nome = nome;
        }

        /**
         * Contrutor simples para migalhas
         * @param nome
         */
        public Mig(String nome, String link) {
            this.nome = nome;
            this.link = link;
        }

        /**
         * Retorna o html para ser inserido a na div
         * @return
         */
        public String getHTML() {
            if (this.link != null && !this.link.isEmpty()) {
                return "<a class='ajaxConteudo' href='Controller?map=" + this.link + "'>" + this.nome + "</a>";
            }
            return this.nome;
        }

        /**
         * Invoca getHTML() internamente
         * @return
         */
        @Override
        public String toString() {
            return this.getHTML();
        }
    }
}
