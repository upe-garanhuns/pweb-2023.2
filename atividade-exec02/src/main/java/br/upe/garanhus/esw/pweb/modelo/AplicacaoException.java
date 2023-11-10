package br.upe.garanhus.esw.pweb.modelo;

public class AplicacaoException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public AplicacaoException() {
    super();
  }

  public AplicacaoException(String msg) {
    super(msg);
  }

}
