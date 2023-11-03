package br.upe.garanhus.esw.pweb.modelo;

public class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	  private int httpStatusCode;

	  public ApplicationException() {
	    super();
	  }

	  public ApplicationException(String message) {
	    super(message);
	  }

	  public ApplicationException(Throwable cause) {
	    super(cause);
	  }

	  public ApplicationException(String message, Throwable cause) {
	    super(message, cause);
	  }

	  public ApplicationException(String message, int httpStatusCode) {
	    super(message);
	    this.httpStatusCode = httpStatusCode;
	  }

	  public ApplicationException(String message, Throwable cause, int httpStatusCode) {
	    super(message, cause);
	    this.httpStatusCode = httpStatusCode;
	  }

	  public ApplicationException(String message, Throwable cause, boolean enableSuppression,
	      boolean writableStackTrace, int httpStatusCode) {
	    super(message, cause, enableSuppression, writableStackTrace);
	    this.httpStatusCode = httpStatusCode;
	  }

	  public Integer getHttpStatusCode() {
	    return httpStatusCode;
	  }
}
