package br.upe.garanhus.esw.pweb.controle;

public class AplicacaoException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private final int codigo;
  private final String mensagem;
  private final String detalhe;

  public AplicacaoException(int codigo, String mensagem, String detalhe, Throwable causa) {
    super(causa);
    this.codigo = codigo;
    this.mensagem = mensagem;
    this.detalhe = detalhe;
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
