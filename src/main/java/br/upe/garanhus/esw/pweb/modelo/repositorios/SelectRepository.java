package br.upe.garanhus.esw.pweb.modelo.repositorios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import br.upe.garanhus.esw.pweb.modelo.PersonagemTO;
import br.upe.garanhus.esw.pweb.modelo.entidades.EpisodioEntity;
import br.upe.garanhus.esw.pweb.modelo.entidades.PersonagemEntity;

public class SelectRepository {

  public PersonagemTO getPersonagenTOPeloId(int id) {

    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rst = null;

    PersonagemEntity personagem = new PersonagemEntity();
    EpisodioEntity episodio = new EpisodioEntity();

    PersonagemTO personagemTO = new PersonagemTO();
    boolean parado = false;

    String querySelect = "SELECT * FROM personagem_episodio WHERE id_personagem = " + id;

    BancoUtil.especificarDriver();

    try {

      con = DriverManager.getConnection(BancoUtil.URL, BancoUtil.USUARIO, BancoUtil.SENHA);
      pst = con.prepareStatement(querySelect);
      rst = pst.executeQuery();

      while (rst.next()) {
        if (!parado) {
          personagem = getPersonagemPeloId(rst.getInt(1));
          personagemTO = personagem.transformarPersonagem(personagem);
          parado = true;
        }
        episodio = getEpisodioPeloId(rst.getInt(2));
        personagemTO.addEpisodio(episodio.getEpisodio());
      }

      return personagemTO;

    } catch (Exception e) {
      BancoUtil.tratarErros(BancoUtil.MSG_ERRO_SQL, e);
      return null;

    } finally {
      BancoUtil.fecharElementos(con, pst, rst);
    }

  }

  public List<PersonagemTO> getPersonagens() {

    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rst = null;

    PersonagemEntity personagem = new PersonagemEntity();
    EpisodioEntity episodio = new EpisodioEntity();

    PersonagemTO personagemTO = null;
    List<PersonagemTO> personagensTO = new ArrayList<>();
    int idPersonagem = -1;

    String querySelect = "SELECT * FROM personagem_episodio";

    BancoUtil.especificarDriver();

    try {

      con = DriverManager.getConnection(BancoUtil.URL, BancoUtil.USUARIO, BancoUtil.SENHA);
      pst = con.prepareStatement(querySelect);
      rst = pst.executeQuery();

      while (rst.next()) {

        if (idPersonagem == -1) {
          idPersonagem = rst.getInt(1);
          personagem = getPersonagemPeloId(rst.getInt(1));
          personagemTO = personagem.transformarPersonagem(personagem);
        }

        if (idPersonagem == rst.getInt(1)) {
          episodio = getEpisodioPeloId(rst.getInt(2));
          personagemTO.addEpisodio(episodio.getEpisodio());
        } else {
          personagensTO.add(personagemTO);

          idPersonagem = rst.getInt(1);
          personagem = getPersonagemPeloId(rst.getInt(1));
          personagemTO = personagem.transformarPersonagem(personagem);
          episodio = getEpisodioPeloId(rst.getInt(2));
          personagemTO.addEpisodio(episodio.getEpisodio());
        }

      }

      personagensTO.add(personagemTO);
      return personagensTO;

    } catch (Exception e) {
      BancoUtil.tratarErros(BancoUtil.MSG_ERRO_SQL, e);
      return Collections.emptyList();

    } finally {
      BancoUtil.fecharElementos(con, pst, rst);
    }

  }

  public PersonagemEntity getPersonagemPeloId(int id) {

    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rst = null;
    PersonagemEntity personagem = new PersonagemEntity();

    String querySelect = "SELECT * FROM personagens WHERE id_personagem = " + id;

    BancoUtil.especificarDriver();

    try {

      con = DriverManager.getConnection(BancoUtil.URL, BancoUtil.USUARIO, BancoUtil.SENHA);
      pst = con.prepareStatement(querySelect);
      rst = pst.executeQuery();

      while (rst.next()) {
        personagem.setIdPersonagem(rst.getInt(1));
        personagem.setNome(rst.getString(2));
        personagem.setStatus(rst.getString(3));
        personagem.setEspecie(rst.getString(4));
        personagem.setGenero(rst.getString(5));
        personagem.setImagem(rst.getString(6));
        personagem.setDataCriacao(rst.getString(7));
      }

      return personagem;

    } catch (Exception e) {
      BancoUtil.tratarErros(BancoUtil.MSG_ERRO_SQL, e);
      return null;

    } finally {
      BancoUtil.fecharElementos(con, pst, rst);
    }

  }

  public EpisodioEntity getEpisodioPeloId(int id) {

    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rst = null;
    EpisodioEntity episodio = new EpisodioEntity();

    String querySelect = "SELECT * FROM episodios WHERE id_episodio = " + id;

    BancoUtil.especificarDriver();

    try {

      con = DriverManager.getConnection(BancoUtil.URL, BancoUtil.USUARIO, BancoUtil.SENHA);
      pst = con.prepareStatement(querySelect);
      rst = pst.executeQuery();

      while (rst.next()) {
        episodio.setIdEpisodio(rst.getInt(1));
        episodio.setEpisodio(rst.getString(2));
      }

      return episodio;

    } catch (Exception e) {
      BancoUtil.tratarErros(BancoUtil.MSG_ERRO_SQL, e);
      return null;

    } finally {
      BancoUtil.fecharElementos(con, pst, rst);
    }

  }

}
