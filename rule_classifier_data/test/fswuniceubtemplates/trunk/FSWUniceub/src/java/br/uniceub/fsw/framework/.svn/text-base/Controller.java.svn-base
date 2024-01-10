package br.uniceub.fsw.framework;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author Gilberto Hiragi
 *
 * Est� a servlet principal.
 * Ser� ser� a controladora da execu��o (Controle do MVC)
 * Todas as requisi��es ir�o passar por ela.
 * Com estas caracter�sticas iremos implementar nela a parte de seguran�a
 */
public class Controller extends HttpServlet {

    private Mensagem mensagens;//Gerenciador de Mensagens
    private Template template;//Gerenciador de Templates
    private Migalha migalha;//Genrenciador das Migalhas
    private Usuario usuario;//Usuario Logado
    private Mapeamento mapeamento;//Gerenciador do Menu e seguranca
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private String url = "";
    private String nomeHtml = "";
    private String nomeFolder = "";
    private String nomeAction = "";
    private String nomeMetodo = "";
    private Boolean acessoPermitido = true;
    private static boolean MODO_DESENVOLVIMENTO = false;
    private static final String PACOTE_PADRAO = "br.uniceub.fsw.";
    private static final String PASTA_PADRAO = "Geral";

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
     * Retorna o caminho completo ate a pasta de imagens
     * @return 
     */
    public String getPathImagens() {
        return this.getPath() + "/imagens/";
    }

    /**
     * Retorna o caminho completo ate a pasta de relatorios
     * @return 
     */
    public String getPathRelatorios() {
        return this.getPath() + "/WEB-INF/reports/";
    }

    /**
     * Retorna o caminho completo ate a pasta de htmls
     * @return 
     */
    public String getPathHtml() {
        return this.getPath() + "/WEB-INF/htmls/";
    }

    /**
     * Retorna o usuario logado
     * @return
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Retorna o gerenciador de Mensagens
     * @return
     */
    Mensagem getMensagens() {
        if (mensagens == null) {
            mensagens = new Mensagem();
        }
        return mensagens;
    }

    /**
     * Retorna o gerenciador das Migalhas
     * @return
     */
    Migalha getMigalha() {
        if (migalha == null) {
            migalha = new Migalha();
        }
        return migalha;
    }

    /**
     * Retorna o gerenciador de Template
     * Responsavem por forma o html a ser mostrado
     * @return 
     */
    Template getTemplate() {
        if (template == null) {
            template = new Template(this);
        }
        return template;
    }

    /**
     * Retorna o Mapemeamento
     * Responsavem por verifira permissoes de acesso e gera o menu
     * @return 
     */
    Mapeamento getMapeamento() {
        if (mapeamento == null) {
            mapeamento = new Mapeamento(this);
        }
        return mapeamento;
    }

    /**
     * Getter do URL
     * @return 
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter do URL
     * @param url 
     */
    private void setUrl(String url) {
        this.url = url;
    }

    /**
     * Controlador geral resposnsavel por gerencia e resposder requisicoes do usario para gerar paginas
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void doRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.request = request;
        this.response = response;
        Documentos.setAmbiente(this);
        setUrl(request.getParameter("map"));
        response.setCharacterEncoding("ISO-8859-1"); // Processar se for responder em HTML
        response.setContentType("text/html; charset=ISO-8859-1");
        session = request.getSession();
        PrintWriter out = response.getWriter();
        acessoPermitido = true;
        mapear();
        if (getUsuario() == null) {
            usuario = logar();
        } 
        HashMap hashMap = executeAction(); // Invoca Action e metodo responsavel por gerar resultados na tela
        String html = getHtml(hashMap); // Monta a pagina
        if (html == null || html.isEmpty()) {
            html = (!Boolean.parseBoolean(request.getParameter("puro")) ? getMigalha().getHTML() + getMensagens().getHTML() : "");
        }
        out.print(html);
        out.close();
    }

    /**
     * Responsavem por Identificar quais classes,metodos, htmls serao usados apartir da url recebida
     */
    private void mapear() {
        try {
            montaUrlSimples("inicio");
            if (getUrl() == null || getUrl().isEmpty()) {
                return;
            }
            if (getUrl().contains(".")) {
                String[] nomes = url.split("\\.");
                switch (nomes.length) {
                    case 2: //Action.metodo
                        nomeAction = nomeFolder = nomes[0];
                        nomeMetodo = nomeHtml = nomes[1];
                        break;
                    case 3: //Action.metodo.html
                        nomeAction = nomeFolder = nomes[0];
                        nomeMetodo = nomes[1];
                        nomeHtml = nomes[2];
                        break;
                    case 4: //Action.metodo.Action.metodo
                        nomeAction = nomeFolder = nomes[0];
                        nomeMetodo = nomeHtml = nomes[1];
                        executeAction(); //Primeira Action e metodo invocados, não produziram resultados na tela. Podem emitir mensagens
                        nomeAction = nomeFolder = nomes[2];
                        nomeMetodo = nomeHtml = nomes[3];
                        break;
                    case 5: //Action.metodo.Action.metodo.html
                        nomeAction = nomeFolder = nomes[0];
                        nomeMetodo = nomeHtml = nomes[1];
                        executeAction(); //Primeira Action e metodo invocados, não produziram resultados na tela. Podem emitir mensagens
                        nomeAction = nomeFolder = nomes[2];
                        nomeMetodo = nomes[3];
                        nomeHtml = nomes[4];
                        break;
                }
            } else {
                if ("logout".equalsIgnoreCase(getUrl()) || "login".equalsIgnoreCase(getUrl())) {
                    logout(); //As informacoes do login sao eleinadas ao tentar logar novamento ou da logout
                }
                montaUrlSimples(getUrl());
            }
        } catch (Exception err) {
            doError(err);
        }
    }

    /**
     * Mapeia para um url simples utilizando o sistema e pasta padroes
     * @param url 
     */
    private void montaUrlSimples(String url) {
        if (url == null || url.isEmpty()) {
            url = "inicio";
        }
        
        nomeAction = nomeFolder = PASTA_PADRAO;
        nomeMetodo = nomeHtml = url;
    }

    /**
     * Resposavel pela execucao da Action e metodo
     * @return 
     */
    private HashMap executeAction() {
        HashMap hashMap = null;
        if (nomeAction != null && !nomeAction.isEmpty() && nomeMetodo != null && !nomeMetodo.isEmpty()) {
            try {
                Action action = (Action) Class.forName(PACOTE_PADRAO + "action." + nomeAction).newInstance();
                action.setAmbiente(this, request, response, session);
                action.invocar(nomeMetodo);
                hashMap = action.getDados();
            } catch (ClassNotFoundException err) {
                doError(new Exception("Classe nao encontrada.", err));
            } catch (AcessoNegadoException err) {
                acessoPermitido = false;
                doError(new Exception("Restriçao de acesso. Entrada não permitida.", err));
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
            // Jogando os parametros do mapeamento para o gerador de conteudo dinamico
            // Mescla o template HTML com o HashMap de dados dinamicos da Action
            if (acessoPermitido) {
                html = getTemplate().getPagina(hashMap, nomeFolder, nomeHtml, Boolean.parseBoolean(request.getParameter("ajax")), Boolean.parseBoolean(request.getParameter("puro")));
            }
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
        String user = (String) session.getAttribute("login");
        String pass = (String) session.getAttribute("pass");
        //Caso a sessio não possua dados do usario, passa a buscalo nos parametros
        if (user == null || user.isEmpty()) {
            user = request.getParameter("login");
            pass = request.getParameter("pass");
        }
        Usuario pessoa = Usuario.getUsuario(user);
        if (pessoa == null) {
            getMensagens().add(Mensagem.Tipo.Notice, "Por favor faça seu login");
        } else if (!pass.equals(pessoa.getSenha())) {
            getMensagens().add(Mensagem.Tipo.Error, "Login ou senha Invalido");
        } else {
            session.setAttribute("login", pessoa.getNome());
            session.setAttribute("pass", pessoa.getSenha());
            montaUrlSimples("inicio");
            return pessoa;
        }
        logout();
        return null;      
    }

    /**
     * Responsavel por fazer logout do usuario
     * @param session 
     */
    private void logout() {
        session.removeAttribute("login");
        session.removeAttribute("pass");
        usuario = null;
        montaUrlSimples("login");
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
            getMensagens().add(Mensagem.Tipo.FrameworkError, err.getMessage() + "<br/>" + sw.toString());
        } else {
            getMensagens().add(Mensagem.Tipo.FrameworkError, "500 - Erro interno do servidor<br/>" + err.getMessage());
        }
        err.printStackTrace();
    }
}
