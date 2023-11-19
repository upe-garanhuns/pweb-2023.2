package br.upe.garanhus.esw.pweb.modelo.entidades;

import java.util.ArrayList;
import java.util.List;
import br.upe.garanhus.esw.pweb.modelo.PersonagemTO;

public class AssociacaoEntity {

  private int idPersonagem;

  private int idEpisodio;

  public List<AssociacaoEntity> associarEntidades(List<PersonagemTO> personagensTO) {

    List<AssociacaoEntity> associacoes = new ArrayList<>();
    String[] urlSemBarra;

    for (PersonagemTO personagemTO : personagensTO) {
      for (int i = 0; i < personagemTO.getEpisodios().size(); i++) {
        AssociacaoEntity personagemEpisodio = new AssociacaoEntity();
        personagemEpisodio.setIdPersonagem(personagemTO.getId());
        urlSemBarra = personagemTO.getEpisodios().get(i).split("/");
        personagemEpisodio.setIdEpisodio(Integer.parseInt(urlSemBarra[urlSemBarra.length - 1]));
        associacoes.add(personagemEpisodio);
      }
    }

    return associacoes;

  }

  public List<AssociacaoEntity> associarEntidades(PersonagemTO personagemTO) {

    List<AssociacaoEntity> associacoes = new ArrayList<>();
    String[] urlSemBarra;

    for (int i = 0; i < personagemTO.getEpisodios().size(); i++) {
      AssociacaoEntity personagemEpisodio = new AssociacaoEntity();
      personagemEpisodio.setIdPersonagem(personagemTO.getId());
      urlSemBarra = personagemTO.getEpisodios().get(i).split("/");
      personagemEpisodio.setIdEpisodio(Integer.parseInt(urlSemBarra[urlSemBarra.length - 1]));
      associacoes.add(personagemEpisodio);
    }

    return associacoes;

  }

  public int getIdPersonagem() {
    return idPersonagem;
  }

  public void setIdPersonagem(int idPersonagem) {
    this.idPersonagem = idPersonagem;
  }

  public int getIdEpisodio() {
    return idEpisodio;
  }

  public void setIdEpisodio(int idEpisodio) {
    this.idEpisodio = idEpisodio;
  }

}
