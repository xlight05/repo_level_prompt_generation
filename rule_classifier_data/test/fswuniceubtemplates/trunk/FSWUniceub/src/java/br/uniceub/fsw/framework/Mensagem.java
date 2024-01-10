package br.uniceub.fsw.framework;

import java.util.*;

/**
 * Classe Responsavel por gerenciar as mensagens a serem exibidas na pagina
 * Classe Restrita interna do framework
 * @author Tiago
 */
class Mensagem {

    private List<Msg> msgs = new ArrayList<Msg>();

    /**
     * Adiciona uma mensagem a lista de mensagens a ser mostrada na pagina
     * @param message
     */
    public void add(Tipo tipoMensagem, String message) {
        msgs.add(new Msg(tipoMensagem, message));
    }

    /**
     * Adiciona uma mensagem a lista de mensagens a ser mostrada na pagina
     * Com o tipo padrao de Informativo
     * @param message
     */
    public void add(String message) {
        msgs.add(new Msg(Tipo.Notice, message));
    }

    /**
     * Gera o html necessario para mostar todas mensagens na pagina
     * a lista e esvasiada apos o uso desse metodo
     * @return 
     */
    public String getHTML() {
        StringBuilder sb = new StringBuilder("<div id='mensagens'>");
        for (Msg message : msgs) {
            sb.append(message.toString());
        }
        msgs.clear();
        return sb.append("</div>").toString();
    }

    /**
     * Invoca getHTML() internamente
     * @return
     */
    @Override
    public String toString() {
        return this.getHTML();
    }

    /**
     * Classe individual da mensagem
     */
    private class Msg {

        private Tipo type;
        private String content = "";

        /**
         * Construtor unico para mensagens
         * @param type
         * @param content
         */
        public Msg(Tipo type, String content) {
            this.type = type;
            this.content = content;
        }

        /**
         * Gera html para cada mensagem individualmente
         * @return
         */
        public String getHTML() {
            if (content == null || content.isEmpty()) {
                return content;
            }
            return "<div class='" + type.toString().toLowerCase() + "'>" + content + "</div>";
        }

        /**
         * Invoca getHTML() internamente
         * @return
         */
        @Override
        public String toString() {
            return this.getHTML();
        }
    }

    /**
     * Enum respomsavel por qualificar o tipo da mensagem, e diferencia sua forma de exibicao
     */
    public enum Tipo {

        Error,
        Notice,
        Info,
        Success,
        FrameworkError;
    }
}
