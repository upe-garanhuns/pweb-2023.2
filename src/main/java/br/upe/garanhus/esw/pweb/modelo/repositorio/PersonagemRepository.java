package br.upe.garanhus.esw.pweb.modelo.repositorio;

import br.upe.garanhus.esw.pweb.modelo.PersonagemTO;
import br.upe.garanhus.esw.pweb.modelo.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonagemRepository {

  private static final Logger logger = Logger.getLogger(PersonagemRepository.class.getName());

  private static final String MSG_ERRO_SQL = "Ocorreu um erro ao executar as consulta SQL";
  private static final String MSG_ERRO_GERAL = "Ocorreu um erro inesperado ao utilizar o banco de dados";
  private static final String NOME_BD = "Personagens";
  private static final String USUARIO = "postgres";
  private static final String SENHA = "noobnaoentra";
  private static final String QUERY_CRIAR_TABELA = "CREATE TABLE IF NOT EXISTS Personagem (" +
      "id INT PRIMARY KEY, " +
      "nome VARCHAR(255), " +
      "status VARCHAR(255), " +
      "especie VARCHAR(255), " +
      "genero VARCHAR(255), " +
      "imagem VARCHAR(255), " +
      "criacao VARCHAR(255))";

  private static final String QUERY_CRIAR_TABELA_ASSOCIACAO =
      "CREATE TABLE IF NOT EXISTS PersonagemEpisodio (" +
          "id_personagem INT, " +
          "episodio VARCHAR(255), " +
          "PRIMARY KEY (id_personagem, episodio), " +
          "FOREIGN KEY (id_personagem) REFERENCES Personagem(id))";
  private static final String QUERY_RECUPERAR_PERSONAGEM = "SELECT * FROM Personagem WHERE id = ";
  private static final String QUERY_RECUPERAR_PERSONAGEM_EPISODIOS =
      "SELECT episodio FROM PersonagemEpisodio " +
          "WHERE id_personagem = ";
  private static final String QUERY_SALVAR_PERSONAGENS = "INSERT INTO Personagem " +
      "(id, nome, status, especie, genero, imagem, criacao) VALUES (?, ?, ?, ?, ?, ?, ?)";
  private static final String QUERY_SALVAR_EPISODIOS = "INSERT INTO PersonagemEpisodio " +
      "(id_personagem, episodio) VALUES (?, ?)";

  private final ConexaoRepository conexaoRepository;
  private PersonagemTO personagemTO;

  public PersonagemRepository() {
    this.conexaoRepository = new ConexaoRepository();
  }

  public void salvarListaPersonagem(List<PersonagemTO> personagens) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = conexaoRepository.criarConexao(NOME_BD, USUARIO, SENHA);
      preparedStatement = connection.prepareStatement(QUERY_SALVAR_PERSONAGENS);

      for (PersonagemTO personagem : personagens) {
        preparedStatement.setInt(1, personagem.getId());
        preparedStatement.setString(2, personagem.getNome());
        preparedStatement.setString(3, personagem.getStatus());
        preparedStatement.setString(4, personagem.getEspecie());
        preparedStatement.setString(5, personagem.getGenero());
        preparedStatement.setString(6, personagem.getImagem());
        preparedStatement.setString(7, personagem.getCriacao());

        preparedStatement.executeUpdate();
      }

      preparedStatement = connection.prepareStatement(QUERY_SALVAR_EPISODIOS);

      for (PersonagemTO personagem : personagens) {
        for (int i = 0; i < personagem.getEpisodios().size(); i++) {
          preparedStatement.setInt(1, personagem.getId());
          preparedStatement.setString(2, personagem.getEpisodios().get(i));

          preparedStatement.executeUpdate();
        }
      }

    } catch (Exception e) {
      this.tratarErros(e);

    } finally {
      conexaoRepository.fecharStatement(preparedStatement);
      conexaoRepository.fecharConexao(connection);
    }
  }

  public PersonagemTO recurarPersonagem(String id) {
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSetPersonagem = null;
    ResultSet resultSetEpisodio = null;

    try {
      connection = conexaoRepository.criarConexao(NOME_BD, USUARIO, SENHA);
      statement = connection.createStatement();

      resultSetPersonagem = statement.executeQuery(QUERY_RECUPERAR_PERSONAGEM + id);

      while (resultSetPersonagem.next()) {
        personagemTO = new PersonagemTO();
        personagemTO.setId(resultSetPersonagem.getInt("id"));
        personagemTO.setNome(resultSetPersonagem.getString("nome"));
        personagemTO.setStatus(resultSetPersonagem.getString("status"));
        personagemTO.setEspecie(resultSetPersonagem.getString("especie"));
        personagemTO.setGenero(resultSetPersonagem.getString("genero"));
        personagemTO.setImagem(resultSetPersonagem.getString("imagem"));
        personagemTO.setCriacao(resultSetPersonagem.getString("criacao"));
      }

      resultSetEpisodio = statement.executeQuery(QUERY_RECUPERAR_PERSONAGEM_EPISODIOS + id);

      personagemTO.setEpisodios(new ArrayList<>());

      while (resultSetEpisodio.next()) {
        personagemTO.getEpisodios().add(resultSetEpisodio.getString("episodio"));
      }

    } catch (Exception e) {
      this.tratarErros(e);

    } finally {
      conexaoRepository.fecharResultSet(resultSetEpisodio);
      conexaoRepository.fecharResultSet(resultSetPersonagem);
      conexaoRepository.fecharStatement(statement);
      conexaoRepository.fecharConexao(connection);
    }

    return personagemTO;
  }

  public void criarTabelas() {
    Connection connection = null;
    Statement statement = null;

    try {
      connection = conexaoRepository.criarConexao(NOME_BD, USUARIO, SENHA);
      statement = connection.createStatement();
      statement.executeUpdate(QUERY_CRIAR_TABELA);
      statement.executeUpdate(QUERY_CRIAR_TABELA_ASSOCIACAO);

    } catch (Exception e) {
      this.tratarErros(e);

    } finally {
      conexaoRepository.fecharStatement(statement);
      conexaoRepository.fecharConexao(connection);
    }
  }

  public void tratarErros(Exception e) {
    if (e instanceof SQLException) {
      logger.log(Level.SEVERE, MSG_ERRO_SQL, e);
      throw new RepositoryException(MSG_ERRO_SQL);
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
