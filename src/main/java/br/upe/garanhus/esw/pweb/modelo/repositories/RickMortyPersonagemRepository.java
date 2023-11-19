package br.upe.garanhus.esw.pweb.modelo.repositories;

import br.upe.garanhus.esw.pweb.modelo.PersonagemTO;
import br.upe.garanhus.esw.pweb.modelo.RickMortyException;
import br.upe.garanhus.esw.pweb.modelo.infra.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

public class RickMortyPersonagemRepository {

  private static final String QUERY_INSERT_CHARACTER =
      "INSERT INTO characters (id, name, gender, species, status, image_url, created_at, "
          + "updated_at) VALUES (?, ?, ?, ?, ?, ?, ?::timestamp, ?)";
  private static final String QUERY_GET_CHRACTER_BY_ID =
      "SELECT * FROM characters WHERE id = ?";
  private static final String QUERY_UPDATE_STATUS =
      "UPDATE characters SET updated_at = ? WHERE id = ?";
  private static final String MSG_ERRO_INSERCAO =
      "Ocorreu algum erro durante as inserções no banco";
  private static final String MSG_ERRO_RECUPERACAO =
      "Ocorreu algum erro durante as consultas no banco";
  private final Connection connection;

  public RickMortyPersonagemRepository() {
    this.connection = new DatabaseConnection().getConnection();
  }

  public PersonagemTO encontrarPersonagemPorId(int id) {
    PersonagemTO personagem = null;

    try (PreparedStatement acharPersonagem = connection
        .prepareStatement(QUERY_GET_CHRACTER_BY_ID)) {

      acharPersonagem.setInt(1, id);

      ResultSet resultado = acharPersonagem.executeQuery();
      if (resultado.next()) {
        personagem = new PersonagemTO();

        personagem.setId(resultado.getInt("id"));
        personagem.setNome(resultado.getString("name"));
        personagem.setGenero(resultado.getString("gender"));
        personagem.setEspecie(resultado.getString("species"));
        personagem.setStatus(resultado.getString("status"));
        personagem.setImagem(resultado.getString("image_url"));
        personagem.setCriacao(resultado.getString("created_at"));
      }

      resultado.close();
    } catch (SQLException e) {
      throw new RickMortyException(MSG_ERRO_RECUPERACAO, e);
    }
    return personagem;
  }

  public void salvarPersonagem(PersonagemTO personagem) {
    Timestamp time = Timestamp.from(Instant.now());

    try (PreparedStatement inserirDadosResposta = connection.prepareStatement(
        QUERY_INSERT_CHARACTER)) {
      inserirDadosResposta.setInt(1, personagem.getId());
      inserirDadosResposta.setString(2, personagem.getNome());
      inserirDadosResposta.setString(3, personagem.getGenero());
      inserirDadosResposta.setString(4, personagem.getEspecie());
      inserirDadosResposta.setString(5, personagem.getStatus());
      inserirDadosResposta.setString(6, personagem.getImagem());
      inserirDadosResposta.setString(7, personagem.getCriacao());
      inserirDadosResposta.setTimestamp(8, time);

      inserirDadosResposta.addBatch();
      inserirDadosResposta.executeBatch();
    } catch (SQLException e) {
      throw new RickMortyException(MSG_ERRO_INSERCAO, e);
    }
  }

  public void atualizarStatus(PersonagemTO personagem) {
    Timestamp time = Timestamp.from(Instant.now());

    try (PreparedStatement atualizarStatus = connection.prepareStatement(QUERY_UPDATE_STATUS)) {
      atualizarStatus.setTimestamp(1, time);
      atualizarStatus.setInt(2, personagem.getId());
      atualizarStatus.executeUpdate();

    } catch (SQLException e) {
      throw new RickMortyException(MSG_ERRO_INSERCAO, e);
    }
  }
}
