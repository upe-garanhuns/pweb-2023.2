package br.upe.garanhus.esw.pweb.repositorio;

import br.upe.garanhus.esw.pweb.modelo.PersonagemTO;

import java.sql.*;
import java.util.List;

public class PersonagemRepository {

  EpisodioRepository episodioRepo = new EpisodioRepository();
  PersonagemEpRepository pesonagemEpRepo = new PersonagemEpRepository();

  public void addPersonagem(Connection conexao, PersonagemTO p) throws SQLException {
    PreparedStatement stmt = null;
    try {
      String sql =
          "INSERT INTO personagens (id, nome,status, especie, genero, imagem, criacao) VALUES (?,?,?,?,?,?,?)";

      stmt = conexao.prepareStatement(sql);
      stmt.setInt(1, p.getId());
      stmt.setString(2, p.getNome());
      stmt.setString(3, p.getStatus());
      stmt.setString(4, p.getEspecie());
      stmt.setString(5, p.getGenero());
      stmt.setString(6, p.getImagem());
      stmt.setString(7, p.getCriacao());

      stmt.execute();

      List<String> eps = p.getEpisodios();
      for (String ep : eps) {
        if (episodioRepo.verifyEpisodio(conexao, ep)) {
          episodioRepo.updateEpisodio(conexao, ep);
        } else {
          episodioRepo.addEpisodio(conexao, ep);
        }

        if (!pesonagemEpRepo.relacaoJaExiste(conexao, p)) {
          pesonagemEpRepo.addPersonagemEpisodio(conexao, p, ep);
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      if (stmt != null) {
        stmt.close();
      }
    }
  }

  public void updatePersonagem(Connection conexao, PersonagemTO p) throws SQLException {
    PreparedStatement stmt = null;
    try {
      String sql = "UPDATE personagens SET data_atualizacao = CURRENT_TIMESTAMP WHERE id = ?";

      stmt = conexao.prepareStatement(sql);
      stmt.setInt(1, p.getId());
      stmt.execute();

      List<String> eps = p.getEpisodios();
      for (String ep : eps) {
        if (episodioRepo.verifyEpisodio(conexao, ep)) {
          episodioRepo.updateEpisodio(conexao, ep);
        } else {
          episodioRepo.addEpisodio(conexao, ep);
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      stmt.close();
    }
  }

  public PersonagemTO findPersonagem(Connection conexao, int id) throws SQLException {

    PreparedStatement stmt = null;
    ResultSet resultado = null;
    PersonagemTO personagem = null;

    try {
      String sql = "SELECT * FROM personagens WHERE id = ?";

      stmt = conexao.prepareStatement(sql);
      stmt.setInt(1, id);

      resultado = stmt.executeQuery();

      personagem = new PersonagemTO();

      while (resultado.next()) {
        personagem.setId(resultado.getInt("id"));
        personagem.setNome(resultado.getString("nome"));
        personagem.setStatus(resultado.getString("status"));
        personagem.setEspecie(resultado.getString("especie"));
        personagem.setGenero(resultado.getString("genero"));
        personagem.setImagem(resultado.getString("imagem"));
        personagem.setCriacao(resultado.getString("criacao"));
      }
      List<String> episodios = pesonagemEpRepo.pegarEpisodios(conexao, personagem);
      personagem.setEpisodios(episodios);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      if (resultado != null) {
        resultado.close();
      }
    }

    return personagem;
  }

  public boolean verifyPersonagem(Connection conexao, PersonagemTO p) throws SQLException {
    PreparedStatement stmt = null;
    ResultSet resultado = null;

    try {
      String sql = "SELECT COUNT(*) as count FROM personagens WHERE id = ?";

      stmt = conexao.prepareStatement(sql);
      stmt.setInt(1, p.getId());

      resultado = stmt.executeQuery();
      if (resultado.next()) {
        int quant = resultado.getInt("count");
        return quant > 0;
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      if (stmt != null) {
        stmt.close();
      }
      if (resultado != null) {
        resultado.close();
      }
    }
    return false;
  }
}
