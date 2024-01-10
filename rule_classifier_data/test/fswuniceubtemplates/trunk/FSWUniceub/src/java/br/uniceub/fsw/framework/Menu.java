/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uniceub.fsw.framework;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Tiago
 */
public class Menu {

    private static Collection<Menu> menu;
    private Collection<Menu> opcoes;
    private Collection<String> permissao;
    private String nome;
    private String url;

    public static Collection<Menu> getMenu() {
        if (menu == null) {
            try {
                menu = Documentos.loadMenu();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return menu;
    }

    public static void setMenu(Collection<Menu> menu) {
        Menu.menu = menu;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Collection<Menu> getOpcoes() {
        return opcoes;
    }

    public void setOpcoes(Collection<Menu> opcoes) {
        this.opcoes = opcoes;
    }

    public Collection<String> getPermissao() {
        return permissao;
    }

    public void setPermissao(Collection<String> permissao) {
        this.permissao = permissao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static Collection<Menu> getMenu(String classe, Collection<Menu> menu) {
        Collection<Menu> m = new ArrayList<Menu>();
        for (Menu item : menu) {
            if (item.getUrl().startsWith(classe + ".")) {
                m.add(item);
            } else {
                m.addAll(getMenu(classe, item.getOpcoes()));
            }
        }
        return m;
    }

    public String getLink() {
        if (getUrl() == null || getUrl().isEmpty()) {
            return "<a>" + getNome() + "</a>";
        }
        return "<a href='Controller?map=" + getUrl() + "' class='ajaxConteudo'>" + getNome() + "</a>";
    }
}
