package br.upe.garanhus.esw.pweb.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

@SuppressWarnings("unchecked")
public class RickMortyRepository {

	private static Timestamp timestamp;

	private static final Logger logger = Logger.getLogger(RickMortyRepository.class.getName());
	private static final Jsonb jsonb = JsonbBuilder.create();

	private static final String STRING_CONEXAO = "jdbc:postgresql://localhost:5432/servlet_db";
	private static final String USUARIO = "postgres";
	private static final String SENHA = "admin";
	private static final String INSERT_PERSONAGENS_SQL = "INSERT INTO characters (id, name, status, species, gender, image, episodes, created, updated_at) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " + "ON CONFLICT (id) DO UPDATE SET " + "name = EXCLUDED.name, "
			+ "status = EXCLUDED.status, " + "species = EXCLUDED.species, " + "gender = EXCLUDED.gender, "
			+ "image = EXCLUDED.image, " + "episodes = EXCLUDED.episodes, " + "updated_at = EXCLUDED.updated_at";
	private static final String SELECT_ALL = "SELECT * FROM characters ";
	private static final String SELECT_BY_ID = "SELECT * FROM characters WHERE id = ";
	
	private static final String MSG_DRIVER_NAO_ENCONTRADO = "Driver do banco de dados não encontrado.";
	private static final String MSG_ERRO_SQL = "Não foi possível estabelecer uma conexão com o banco de dados.";

	private static final String MSG_ERRO_SALVAR = "Não foi possível inserir os dados no banco de dados.";
	private static final String MSG_ERRO_RECUPERAR = "Não foi possível recuperar os dados do banco de dados.";

	private static final String CONEXAO_SUCESSO = "Conexao estabelecida com sucesso com o banco de dados: ";

	
	public Connection getConnection() {
		Connection cn = null;

		try {
			Class.forName("org.postgresql.Driver");
			cn = DriverManager.getConnection(STRING_CONEXAO, USUARIO, SENHA);
			logger.log(Level.INFO, CONEXAO_SUCESSO + cn.getClientInfo());
		} catch (ClassNotFoundException e) {
		    logger.log(Level.SEVERE, MSG_DRIVER_NAO_ENCONTRADO);
			throw new RickMortyException(MSG_DRIVER_NAO_ENCONTRADO);
		} catch (SQLException e) {
		    logger.log(Level.SEVERE, MSG_ERRO_SQL);
			throw new RickMortyException(MSG_ERRO_SQL);
		}

		
		return cn;
	}

	public void salvar(PersonagemTO personagem) {

		timestamp = Timestamp.valueOf(LocalDateTime.now());
		List<String> episodios = personagem.getEpisodios();
		String epJson = jsonb.toJson(episodios);

		try (Connection cn = getConnection();
				PreparedStatement psPersonagens = cn.prepareStatement(INSERT_PERSONAGENS_SQL);) {

			psPersonagens.setInt(1, personagem.getId());
			psPersonagens.setString(2, personagem.getNome());
			psPersonagens.setString(3, personagem.getStatus());
			psPersonagens.setString(4, personagem.getEspecie());
			psPersonagens.setString(5, personagem.getGenero());
			psPersonagens.setString(6, personagem.getImagem());
			psPersonagens.setString(7, epJson);
			psPersonagens.setTimestamp(8, personagem.getCriacao());
			psPersonagens.setTimestamp(9, timestamp);

			psPersonagens.executeUpdate();
		} catch (SQLException e) {
		    logger.log(Level.SEVERE, MSG_ERRO_SALVAR);
			throw new RickMortyException(MSG_ERRO_SALVAR);
		}
	}

	public List<PersonagemTO> recuperarTodos() {
		List<PersonagemTO> personagens = new ArrayList<>();

		try (Connection cn = getConnection();
				PreparedStatement psRecuperar = cn.prepareStatement(SELECT_ALL + "ORDER BY id");
				ResultSet rs = psRecuperar.executeQuery()) {

			while (rs.next()) {
				PersonagemTO personagem = new PersonagemTO();
				personagem.setId(rs.getInt("id"));
				personagem.setNome(rs.getString("name"));
				personagem.setStatus(rs.getString("status"));
				personagem.setEspecie(rs.getString("species"));
				personagem.setGenero(rs.getString("gender"));
				personagem.setImagem(rs.getString("image"));

				String epJson = rs.getString("episodes");
				List<String> episodios = jsonb.fromJson(epJson, new ArrayList<String>().getClass());
				personagem.setEpisodios(episodios);

				personagem.setCriacao(rs.getTimestamp("created"));

				personagens.add(personagem);
			    logger.log(Level.INFO, jsonb.toJson(personagens));

			}

		} catch (SQLException e) {
		    logger.log(Level.SEVERE, MSG_ERRO_RECUPERAR);
			throw new RickMortyException(MSG_ERRO_RECUPERAR);
		}

		return personagens;
	}

	public PersonagemTO recuperarPorId(String id) {
		PersonagemTO personagem = null;

		try (Connection cn = getConnection();
				PreparedStatement psRecuperar = cn.prepareStatement(SELECT_BY_ID + id);
				ResultSet rs = psRecuperar.executeQuery()) {

			if (rs.next()) {
				personagem = new PersonagemTO();
				personagem.setId(rs.getInt("id"));
				personagem.setNome(rs.getString("name"));
				personagem.setStatus(rs.getString("status"));
				personagem.setEspecie(rs.getString("species"));
				personagem.setGenero(rs.getString("gender"));
				personagem.setImagem(rs.getString("image"));

				String epJson = rs.getString("episodes");
				List<String> episodios = jsonb.fromJson(epJson, new ArrayList<String>().getClass());
				personagem.setEpisodios(episodios);

				personagem.setCriacao(rs.getTimestamp("created"));
			    logger.log(Level.INFO, jsonb.toJson(personagem));
			}

		} catch (SQLException e) {
		    logger.log(Level.SEVERE, MSG_ERRO_RECUPERAR);
			throw new RickMortyException(MSG_ERRO_RECUPERAR);
		}

		return personagem;
	}

}
