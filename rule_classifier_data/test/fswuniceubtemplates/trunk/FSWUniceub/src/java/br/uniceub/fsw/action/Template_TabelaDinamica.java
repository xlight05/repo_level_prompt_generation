/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uniceub.fsw.action;

import br.uniceub.fsw.framework.Action;
import br.uniceub.fsw.templates.ListaValores;
import br.uniceub.fsw.templates.Tabela;

/**
 *
 * @author Tiago
 */
public class Template_TabelaDinamica extends Action {

    public void formulario() {
    }

    public void listar() {
        String[] codAlunos = parametroStrings("codAluno");
        String[] codMaterias = parametroStrings("codMateria");
        String[] numNotas = parametroStrings("numNota");
        Tabela tab = new Tabela();
        tab.addCabecalho(0, "Codigo Aluno");
        tab.addCabecalho(1, "Codigo Materia");
        tab.addCabecalho(2, "Nota");
        if (codAlunos != null ) {
            for (int i = 0; i < codAlunos.length; i++) {
                tab.add(0, i + 1, codAlunos[i]);
                tab.add(1, i + 1, codMaterias[i]);
                tab.add(2, i + 1, numNotas[i]);
            }
        }
        inserir("TAB_RESULTADOS", tab.getHTML("", ""));
    }

    public void lvAluno() {
        String filtro = parametroString("filtroLV");
        ListaValores lv = new ListaValores("alunos", "nolistStyle");
        lv.addLinha(1, "Tiago");
        lv.addLinha(2, "Lucas");
        lv.addLinha(3, "Rafael");
        lv.addLinha(4, "Maria");
        lv.addLinha(5, "Nicole");
        lv.addLinha(6, "João");
        lv.addLinha(7, "Carolina");
        lv.addLinha(8, "Luiza");
        lv.addLinha(9, "Saulo");
        lv.addLinha(10, "Bruno");
        lv.addLinha(11, "Vitor");
        lv.addLinha(12, "Elias");
        inserir("LISTA_VALORES_ALUNO", lv.getHTML(filtro));
    }

    public void lvMateria() {
        String filtro = parametroString("filtroLV");
        Integer area = parametroInteiro("codArea");
        ListaValores lv = new ListaValores("materia", "nolistStyle");

        if (area == null || area == 0) {
            lv.addLinha(1, "Matematica");
            lv.addLinha(2, "Quimica");
            lv.addLinha(3, "Fisica");
            lv.addLinha(4, "Portugues");
            lv.addLinha(5, "Ingles");
            lv.addLinha(6, "Sociologia");
            lv.addLinha(7, "Artes");
            lv.addLinha(8, "Geografia");
            lv.addLinha(9, "Historia");
            lv.addLinha(10, "Teatro");
            lv.addLinha(11, "Musica");
            lv.addLinha(12, "Educação fisica");
            lv.addLinha(13, "Biologia");
        } else if (area == 1) {
            lv.addLinha(1, "Matematica");
            lv.addLinha(2, "Quimica");
            lv.addLinha(3, "Fisica");
        } else if (area == 2) {
            lv.addLinha(4, "Portugues");
            lv.addLinha(5, "Ingles");
            lv.addLinha(6, "Sociologia");
            lv.addLinha(7, "Artes");
            lv.addLinha(8, "Geografia");
            lv.addLinha(9, "Historia");
            lv.addLinha(10, "Teatro");
            lv.addLinha(11, "Musica");
        } else {
            lv.addLinha(12, "Educação fisica");
            lv.addLinha(13, "Biologia");
        }
        inserir("LISTA_VALORES_MATERIA", lv.getHTML(filtro));
    }

    public void lvAreaConhecimento() {
        String filtro = parametroString("filtroLV");
        ListaValores lv = new ListaValores("area", "nolistStyle");
        lv.addLinha(0, "Todas");
        lv.addLinha(1, "Exatas");
        lv.addLinha(2, "Humanas");
        lv.addLinha(3, "Biologicas");

        inserir("LISTA_VALORES_AREACONHECIEMNTO", lv.getHTML(filtro));
    }
}
