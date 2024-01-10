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
class Mensagem {

    private Type type;
    private String content = "";
    private static List<Mensagem> msgs = new ArrayList<Mensagem>();

    /**
     * Adiciona uma mensagem a lista de mensagens a ser mostrada na pagina
     * @param message 
     */
    public static void add(Mensagem message) {
        msgs.add(message);
    }

    /**
     * Gera o html necessario para mostar todas mensagens na pagina
     * a lista e esvasiada apos o uso desse metodo
     * @return 
     */
    public static String getMessagesHtml() {
        StringBuilder sb = new StringBuilder();
        for (Mensagem message : msgs) {
            sb.append(message.toString());
        }
        msgs.clear();
        return sb.toString();
    }

    /**
     * Construtor unico para mensagens
     * @param type
     * @param content 
     */
    public Mensagem(Type type, String content) {
        this.type = type;
        this.content = content;
    }
    /**
     * Gera html para cada mensagem individualmente
     * @return 
     */
    @Override
    public String toString() {
        if ("".equals(content)) {
            return "";
        }
        return "<div class='" + type.toString().toLowerCase() + "'>" + content + "</div>";
    }
    /**
     * Enum respomsavel por qualificar o tipo da mensagem, e diferencia sua forma de exibicao
     */
    public enum Type {

        Error,
        Notice,
        Info,
        Success,
        FrameworkError;
    }
}
