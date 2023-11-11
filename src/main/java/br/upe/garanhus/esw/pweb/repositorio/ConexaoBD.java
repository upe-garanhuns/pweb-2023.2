package br.upe.garanhus.esw.pweb.repositorio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexaoBD {
  
  private static final Logger logger = Logger.getLogger(ConexaoBD.class.getName());
  private static final String URL = "jdbc:postgresql://localhost:5432/pweb-2023";
  private static final String USER = "postgres";
  private static final String SENHA = "1234";

  private Connection conexao;
  
  public ConexaoBD() {
    try {
      Class.forName("org.postgresql.Driver");
      this.conexao = DriverManager.getConnection(URL, USER, SENHA);
      
      if(conexao != null) {
        logger.log(Level.INFO, "BD conectado");
      }
      
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public Connection getConexao() {
    return conexao;
  }

}
