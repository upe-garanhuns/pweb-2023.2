package br.upe.garanhus.esw.pweb.repositorio;

import br.upe.garanhus.esw.pweb.modelo.PersonagemTO;
import br.upe.garanhus.esw.pweb.modelo.RickMortyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RickMortyRepository {
  private final Connection conexao;
  private static final String MSG_ERRO_INSERCAO_BD =
      "Ocorreu um erro ao inserir os dados no banco de dados";
  private static final String MSG_ERRO_CONSULTA_BD =
      "Ocorreu um erro ao consultar os dados no banco de dados";

  public RickMortyRepository(Connection conexao) {
    this.conexao = conexao;
  }

  public void inserirPersonagem(PersonagemTO personagem) {
    String sql =
        "INSERT INTO personagem (id, nome, status, especie, genero, imagem, criacao, ultima_atualizacao) VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";

    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

      stmt.setInt(1, personagem.getId());
      stmt.setString(2, personagem.getNome());
      stmt.setString(3, personagem.getStatus());
      stmt.setString(4, personagem.getEspecie());
      stmt.setString(5, personagem.getGenero());
      stmt.setString(6, personagem.getImagem());
      stmt.setString(7, personagem.getCriacao());

      stmt.execute();
    } catch (Exception e) {
      throw new RickMortyException(MSG_ERRO_INSERCAO_BD, e);
    }
  }

  public PersonagemTO buscarPersonagem(int id) {
    PersonagemTO personagem = new PersonagemTO();
    String sql = "SELECT * FROM personagem WHERE id = ?";

    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

      stmt.setInt(1, id);

      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          personagem.setId(rs.getInt("id"));
          personagem.setNome(rs.getString("nome"));
          personagem.setStatus(rs.getString("status"));
          personagem.setEspecie(rs.getString("especie"));
          personagem.setGenero(rs.getString("genero"));
          personagem.setImagem(rs.getString("imagem"));
          personagem.setCriacao(rs.getString("criacao"));
        }
      }
    } catch (Exception e) {
      throw new RickMortyException(MSG_ERRO_CONSULTA_BD, e);
    }
    return personagem;
  }

  public void atualizarPersonagem(PersonagemTO personagem) {
    String sql =
        "UPDATE personagem SET nome=?, status=?, especie=?, genero=?, imagem=?, criacao=?, ultima_atualizacao=NOW() WHERE id=?";

    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

      stmt.setString(1, personagem.getNome());
      stmt.setString(2, personagem.getStatus());
      stmt.setString(3, personagem.getEspecie());
      stmt.setString(4, personagem.getGenero());
      stmt.setString(5, personagem.getImagem());
      stmt.setString(6, personagem.getCriacao());
      stmt.setInt(7, personagem.getId());

      stmt.execute();
    } catch (Exception e) {
      throw new RickMortyException(MSG_ERRO_INSERCAO_BD, e);
    }
  }

  public boolean isCadastrado(int id) {
    String sql = "SELECT COUNT(*) FROM personagem WHERE id = ?";
    boolean isCadastrado = false;

    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
      stmt.setInt(1, id);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          int count = rs.getInt(1);
          isCadastrado = count > 0;
        }
      }
    } catch (Exception e) {
      throw new RickMortyException(MSG_ERRO_CONSULTA_BD, e);
    }
    return isCadastrado;
  }

  public List<PersonagemTO> buscarTodosPersonagens() {
    List<PersonagemTO> personagens = new ArrayList<>();
    String sql = "SELECT * FROM personagem";

    try (PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        PersonagemTO personagem = new PersonagemTO();

        personagem.setId(rs.getInt("id"));
        personagem.setNome(rs.getString("nome"));
        personagem.setStatus(rs.getString("status"));
        personagem.setEspecie(rs.getString("especie"));
        personagem.setGenero(rs.getString("genero"));
        personagem.setImagem(rs.getString("imagem"));
        personagem.setCriacao(rs.getString("criacao"));

        personagem.setEpisodios(this.buscarEpisodios(personagem.getId()));

        personagens.add(personagem);
      }
    } catch (Exception e) {
      throw new RickMortyException(MSG_ERRO_CONSULTA_BD, e);
    }
    return personagens;
  }

  public void cadastrarEpisodio(int id, String episodio) {
    String sql = "INSERT INTO personagem_episodio (id, episodio) VALUES (?, ?)";

    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

      stmt.setInt(1, id);
      stmt.setString(2, episodio);
      stmt.execute();
    } catch (Exception e) {
      throw new RickMortyException(MSG_ERRO_INSERCAO_BD, e);
    }
  }

  public List<String> buscarEpisodios(int id) {
    List<String> episodios = new ArrayList<>();
    String sql = "SELECT * FROM personagem_episodio WHERE id = ?";

    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

      stmt.setInt(1, id);

      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          String episodio = rs.getString("episodio");
          episodios.add(episodio);
        }
      }
    } catch (Exception e) {
      throw new RickMortyException(MSG_ERRO_CONSULTA_BD, e);
    }
    return episodios;
  }

  public Connection getConexao() {
    return this.conexao;
  }
}
