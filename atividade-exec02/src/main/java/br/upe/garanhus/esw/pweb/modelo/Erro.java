package br.upe.garanhus.esw.pweb.modelo;

public class Erro {

  private int codigo;

  private String mensagem;

  private String detalhe;

  public Erro() {}

  public int getCodigo() {
    return codigo;
  }

  public void setCodigo(int codigo) {
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
