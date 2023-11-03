package br.upe.garanhus.esw.pweb.modelo.servico;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import br.upe.garanhus.esw.pweb.modelo.ApplicationException;
import br.upe.garanhus.esw.pweb.modelo.DogApiResponse;
import br.upe.garanhus.esw.pweb.modelo.NoDogFoundException;
import br.upe.garanhus.esw.pweb.modelo.DTO.DogDTO;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.servlet.http.HttpServletRequest;

public class DogService {
	
	private DogApiResponse dogApiResponse;
	  private List<DogDTO> dogList;
	  private JsonArray dogJsonArray;
	  private JsonArrayBuilder jsonArrayBuilder;
	  private static final Logger logger = Logger.getLogger(DogService.class.getName());

	  private static final String ERROR_FETCH_DOGS = "Erro - Recuperação de todos os dados.";
	  private static final String ERROR_NO_DOG_FOUND = "Erro - Nenhum dado encontrado para o ID: ";
	  private static final String ERROR_READ_REQUEST_BODY = "Erro - Leitura ID do corpo da solicitação.";
	  private static final String ERROR_REQUIRE_GET_REQUEST = "Erro - Primeiro garanta que está fazendo uma solicitação GET para /atividade-exec02/processa-imagem";

	  private static final String LOG_FETCH_ALL_DOGS = "Atenção - Estamos tentando recuperar os dados.";
	  private static final String LOG_FETCH_DOG_BY_ID = "Atenção - Estamos tentando recuperar o cachorro com o id: ";
	  private static final String LOG_READ_ID_FROM_REQUEST = "Atenção - Estamos tentando recuperar o id da requisição.";
	  private static final String LOG_BUILD_JSON_OBJECT = "Construindo objeto JSON.";

	  public DogService() {
	    dogApiResponse = new DogApiResponse();
	    jsonArrayBuilder = Json.createArrayBuilder();
	  }

	  public JsonArray getAllDogs() {
	    try {
	      logger.info(LOG_FETCH_ALL_DOGS);
	      dogList = dogApiResponse.parseData(dogApiResponse.fetchData());

	      for (DogDTO dogDTO : dogList) {
	        JsonObject dogObject = buildDogObject(dogDTO);
	        jsonArrayBuilder.add(dogObject);
	      }

	      dogJsonArray = jsonArrayBuilder.build();
	      logger.info(LOG_BUILD_JSON_OBJECT);
	    } catch (ApplicationException e) {
	      logger.severe(ERROR_FETCH_DOGS);
	      throw new ApplicationException(ERROR_FETCH_DOGS, e, 502);
	    }

	    return dogJsonArray;
	  }

	  public JsonArray getDogById(String id) {
	    boolean dogFound = false;

	    try {
	      if (dogList == null) {
	        logger.severe(ERROR_REQUIRE_GET_REQUEST);
	        throw new ApplicationException(ERROR_REQUIRE_GET_REQUEST, new ApplicationException(), 400);
	      }
	      
	      logger.info(LOG_FETCH_DOG_BY_ID + id);
	      
	      for (DogDTO dogDTO : dogList) {
	        if (dogDTO.getId().equals(id)) {
	          JsonObject dogArray = buildDogObject(dogDTO);
	          jsonArrayBuilder.add(dogArray);
	          dogFound = true;
	        }
	      }

	      if (!dogFound) {
	        logger.severe(ERROR_NO_DOG_FOUND + id);
	        throw new NoDogFoundException(ERROR_NO_DOG_FOUND + id, new NoDogFoundException(), 404);
	      }
	      
	      dogJsonArray = jsonArrayBuilder.build();
	    } catch (NoDogFoundException e) {
	      logger.severe(ERROR_NO_DOG_FOUND + id);
	      throw new NoDogFoundException(ERROR_NO_DOG_FOUND + id, e, 404);
	    } catch (ApplicationException e) {
	      logger.severe(ERROR_NO_DOG_FOUND + id);
	      throw new NoDogFoundException(ERROR_NO_DOG_FOUND + id, e, 400);
	    }

	    return dogJsonArray;
	  }

	  public String getIdFromRequest(HttpServletRequest request) {
	    try {
	      logger.info(LOG_READ_ID_FROM_REQUEST);
	      JsonReader reader = Json.createReader(request.getReader());
	      JsonObject requestBody = reader.readObject();
	      return requestBody.getString("id");
	    } catch (IOException e) {
	      logger.severe(ERROR_READ_REQUEST_BODY);
	      throw new ApplicationException(ERROR_READ_REQUEST_BODY, e, 400);
	    }
	  }

	  protected JsonObject buildDogObject(DogDTO dogDTO) {
	    return Json.createObjectBuilder()
	        .add("id", dogDTO.getId())
	        .add("url", dogDTO.getUrl())
	        .add("width", dogDTO.getWidth())
	        .add("height", dogDTO.getHeight())
	        .build();
	  }
}
