/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uniceub.prj.action;

import br.uniceub.fsw.framework.*;

/**
 *
 * @author Tiago
 */
public class Exemplos extends Action {

    /**
     * Exemplo de como utilizar mensagens vinda da Action
     */
    public void mensagens() {
        //Exemplo de uso de migalhas
        addMigalha("Home", "Controller?map=Index.inicio");
        addMigalha("Exemplos");
        //Exemplo de recepsao de dados do formulario
        String msg = request.getParameter("pMensagemText");
        String tipo = request.getParameter("pTipoMensagem");
        if (tipo != null && !tipo.isEmpty()) {
            if ("Info".startsWith(tipo)) {
                //Exemplo de envio de mensagem do tipo Informativo
                addInfoMessage(msg);
            } else if ("Error".startsWith(tipo)) {
                //Exemplo de envio de mensagem do tipo Erro
                addErrorMessage(msg);
            } else if ("Notice".startsWith(tipo)) {
                //Exemplo de envio de mensagem do tipo Atencao
                addNoticeMessage(msg);
            } else if ("Success".startsWith(tipo)) {
                //Exemplo de envio de mensagem do tipo Sucesso
                addSuccessMessage(msg);
            }
        }
    }
}
