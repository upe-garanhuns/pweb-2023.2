package br.upe.garanhus.esw.pweb.modelo;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import br.upe.garanhus.esw.pweb.modelo.DTO.DogDTO;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class DogApiResponse {
	private static HttpClient client;
	  private static HttpRequest request;
	  private static final Logger logger = Logger.getLogger(DogApiResponse.class.getName());
	  private static final String BASE_URL = "https://api.thedogapi.com/v1/images/search?limit=10";

	  private static final String ERROR_REQUEST = "Erro - Requisição HTTP.";
	  private static final String ERROR_FETCH_DATA = "Erro - Busca dos dados da API.";
	  private static final String ERROR_PARSE_DATA = "Erro - Análise dos dados dos dogs.";
	  
	  private static final String LOG_FETCH_DATA = "Atenção - Estamos buscando dados da API.";
	  private static final String LOG_REQUEST_BODY = "Atenção - Body da request: ";
	  private static final String LOG_ANALYZE_DATA = "Atenção - Estamos analisando dados recebidos.";

	  public DogApiResponse() {
	    client = HttpClient.newHttpClient();
	    request = HttpRequest.newBuilder().uri(URI.create(BASE_URL)).build();
	  }

	  public String fetchData() {
	    try {
	      logger.info(LOG_FETCH_DATA);
	      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	      if (response.statusCode() == 200) {
	        logger.info(LOG_REQUEST_BODY + response.body());
	        return response.body();
	      } else {
	        logger.severe(ERROR_REQUEST);
	        throw new ApplicationException(ERROR_REQUEST, new ApplicationException(), response.statusCode());
	      }
	    } catch (IOException | InterruptedException e) {
	      logger.severe(ERROR_REQUEST);
	      throw new ApplicationException(ERROR_FETCH_DATA, e, 502);
	    }
	  }

	  public List<DogDTO> parseData(String response) {
	    List<DogDTO> dogList = new ArrayList<>();
	    
	    try {
	      logger.info(LOG_ANALYZE_DATA);
	      JsonReader jsonReader = Json.createReader(new StringReader(response));
	      JsonArray jsonArray = jsonReader.readArray();

	      for (JsonObject dogJson : jsonArray.getValuesAs(JsonObject.class)) {
	        String id = dogJson.getString("id");
	        String url = dogJson.getString("url");
	        int width = dogJson.getInt("width");
	        int height = dogJson.getInt("height");
	        DogDTO dogDTO = new DogDTO(id, url, width, height);
	        dogList.add(dogDTO);
	      }
	    } catch (jakarta.json.JsonException e) {
	      logger.severe(ERROR_PARSE_DATA);
	      throw new ApplicationException(ERROR_PARSE_DATA, e, 500);
	    }

	    return dogList;
	  }
}
