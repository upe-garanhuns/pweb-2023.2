package br.upe.garanhus.esw.pweb.modelo;

public class RepositoryException extends RuntimeException {

    public RepositoryException(String mensagem) {
        super(mensagem);
    }

    public RepositoryException(String mensagem, Exception origem) {
        super(mensagem, origem);
    }
}
