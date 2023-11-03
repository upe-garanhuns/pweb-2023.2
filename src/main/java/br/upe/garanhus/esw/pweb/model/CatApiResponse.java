package br.upe.garanhus.esw.pweb.model;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import br.upe.garanhus.esw.pweb.model.DTO.CatDTO;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class CatApiResponse {

  private static HttpClient client;
  private static HttpRequest request;
  private static final Logger logger = Logger.getLogger(CatApiResponse.class.getName());
  private static final String BASE_URL = "https://api.thecatapi.com/v1/images/search?limit=10";

  private static final String ERROR_REQUEST = "Erro na requisição HTTP.";
  private static final String ERROR_FETCH_DATA = "Ocorreu um erro ao buscar os dados da API.";
  private static final String ERROR_PARSE_DATA = "Erro ao analisar os dados dos gatos.";
  
  private static final String LOG_FETCH_DATA = "Buscando dados da API.";
  private static final String LOG_REQUEST_BODY = "Body da request: ";
  private static final String LOG_ANALYZE_DATA = "Analisando dados recebidos da API.";

  public CatApiResponse() {
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
        throw new AplicacaoException(ERROR_REQUEST, new AplicacaoException(), response.statusCode());
      }
    } catch (IOException | InterruptedException e) {
      logger.severe(ERROR_REQUEST);
      throw new AplicacaoException(ERROR_FETCH_DATA, e, 502);
    }
  }

  public List<CatDTO> parseData(String response) {
    List<CatDTO> catList = new ArrayList<>();
    
    try {
      logger.info(LOG_ANALYZE_DATA);
      JsonReader jsonReader = Json.createReader(new StringReader(response));
      JsonArray jsonArray = jsonReader.readArray();

      for (JsonObject catJson : jsonArray.getValuesAs(JsonObject.class)) {
        String id = catJson.getString("id");
        String url = catJson.getString("url");
        int width = catJson.getInt("width");
        int height = catJson.getInt("height");
        CatDTO catDTO = new CatDTO(id, url, width, height);
        catList.add(catDTO);
      }
    } catch (jakarta.json.JsonException e) {
      logger.severe(ERROR_PARSE_DATA);
      throw new AplicacaoException(ERROR_PARSE_DATA, e, 500);
    }

    return catList;
  }
}
