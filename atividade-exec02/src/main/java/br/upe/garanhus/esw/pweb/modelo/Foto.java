package br.upe.garanhus.esw.pweb.modelo;

import jakarta.json.bind.annotation.JsonbProperty;

public class Foto {

  @JsonbProperty("id")
  private int idFoto;

  @JsonbProperty("width")
  private int largura;

  @JsonbProperty("height")
  private int altura;

  private String url;

  @JsonbProperty("photographer")
  private String fotografo;

  @JsonbProperty("photographer_url")
  private String urlFotografo;

  @JsonbProperty("photographer_id")
  private int idFotografo;

  @JsonbProperty("avg_color")
  private String cor;

  @JsonbProperty("src")
  private Fonte fonteLinks;

  @JsonbProperty("liked")
  private boolean curtida;

  @JsonbProperty("alt")
  private String descricao;

  public Foto() {}

  public int getIdFoto() {
    return idFoto;
  }

  public void setIdFoto(int idFoto) {
    this.idFoto = idFoto;
  }

  public int getLargura() {
    return largura;
  }

  public void setLargura(int largura) {
    this.largura = largura;
  }

  public int getAltura() {
    return altura;
  }

  public void setAltura(int altura) {
    this.altura = altura;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getFotografo() {
    return fotografo;
  }

  public void setFotografo(String fotografo) {
    this.fotografo = fotografo;
  }

  public String getUrlFotografo() {
    return urlFotografo;
  }

  public void setUrlFotografo(String urlFotografo) {
    this.urlFotografo = urlFotografo;
  }

  public int getIdFotografo() {
    return idFotografo;
  }

  public void setIdFotografo(int idFotografo) {
    this.idFotografo = idFotografo;
  }

  public String getCor() {
    return cor;
  }

  public void setCor(String cor) {
    this.cor = cor;
  }

  public Fonte getOrigemLinks() {
    return fonteLinks;
  }

  public void setOrigemLinks(Fonte origemLinks) {
    this.fonteLinks = origemLinks;
  }

  public boolean isCurtida() {
    return curtida;
  }

  public void setCurtida(boolean curtida) {
    this.curtida = curtida;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }
}
