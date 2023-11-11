package br.upe.garanhus.esw.pweb.repositorio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoBD {

  private static final String URL = "jdbc:postgresql://localhost:5432/pweb-2023";
  private static final String USER = "postgres";
  private static final String SENHA = "1234";
  
  private Connection conexao;

  
  public ConexaoBD() {
    try {
      Class.forName("org.postgresql.Driver");
      this.conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/pweb-2023", "postgres", "1234");
      if(conexao != null) {
        System.out.println("BD conectado");
      } else {
        System.out.println("Erro ao conectar com o BD");
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  public void inserir(String queryString) {   
    Statement statement;
    ResultSet resultado;
      
    try {
      statement = conexao.createStatement();
      resultado = statement.executeQuery(queryString);
      
      statement.close(); 
      resultado.close();
      
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

}
