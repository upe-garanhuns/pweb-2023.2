package br.upe.garanhus.esw.pweb.modelo;

public class AplicacaoException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  
  private final int codigoErro;
  
  // Esse construtor serve apenas para criar um objeto que é passado 
  // como causa quando o ID informado em uma requisição é inválido.
  public AplicacaoException() {
    this.codigoErro = 0;
  }

  public AplicacaoException(int codigoErro, String msg, Throwable cause) {
    super(msg, cause);
    this.codigoErro = codigoErro;
  }

  public int getCodigoErro() {
    return codigoErro;
  }
  
}
