package br.upe.garanhus.esw.pweb;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

public class AplicacaoException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private int codigo;
	private String mensagem;
	private String detalhe;
	
	private static final String SERVLET_EXCEPTION_MESSAGE = "Erro na execução do servlet.";
	private static final String IO_EXCEPTION_MESSAGE = "Erro de entrada/saída.";
	private static final String INTERRUPTED_EXCEPTION_MESSAGE = "Erro de Thread interrompida.";
	private static final String ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE = "Erro de Argumento inválido.";
	private static final String SECURITY_EXCEPTION_MESSAGE = "Erro de segurança.";
	private static final String URI_SYNTAX_MESSAGE = "Erro ao analisar o Identificador de Recurso Uniforme (URI)";

	private static final Logger logger = Logger.getLogger(AplicacaoException.class.getName());

	public String capturaExcecao(Exception e, HttpServletResponse resp) {
		codigo = resp.getStatus();
		detalhe = e.getMessage();
		
		if (e instanceof ServletException) {
			mensagem = SERVLET_EXCEPTION_MESSAGE;
		} else if (e instanceof IOException) {
			mensagem = IO_EXCEPTION_MESSAGE;
		} else if (e instanceof InterruptedException) {
			mensagem = INTERRUPTED_EXCEPTION_MESSAGE;
		} else if (e instanceof IllegalArgumentException) {
			mensagem = ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE;
		} else if (e instanceof SecurityException) {
			mensagem = SECURITY_EXCEPTION_MESSAGE;
		} else if (e instanceof URISyntaxException) {
			mensagem = URI_SYNTAX_MESSAGE;
		}
		String jsonErroMsg = "{\"codigo\": " + codigo + ", \"mensagem\": \"" + mensagem + "\", \"detalhe\": \"" + detalhe + "\"}";
		return jsonErroMsg;
	}
}
