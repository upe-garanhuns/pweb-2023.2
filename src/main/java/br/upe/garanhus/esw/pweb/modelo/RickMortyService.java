package br.upe.garanhus.esw.pweb.modelo;

import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.upe.garanhus.esw.pweb.repositorio.FabricaConexao;
import br.upe.garanhus.esw.pweb.repositorio.PersonagemRepository;
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

  private final HttpClient cliente;

  private PersonagemRepository personagemRepository = new PersonagemRepository();
  Connection conexao = null;

  public RickMortyService() {
    this.cliente = HttpClient.newBuilder().proxy(ProxySelector.getDefault()).build();
  }

  public List<PersonagemTO> listar() {
    List<PersonagemTO> personagensBD = new ArrayList<>();
    HttpResponse<String> response = null;
    Connection conexao = null;

    try {
      final HttpRequest request = HttpRequest.newBuilder().uri(new URI(URL_API + "character")).GET().build();
      response = this.cliente.send(request, BodyHandlers.ofString());

      if (HttpServletResponse.SC_OK != response.statusCode()) {
        this.tratarErroRetornoAPI(response.statusCode());
      }

      logger.log(Level.INFO, response.body());

      RespostaListaPersonagensTO respostaAPI = jsonb.fromJson(response.body(), RespostaListaPersonagensTO.class);
      List<PersonagemTO> personagensAPI = respostaAPI.getPersonagens();

      conexao = FabricaConexao.getConexao();

      for (PersonagemTO p : personagensAPI) {
        if (personagemRepository.verifyPersonagem(conexao, p)) {
          personagemRepository.updatePersonagem(conexao, p);
        } else {
          personagemRepository.addPersonagem(conexao, p);
        }

        PersonagemTO pBD = personagemRepository.findPersonagem(conexao, p.getId());
        personagensBD.add(pBD);
      }

    } catch (Exception e) {
      this.tratarErros(e);
    } finally {
      if (conexao != null) {
        try {
          conexao.close();
        } catch (SQLException e) {
          this.tratarErros(e);
        }
      }
    }

    return personagensBD;
  }


  public PersonagemTO recuperar(String id) {
    PersonagemTO personagemBD = null;
    Connection conexao = null;

    if (id == null || id.isEmpty()) {
      logger.log(Level.SEVERE, MSG_ERRO_ID_NAO_INFORMADO);
      throw new RickMortyException(MSG_ERRO_ID_NAO_INFORMADO);
    }

    try {
      HttpClient cliente = HttpClient.newHttpClient();

      final HttpRequest request = HttpRequest.newBuilder().uri(new URI(URL_API + "character/" + id)).GET().build();
      HttpResponse<String> response = cliente.send(request, BodyHandlers.ofString());

      if (HttpServletResponse.SC_OK != response.statusCode()
              && HttpServletResponse.SC_NOT_FOUND != response.statusCode()) {
        this.tratarErroRetornoAPI(response.statusCode());
      }

      PersonagemTO personagemAPI = jsonb.fromJson(response.body(), PersonagemTO.class);

      conexao = FabricaConexao.getConexao();

      if (personagemRepository.verifyPersonagem(conexao, personagemAPI)) {
        personagemRepository.updatePersonagem(conexao, personagemAPI);
      } else {
        personagemRepository.addPersonagem(conexao, personagemAPI);
      }

      personagemBD = personagemRepository.findPersonagem(conexao, personagemAPI.getId());

      logger.log(Level.INFO, response.body());

    } catch (Exception e) {
      this.tratarErros(e);
    } finally {
      if (conexao != null) {
        try {
          conexao.close();
        } catch (SQLException e) {
          this.tratarErros(e);
        }
      }
    }

    return personagemBD;
  }


  private void tratarErros(Exception e) {
    if (e instanceof URISyntaxException) {
      logger.log(Level.SEVERE, MSG_ERRO_MONTAR_DADOS, e);
      throw new RickMortyException(MSG_ERRO_MONTAR_DADOS);
    }
    if (e instanceof InterruptedException) {
      logger.log(Level.SEVERE, MSG_ERRO_CONSUMIR_DADOS, e);
      throw new RickMortyException(MSG_ERRO_CONSUMIR_DADOS);
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
