/*
 * To change this template, choose Tools | Templates_CRUDCompleto
 * and open the template in the editor.
 */
package br.uniceub.fsw.action;

import br.uniceub.fsw.framework.Action;
import br.uniceub.fsw.templates.ListaValores;

/**
 *
 * @author Tiago
 */
public class Templates_CRUDCompleto extends Action {

    public void listar() {
        //Obrigatorio para qualquer crud
        //Aqui fica o codigo de geracao da tabela mostrando todos os dados necessarios
        addMigalha("Lista de Usuarios");
    }

    public void novo() {
        //Obrigatorio para CRUD Completo
        //Aqui fica o codigo de geracao do formulario
        addMigalha("Usuarios", "Templates_CRUDCompleto.listar");
        addMigalha("Novo Usuario");

        inserir("IDT_USUARIO", "");
        inserir("NME_USUARIO", "");
        inserir("USR_LOGIN", "");
        inserir("PWD_LOGIN", "");
        inserir("URL_USUARIO", "");
        inserir("END_BLOCO", "");
        inserir("END_SALA", "");
        inserir("TEL_RAMAL", "");
        inserir("OBS_USUARIO", "");
        inserir("COD_CONHECIMENTO","");
        inserir("NME_CONHECIMENTO", "");

        inserir("ACAO_FORMULARIO", "Templates_CRUDCompleto.incluir.Templates_CRUDCompleto.listar");
        inserir("TIPO_FORMULARIO", "Criar novo Usuario");
    }

    public void incluir() {
        //Obrigatorio para qualquer crud, porem pode exister restricoes de uso
        //Aqui fica o codigo de inclusao dos dados no banco ou computacao necessaria
    }

    public void editar() {
        //Obrigatorio para CRUD Completo        
        //Aqui fica o codigo de  geracao do formulario de alteracao
        String parameter = parametroString("idtUsuario");
        addMigalha("Usuarios", "Templates.listar");
        addMigalha("Editar Usuario");

        inserir("IDT_USUARIO", "2");
        inserir("NME_USUARIO", "Fulano");
        inserir("USR_LOGIN", "fulano");
        inserir("PWD_LOGIN", "");
        inserir("URL_USUARIO", "fulano@gmail.com");
        inserir("END_BLOCO", "Sul");
        inserir("END_SALA", "11");
        inserir("TEL_RAMAL", "1465");
        inserir("OBS_USUARIO", "");
        inserir("COD_CONHECIMENTO","6");
        inserir("NME_CONHECIMENTO", "Superior completo");

        inserir("ACAO_FORMULARIO", "Templates_CRUDCompleto.alterar.Templates_CRUDCompleto.listar");
        inserir("TIPO_FORMULARIO", "Editar Usuario");
    }

    public void visualizar() {
        //Recomendavel para CRUD Completo
        //Agui fica o codigo para visializacao simples todos dados em formato organizado
        String parameter = parametroString("idtUsuario");
        addMigalha("Usuarios", "Templates_CRUDCompleto.listar");
        addMigalha("Visializar Usuario");

        inserir("IDT_USUARIO", "2");
        inserir("NME_USUARIO", "Fulano");
        inserir("USR_LOGIN", "fulano");
        inserir("PWD_LOGIN", "");
        inserir("URL_USUARIO", "fulano@gmail.com");
        inserir("END_BLOCO", "Sul");
        inserir("END_SALA", "11");
        inserir("TEL_RAMAL", "1465");
        inserir("OBS_USUARIO", "");
        inserir("STATUS", "Ativo");
        inserir("PERMISSOES", "Desenvolvedor, Funcionário");
        inserir("TIPO_PAGINA", "Visualizar Usuário");
    }

    public void alterar() {
        //Obrigatorio para qualquer crud, porem pode exister restricoes de uso
        //Aqui fica o codigo de alteracao dos dados no banco
    }

    public void excluir() {
        //Obrigatorio para qualquer crud, porem pode exister restricoes de uso
        //Aqui fica o codigo de exclusao dos dados no banco 
    }

    public void lvConhecimento() {
        String filtro = parametroString("filtroLV");
        //Nome to parametro é fixo caso use a classe ListaValores para gera a lv
        ListaValores lv = new ListaValores("opcoesLV", "nolistStyle");
        lv.addLinha(1, "Fundamental incompleto");
        lv.addLinha(2, "Fundamental completo");
        lv.addLinha(3, "Medio incompleto");
        lv.addLinha(4, "Medio completo");
        lv.addLinha(5, "Superior incompleto");
        lv.addLinha(6, "Superior completo");
        lv.addLinha(7, "Pro-Graduado");
        lv.addLinha(8, "Mestrado");
        lv.addLinha(9, "Doutorado");
        lv.addLinha(10, "Pos-Doutorado");
        inserir("LV_COMNHECIMENTO", lv.getHTML(filtro));        
    }
}
