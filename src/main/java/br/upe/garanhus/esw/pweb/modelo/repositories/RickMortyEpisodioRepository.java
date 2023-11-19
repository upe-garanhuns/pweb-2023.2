package br.upe.garanhus.esw.pweb.modelo.repositories;

import br.upe.garanhus.esw.pweb.modelo.PersonagemTO;
import br.upe.garanhus.esw.pweb.modelo.RickMortyException;
import br.upe.garanhus.esw.pweb.modelo.infra.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RickMortyEpisodioRepository {

  private static final String QUERY_INSERT_EP =
      "INSERT INTO episodes (episode_url) VALUES (?)";
  private static final String QUERY_INSERT_CHAR_EP =
      "INSERT INTO character_episodes (character_id, episode_id) VALUES (?, ?)";
  private static final String QUERY_GET_EP_ID_BY_URL =
      "SELECT id FROM episodes WHERE episode_url = ?";
  private static final String QUERY_GET_EP_BY_CHARACTER_ID =
      "SELECT episodes.episode_url FROM episodes JOIN character_episodes ON episodes.id = "
          + "character_episodes.episode_id WHERE character_episodes.character_id = ?";
  private static final String MSG_ERRO_INSERCAO =
      "Ocorreu algum erro durante as inserções no banco";
  private static final String MSG_ERRO_RECUPERACAO =
      "Ocorreu algum erro durante as consultas no banco";
  private final Connection connection;

  public RickMortyEpisodioRepository() {
    this.connection = new DatabaseConnection().getConnection();
  }

  public int encontrarIdEpisodio(String url) {
    int resultado = -1;
    try (PreparedStatement consultarIdEpisodio = connection.prepareStatement(
        QUERY_GET_EP_ID_BY_URL)) {
      consultarIdEpisodio.setString(1, url);
      ResultSet resultSet = consultarIdEpisodio.executeQuery();

      if (resultSet.next()) {
        resultado = resultSet.getInt("id");
      }
    } catch (SQLException e) {
      throw new RickMortyException(MSG_ERRO_RECUPERACAO, e);
    }
    return resultado;
  }

  public List<String> encontrarEpisodiosPorIdPersonagem(int id) {
    List<String> episodios = new ArrayList<>();
    try (PreparedStatement acharEpisodios = connection.
        prepareStatement(QUERY_GET_EP_BY_CHARACTER_ID)) {

      acharEpisodios.setInt(1, id);
      ResultSet resultadoEpisodios = acharEpisodios.executeQuery();

      while (resultadoEpisodios.next()) {
        episodios.add(resultadoEpisodios.getString("episode_url"));
      }

      resultadoEpisodios.close();
    } catch (SQLException e) {
      throw new RickMortyException(MSG_ERRO_RECUPERACAO, e);
    }

    return episodios;
  }

  public void salvarEpisodios(PersonagemTO personagem) {
    try (PreparedStatement inserirEpisodio = connection.prepareStatement(QUERY_INSERT_EP)) {

      for (String episode : personagem.getEpisodios()) {
        if (encontrarIdEpisodio(episode) == -1) {
          inserirEpisodio.setString(1, episode);
          inserirEpisodio.addBatch();
        }
      }

      inserirEpisodio.executeBatch();
    } catch (SQLException e) {
      throw new RickMortyException(MSG_ERRO_INSERCAO, e);
    }
  }

  public void salvarRelacaoEpisodioPersonagem(PersonagemTO personagem) {
    try (PreparedStatement inserirEpisodioPersonagem = connection
        .prepareStatement(QUERY_INSERT_CHAR_EP)) {
      for (String episode : personagem.getEpisodios()) {

        int idEpisodio = encontrarIdEpisodio(episode);
        inserirEpisodioPersonagem.setInt(1, personagem.getId());
        inserirEpisodioPersonagem.setInt(2, idEpisodio);
        inserirEpisodioPersonagem.addBatch();

      }
      inserirEpisodioPersonagem.executeBatch();
    } catch (SQLException e) {
      throw new RickMortyException(MSG_ERRO_INSERCAO, e);
    }
  }
}
