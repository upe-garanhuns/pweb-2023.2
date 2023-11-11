package br.upe.garanhus.esw.pweb.model.infra;

public class ApplicationException extends RuntimeException {

  private final Integer code;
  private final String errorMessage;
  private final Throwable exception;

  public ApplicationException(String errorMessage, Throwable exception, Integer code) {
    super();
    this.errorMessage = errorMessage;
    this.exception = exception;
    this.code = code;
  }

  @Override
  public synchronized Throwable getCause() {
    return exception;
  }

  @Override
  public String getMessage() {
    return errorMessage;
  }

  public Integer getCode() {
    return code;
  }
}
