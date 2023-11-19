package br.upe.garanhus.esw.pweb.modelo;

import br.upe.garanhus.esw.pweb.modelo.repositories.RickMortyEpisodioRepository;
import br.upe.garanhus.esw.pweb.modelo.repositories.RickMortyPersonagemRepository;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.servlet.http.HttpServletResponse;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class RickMortyService {

  private static final Logger logger = Logger.getLogger(RickMortyService.class.getName());
  private static final Jsonb jsonb = JsonbBuilder.create();
  private static final String URL_API_GET_CHARACTERS = "https://rickandmortyapi.com/api/character";
  private static final String URL_API_GET_CHARACTER = "https://rickandmortyapi.com/api/character/";
  private static final String MSG_ADICIONANDO_PERSONAGEM =
      "Personagem não encontrado no banco, ele será adicionado agora.";
  private static final String MSG_ERRO_MONTAR_DADOS =
      "Ocorreu um erro montar os dados da requisição para a API Web Externa";
  private static final String MSG_ERRO_CONSUMIR_DADOS =
      "Ocorreu um erro ao executar o cliente HTTP para consumir a API Web Externa";
  private static final String MSG_ERRO_GERAL =
      "Ocorreu um erro inesperado ao consumir a API Web Externa";
  private static final String MSG_ERRO_ID_NAO_INFORMADO =
      "É necessário informar o identificador do personagem para consumir a API Web Externa";
  private static final String MSG_ERRO_INESPERADO =
      "Não foi possível obter os dados da API Web: Rick and Morty";
  private static final String MSG_ERRO_ID_NAO_ENCONTRADO =
      "O id informado não existe.";
  private final RickMortyPersonagemRepository rickMortyPersonagemRepository;
  private final RickMortyEpisodioRepository rickMortyEpisodioRepository;
  private final HttpClient cliente;

  public RickMortyService() {
    this.cliente = HttpClient.newBuilder().proxy(ProxySelector.getDefault()).build();
    this.rickMortyPersonagemRepository = new RickMortyPersonagemRepository();
    this.rickMortyEpisodioRepository = new RickMortyEpisodioRepository();
  }

  public List<PersonagemTO> listarPersonagens() {
    List<PersonagemTO> listaPersonagens = new ArrayList<>();
    try {
      List<PersonagemTO> personagensAPI = jsonb.fromJson(consultarAPI(URL_API_GET_CHARACTERS),
          RespostaListaPersonagensTO.class).getPersonagens();

      for (PersonagemTO personagem : personagensAPI) {
        listaPersonagens.add(recuperarPersonagem(personagem.getId()));
      }
    } catch (JsonbException e) {
      logger.log(Level.SEVERE, MSG_ERRO_MONTAR_DADOS);
      throw new RickMortyException(MSG_ERRO_MONTAR_DADOS);
    }

    return listaPersonagens;
  }

  public PersonagemTO recuperarPersonagem(int id) {
    PersonagemTO personagem;
    try {
      PersonagemTO personagemAPI = jsonb.fromJson(consultarAPI(URL_API_GET_CHARACTER + id),
          PersonagemTO.class);

      if (personagemAPI == null) {
        logger.log(Level.SEVERE, MSG_ERRO_ID_NAO_ENCONTRADO);
        throw new RickMortyException(MSG_ERRO_ID_NAO_INFORMADO);

      }

      salvarPersonagem(personagemAPI);
      personagem = rickMortyPersonagemRepository.encontrarPersonagemPorId(personagemAPI.getId());
      personagem.setEpisodios(rickMortyEpisodioRepository
          .encontrarEpisodiosPorIdPersonagem(personagemAPI.getId()));

    } catch (JsonbException e) {
      logger.log(Level.SEVERE, MSG_ERRO_MONTAR_DADOS);
      throw new RickMortyException(MSG_ERRO_MONTAR_DADOS);
    }

    return personagem;
  }

  private void salvarPersonagem(PersonagemTO personagem) {
    if (rickMortyPersonagemRepository.encontrarPersonagemPorId(personagem.getId()) == null) {
      logger.log(Level.INFO, MSG_ADICIONANDO_PERSONAGEM);
      rickMortyPersonagemRepository.salvarPersonagem(personagem);
      rickMortyEpisodioRepository.salvarEpisodios(personagem);
      rickMortyEpisodioRepository.salvarRelacaoEpisodioPersonagem(personagem);
    } else {
      rickMortyPersonagemRepository.atualizarStatus(personagem);
    }
  }

  private String consultarAPI(String url) {
    HttpResponse<String> response = null;

    try {
      final HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).GET().build();
      response = this.cliente.send(request, BodyHandlers.ofString());

      if (HttpServletResponse.SC_OK != response.statusCode()) {
        this.tratarErroRetornoAPI(response.statusCode());
      }

      logger.log(Level.INFO, response.body());

    } catch (InterruptedException e) {
      logger.log(Level.WARNING, "Thread interrompida");
      Thread.currentThread().interrupt();
    } catch (Exception e) {
      this.tratarErros(e);
    }

    return response != null ? response.body() : null;
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
      if (e instanceof RickMortyException exception) {
        throw exception;
      } else {
        logger.log(Level.SEVERE, MSG_ERRO_GERAL, e);
        throw new RickMortyException(MSG_ERRO_GERAL, e);
      }
    }
  }

  private void tratarErroRetornoAPI(int statusCode) {
    logger.log(Level.SEVERE, () -> MSG_ERRO_INESPERADO + "Status Code" + statusCode);
    throw new RickMortyException(MSG_ERRO_INESPERADO);
  }
}
