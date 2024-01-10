package br.uniceub.fsw.framework;

import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 *
 * @author Tiago
 */
class Documentos {

    protected static String contextPath;

    /**
     * Metodo para carregar o caminho da aplicacao
     * @param path 
     */
    public static void setPath(String path) {
        Documentos.contextPath = path;
    }

    /**
     * Metodo responsavel por carregar todos os Usuarios
     * @return
     * @throws Exception 
     */
    public static List<Usuario> loadUsuarios() throws Exception {
        List<Usuario> users = new ArrayList<Usuario>();
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(contextPath + "\\" + "WEB-INF\\usuarios.xml");
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
    public static List<Mapeamento> loadMenu() throws Exception {
        List<Mapeamento> itens = new LinkedList<Mapeamento>();

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(contextPath + "\\" + "WEB-INF\\mapeamento.xml");
        Element docele = doc.getDocumentElement();
        NodeList nl = docele.getElementsByTagName("Grupo");
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
    private static Mapeamento getMenu(Element e) {
        Mapeamento m = new Mapeamento(getTextAtribute(e, "nome"), Arrays.asList(getTextAtribute(e, "permissao").split(",")));
        NodeList nl = e.getElementsByTagName("Item");
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {
                Element el = (Element) nl.item(i);
                m.addItem(getTextValue(el, "nome"),
                        getTextValue(el, "classe"),
                        getTextValue(el, "metodo"),
                        Arrays.asList(getTextValue(el, "permissao").split(",")));
            }
        }
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
