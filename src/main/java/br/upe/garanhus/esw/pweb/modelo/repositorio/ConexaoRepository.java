package br.upe.garanhus.esw.pweb.modelo.repositorio;

import br.upe.garanhus.esw.pweb.modelo.RepositoryException;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexaoRepository {

  private static final Logger logger = Logger.getLogger(ConexaoRepository.class.getName());

  private static final String URL = "jdbc:postgresql://localhost:5432/";
  private static final String MSG_ERRO_FECHAR_CONEXAO = "Ocorreu um erro ao fechar as conexões";
  private static final String MSG_ERRO_FAZER_CONEXAO = "Ocorreu um erro de conexão com o banco de dados";
  private static final String MSG_ERRO_GERAL = "Ocorreu um erro inesperado ao utilizar o banco de dados";

  public Connection criarConexao(String nomeBD, String usuario, String senha) {
    Connection connection = null;
    try {
      Class.forName("org.postgresql.Driver");
      connection = DriverManager.getConnection(URL + nomeBD, usuario, senha);

      if (connection != null) {
        logger.log(Level.INFO, "Conexão com o banco de dados realizada com sucesso.");
      }
    } catch (Exception e) {
      this.tratarErros(e);
    }

    return connection;
  }

  public void fecharConexao(Connection connection) {
    try {
      connection.close();
    } catch (Exception e) {
      this.tratarErros(e);
    }
  }

  public void fecharStatement(Statement statement) {
    try {
      statement.close();
    } catch (Exception e) {
      this.tratarErros(e);
    }
  }

  public void fecharResultSet(ResultSet resultSet) {
    try {
      resultSet.close();
    } catch (Exception e) {
      this.tratarErros(e);
    }
  }

  public void tratarErros(Exception e) {
    if (e instanceof SQLException) {
      logger.log(Level.SEVERE, MSG_ERRO_FECHAR_CONEXAO, e);
      throw new RepositoryException(MSG_ERRO_FECHAR_CONEXAO);
    }
    if (e instanceof ClassNotFoundException) {
      logger.log(Level.SEVERE, MSG_ERRO_FAZER_CONEXAO, e);
      throw new RepositoryException(MSG_ERRO_FAZER_CONEXAO);
    } else {
      if (e instanceof RepositoryException) {
        throw (RepositoryException) e;
      } else {
        logger.log(Level.SEVERE, MSG_ERRO_GERAL, e);
        throw new RepositoryException(MSG_ERRO_GERAL, e);
      }
    }
  }
}
