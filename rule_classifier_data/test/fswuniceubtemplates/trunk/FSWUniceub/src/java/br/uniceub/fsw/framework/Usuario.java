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
public class Usuario {

    private static Collection<Usuario> users;
    private String nome;
    private String senha;
    private Collection<String> perfis;

    /**
     * Getter do nome
     * @return 
     */
    public String getNome() {
        return nome;
    }

    /**
     * Getter dos perfils
     * @return 
     */
    public Collection<String> getPerfis() {
        return perfis;
    }

    /**
     * Getter da senha
     * @return 
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Carregador do usuario apartir do nome
     * @param nome
     * @return 
     */
    public static Usuario getUsuario(String nome) {
        Usuario user = null;
        if (nome != null && !nome.isEmpty()) {
            if (users == null || users.isEmpty()) {
                try {
                    users = Documentos.loadUsuarios();
                } catch (Exception ex) {
                }
            }
            for (Usuario usuario : users) {
                if (usuario.nome.equalsIgnoreCase(nome)) {
                    user = usuario;
                    break;
                }
            }
        }
        return user;
    }

    /**
     * Retorna true se o usuario pertence ao perfil especificado
     * @param perfil
     * @return 
     */
    public boolean pertencePerfil(String perfil) {
        return this.perfis.contains(perfil);
    }

    /**
     * Construto unico
     * @param nome
     * @param senha
     * @param perfis 
     */
    public Usuario(String nome, String senha, Collection<String> perfis) {
        this.nome = nome;
        this.senha = senha;
        this.perfis = perfis;
    }
}
