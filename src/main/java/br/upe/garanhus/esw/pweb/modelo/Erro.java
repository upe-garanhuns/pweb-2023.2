package br.upe.garanhus.esw.pweb.modelo;

import jakarta.json.bind.annotation.JsonbProperty;

public class Erro {

  @JsonbProperty("codigo")
  private int codigo;
  @JsonbProperty("mensagem")
  private String mensagem;
  @JsonbProperty("detalhe")
  private String detalhe;
  
  public Erro(int codigo, String mensagem, String detalhe) {
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
  
  public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
  }

  public String getDetalhe() {
    return detalhe;
  }
  
}
