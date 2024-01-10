package br.uniceub.fsw.framework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 * Classe responsavel para se a interface de arquivos
 * Classe Restrita interna do framework
 * @author Tiago
 */
class Documentos {

    protected static Controller controller;
    private static String usuariosXML = "/WEB-INF/usuarios.xml";
    private static String menuXML = "/WEB-INF/menu.xml";

    public static void setAmbiente(Controller conroller) {
        Documentos.controller = conroller;
    }

    /**
     * Carrega o arquivo html
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String getHtmlFile(String fileName) throws Exception {
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

    /**
     * Metodo responsavel por carregar todos os Usuarios
     * @return
     * @throws Exception 
     */
    public static List<Usuario> loadUsuarios() throws Exception {
        List<Usuario> users = new ArrayList<Usuario>();
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(controller.getPath() + usuariosXML);
        Element docele = doc.getDocumentElement();
        NodeList nl = docele.getElementsByTagName("Usuario");
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {
                Element el = (Element) nl.item(i);
                users.add(getUsuario(el));
            }
        }
        return users;
    }

    /**
     * Metodo responsavel por montar o menu da pagina
     * @return
     * @throws Exception 
     */
    public static List<Menu> loadMenu() throws Exception {
        List<Menu> itens = new LinkedList<Menu>();

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(controller.getPath() + menuXML);
        Element docele = doc.getDocumentElement();
        NodeList nl = docele.getElementsByTagName("Menu");
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {
                Element el = (Element) nl.item(i);
                itens.add(getMenu(el));
            }
        }
        return itens;

    }

    /**
     * Metodo responsavem por montar cada grupo de menus
     * @param e
     * @return 
     */
    private static Menu getMenu(Element e) {
        Menu m = new Menu();
        m.setNome(getTextAtribute(e, "nome"));
        m.setPermissao(Arrays.asList(getTextAtribute(e, "permissao").split(",")));
        m.setUrl(getTextAtribute(e, "url"));
        List<Menu> itens = new LinkedList<Menu>();
        if (e.hasChildNodes()) {
            NodeList nl = e.getElementsByTagName("SubMenu");
            for (int i = 0; i < nl.getLength(); i++) {
                Element el = (Element) nl.item(i);
                Menu s = new Menu();
                s.setNome(getTextAtribute(el, "nome"));
                s.setPermissao(Arrays.asList(getTextAtribute(el, "permissao").split(",")));
                s.setUrl(getTextAtribute(el, "url"));
                s.setOpcoes(Collections.EMPTY_LIST);
                itens.add(s);
            }
        }
        m.setOpcoes(itens);
        return m;
    }

    /**
     * Encontra o Valor do tipo texto entre tags do nome expecificado
     * Exemplo: <tag>texto</tag>
     */
    private static String getTextValue(Element ele, String tagName) {
        String textVal = "";
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            if (el.hasChildNodes()) {
                textVal = el.getFirstChild().getNodeValue();
            }
        }
        return textVal;
    }

    /**
     * Encontra o Valor do topo int entre tags do nome expecificado
     * Exemplo: <tag>numero</tag>
     */
    private static int getIntValue(Element ele, String tagName) {
        int i = 0;
        try {
            i = Integer.parseInt(getTextValue(ele, tagName));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * Encontra o Valor do topo texto dentro do atributo da tag do nome expecificado
     * Exemplo: <tag>texto</tag>
     */
    private static String getTextAtribute(Element ele, String atrName) {
        return ele.getAttribute(atrName);
    }

    /**
     * Metodo responsavel por montar cada Ususario
     */
    private static Usuario getUsuario(Element el) {
        return new Usuario(getTextValue(el, "nome"), getTextValue(el, "senha"), Arrays.asList(getTextValue(el, "perfil").split(",")));
    }
}
