package br.uniceub.fsw.framework;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

/**
 *
 * @author Gilberto Hiragi
 *
 * Está é a servlet principal.
 * Será a controladora da execução (Controle do MVC)
 * Todas as requisições irão passar por ela.
 * Com estas características iremos implementar nela a parte de segurança, se for necessário
 */
public class Controller extends HttpServlet {

    private Usuario usuario;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private String mapeamento;
    private String nomeHtml = "";
    private String nomeFolder = "";
    private String nomeAction = "";
    private String nomeMetodo = "";
    private boolean acesso = true;
    private static boolean MODO_DESENVOLVIMENTO = false;

    /**
     * Redirecionada para doRun
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doRun(request, response);
    }

    /**
     * Redirecionada para doRun
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doRun(request, response);
    }

    /**
     * Retorna o endereco local da aplicacao
     * @return 
     */
    public String getPath() {
        return this.getServletContext().getRealPath("/");
    }

    /**
     * Retorna o usuario logado
     * @return
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Controlador geral resposnsavel por gerencia e resposder requisicoes do usario para gerar paginas
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void doRun(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.request = request;
        this.response = response;
        mapeamento = request.getParameter("map");
        // Processar se for responder em HTML
        response.setCharacterEncoding("ISO-8859-1");
        response.setContentType("text/html; charset=ISO-8859-1");
        session = request.getSession();
        PrintWriter out = response.getWriter();
        Documentos.setPath(getPath());
        usuario = logar();
        acesso = true;
        mapear();
        HashMap hashMap = executeAction();
        out.print(getHtml(hashMap));
        out.close();
    }

    /**
     * Responsavem por Identificar quais classes,metodos, htmls serao usados apartir da url recebida
     */
    private void mapear() {
        nomeAction = nomeFolder = nomeMetodo = nomeHtml = "";
        if (mapeamento.contains(".")) {
            String[] nomes = mapeamento.split("\\.");
            nomeAction = nomeFolder = nomes[0];
            nomeMetodo = nomeHtml = nomes[1];
            if (nomes.length == 3) {
                nomeHtml = nomes[2];
            } else if (nomes.length >= 4) {
                executeAction(); // Action sem resultados, porem pode motrar mensagens
                nomeAction = nomeFolder = nomes[2];
                nomeMetodo = nomeHtml = nomes[3];
                if (nomes.length >= 5) {
                    nomeHtml = nomes[4];
                }
            }
        } else {
            if ("index".equals(mapeamento)) {
                nomeHtml = "inicio";
            } else if ("logout".equals(mapeamento)) {
                logout(session);
            } else {
                nomeHtml = mapeamento;
            }
        }
    }

    /**
     * Resposavel pela execucao da Action e metodo
     * @return 
     */
    private HashMap executeAction() {
        HashMap hashMap = null;
        if (nomeAction != null && !nomeAction.isEmpty() && nomeMetodo != null && !nomeMetodo.isEmpty()) {
            String actionPackage = "br.uniceub.prj.action.";
            try {
                Action action = (Action) Class.forName(actionPackage + nomeAction).newInstance();
                action.setAmbiente(this, request, response, session);
                action.invocar(nomeMetodo);
                hashMap = action.getDados();
            } catch (ClassNotFoundException err) {
                doError(new Exception("Classe nao encontrada.", err));
            } catch (AcessoNegadoException err) {
                acesso = false;
                if (err.getMessage() == null || err.getMessage().isEmpty()) {
                    doError(new Exception("Restriçao de acesso. Entrada não permitida.", err));
                } else {
                    doError(err);
                }
            } catch (Exception err) {
                doError(err);
            }
        }
        return hashMap;
    }

    /** 
     * Reponsavel pela montagem da html, inserindo dados gerados na Action
     * @param hashMap
     * @return 
     */
    private String getHtml(HashMap hashMap) {
        String html = "";
        try {
            // Jogando os parametros do mapeamento para o gerador de conteúdo dinâmico
            // Mescla o template HTML com o HashMap de dados dinâmicos da Action
            Template tpl = new Template();
            tpl.setPath(getPath() + "WEB-INF\\htmls\\");
            html = tpl.getPagina(hashMap, usuario, nomeFolder, !acesso ? "" : nomeHtml);
        } catch (FileNotFoundException err) {
            doError(new Exception("Pagina nao encontrado", err));
        } catch (IOException err) {
            doError(new Exception("Pagina nao pode ser aberta", err));
        } catch (Exception err) {
            doError(err);
        }
        return html;
    }

    /**
     * Responsavel pelo processo de Login do usuario
     * @return 
     */
    private Usuario logar() {
        usuario = Usuario.getUsuario((String) session.getAttribute("login"));
        if ("login".equals(mapeamento) || usuario == null) {
            String user = request.getParameter("login");
            String pass = request.getParameter("pass");
            Usuario u = Usuario.getUsuario(user);
            if (user != null && !user.isEmpty() && u != null && u.getSenha().equals(pass)) {
                session.setAttribute("login", u.getNome());
                return u;
            } else {
                mapeamento = "login";
            }
        }
        return usuario;
    }

    /**
     * Responsavel por fazer logout do usuario
     * @param session 
     */
    private void logout(HttpSession session) {
        session.removeAttribute("login");
        usuario = null;
        nomeHtml = "login";
    }

    /**
     * Controler geral das mensagens de erro
     * @param err 
     */
    private void doError(Exception err) {
        if (MODO_DESENVOLVIMENTO) {
            Writer sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            err.printStackTrace(pw);
            Mensagem.add(new Mensagem(Mensagem.Type.FrameworkError, err.getMessage() + "<br/>" + sw.toString()));
        } else {
            Mensagem.add(new Mensagem(Mensagem.Type.FrameworkError, "500 - Erro interno do servidor<br/>" + err.getMessage()));
        }
        err.printStackTrace();
    }
}
