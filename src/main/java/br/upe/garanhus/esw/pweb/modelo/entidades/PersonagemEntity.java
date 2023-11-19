package br.upe.garanhus.esw.pweb.modelo.entidades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import br.upe.garanhus.esw.pweb.modelo.PersonagemTO;

public class PersonagemEntity {

  private int idPersonagem;

  private String nome;

  private String status;

  private String especie;

  private String genero;

  private String imagem;

  private String dataCriacao;

  private String dataAtualizacao;

  public PersonagemEntity() {}

  public PersonagemEntity(PersonagemTO personagemTO) {

    this.idPersonagem = personagemTO.getId();
    this.nome = personagemTO.getNome();
    this.status = personagemTO.getStatus();
    this.especie = personagemTO.getEspecie();
    this.genero = personagemTO.getGenero();
    this.imagem = personagemTO.getImagem();
    this.dataCriacao = personagemTO.getCriacao();

  }

  public List<PersonagemEntity> listarPersonagens(List<PersonagemTO> personagensTO) {

    List<PersonagemEntity> personagens = new ArrayList<>();

    for (PersonagemTO personagem : personagensTO) {
      personagens.add(new PersonagemEntity(personagem));
    }

    return personagens;

  }

  public PersonagemTO transformarPersonagem(PersonagemEntity personagemEntidade) {

    PersonagemTO personagemTO = new PersonagemTO();
    personagemTO.setId(personagemEntidade.getIdPersonagem());
    personagemTO.setNome(personagemEntidade.getNome());
    personagemTO.setStatus(personagemEntidade.getStatus());
    personagemTO.setEspecie(personagemEntidade.getEspecie());
    personagemTO.setGenero(personagemEntidade.getGenero());
    personagemTO.setImagem(personagemEntidade.getImagem());
    personagemTO.setCriacao(personagemEntidade.getDataCriacao());

    return personagemTO;


  }

  public PersonagemEntity transformarPersonagem(PersonagemTO personagemTO) {

    PersonagemEntity personagemEntidade = new PersonagemEntity();
    personagemEntidade.setIdPersonagem(personagemTO.getId());
    personagemEntidade.setNome(personagemTO.getNome());
    personagemEntidade.setStatus(personagemTO.getStatus());
    personagemEntidade.setEspecie(personagemTO.getEspecie());
    personagemEntidade.setGenero(personagemTO.getGenero());
    personagemEntidade.setImagem(personagemTO.getImagem());
    personagemEntidade.setDataCriacao(personagemTO.getCriacao());

    return personagemEntidade;

  }


  public int getIdPersonagem() {
    return idPersonagem;
  }

  public void setIdPersonagem(int id) {
    this.idPersonagem = id;
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

  public String getDataCriacao() {
    return dataCriacao;
  }

  public void setDataCriacao(String criacao) {
    this.dataCriacao = criacao;
  }

  public String getDataAtualizacao() {
    return dataAtualizacao;
  }

  public void setDataAtualizacao(String dataAtualizacao) {
    this.dataAtualizacao = dataAtualizacao;

  }

  public void setDataAtualizacaoAgora() {

    DateTimeFormatter padraoDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime agora = LocalDateTime.now();
    String dataAgora = agora.format(padraoDateTime);
    this.setDataAtualizacao(dataAgora);

  }

}
