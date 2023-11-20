package br.upe.garanhus.esw.pweb.repositorio;

import br.upe.garanhus.esw.pweb.modelo.PersonagemTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonagemEpRepository {

  public void addPersonagemEpisodio(Connection conexao, PersonagemTO p, String url)
      throws SQLException {
    PreparedStatement stmt = null;

    try {
      String sql = "INSERT INTO Personagens_Episodios (id_personagem, id_episodio) VALUES (?, ?)";
      stmt = conexao.prepareStatement(sql);
      stmt.setInt(1, p.getId());
      stmt.setInt(2, extrairID(url));
      stmt.execute();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      if (stmt != null) {
        stmt.close();
      }
    }
  }

  public boolean relacaoJaExiste(Connection conexao, PersonagemTO p) throws SQLException {
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
      String sql = "SELECT COUNT(*) FROM Personagens_Episodios WHERE id_personagem = ?";
      stmt = conexao.prepareStatement(sql);
      stmt.setInt(1, p.getId());
      rs = stmt.executeQuery();

      if (rs.next()) {
        int count = rs.getInt(1);
        int quantEP = p.getEpisodios().size();
        return count > quantEP;
      }
      return false;

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      if (rs != null) {
        rs.close();
      }
      if (stmt != null) {
        stmt.close();
      }
    }
  }

  public List<String> pegarEpisodios(Connection conexao, PersonagemTO p) throws SQLException {
    List<String> eps = new ArrayList<>();
    PreparedStatement stmt = null;
    ResultSet resultado = null;

    String sql =
        "SELECT episodios.url FROM episodios\n"
            + "JOIN Personagens_Episodios ON episodios.id = Personagens_Episodios.id_episodio\n"
            + "JOIN personagens ON Personagens_Episodios.id_personagem = personagens.id\n"
            + "WHERE personagens.id = ?;";

    try {
      stmt = conexao.prepareStatement(sql);
      stmt.setInt(1, p.getId());

      resultado = stmt.executeQuery();

      while (resultado.next()) {
        String ep = resultado.getString("url");
        eps.add(ep);
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      if (resultado != null) {
        resultado.close();
      }
      if (stmt != null) {
        stmt.close();
      }
    }

    return eps;
  }

  private static int extrairID(String url) {
    int indice = url.lastIndexOf('/');

    if (indice != -1) {
      try {
        return Integer.parseInt(url.substring(indice + 1));
      } catch (NumberFormatException e) {
        throw new RuntimeException(e);
      }
    }
    return -1;
  }
}
