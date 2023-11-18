package br.upe.garanhus.esw.pweb.modelo;

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
import br.upe.garanhus.esw.pweb.repositorio.EpisodioRepositorio;
import br.upe.garanhus.esw.pweb.repositorio.PersonagemRepositorio;
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
  private final PersonagemRepositorio personagemRepo;
  private final EpisodioRepositorio episodioRepo;

  public RickMortyService() {
    this.cliente = HttpClient.newBuilder().proxy(ProxySelector.getDefault()).build();
    this.personagemRepo = new PersonagemRepositorio();
    this.episodioRepo = new EpisodioRepositorio();
  }

  public List<PersonagemTO> listar() {
    ArrayList<PersonagemTO> personagens = new ArrayList<>();
    HttpResponse<String> response = null;
    
    try {
      final HttpRequest request = HttpRequest.newBuilder().uri(new URI(URL_API + "character/")).GET().build();
      response = this.cliente.send(request, BodyHandlers.ofString());

      if (HttpServletResponse.SC_OK != response.statusCode()) {
        this.tratarErroRetornoAPI(response.statusCode());
      }

      logger.log(Level.INFO, response.body());

      RespostaListaPersonagensTO respostaAPI = jsonb.fromJson(response.body(), RespostaListaPersonagensTO.class);
      
      respostaAPI.getPersonagens().forEach(personagem -> {
        salvarPersonagem(personagem);
        personagens.add(personagemRepo.encontrarPersonagem(personagem.getId()));
      });
      
    } catch (Exception e) {
      this.tratarErros(e);
    }

    return personagens;
  }

  public PersonagemTO recuperar(String id) {
    PersonagemTO personagem = null;
    HttpResponse<String> response = null;

    if (id == null || id.isEmpty()) {
      logger.log(Level.SEVERE, MSG_ERRO_ID_NAO_INFORMADO);
      throw new RickMortyException(MSG_ERRO_ID_NAO_INFORMADO);
    }

    try {
      final HttpRequest request = HttpRequest.newBuilder().uri(new URI(URL_API + "character/" + id)).GET().build();
      response = cliente.send(request, BodyHandlers.ofString());

      if (HttpServletResponse.SC_OK != response.statusCode()
          && HttpServletResponse.SC_NOT_FOUND != response.statusCode()) {
        this.tratarErroRetornoAPI(response.statusCode());
      }

      personagem = jsonb.fromJson(response.body(), PersonagemTO.class);
      logger.log(Level.INFO, response.body());
      
      salvarPersonagem(personagem);      
      personagem = personagemRepo.encontrarPersonagem(personagem.getId());
      
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
  
  private void salvarPersonagem(PersonagemTO personagem) { 
    if(personagemRepo.encontrarPersonagem(personagem.getId()) != null) {
      personagemRepo.atualizarPersonagem(personagem);
    } else {
      personagemRepo.inserirPersonagem(personagem);
    }
    
    personagem.getEpisodios().forEach(episodio -> {
      salvarEpisodio(episodio);
      salvarPersonagemEp(personagem.getId(), episodio);
    });
  }
  
  private void salvarEpisodio(String url) {

    EpisodioTO episodio = episodioRepo.encontrarEpisodio(url);

    if(episodio == null) {
      episodioRepo.inserirEpisodio(url);
    }
  }
  
  private void salvarPersonagemEp(int idPersonagem, String url) {
    EpisodioTO episodio = episodioRepo.encontrarEpisodio(url);
    boolean epExiste = episodioRepo.encontrarPersonagemEpPorId(idPersonagem, episodio.getId());
    
    if(!epExiste) {
      episodioRepo.inserirEpPersonagem(idPersonagem, episodio.getId());
    }
  }
  
}
