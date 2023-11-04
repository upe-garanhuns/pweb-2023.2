package br.upe.garanhus.esw.pweb.modelo.DTO;

public class ErrorDTO {
	private Integer codigo;
	  private String mensagem;
	  private String detalhe;

	  public ErrorDTO() {}

	  public ErrorDTO(Integer codigo, String mensagem, String detalhe) {
	    this.codigo = codigo;
	    this.mensagem = mensagem;
	    this.detalhe = detalhe;
	  }

	  public Integer getCodigo() {
	    return codigo;
	  }

	  public void setCodigo(Integer codigo) {
	    this.codigo = codigo;
	  }

	  public String getMensagem() {
	    return mensagem;
	  }

	  public void setMensagem(String mensagem) {
	    this.mensagem = mensagem;
	  }

	  public String getDetalhe() {
	    return detalhe;
	  }

	  public void setDetalhe(String detalhe) {
	    this.detalhe = detalhe;
	  }
}
