package br.upe.garanhus.esw.pweb.model;

public class AplicacaoException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private int httpStatusCode;

  public AplicacaoException() {
    super();
  }

  public AplicacaoException(String message) {
    super(message);
  }

  public AplicacaoException(Throwable cause) {
    super(cause);
  }

  public AplicacaoException(String message, Throwable cause) {
    super(message, cause);
  }

  public AplicacaoException(String message, int httpStatusCode) {
    super(message);
    this.httpStatusCode = httpStatusCode;
  }

  public AplicacaoException(String message, Throwable cause, int httpStatusCode) {
    super(message, cause);
    this.httpStatusCode = httpStatusCode;
  }

  public AplicacaoException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace, int httpStatusCode) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.httpStatusCode = httpStatusCode;
  }

  public Integer getHttpStatusCode() {
    return httpStatusCode;
  }
}
