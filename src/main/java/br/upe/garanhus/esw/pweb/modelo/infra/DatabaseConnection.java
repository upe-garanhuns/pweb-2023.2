package br.upe.garanhus.esw.pweb.modelo.infra;

import br.upe.garanhus.esw.pweb.modelo.RickMortyException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

  private static final String BD_URL = "jdbc:postgresql://localhost:5432/rickandmortyDB";
  private static final String USER = "postgres";
  private static final String PASSWORD = "postgres";
  private static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";
  private static final String MSG_ERRO_FALHA_CONEXAO =
      "Ocorreu algum erro durante a conexão com o banco";
  private final Connection connection;

  public DatabaseConnection() {
    this.connection = this.abrirConexao();
  }

  public Connection abrirConexao() {

    Connection conexao;
    try {
      Class.forName(DRIVER_CLASS_NAME);
      conexao = DriverManager.getConnection(BD_URL, USER, PASSWORD);
    } catch (SQLException | ClassNotFoundException e) {
      throw new RickMortyException(MSG_ERRO_FALHA_CONEXAO, e);
    }
    return conexao;
  }

  public void fecharConexao() {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        throw new RickMortyException(MSG_ERRO_FALHA_CONEXAO, e);
      }
    }
  }

  public Connection getConnection() {
    return connection;
  }
}
