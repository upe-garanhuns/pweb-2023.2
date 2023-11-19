package br.upe.garanhus.esw.pweb.repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import br.upe.garanhus.esw.pweb.modelo.PersonagemTO;

public class PersonagemRepositorio {

  private ConexaoBD conexaoBD;
  private Connection bd;
  private EpisodioRepositorio episodioRepo;
  
  public PersonagemRepositorio() {
    this.conexaoBD = new ConexaoBD();
    this.bd = conexaoBD.getConexao();
    this.episodioRepo = new EpisodioRepositorio();
  }
  
  public void inserirPersonagem(PersonagemTO personagem) {   
    final String queryString = "INSERT INTO personagens VALUES (?,?,?,?,?,?,?,?);";
    
    try {
      PreparedStatement prepStmt = bd.prepareStatement(queryString);
      prepStmt.setInt(1, personagem.getId());
      prepStmt.setString(2, personagem.getNome());
      prepStmt.setString(3, personagem.getStatus());
      prepStmt.setString(4, personagem.getEspecie());
      prepStmt.setString(5, personagem.getGenero());
      prepStmt.setString(6, personagem.getImagem());
      prepStmt.setString(7, personagem.getCriacao());
      prepStmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
      prepStmt.executeUpdate();
      
      prepStmt.close();
      
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }
  
  public void atualizarPersonagem(PersonagemTO personagem) {
    final String queryString = "UPDATE personagens SET nome=?, status=?, especie=?, "
        + "genero=?, imagem=?, criacao=?, atualizacao=? WHERE id=? ;";
    
    PreparedStatement prepStmt;
    
    try {
      prepStmt = bd.prepareStatement(queryString);
      prepStmt.setString(1, personagem.getNome());
      prepStmt.setString(2, personagem.getStatus());
      prepStmt.setString(3, personagem.getEspecie());
      prepStmt.setString(4, personagem.getGenero());
      prepStmt.setString(5, personagem.getImagem());
      prepStmt.setString(6, personagem.getCriacao());
      prepStmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
      prepStmt.setInt(8, personagem.getId());
      prepStmt.executeUpdate();
      
      prepStmt.close();
      
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  public PersonagemTO encontrarPersonagem(int id) {
    final String queryString  = "SELECT * FROM personagens WHERE id = '" + id + "'";
    
    PreparedStatement prepStmt;
    ResultSet resultado;
    PersonagemTO personagem = null;
      
    try {
      prepStmt = bd.prepareStatement(queryString);
      resultado = prepStmt.executeQuery();

      while(resultado.next()) {
        personagem = new PersonagemTO();
        personagem.setId(resultado.getInt("id"));
        personagem.setNome(resultado.getString("nome"));
        personagem.setStatus(resultado.getString("status"));
        personagem.setEspecie(resultado.getString("especie"));
        personagem.setGenero(resultado.getString("genero"));
        personagem.setImagem(resultado.getString("imagem"));
        personagem.setCriacao(resultado.getString("criacao"));
        personagem.setEpisodios(episodioRepo.encontrarEpsPersonagem(id));
      }

      resultado.close();
      prepStmt.close(); 
     
    } catch (SQLException e) {
      e.printStackTrace();
    }
    
    return personagem;
  }

}
