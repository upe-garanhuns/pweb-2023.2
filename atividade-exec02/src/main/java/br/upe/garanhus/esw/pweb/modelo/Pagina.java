package br.upe.garanhus.esw.pweb.modelo;

import java.util.List;
import jakarta.json.bind.annotation.JsonbProperty;

public class Pagina {

  @JsonbProperty("page")
  private int qtdPagina;

  @JsonbProperty("per_page")
  private int porPagina;

  @JsonbProperty("photos")
  private List<Foto> fotos;

  @JsonbProperty("total_results")
  private int resultadosTotais;

  @JsonbProperty("next_page")
  private String proximaPagina;

  public Pagina() {}

  public int getQtdPagina() {
    return qtdPagina;
  }

  public void setQtdPagina(int qtdPagina) {
    this.qtdPagina = qtdPagina;
  }

  public int getPorPagina() {
    return porPagina;
  }

  public void setPorPagina(int porPagina) {
    this.porPagina = porPagina;
  }

  public List<Foto> getFotos() {
    return fotos;
  }

  public void setFotos(List<Foto> fotos) {
    this.fotos = fotos;
  }

  public int getResultadosTotais() {
    return resultadosTotais;
  }

  public void setResultadosTotais(int resultadosTotais) {
    this.resultadosTotais = resultadosTotais;
  }

  public String getProximaPagina() {
    return proximaPagina;
  }

  public void setProximaPagina(String proximaPagina) {
    this.proximaPagina = proximaPagina;
  }

}
