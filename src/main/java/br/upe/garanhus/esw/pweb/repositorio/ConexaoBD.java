package br.upe.garanhus.esw.pweb.repositorio;

import br.upe.garanhus.esw.pweb.modelo.RickMortyException;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoBD {
  private static final String URL = "jdbc:mysql://localhost:3306/bancopersonagem";
  private static final String USUARIO = "root";
  private static final String SENHA = "senhadobanco";
  private static final String MSG_ERRO_ABRIR_CONEXAO =
      "Ocorreu um erro ao conectar-se com com banco de dados";
  private static final String MSG_ERRO_FECHAR_CONEXAO =
      "Ocorreu um erro ao fechar a conexão com o banco de dados";

  public static Connection getConexao() {
    Connection conexao = null;

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
    } catch (Exception e) {
      throw new RickMortyException(MSG_ERRO_ABRIR_CONEXAO, e);
    }

    return conexao;
  }

  public static void fecharConexao(Connection conexao) {
    try {
      conexao.close();
    } catch (Exception e) {
      throw new RickMortyException(MSG_ERRO_FECHAR_CONEXAO, e);
    }
  }
}
