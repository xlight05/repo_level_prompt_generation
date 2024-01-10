package br.uniceub.fsw.framework;

import java.lang.reflect.*;
import javax.servlet.http.*;
import java.util.*;

/**
 *
 * @author Gilberto Hiragi
 */
public abstract class Action {

    protected Controller controller;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
    protected HashMap dados;

    /**
     * Toda Action deve saber o nome de seus metodos controlando acesso, invocandos no momento adequado
     * Caso contrario este metodo ira filtrar apartir de permissoes configuradas no menu do site
     * @param nome do metodo
     * @throws AcessoNegadoException 
     */
    public void invocar(String nomeMetodo) throws AcessoNegadoException, Exception {
        if (Mapeamento.permicaoAcesso(controller.getUsuario(), this.getClass().getSimpleName())) {
            Method me = this.getClass().getMethod(nomeMetodo, null);
            me.invoke(this, null);
        } else {
            throw new AcessoNegadoException();
        }
    }

    /**
     * Construtor padrao
     */
    protected Action() {
        this.dados = new HashMap();
    }

    /**
     * Caregador de objetos necessarios no interior da Action
     * @param controller
     * @param request
     * @param response
     * @param session 
     */
    public void setAmbiente(Controller controller, HttpServletRequest request,
            HttpServletResponse response, HttpSession session) {
        this.controller = controller;
        this.request = request;
        this.response = response;
        this.session = session;
    }

    /**
     * Metodo que encapsula o hashmap que guarda dados a serem inseridos ao html
     * subistituindo os marcadores(placeholders).
     * Onde o marcador no html deve ser "{marcador}" , sem aspas.
     * @param marcador
     * @param conteudo
     */
    protected void inserir(String marcador, String conteudo) {
        dados.put(marcador, conteudo);
    }

    /**
     * Retorna os dados a serem inseridos na html
     * @return 
     */
    protected HashMap getDados() {
        return dados;
    }

    /**
     * Metodo que salva as mensagens para ser mostradas na tela
     * do tipo Error
     * @param message
     */
    public void addErrorMessage(String message) {
        Mensagem.add(new Mensagem(Mensagem.Type.Error, message));
    }

    /**
     * Metodo que salva as mensagens para ser mostradas na tela
     * do tipo Notice
     * @param message
     */
    public void addNoticeMessage(String message) {
        Mensagem.add(new Mensagem(Mensagem.Type.Notice, message));
    }

    /**
     * Metodo que salva as mensagens para ser mostradas na tela
     * do tipo Info
     * @param message
     */
    public void addInfoMessage(String message) {
        Mensagem.add(new Mensagem(Mensagem.Type.Info, message));
    }

    /**
     * Metodo que salva as mensagens para ser mostradas na tela
     * do tipo Success
     * @param message
     */
    public void addSuccessMessage(String message) {
        Mensagem.add(new Mensagem(Mensagem.Type.Success, message));
    }

    /**
     * Metoro responsasavem por cadastrar migalhas para a pagina
     * @param nome
     * @param link
     */
    public void addMigalha(String nome, String link) {
        Migalha.addMigalha(new Migalha(nome, link));
    }

    /**
     * Metoro resposnsavem por cadastrar migalhas sem link para a pagina, ou seja, deve ser a ultima migalha refeterente a pagina atual
     * @param nome
     */
    public void addMigalha(String nome) {
        Migalha.addMigalha(new Migalha(nome));
    }

    /**
     * Retorna o usuario requisitante da action/pagina
     * @return 
     */
    public Usuario getUsuario() {
        Usuario user = null;
        String login = (String) session.getAttribute("login");
        if (login != null) {
            user = Usuario.getUsuario(login);
        }
        return user == null ? new Usuario("", "", Collections.EMPTY_LIST) : user;
    }
}
