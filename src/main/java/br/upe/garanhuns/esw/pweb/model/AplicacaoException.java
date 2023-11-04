package br.upe.garanhuns.esw.pweb.model;

public class AplicacaoException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public AplicacaoException() {
		
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
	
	public AplicacaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
