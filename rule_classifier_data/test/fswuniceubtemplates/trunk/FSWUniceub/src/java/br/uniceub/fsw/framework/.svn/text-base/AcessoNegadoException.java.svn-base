package br.uniceub.fsw.framework;

/**
 * Classe usada para marcar que algum metodo da action nao pode ser acessado pelo atual usuario
 * @author Tiago
 */
public class AcessoNegadoException extends Exception {    
    
    private Throwable ex;

    public AcessoNegadoException() {
        super((Throwable) null);
    }

    public AcessoNegadoException(String mensagem) {
        super(mensagem, null);
    }

    public AcessoNegadoException(String mensagem, Throwable ex) {
        super(mensagem, null);
        this.ex = ex;
    }

    public Throwable getException() {
        return ex;
    }

    @Override
    public Throwable getCause() {
        return ex;
    }
}
