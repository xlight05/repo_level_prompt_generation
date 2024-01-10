package br.uniceub.fsw.framework;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Classe de geramento dos HTMLs
 * Classe Restrita interna do framework
 * @author Tiago
 */
class Template {

    Controller controller;

    /**
     * Contrutor Padrao resposnavel por carregar partes nrecessarias
     * @param mensagens
     * @param migalha
     * @param usuario
     */
    Template(Controller controller) {
        this.controller = controller;
    }

    /**
     * Troca o marcador no html pelo conteudo fornecido
     * @param pagina
     * @param marcador
     * @param conteudo
     * @return 
     */
    private String troca(String pagina, String marcador, String conteudo) {
        return pagina.replace("{" + marcador + "}", conteudo);
    }
    /**
     * Contrutor da pagina completa
     * @param conteudo
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */
    private String montarTemplate(String conteudo) throws FileNotFoundException, IOException{
        String page = "";
        page = getHtmlFile("index.html");
        page = troca(page, "CONTEUDO", conteudo);
        page = troca(page, "MIGALHAS", controller.getMigalha().getHTML());
        page = troca(page, "MENSAGENS", controller.getMensagens().getHTML());
        page = troca(page, "MENU", controller.getMapeamento().getHTML());
        page = troca(page, "LOGIN", controller.getUsuario() == null ? "<a class='ajaxConteudo' href='Controller?map=login'>Login</a>" : controller.getUsuario().getNome()+" (<a class='ajaxConteudo' href='Controller?map=logout'>Logout</a>) ");
        return page;
    }

    /**
     * Metodo resposavel por monatar pagina toda 
     * @param dinamico HashMap contendo marcadores e conteudo para substitui-los no html
     * @param user
     * @param folder
     * @param html
     * @return
     * @throws Exception 
     */
    public String getPagina(HashMap dinamico, String folder, String html, boolean ajax, boolean puro) throws FileNotFoundException, IOException{
        String conteudo = "";

        if (html != null && !html.isEmpty()) {
            conteudo = getHtmlFile(folder + "/" + html + ".html");

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
        if(puro){ //Retorna somente o conteudo gerado para  a tela
            return conteudo;
        }
        if (ajax) { //Retorna o conteudo a ser exporta na div#conteudo , contendo as Migalhas , Mensagens e o conteudo em si
            return controller.getMigalha().getHTML() + controller.getMensagens().getHTML() + conteudo;
        }
        return montarTemplate(conteudo); //Retorna o site inteiro
    }

    /**
     * Carrega o arquivo html
     * @param fileName
     * @return
     * @throws Exception
     */
    public String getHtmlFile(String fileName) throws FileNotFoundException, IOException{
        String source = "";

        FileReader reader = new FileReader(controller.getPathHtml() + fileName);
        BufferedReader breader = new BufferedReader(reader);

        String line = null;
        while ((line = breader.readLine()) != null) {
            source += line + "\n";
        }
        breader.close();
        reader.close();

        return source;
    }
}
