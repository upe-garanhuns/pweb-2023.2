package br.upe.garanhus.esw.pweb.modelo;

import jakarta.json.bind.annotation.JsonbProperty;

public class Fonte {

  private String original;

  @JsonbProperty("large2x")
  private String grande2x;

  @JsonbProperty("large")
  private String grande;

  @JsonbProperty("medium")
  private String media;

  @JsonbProperty("small")
  private String pequena;

  @JsonbProperty("portrait")
  private String retrato;

  @JsonbProperty("landscape")
  private String paisagem;

  @JsonbProperty("tiny")
  private String pequena2x;

  public Fonte() {}

  public String getOriginal() {
    return original;
  }

  public void setOriginal(String original) {
    this.original = original;
  }

  public String getGrande2x() {
    return grande2x;
  }

  public void setGrande2x(String grande2x) {
    this.grande2x = grande2x;
  }

  public String getGrande() {
    return grande;
  }

  public void setGrande(String grande) {
    this.grande = grande;
  }

  public String getMedia() {
    return media;
  }

  public void setMedia(String media) {
    this.media = media;
  }

  public String getPequena() {
    return pequena;
  }

  public void setPequena(String pequena) {
    this.pequena = pequena;
  }

  public String getRetrato() {
    return retrato;
  }

  public void setRetrato(String retrato) {
    this.retrato = retrato;
  }

  public String getPaisagem() {
    return paisagem;
  }

  public void setPaisagem(String paisagem) {
    this.paisagem = paisagem;
  }

  public String getPequena2x() {
    return pequena2x;
  }

  public void setPequena2x(String pequena2x) {
    this.pequena2x = pequena2x;
  }

}


