package modelo;

public class AplicacaoException extends RuntimeException {

  public AplicacaoException(String mensagem, Throwable causa) {
    super(mensagem, causa);
  }
}
