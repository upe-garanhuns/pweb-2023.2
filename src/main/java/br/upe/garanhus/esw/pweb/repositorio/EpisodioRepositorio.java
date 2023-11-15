package br.upe.garanhus.esw.pweb.repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import br.upe.garanhus.esw.pweb.modelo.EpisodioTO;

public class EpisodioRepositorio {

  private ConexaoBD conexaoBD;
  private Connection bd;
  
  public EpisodioRepositorio() {
    conexaoBD = new ConexaoBD();
    bd = conexaoBD.getConexao();
  }
  
  public List<String> encontrarEpsPersonagem(int idPersonagem) {
    String query = "SELECT DISTINCT ON (e.url) e.id, e.url FROM personagens p"
        + " INNER JOIN personagens_episodios pe ON p.id = pe.id_personagem"
        + " INNER JOIN episodios e ON e.id = pe.id_episodio"
        + " WHERE p.id = (?)";
    
    PreparedStatement prepStmt;
    ResultSet resultado;
    ArrayList<String> listaEps = new ArrayList<>();
    
    try {
      prepStmt = bd.prepareStatement(query);
      prepStmt.setInt(1, idPersonagem);
      
      resultado = prepStmt.executeQuery();

      while(resultado.next()) {
        listaEps.add(resultado.getString("url"));
       }

      prepStmt.close(); 
     
    } catch (SQLException e) {
      e.printStackTrace();
    }
    
    return listaEps;
    
  }
  
  public void inserirEpisodio(String url) {   
    String query = "INSERT INTO episodios(url) VALUES (?);";
    
    try {
      PreparedStatement prepStmt = bd.prepareStatement(query);
      prepStmt.setString(1, url);
      prepStmt.executeUpdate();
      
      prepStmt.close();
      
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }
  
  public EpisodioTO encontrarEpisodio(String url) {
    String queryString  = "SELECT * FROM episodios WHERE url = '" + url + "'";
    
    Statement statement;
    ResultSet resultado;
    EpisodioTO episodio = null;
      
    try {
      statement = bd.createStatement();
      resultado = statement.executeQuery(queryString);

      while(resultado.next()) {
        episodio = new EpisodioTO();
        episodio.setId(resultado.getInt("id"));
        episodio.setUrl(resultado.getString("url"));
      }

      statement.close(); 
      resultado.close();
     
    } catch (SQLException e) {
      e.printStackTrace();
    }
    
    return episodio;
    
  }
  
  public void inserirPersonagemEp(int idPersonagem, int idEpisodio) {
    String query = "INSERT INTO personagens_episodios(id_personagem, id_episodio) VALUES (?, ?);";
    
    try {
      PreparedStatement prepStmt = bd.prepareStatement(query);
      prepStmt.setInt(1, idPersonagem);
      prepStmt.setInt(2, idEpisodio);
      prepStmt.executeUpdate();
      
      prepStmt.close();
      
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
}
