package br.uniceub.fsw.templates;

import java.util.*;

/**
 * Estrutura de dados em formato de tabela, utiliza um Map onde a Key = Posicao
 * e Value = T, Rezervando na matriz a linha 0 para cabeçalhos e a coluna 0
 * para cabeçalhos laterais
 * @author Tiago
 */
public class Tabela {

    private TreeMap<Posicao, String> celulas = new TreeMap<Posicao, String>();
    private int col = 0, lin = 0;

//    /**
//     * Gera uma tabela partir de uma lista de listas
//     * @param table
//     * @return
//     */
//    public static <String extends CharSequence> Tabela<String> getTabela(List<List<String>> table) {
//        Tabela tab = new Tabela<String>();
//        int linha = 0, coluna = 0;
//        for (List<String> lin : table) {
//            for (String s : lin) {
//                tab.add(coluna++, linha, s);
//            }
//            linha++;
//            coluna = 0;
//        }
//        return tab;
//    }
    /**
     * Expoe a quantidade maxima conhecida de Colunas
     * @return
     */
    public int getQuantidadeColunas() {
        return col + 1;
    }

    /**
     * Expoe a quantidade maxima conhecida de Linhas
     * @return
     */
    public int getQuantidadeLinhas() {
        return lin + 1;
    }

    /**
     * Equivale ao canto que demonstra o tanaho da tabela, inferior direito.
     * @return
     */
    public Posicao size() {
        return new Posicao(col, lin);
    }

//    /**
//     * Cria uma nova tabela usando a tabela atual com o tamanho especificado ,com
//     * cabecalho, caso possua, usando o existente se a coluna n�o foi removida.
//     * Todos os numeros s�o inclusos. Use 0 para fimLinha e fimColuna, para
//     * sinalizar at� o fim.
//     * @param inicioLinha tabela come�a no 1, o cabe�alho � linha 0
//     * @param inicioColuna tabela come�a no 0
//     * @param fimLinha
//     * @param fimColuna
//     * @return
//     */
//    public Tabela resize(int inicioLinha, int fimLinha, int inicioColuna, int fimColuna) {
//        List<List<String>> outerList = new ArrayList<List<String>>();
//        for (int l = 0; l < getQuantidadeLinhas(); l++) {
//            if (l < inicioLinha || (l > fimLinha && fimLinha != 0)) {
//                continue;
//            }
//            List<String> innerList = new ArrayList<String>();
//            for (int c = 0; c < getQuantidadeColunas(); c++) {
//                if (c < inicioColuna || (c > fimColuna && fimColuna != 0)) {
//                    continue;
//                }
//                innerList.add(get(new Posicao(c, l)));
//            }
//            outerList.add(innerList);
//        }
//        Tabela tab = getTabela(outerList);
//        return tab;
//    }
    /**
     * Devolve o cabecalho da coluna especificada, retorna null caso n�o possua
     * @param coluna
     * @return
     */
    public String getCabecalho(int coluna) {
        return get(coluna, 0);
    }

    /**
     * Devolve a lista de cabecalhos
     * @param coluna
     * @return
     */
    public List<String> getCabecalhos() {
        return getLinha(0);
    }

    /**
     * Devolve a coluna inteira em formato de lista, a coluna 0 equivale aos
     * cabe�ahos laterais caso existam.
     * @param coluna
     * @return
     */
    public List<String> getColuna(int coluna) {
        List<String> li = new ArrayList<String>(getQuantidadeLinhas());
        for (Posicao p : celulas.keySet()) {
            if (p.coluna == coluna) {
                li.add(get(p));
            }
        }
        return li;
    }

    /**
     * Devolve a Linha inteira em formato de lista, linha 0 equivale aos
     * cabeçahos caso existam.
     * @param linha
     * @return
     */
    public List<String> getLinha(int linha) {
        List<String> li = new ArrayList<String>(getQuantidadeColunas());
        for (Posicao p : celulas.keySet()) {
            if (p.linha == linha) {
                li.add(get(p));
            }
        }
        return li;
    }

    /**
     * Retorna a celula relacionada a coluna e linahs especificadas
     * @param coluna
     * @param linha
     * @return
     */
    public String get(int coluna, int linha) {
        return get(new Posicao(coluna, linha));
    }

    /**
     * Retorna a celula relacionada a posicao especificada
     * @param p A posicao com a linah e coluna especificadas internamente
     * @return
     */
    protected String get(Posicao p) {
        return celulas.get(p);
    }

//    /**
//     * Torna os valores da coluna descrita em null, e devolve os valores previos
//     * @param coluna
//     * @return
//     */
//    public List<String> removeColuna(int coluna) {
//        List<String> li = getColuna(coluna);
//        for (Posicao p : celulas.keySet()) {
//            if (p.coluna == coluna) {
//                celulas.remove(p);
//            }
//        }
//        return li;
//    }
//
//    /**
//     * Torna os valores da linha descrita em null, e devole os valores previos
//     * @param linha
//     * @return
//     */
//    public List<String> removeLinha(int linha) {
//        List<String> li = getLinha(linha);
//        for (Posicao p : celulas.keySet()) {
//            if (p.linha == linha) {
//                celulas.remove(p);
//            }
//        }
//        return li;
//    }
    /**
     * Adiciona ou subistitui o valor a posicao especificada
     * A linha 0 deve ser reservada para os cabecalhos (opcional)
     * A Coluna 0 deve ser reservada para os cabecalhos laterais (opcional)
     * @param p Posicao contendo linah e coluna
     * @param valor
     * @return
     */
    protected String add(Posicao p, String valor) {
        if (p.coluna > col) {
            col = p.coluna;
        }
        if (p.linha > lin) {
            lin = p.linha;
        }
        return celulas.put(p, valor);
    }

    /**
     * Adicona ou subistitui o valor na coluan e linha especificada
     * A linha 0 deve ser reservada para os cabecalhos (opcional)
     * A Coluna 0 deve ser reservada para os cabecalhos laterais (opcional)
     * @param coluna
     * @param linha
     * @param valor
     * @return
     */
    public String add(int coluna, int linha, String valor) {
        return add(new Posicao(coluna, linha), valor);
    }

    /**
     * Adiciona ou subistitui o valor no cabechalho da coluna especificada
     * @param coluna
     * @param valor
     * @return 
     */
    public String addCabecalho(int coluna, String valor) {
        return add(new Posicao(coluna, 0), valor);
    }

    /**
     * Adiciona ou subistitui uma linha inteira a tabela
     * @param linha
     * @param valores
     * @return
     */
    public List<String> addLinha(int linha, List<String> valores) {
        List<String> res = new ArrayList<String>(getQuantidadeColunas());
        int i = 0;
        for (ListIterator<String> it = valores.listIterator(); it.hasNext();) {
            String s = it.next();
            res.add(add(i++, linha, s));
        }
//        for (int j = res.size(); j <= getQuantidadeColunas(); j++) {
//            res.add(add(j, linha, null));
//        }
        return res;
    }

    /**
     * Adiciona ou subistitui um coluna inteira a tabela
     * @param coluna
     * @param valores
     * @return
     */
    public List<String> addColuna(int coluna, List<String> valores) {
        List<String> res = new ArrayList<String>(getQuantidadeLinhas());
        for (int i = 1; i <= getQuantidadeLinhas(); i++) {
            res.add(add(coluna, i, valores.get(i)));
        }
        return res;
    }

    /**
     * Limpa a tabela de todos os seus valores
     */
    public void clear() {
        celulas.clear();
    }

    /**
     * Retorna true se a tabela estiver vazia, cabecalho n�o conta como conteudo
     * @return
     */
    public boolean isEmpty() {
        return size().linha < 1;
    }

    /**
     * Encontra as linhas que possuem somente valores nulos,
     * nao ignorando cabecalhos
     * @return
     */
    private Set<Integer> encontrarLinhasNulas() {
        Set<Integer> linhas = new HashSet<Integer>();
        for (int l = 0; l < getQuantidadeLinhas(); l++) {
            Boolean b = true;
            for (int c = 0; c < getQuantidadeColunas(); c++) {
                if (get(c, l) != null) {
                    b = false;
                    break;
                }
            }
            if (b) {
                linhas.add(l);
            }

        }
        return linhas;
    }

    /**
     * Encontra as linhas que possuem somente valores nulos,
     *  nao ignorando cabecalhos
     * @return
     */
    private Set<Integer> encontrarColunasNulas() {
        Set<Integer> colunas = new HashSet<Integer>();
        for (int c = 0; c < getQuantidadeColunas(); c++) {
            Boolean b = true;
            for (int l = 0; l < getQuantidadeLinhas(); l++) {
                if (get(c, l) != null) {
                    b = false;
                    break;
                }
            }
            if (b) {
                colunas.add(c);
            }
        }
        return colunas;
    }

    /**
     * De a tabela me formato TXString pronto pra ser salvo por TXTDocument
     * Com cabecalhos
     * @return
     */
    @Override
    public String toString() {
        return toLists().toString().replace("], [", "\n").replace("[", "").replace("]", "").replace(", ", "\t");
    }

    /**
     * Trasforma a tabela me Lista de listas, para ser aproveita do por
     * Domunetos e ser salvos no formato que desejar.
     * Linhas e colunas nulas s�o removidas, ignorando cabe�alhos
     * @param cabecalho Incluir cabecalhos de colunas n�o nulas
     * @return
     */
    public List<List<String>> toLists() {
        return toLists(false);
    }

    /**
     * Trasforma a tabela me Lista de listas, para ser aproveita do por
     * Domunetos e ser salvos no formato que desejar.
     * @param removerNulos true se quiser remover linhas e colunas completamente vazias até mesmo os cabeçalhos
     * @return 
     */
    public List<List<String>> toLists(Boolean removerNulos) {
        Set<Integer> linhas = null, colunas = null;
        if (removerNulos) {
            linhas = encontrarLinhasNulas();
            colunas = encontrarColunasNulas();
        }
        List<List<String>> outerList = new ArrayList<List<String>>();
        for (int l = 0; l < getQuantidadeLinhas(); l++) {
            if (removerNulos && linhas.contains(l)) {
                continue;
            }
            List<String> innerList = new ArrayList<String>();
            for (int c = 0; c < getQuantidadeColunas(); c++) {
                if (removerNulos && colunas.contains(c)) {
                    continue;
                }
                innerList.add(get(c, l));
            }
            outerList.add(innerList);
        }
        return outerList;
    }

    /**
     * Gera uma tabela em html com os dados desta estrutura
     * @param id da tabela
     * @param classes da tabela
     * @return 
     */
    public String getHTML(String id, String classes) {
        return getHTML(id, classes, false);
    }

    /**
     * Gera uma tabela em html com os dados desta estrutura
     * @param id da tabela
     * @param classes da tabela
     * @param removerNulos true se quiser remover linhas e colunas completamente vazias até mesmo os cabeçalhos
     * @return 
     */
    public String getHTML(String id, String classes, Boolean removerNulos) {
        Set<Integer> linhas = null, colunas = null;
        if (removerNulos) {
            linhas = encontrarLinhasNulas();
            colunas = encontrarColunasNulas();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<table id='").append(id).append("' class='").append(classes).append("'>\n");
        {
            sb.append("\t<thead>\n");
            {
                sb.append("\t\t<tr>\n");
                for (int c = 0; c < getQuantidadeColunas(); c++) {
                    if (removerNulos && colunas.contains(c)) {
                        continue;
                    }
                    String s = getCabecalho(c);
                    sb.append("\t\t\t<th>").append(s).append("</th>\n");
                }
                sb.append("\t\t</tr>\n");
            }
            sb.append("\t</thead>\n");
            sb.append("\t<tbody>\n");
            {
                for (int l = 1; l < getQuantidadeLinhas(); l++) {
                    if (removerNulos && linhas.contains(l)) {
                        continue;
                    }
                    sb.append("\t\t<tr>\n");
                    for (int c = 0; c < getQuantidadeColunas(); c++) {
                        if (removerNulos && colunas.contains(c)) {
                            continue;
                        }
                        String s = get(c, l);
                        sb.append("\t\t\t<td>").append(s).append("</td>\n");
                    }
                    sb.append("\t\t</tr>\n");
                }
            }
            sb.append("\t</tbody>\n");
        }
        sb.append("</table>");
        return sb.toString();
    }

    /**
     * A classe Posicao � utilizada como objeto key para o mapa que interno
     * a Tabela, contem apenas linah e coluna que s�o imultaveis
     */
    protected class Posicao implements Comparable<Posicao> {

        public final int coluna, linha;

        /**
         * Contrutor padr�o
         * @param coluna
         * @param linha
         */
        public Posicao(int coluna, int linha) {
            this.coluna = coluna;
            this.linha = linha;
        }

        /**
         * Usado para ordera o mapa da tabela
         * @param o
         * @return
         */
        @Override
        public int compareTo(Posicao o) {
            int result = 0;
            result = o.linha - this.linha;
            if (result == 0) {
                result = o.coluna - this.coluna;
            }
            return (int) Math.signum(result);
        }
    }

    /**
     * Somente para testes
     * @param args
     */
    public static void main(String[] args) {
        Tabela t = new Tabela();
        //Cabecalhos
        System.out.print(t.add(3, 0, "test") + " ");
        System.out.print(t.add(4, 0, "intext") + " ");
        System.out.print(t.add(1, 0, "manife") + " ");
        System.out.print(t.add(2, 0, "suspi") + " ");
        System.out.println("\n-------------------");
        //Conteudo
        System.out.print(t.add(1, 1, "11") + " ");
        System.out.print(t.add(1, 2, "12") + " ");
        System.out.print(t.add(1, 4, "14") + " ");
        System.out.print(t.add(4, 1, "41") + " ");
        System.out.print(t.add(4, 2, "42") + " ");
        System.out.print(t.add(4, 4, "44") + " \n");
        System.out.println("\n-------------------");
        System.out.print(t.toString());
        System.out.println("\n-------------------");
        System.out.println(t.toLists(false));
        System.out.println("\n-------------------");
        System.out.println(t.getHTML("", "", true));
        System.out.println("\n-------------------");
        System.out.println(t.getHTML("", ""));
    }
}
