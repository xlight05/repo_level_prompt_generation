package br.uniceub.fsw.framework;

import java.lang.reflect.*;
import javax.servlet.http.*;
import java.util.*;

/**
 * Classe Pai de todas as action responsavel por definir comportamento padroes
 * @author Gilberto Hiragi
 */
public abstract class Action {

    private Controller controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private HashMap dados;

    /**
     * Toda Action deve saber o nome de seus metodos controlando acesso, invocandos no momento adequado
     * Caso contrario este metodo ira filtrar apartir de permissoes configuradas no menu do site
     * @param nome do metodo
     * @throws AcessoNegadoException 
     */
    public void invocar(String nomeMetodo) throws AcessoNegadoException, Exception {
        invocar(nomeMetodo, permissaoAcesso(this));
    }

    /**
     * Invcca o metodo se lher for dada permissao
     * @param nomeMetodo
     * @param permissao
     * @throws AcessoNegadoException
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    protected void invocar(String nomeMetodo, boolean permissao) throws AcessoNegadoException, Exception {
        if (permissao) {
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
     * Onde o marcador no html deve ser "{marcador}" , sem aspas e sem chaves.
     * @param marcador
     * @param conteudo
     */
    protected void inserir(String marcador, String conteudo) {
        if (marcador == null || marcador.isEmpty()) {
            return;
        }
        if (conteudo == null || "null".equalsIgnoreCase(conteudo)) {
            conteudo = "";
        }
        dados.put(marcador, conteudo);
    }

    /**
     * Metodo que encapsula o hashmap que guarda dados a serem inseridos ao html
     * subistituindo os marcadores(placeholders).
     * Onde o marcador no html deve ser "{marcador}" , sem aspas e sem chaves.
     * @param marcador
     * @param conteudo
     */
    protected void inserir(String marcador, Number conteudo) {
        if (conteudo == null) {
            inserir(marcador, "");
        } else {
            inserir(marcador, conteudo.toString());
        }
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
    protected void addErrorMessage(String message) {
        controller.getMensagens().add(Mensagem.Tipo.Error, message);
    }

    /**
     * Metodo que salva as mensagens para ser mostradas na tela
     * do tipo Notice
     * @param message
     */
    protected void addNoticeMessage(String message) {
        controller.getMensagens().add(Mensagem.Tipo.Notice, message);
    }

    /**
     * Metodo que salva as mensagens para ser mostradas na tela
     * do tipo Info
     * @param message
     */
    protected void addInfoMessage(String message) {
        controller.getMensagens().add(Mensagem.Tipo.Info, message);
    }

    /**
     * Metodo que salva as mensagens para ser mostradas na tela
     * do tipo Success
     * @param message
     */
    protected void addSuccessMessage(String message) {
        controller.getMensagens().add(Mensagem.Tipo.Success, message);
    }

    /**
     * Metoro responsasavem por cadastrar migalhas para a pagina
     * @param nome
     * @param link
     */
    protected void addMigalha(String nome, String link) {
        controller.getMigalha().add(nome, link);
    }

    /**
     * Metoro resposnsavem por cadastrar migalhas sem link para a pagina, ou seja, deve ser a ultima migalha refeterente a pagina atual
     * @param nome
     */
    protected void addMigalha(String nome) {
        controller.getMigalha().add(nome);
    }

    /**
     * Retorna o usuario requisitante da action/pagina
     * @return 
     */
    protected Usuario getUsuario() {
        return controller.getUsuario();
    }

    /**
     * Metodo de teste de acesso para um sistema
     * Encapsulamento de acesso para o Mapeamento
     * @param vo
     * @return 
     */
    protected boolean podeAcessar(Menu vo) {
        return controller.getMapeamento().podeAcessar(vo);
    }

    /**
     * Testa se o usario tem permicao sufifiente para acessar a classe expecificada
     * Encapsulamento de acesso para o Mapeamento
     * @param nomeAction
     * @return
     */
    protected boolean permissaoAcesso(Action action) {
        return controller.getMapeamento().permissaoAcesso(action);
    }

    /**
     * Retorna o paramentro especificado no formato String
     * @param parametro null se nao existir
     * @return 
     */
    protected String parametroString(String parametro) {
        String value = request.getParameter(parametro);
        if (value == null || value.isEmpty()) {
            return null;
        }
        return value;
    }

    /**
     * Retorna o array com todos os valores do paramentro especificado no formato String
     * @param parametro null se nao existir
     * @return 
     */
    protected String[] parametroStrings(String parametro) {
        return request.getParameterValues(parametro);
    }

    /**
     * Retorna o parametro especificado no formato Integer
     * @param parametro null se nao for um numero ou nao existir
     * @return 
     */
    protected Integer parametroInteiro(String parametro) {
        String valor = parametroString(parametro);
        try {
            return Integer.parseInt(valor);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retorna o parametro especificado no formato Integer
     * @param parametro null se nao for um numero ou nao existir
     * @return 
     */
    protected Integer[] parametroInteiros(String parametro) {
        String[] valores = parametroStrings(parametro);
        ArrayList<Integer> array = new ArrayList<Integer>();
        for (String valor : valores) {
            try {
                array.add(Integer.parseInt(valor));
            } catch (Exception e) {
                array.add(null);
            }
        }
        if (array.isEmpty()) {
            return null;
        }
        return (Integer[]) array.toArray();
    }
}
