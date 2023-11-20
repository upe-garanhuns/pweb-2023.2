package br.upe.garanhus.esw.pweb.repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EpisodioRepository {

  public void addEpisodio(Connection conexao, String ep) throws SQLException {
    PreparedStatement stmt = null;
    try {
      String sql = "INSERT INTO episodios (id, url) VALUES (?,?)";

      stmt = conexao.prepareStatement(sql);
      stmt.setInt(1, extrairID(ep));
      stmt.setString(2, ep);
      stmt.execute();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      stmt.close();
    }
  }

  public void updateEpisodio(Connection conexao, String ep) throws SQLException {
    PreparedStatement stmt = null;
    try {
      String sql = "UPDATE episodios SET data_atualizacao = CURRENT_TIMESTAMP WHERE id = ?";

      stmt = conexao.prepareStatement(sql);
      stmt.setInt(1, extrairID(ep));
      stmt.execute();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      stmt.close();
    }
  }

  public boolean verifyEpisodio(Connection conexao, String url) throws SQLException {
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
      String sql = "SELECT COUNT(*) FROM episodios WHERE url = ?";
      stmt = conexao.prepareStatement(sql);
      stmt.setString(1, url);
      rs = stmt.executeQuery();

      if (rs.next()) {
        int count = rs.getInt(1);
        return count > 0;
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
