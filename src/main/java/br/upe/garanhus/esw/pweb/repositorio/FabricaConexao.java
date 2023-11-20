package br.upe.garanhus.esw.pweb.repositorio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexao {

  static {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(
          "Driver JDBC do MySQL não encontrado. Verifique se o JAR do driver está no classpath.");
    }
  }

  public static Connection getConexao() throws SQLException {
    final String url =
        "jdbc:mysql://localhost/rickmorty?verifyServerCertificate=false&useSSL=true&useTimezone=true&serverTimezone=UTC";
    final String usuario = "root";
    final String senha = "brasil2004";

    return DriverManager.getConnection(url, usuario, senha);
  }
}
