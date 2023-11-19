package br.upe.garanhus.esw.pweb.modelo.entidades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import br.upe.garanhus.esw.pweb.modelo.PersonagemTO;

public class EpisodioEntity {

  private int idEpisodio;

  private String episodio;

  private String dataAtualizacao;

  public List<EpisodioEntity> listarEpisodios(List<PersonagemTO> personagensTO) {

    List<EpisodioEntity> episodios = new ArrayList<>();
    String[] urlSemBarra;
    String episodioEmString;
    boolean repetido;
    int idLocalEpisodio;

    for (PersonagemTO personagem : personagensTO) {
      for (int i = 0; i < personagem.getEpisodios().size(); i++) {

        EpisodioEntity episodioEntidade = new EpisodioEntity();
        episodioEmString = personagem.getEpisodios().get(i);
        episodioEntidade.setEpisodio(episodioEmString);
        urlSemBarra = episodioEmString.split("/");
        idLocalEpisodio = Integer.parseInt(urlSemBarra[urlSemBarra.length - 1]);
        episodioEntidade.setIdEpisodio(idLocalEpisodio);
        repetido = checarRepeticao(episodios, idLocalEpisodio);

        if (!repetido) {
          episodios.add(episodioEntidade);
        }

      }
    }

    return episodios;

  }

  public List<EpisodioEntity> listarEpisodios(PersonagemTO personagemTO) {

    List<EpisodioEntity> episodios = new ArrayList<>();
    String[] urlSemBarra;
    String episodioEmString;
    boolean repetido;
    int idLocalEpisodio;

    for (int i = 0; i < personagemTO.getEpisodios().size(); i++) {

      EpisodioEntity episodioEntidade = new EpisodioEntity();
      episodioEmString = personagemTO.getEpisodios().get(i);
      episodioEntidade.setEpisodio(episodioEmString);
      urlSemBarra = episodioEmString.split("/");
      idLocalEpisodio = Integer.parseInt(urlSemBarra[urlSemBarra.length - 1]);
      episodioEntidade.setIdEpisodio(idLocalEpisodio);
      repetido = checarRepeticao(episodios, idLocalEpisodio);

      if (!repetido) {
        episodios.add(episodioEntidade);
      }

    }

    return episodios;

  }


  public boolean checarRepeticao(List<EpisodioEntity> episodios, int id) {

    for (EpisodioEntity episodioEntidade : episodios) {
      if (episodioEntidade.getIdEpisodio() == id) {
        return true;
      }
    }

    return false;

  }

  public int getIdEpisodio() {
    return idEpisodio;
  }

  public void setIdEpisodio(int id) {
    this.idEpisodio = id;
  }

  public String getEpisodio() {
    return episodio;
  }

  public void setEpisodio(String episodio) {
    this.episodio = episodio;
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
