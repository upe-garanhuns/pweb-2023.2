package br.upe.garanhus.esw.pweb.model;

public class AplicacaoException extends RuntimeException {
    private final int codigo;
    private final String detalhe;

    public AplicacaoException(int codigo, String mensagem, String detalhe) {
        super(mensagem);
        this.codigo = codigo;
        this.detalhe = detalhe;
    }

    public AplicacaoException(int codigo, String mensagem, Throwable ex) {
        super(mensagem, ex);
        this.codigo = codigo;
        this.detalhe = getCause().getClass().getName();
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDetalhe() {
        return detalhe;
    }
}
