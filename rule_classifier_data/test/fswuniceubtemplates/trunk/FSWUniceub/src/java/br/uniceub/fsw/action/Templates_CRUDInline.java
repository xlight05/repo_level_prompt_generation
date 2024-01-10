/*
 * To change this template, choose Tools | Templates_CRUDCompleto
 * and open the template in the editor.
 */
package br.uniceub.fsw.action;

import br.uniceub.fsw.framework.Action;

/**
 *
 * @author Tiago
 */
public class Templates_CRUDInline extends Action {   

    public void listar() {
        //Obrigatorio para qualquer crud
        //Aqui fica o codigo de geracao da tabela mostrando todos os dados necessarios
        addMigalha("Lista de Perfil");
    }

    public void novo() {
        //No CRUD em linha esta parte é opcional e não recomendada
        //Aqui fica o codigo de geracao do formulario
    }

    public void incluir() {
        // Obrigatorio para qualquer crud, porem pode exister restricoes de uso
        //Aqui fica o codigo de inclusao dos dados no banco ou computacao necessaria
    }

    public void editar() {
        //No CRUD em linha esta parte é opcional e não recomendada
        //Aqui fica o codigo de  geracao do formulario de alteracao
    }
    
    public void alterar() {
        // Obrigatorio para qualquer crud, porem pode exister restricoes de uso
        //Aqui fica o codigo de alteracao dos dados no banco
    }

    public void excluir() {
        // Obrigatorio para qualquer crud, porem pode exister restricoes de uso
        //Aqui fica o codigo de exclusao dos dados no banco 
    }
}
