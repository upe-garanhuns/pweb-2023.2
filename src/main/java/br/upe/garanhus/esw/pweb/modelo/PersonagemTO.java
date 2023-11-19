package br.upe.garanhus.esw.pweb.modelo;

import java.util.ArrayList;
import java.util.List;
import jakarta.json.bind.annotation.JsonbProperty;

public class PersonagemTO {

  private int id;

  @JsonbProperty("name")
  private String nome;

  private String status;

  @JsonbProperty("species")
  private String especie;

  @JsonbProperty("gender")
  private String genero;

  @JsonbProperty("image")
  private String imagem;

  @JsonbProperty("episode")
  private List<String> episodios;

  @JsonbProperty("created")
  private String criacao;

  public PersonagemTO() {
    episodios = new ArrayList<>(); // construtor adicionado
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getEspecie() {
    return especie;
  }

  public void setEspecie(String especie) {
    this.especie = especie;
  }

  public String getGenero() {
    return genero;
  }

  public void setGenero(String genero) {
    this.genero = genero;
  }

  public String getImagem() {
    return imagem;
  }

  public void setImagem(String imagem) {
    this.imagem = imagem;
  }

  public List<String> getEpisodios() {
    return episodios;
  }

  public void setEpisodios(List<String> episodios) {
    this.episodios = episodios;
  }

  public void addEpisodio(String episodio) { // método adicionado
    this.episodios.add(episodio);
  }

  public String getCriacao() {
    return criacao;
  }

  public void setCriacao(String criacao) {
    this.criacao = criacao;
  }


}
