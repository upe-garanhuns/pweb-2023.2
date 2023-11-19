package br.upe.garanhus.esw.pweb.modelo.repositorios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import br.upe.garanhus.esw.pweb.modelo.PersonagemTO;
import br.upe.garanhus.esw.pweb.modelo.entidades.AssociacaoEntity;
import br.upe.garanhus.esw.pweb.modelo.entidades.EpisodioEntity;
import br.upe.garanhus.esw.pweb.modelo.entidades.PersonagemEntity;

public class InsertUpdateRepository {

  public void adicionarPersonagem(PersonagemTO personagemTO) {

    PersonagemEntity personagemEntidade = new PersonagemEntity();
    String queryInsert =
        "INSERT INTO personagens(id_personagem, nome, status, especie, genero, url_imagem, data_criacao, data_atualizacao) values (?, ?, ?, ?, ?, ?, ?, ?)";

    BancoUtil.especificarDriver();

    try (
        Connection con =
            DriverManager.getConnection(BancoUtil.URL, BancoUtil.USUARIO, BancoUtil.SENHA);
        PreparedStatement pst = con.prepareStatement(queryInsert)) {

      personagemEntidade = personagemEntidade.transformarPersonagem(personagemTO);

      int idConfirmacao = confirmarPersonagem(personagemEntidade.getIdPersonagem());

      if (idConfirmacao == 0) {

        pst.setInt(1, personagemEntidade.getIdPersonagem());
        pst.setString(2, personagemEntidade.getNome());
        pst.setString(3, personagemEntidade.getStatus());
        pst.setString(4, personagemEntidade.getEspecie());
        pst.setString(5, personagemEntidade.getGenero());
        pst.setString(6, personagemEntidade.getImagem());
        pst.setString(7, personagemEntidade.getDataCriacao());
        personagemEntidade.setDataAtualizacaoAgora();
        pst.setString(8, personagemEntidade.getDataAtualizacao());
        pst.executeUpdate();

        this.adicionarEpisodio(personagemTO);
        this.adicionarAssociacao(personagemTO);

      } else if (idConfirmacao > 0) {
        this.atualizarPersonagem(personagemEntidade);
        this.adicionarEpisodio(personagemTO);
      }

    } catch (Exception e) {
      BancoUtil.tratarErros(BancoUtil.MSG_ERRO_SQL, e);
    }

  }

  public void adicionarEpisodio(PersonagemTO personagemTO) {

    String queryInsert =
        "INSERT INTO episodios(id_episodio, url_episodio, data_atualizacao) values (?, ?, ?)";

    BancoUtil.especificarDriver();

    try (
        Connection con =
            DriverManager.getConnection(BancoUtil.URL, BancoUtil.USUARIO, BancoUtil.SENHA);
        PreparedStatement pst = con.prepareStatement(queryInsert)) {

      EpisodioEntity episodioEntidade = new EpisodioEntity();
      List<EpisodioEntity> episodios = episodioEntidade.listarEpisodios(personagemTO);
      int idConfirmacao;

      for (EpisodioEntity episodio : episodios) {

        idConfirmacao = confirmarEpisodio(episodio.getIdEpisodio());

        if (idConfirmacao == 0) {
          pst.setInt(1, episodio.getIdEpisodio());
          pst.setString(2, episodio.getEpisodio());
          episodio.setDataAtualizacaoAgora();
          pst.setString(3, episodio.getDataAtualizacao());
          pst.executeUpdate();
        } else if (idConfirmacao > 0) {
          this.atualizarEpisodio(episodio);
        }

      }

    } catch (Exception e) {
      BancoUtil.tratarErros(BancoUtil.MSG_ERRO_SQL, e);
    }

  }

  public void adicionarAssociacao(PersonagemTO personagemTO) {

    AssociacaoEntity associacao = new AssociacaoEntity();
    String queryInsert =
        "INSERT INTO personagem_episodio(id_personagem, id_episodio) values (?, ?)";

    BancoUtil.especificarDriver();

    try (
        Connection con =
            DriverManager.getConnection(BancoUtil.URL, BancoUtil.USUARIO, BancoUtil.SENHA);
        PreparedStatement pst = con.prepareStatement(queryInsert)) {

      List<AssociacaoEntity> associacoes = associacao.associarEntidades(personagemTO);

      for (AssociacaoEntity associcaoEntidade : associacoes) {
        pst.setInt(1, associcaoEntidade.getIdPersonagem());
        pst.setInt(2, associcaoEntidade.getIdEpisodio());
        pst.executeUpdate();
      }

    } catch (Exception e) {
      BancoUtil.tratarErros(BancoUtil.MSG_ERRO_SQL, e);
    }

  }

  public void adicionarPersonagens(List<PersonagemTO> personagensTO) {

    PersonagemEntity personagem = new PersonagemEntity();
    List<PersonagemEntity> personagensEntidade = personagem.listarPersonagens(personagensTO);

    for (int i = 0; i < personagensEntidade.size(); i++) {
      adicionarPersonagem(personagensTO.get(i));
    }

  }


  public void atualizarPersonagem(PersonagemEntity personagem) {

    Connection con = null;
    PreparedStatement pst = null;

    String queryUpdate = "UPDATE personagens SET data_atualizacao = ? WHERE id_personagem = ?";

    BancoUtil.especificarDriver();

    try {

      con = DriverManager.getConnection(BancoUtil.URL, BancoUtil.USUARIO, BancoUtil.SENHA);
      pst = con.prepareStatement(queryUpdate);
      personagem.setDataAtualizacaoAgora();
      pst.setString(1, personagem.getDataAtualizacao());
      pst.setInt(2, personagem.getIdPersonagem());
      pst.executeUpdate();

    } catch (Exception e) {
      BancoUtil.tratarErros(BancoUtil.MSG_ERRO_SQL, e);

    } finally {
      BancoUtil.fecharElementos(con, pst, null);
    }

  }

  public void atualizarEpisodio(EpisodioEntity episodio) {

    Connection con = null;
    PreparedStatement pst = null;

    String queryUpdate = "UPDATE episodios SET data_atualizacao = ? WHERE id_episodio = ?";

    BancoUtil.especificarDriver();

    try {

      con = DriverManager.getConnection(BancoUtil.URL, BancoUtil.USUARIO, BancoUtil.SENHA);
      pst = con.prepareStatement(queryUpdate);
      episodio.setDataAtualizacaoAgora();
      pst.setString(1, episodio.getDataAtualizacao());
      pst.setInt(2, episodio.getIdEpisodio());
      pst.executeUpdate();

    } catch (Exception e) {
      BancoUtil.tratarErros(BancoUtil.MSG_ERRO_SQL, e);

    } finally {
      BancoUtil.fecharElementos(con, pst, null);
    }

  }

  public int confirmarPersonagem(int id) {

    String querySelect = "SELECT COUNT(*) FROM personagens WHERE id_personagem = " + id;

    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rst = null;

    BancoUtil.especificarDriver();

    try {

      con = DriverManager.getConnection(BancoUtil.URL, BancoUtil.USUARIO, BancoUtil.SENHA);
      pst = con.prepareStatement(querySelect);
      rst = pst.executeQuery();

      rst.next();
      return rst.getInt(1);

    } catch (Exception e) {
      BancoUtil.tratarErros(BancoUtil.MSG_ERRO_SQL, e);
      return -1;

    } finally {
      BancoUtil.fecharElementos(con, pst, rst);
    }

  }

  public int confirmarEpisodio(int id) {

    String querySelect = "SELECT COUNT(*) FROM episodios WHERE id_episodio = " + id;

    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rst = null;

    BancoUtil.especificarDriver();

    try {
      con = DriverManager.getConnection(BancoUtil.URL, BancoUtil.USUARIO, BancoUtil.SENHA);
      pst = con.prepareStatement(querySelect);
      rst = pst.executeQuery();

      rst.next();
      return rst.getInt(1);

    } catch (Exception e) {
      BancoUtil.tratarErros(BancoUtil.MSG_ERRO_SQL, e);
      return -1;

    } finally {
      BancoUtil.fecharElementos(con, pst, rst);
    }

  }

}
