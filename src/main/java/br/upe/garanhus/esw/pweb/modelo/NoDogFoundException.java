package br.upe.garanhus.esw.pweb.modelo;

public class NoDogFoundException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	  public NoDogFoundException() {
	    super();
	  }

	  public NoDogFoundException(String message, int httpStatusCode) {
	    super(message, httpStatusCode);
	  }

	  public NoDogFoundException(String message, Throwable cause, boolean enableSuppression,
	      boolean writableStackTrace, int httpStatusCode) {
	    super(message, cause, enableSuppression, writableStackTrace, httpStatusCode);
	  }

	  public NoDogFoundException(String message, Throwable cause, int httpStatusCode) {
	    super(message, cause, httpStatusCode);
	  }

	  public NoDogFoundException(String message, Throwable cause) {
	    super(message, cause);
	  }

	  public NoDogFoundException(String message) {
	    super(message);
	  }

	  public NoDogFoundException(Throwable cause) {
	    super(cause);
	  }

}
