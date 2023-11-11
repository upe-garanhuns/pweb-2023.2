package br.upe.garanhus.esw.pweb.repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import br.upe.garanhus.esw.pweb.modelo.PersonagemTO;

public class PersonagemRepositorio {

  private ConexaoBD conexaoBD;
  private Connection bd;
  
  public PersonagemRepositorio() {
    conexaoBD = new ConexaoBD();
    bd = conexaoBD.getConexao();
  }
  
  public void inserirPersonagem(PersonagemTO personagem) {   
    String query = "INSERT INTO personagens VALUES (?,?,?,?,?,?,?,?);";
    
    try {
      PreparedStatement inserirPersonagem = bd.prepareStatement(query);
      inserirPersonagem.setInt(1, personagem.getId());
      inserirPersonagem.setString(2, personagem.getNome());
      inserirPersonagem.setString(3, personagem.getStatus());
      inserirPersonagem.setString(4, personagem.getEspecie());
      inserirPersonagem.setString(5, personagem.getGenero());
      inserirPersonagem.setString(6, personagem.getImagem());
      inserirPersonagem.setString(7, personagem.getCriacao());
      inserirPersonagem.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
      inserirPersonagem.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }
  
  public void atualizarPersonagem(PersonagemTO personagem) {
    String query = "UPDATE personagens SET nome=?, status=?, especie=?, "
        + "genero=?, imagem=?, criacao=?, atualizacao=? WHERE id=? ;";
    
    try {
      PreparedStatement inserirPersonagem = bd.prepareStatement(query);
      inserirPersonagem.setString(1, personagem.getNome());
      inserirPersonagem.setString(2, personagem.getStatus());
      inserirPersonagem.setString(3, personagem.getEspecie());
      inserirPersonagem.setString(4, personagem.getGenero());
      inserirPersonagem.setString(5, personagem.getImagem());
      inserirPersonagem.setString(6, personagem.getCriacao());
      inserirPersonagem.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
      inserirPersonagem.setInt(8, personagem.getId());
      inserirPersonagem.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }
  
  

  
  public boolean procurar(String queryString) {
    Statement statement;
    ResultSet resultado;
    boolean res = false;
      
    try {
      statement = bd.createStatement();
      resultado = statement.executeQuery(queryString);

      res = resultado.next();
      
      statement.close(); 
      resultado.close();
     
    } catch (SQLException e) {
      e.printStackTrace();
    }
    
    return res;
    
  }

  
}
