/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uniceub.fsw.action;

import br.uniceub.fsw.framework.AcessoNegadoException;
import br.uniceub.fsw.framework.Action;

/**
 *
 * @author Tiago
 */
public class Geral extends Action {

    /**
     * Nesta classe teoricamente todos podem acessa-la
     * @param nomeMetodo
     * @throws AcessoNegadoException
     * @throws Exception 
     */
    @Override
    public void invocar(String nomeMetodo) throws AcessoNegadoException, Exception {
        invocar(nomeMetodo, true);
    }

    /**
     * Construtor da pagina: login
     */
    public void login() {
    }

    /**
     * Construtor da pagina: inicio
     */
    public void inicio() {
    }
}
