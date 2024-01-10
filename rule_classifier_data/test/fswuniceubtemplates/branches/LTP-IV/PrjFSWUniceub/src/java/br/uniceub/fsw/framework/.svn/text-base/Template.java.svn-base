package br.uniceub.fsw.framework;

import java.io.*;
import java.util.*;

/**
 *
 * @author Gilberto Hiragi
 */
public class Template {

    private String path = "";

    /**
     * Metodo para carregar o caminho da aplicacao
     * @param path 
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Carrega o arquivo html
     * @param fileName
     * @return
     * @throws Exception 
     */
    private String getHtmlFile(String fileName) throws Exception {
        String source = "";

        FileReader reader = new FileReader(this.path + fileName);
        BufferedReader breader = new BufferedReader(reader);

        String line = null;
        while ((line = breader.readLine()) != null) {
            source += line + "\n";
        }
        breader.close();
        reader.close();

        return source;
    }

    /**
     * Troca o marcador no html pelo conteudo fornecido
     * @param pagina
     * @param marcador
     * @param conteudo
     * @return 
     */
    public String troca(String pagina, String marcador, String conteudo) {
        return pagina.replace("{" + marcador + "}", conteudo);
//        int pos = 0;
//        String placeholder = "{" + marcador + "}";
//        while (pos != -1) {
//            pos = pagina.indexOf(placeholder);
//            if (pos != -1) {
//                pagina = pagina.substring(0, pos) + conteudo + pagina.substring(pos + placeholder.length());
//            }
//        }
//        return pagina;
    }

    /**
     * Metodo resposavel por monatar pagina toda 
     * @param dinamico
     * @param user
     * @param folder
     * @param html
     * @return
     * @throws Exception 
     */
    public String getPagina(HashMap dinamico, Usuario user, String folder, String html) throws Exception {
        String page = "", conteudo = "";        
        page = getHtmlFile("index.html");
        if (html != null && !html.isEmpty()) {
            conteudo = getHtmlFile(folder + "\\" + html + ".html");

            if (dinamico != null && !dinamico.isEmpty()) {
                Collection names = dinamico.keySet();
                Iterator it = names.iterator();
                while (it.hasNext()) {
                    String placeholder = (String) it.next();
                    String value = (String) dinamico.get(placeholder);
                    conteudo = troca(conteudo, placeholder, value);
                }
            }
        }
        page = troca(page, "CONTEUDO", conteudo);
        page = troca(page, "MIGALHAS", Migalha.getMigalhasHTML());
        page = troca(page, "MENSAGENS", Mensagem.getMessagesHtml());
        page = troca(page, "MENU", Mapeamento.gerarMenuHtml(user));
        page = troca(page, "LOGIN", user == null ? "" : "<a href='Controller?map=logout'>Logout</a>");

        return page;
    }
}
