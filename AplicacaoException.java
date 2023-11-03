package br.upe.garanhus.esw.pweb.modelo;

public class AplicacaoException extends RuntimeException {
	    
	private static final long serialVersionUID = 1L;
		private int codigo;
	    private String mensagem;
	    private String detalhe;

	    public AplicacaoException(int codigo, String mensagem, String detalhe) {
	        this.codigo = codigo;
	        this.mensagem = mensagem;
	        this.detalhe = detalhe;
	       
	    }
	    public AplicacaoException() {
	        this.codigo = 500;
	        this.mensagem = "Erro interno da aplicação";
	        this.detalhe = "Causa raiz não especificada";
	    }

	    public int getCodigo() {
	        return codigo;
	    }

	    public String getMensagem() {
	        return mensagem;
	    }

	    public String getDetalhe() {
	        return detalhe;
	    }
	}

