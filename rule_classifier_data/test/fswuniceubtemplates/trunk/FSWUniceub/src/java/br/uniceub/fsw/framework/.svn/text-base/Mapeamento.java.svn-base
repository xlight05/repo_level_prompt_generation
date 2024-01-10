/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uniceub.fsw.framework;

import java.util.*;

/**
 * Classe responsavel por Organizar os mapeamentos , verificando premicoes de acesso
 * E Gera o menu da pagina por perfis
 * @author Tiago
 */
class Mapeamento {

    private Controller controller;

    /**
     * Contrutor padao
     * @param controller 
     */
    public Mapeamento(Controller controller) {
        this.controller = controller;
    }

    /**
     * Gera o html do menu intero expecifico pra o perfil do usuario, pronto para se adicionado a pagina
     * @param user
     * @return
     * @throws Exception 
     */
    public String getHTML() {
        StringBuilder html = new StringBuilder();
        html.append("<ul id='nav' class=''>");
        for (Menu vo : Menu.getMenu()) {
            html.append(getMenuHTML(vo));
        }
        html.append("</ul>");
        return html.toString();

    }

    /**
     * Gera html do menu em questao
     * @param vo
     * @return
     */
    private String getMenuHTML(Menu vo) {
        if (!podeAcessar(vo)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<li>");
        sb.append(vo.getLink());
        if (!vo.getOpcoes().isEmpty()) {
            sb.append("<ul>");
            for (Menu submenu : vo.getOpcoes()) {
                sb.append(getMenuHTML(submenu));
            }
            sb.append("</ul>");
        }
        sb.append("</li>");
        return sb.toString();
    }

    /**
     * Procura pela lista de privilegios do menu expecificada
     * @param vo
     * @return
     */
    private Collection<String> getPrivilegios(Menu vo) {
        Collection<String> set = new HashSet<String>();
        set.addAll(vo.getPermissao());
        if (vo.getOpcoes() != null && !vo.getOpcoes().isEmpty()) {
            for (Menu sub : vo.getOpcoes()) {
                set.addAll(getPrivilegios(sub));
            }
        }
        return set;
    }

    /**
     * Procura por um menu com um url contendo a classe e metodo especificados,
     * afim de buscar privilegios pertencentes ao metodo 
     * @param classe
     * @return 
     */
    private Collection<String> getPrivilegios(String classe) {
        Collection<String> set = new HashSet<String>();
        Collection<Menu> menus = Menu.getMenu(classe,Menu.getMenu());
        if (menus != null && !menus.isEmpty()) {
            for (Menu menu : menus) {
                set.addAll(getPrivilegios(menu));
            }
        }
        return set;
    }

    /**
     * Metodo de teste de acesso para um menu
     * @param vo
     * @return 
     */
    public boolean podeAcessar(Menu vo) {
        return vo != null && controller.getUsuario() != null && !Collections.disjoint(controller.getUsuario().getPerfis(), getPrivilegios(vo));
    }

    /**
     * Testa se o usario tem permicao sufifiente para acessar a classe expecificada
     * @param nomeAction
     * @return
     */
    public boolean permissaoAcesso(Action action) {
        if (controller.getUsuario().pertencePerfil("Administrador")) {
            return true;
        }
        String classe = action.getClass().getSimpleName();
        if (!Collections.disjoint(controller.getUsuario().getPerfis(), getPrivilegios(classe))) {
            return true;
        } else {
            return false;
        }
    }
}
