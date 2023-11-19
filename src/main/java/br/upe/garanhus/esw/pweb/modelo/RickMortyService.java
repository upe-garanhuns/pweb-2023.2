package br.upe.garanhus.esw.pweb.modelo;

import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.upe.garanhus.esw.pweb.modelo.repositorio.PersonagemRepository;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.http.HttpServletResponse;

public final class RickMortyService {

  private static final Logger logger = Logger.getLogger(RickMortyService.class.getName());
  private static final Jsonb jsonb = JsonbBuilder.create();
  private static final String URL_API = "https://rickandmortyapi.com/api/";

  private static final String MSG_ERRO_MONTAR_DADOS =
      "Ocorreu um erro montar os dados da requisição para a API Web Externa";
  private static final String MSG_ERRO_CONSUMIR_DADOS =
      "Ocorreu um erro ao executar o cliente HTTP para consumir a API Web Externa";
  private static final String MSG_ERRO_GERAL = "Ocorreu um erro inesperado ao consumir a API Web Externa";
  private static final String MSG_ERRO_ID_NAO_INFORMADO =
      "É necessário informar o identificador do personagem para consumir a API Web Externa";
  private static final String MSG_ERRO_INESPERADO = "Não foi possível obter os dados da API Web: Rick and Morty";
  private static final String MSG_ERRO_SQL= "Ocorreu um erro ao executar as consulta SQL";
  private static final String MSG_ERRO_FAZER_CONEXAO = "Ocorreu um erro de conexão com o banco de dados";
  private static final String MSG_ERRO_SQL_GERAL= "Ocorreu um erro inesperado ao utilizar o banco de dados";

  private final HttpClient cliente;
  private PersonagemRepository personagemRepository;

  public RickMortyService() {
    this.cliente = HttpClient.newBuilder().proxy(ProxySelector.getDefault()).build();
  }

  public List<PersonagemTO> listar() {
    List<PersonagemTO> personagens = null;
    HttpResponse<String> response;
    personagemRepository = new PersonagemRepository();

    try {
      final HttpRequest request = HttpRequest.newBuilder().uri(new URI(URL_API + "character")).GET().build();
      response = this.cliente.send(request, BodyHandlers.ofString());

      if (HttpServletResponse.SC_OK != response.statusCode()) {
        this.tratarErroRetornoAPI(response.statusCode());
      }

      logger.log(Level.INFO, response.body());

      RespostaListaPersonagensTO respostaAPI = jsonb.fromJson(response.body(), RespostaListaPersonagensTO.class);
      personagens = respostaAPI.getPersonagens();

      personagemRepository.criarTabelas();
      personagemRepository.salvarListaPersonagem(personagens);

    } catch (Exception e) {
      this.tratarErros(e);
    }

    return personagens;
  }

  public PersonagemTO recuperar(String id) {
    PersonagemTO personagem = null;
    personagemRepository = new PersonagemRepository();

    if (id == null || id.isEmpty()) {
      logger.log(Level.SEVERE, MSG_ERRO_ID_NAO_INFORMADO);
      throw new RickMortyException(MSG_ERRO_ID_NAO_INFORMADO);
    }

    try {
      personagemRepository.criarTabelas();
      personagem = personagemRepository.recurarPersonagem(id);

    } catch (Exception e) {
      this.tratarErros(e);
    }

    return personagem;
  }

  private void tratarErros(Exception e) {
    if (e instanceof URISyntaxException) {
      logger.log(Level.SEVERE, MSG_ERRO_MONTAR_DADOS, e);
      throw new RickMortyException(MSG_ERRO_MONTAR_DADOS);
    }
    if (e instanceof InterruptedException) {
      logger.log(Level.SEVERE, MSG_ERRO_CONSUMIR_DADOS, e);
      throw new RickMortyException(MSG_ERRO_CONSUMIR_DADOS);
    } if (e instanceof SQLException) {
      logger.log(Level.SEVERE, MSG_ERRO_SQL, e);
      throw new RepositoryException(MSG_ERRO_SQL);
    } if (e instanceof ClassNotFoundException) {
      logger.log(Level.SEVERE, MSG_ERRO_FAZER_CONEXAO, e);
      throw new RepositoryException(MSG_ERRO_FAZER_CONEXAO);
    } if (e instanceof RepositoryException) {
      logger.log(Level.SEVERE, MSG_ERRO_FAZER_CONEXAO, e);
      throw new RepositoryException(MSG_ERRO_SQL_GERAL);
    } else {
      if (e instanceof RickMortyException) {
        throw (RickMortyException) e;
      } else {
        logger.log(Level.SEVERE, MSG_ERRO_GERAL, e);
        throw new RickMortyException(MSG_ERRO_GERAL, e);
      }
    }
  }

  private void tratarErroRetornoAPI(int statusCode) {
    logger.log(Level.SEVERE, MSG_ERRO_INESPERADO + "Status Code" + statusCode);
    throw new RickMortyException(MSG_ERRO_INESPERADO);
  }
}
