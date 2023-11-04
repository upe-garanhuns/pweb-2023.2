package br.upe.garanhus.esw.pweb.model;

public class NoCatFoundException extends AplicacaoException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public NoCatFoundException() {
    super();
    // TODO Auto-generated constructor stub
  }

  public NoCatFoundException(String message, int httpStatusCode) {
    super(message, httpStatusCode);
    // TODO Auto-generated constructor stub
  }

  public NoCatFoundException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace, int httpStatusCode) {
    super(message, cause, enableSuppression, writableStackTrace, httpStatusCode);
    // TODO Auto-generated constructor stub
  }

  public NoCatFoundException(String message, Throwable cause, int httpStatusCode) {
    super(message, cause, httpStatusCode);
    // TODO Auto-generated constructor stub
  }

  public NoCatFoundException(String message, Throwable cause) {
    super(message, cause);
    // TODO Auto-generated constructor stub
  }

  public NoCatFoundException(String message) {
    super(message);
    // TODO Auto-generated constructor stub
  }

  public NoCatFoundException(Throwable cause) {
    super(cause);
    // TODO Auto-generated constructor stub
  }

}
