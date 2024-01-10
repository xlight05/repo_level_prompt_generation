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
public class Mapeamento {

    private static Collection<Mapeamento> grupos;
    private Collection<Item> itens;
    private Collection<String> permissao;
    private String nome;

    /**
     * Construtor unico, representa um Grupo de items do menu
     * @param nome
     * @param Permissao 
     */
    public Mapeamento(String nome, Collection<String> Permissao) {
        this.permissao = Permissao;
        this.nome = nome;
    }

    /**
     * Gera o html do menu intero expecifico pra o perfil do usuario, pronto para se adicionado a pagina
     * @param user
     * @return
     * @throws Exception 
     */
    public static String gerarMenuHtml(Usuario user) throws Exception {
        if (user != null) {
            if (grupos == null || grupos.isEmpty()) {
                grupos = Documentos.loadMenu();
            }
            StringBuilder html = new StringBuilder();
            html.append("<ul id='menuPrincipal'>\n");
            for (Mapeamento item : grupos) {
                if (!Collections.disjoint(user.getPerfis(), item.permissao)) {
                    html.append(" ").append(item.getHTML(user)).append("\n");
                }
            }
            html.append("</ul>");
            return html.toString();
        }
        return "";
    }

    /**
     * Gera html do group em questao
     * @param user
     * @return 
     */
    private String getHTML(Usuario user) {
        StringBuilder sb = new StringBuilder();
        sb.append("<li class='grupoMenu'><span class='tituloSubMenu'>").append(this.nome).append("</span><ul class='subMenu'>");
        for (Item iten : this.itens) {
            if (!Collections.disjoint(user.getPerfis(), iten.permissao)) {
                sb.append(iten.getHTML());
            }
        }
        sb.append("</ul>");
        return sb.toString();
    }

    /**
     * Adiciona um novo Item do menu ao groupo de menus
     * @param nome
     * @param metodo
     * @param permissao 
     */
    public void addItem(String nome, String classe, String metodo, List<String> permissao) {
        if (itens == null) {
            itens = new LinkedList<Item>();
        }
        this.itens.add(new Item(nome, classe, metodo, permissao));
    }

    /**
     * Testa se o usario tem permição sufifiente para acessar a classe expecificada
     * @param usuario
     * @param nomeAction
     * @return 
     */
    public static boolean permicaoAcesso(Usuario usuario, String classe) {
        if(usuario.pertencePerfil("Administrador")) return true;
        if (!Collections.disjoint(usuario.getPerfis(), getClassePermicoes(classe))) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * Procura pela lista de permicoes da classe expecificada
     * @param classe
     * @return 
     */
    public static Collection<String> getClassePermicoes(String classe) {
        for (Mapeamento mapeamento : grupos) {
            for (Item iten : mapeamento.itens) {
                if(iten.classe.equals(classe))
                    return iten.permissao;
            }
        }
        return Collections.EMPTY_LIST;
    }

    protected class Item {

        private Collection<String> permissao;
        private String nome;
        private String metodo;
        private String classe;

        /**
         * Constutor unico para o item do menu
         * @param nome
         * @param metodo
         * @param Permissao 
         */
        protected Item(String nome, String classe, String metodo, Collection<String> Permissao) {
            this.permissao = Permissao;
            this.nome = nome;
            this.classe = classe;
            this.metodo = metodo;
        }

        /**
         * Gera o html do item do menu
         * @return 
         */
        protected String getHTML() {
            String html = "<li><a href=Controller?map=" + getLink();
            html += ">" + nome + "</a></li>";
            return html;
        }
        /**
         * Gera o link correto para usar no menu
         * @return 
         */
        private String getLink() {
            if (classe == null || classe.isEmpty()) {
                return metodo;
            } else {
                return classe + "." + metodo;
            }
        }
    }
}
