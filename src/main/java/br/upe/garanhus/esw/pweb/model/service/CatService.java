package br.upe.garanhus.esw.pweb.model.service;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import br.upe.garanhus.esw.pweb.model.AplicacaoException;
import br.upe.garanhus.esw.pweb.model.CatApiResponse;
import br.upe.garanhus.esw.pweb.model.NoCatFoundException;
import br.upe.garanhus.esw.pweb.model.DTO.CatDTO;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.servlet.http.HttpServletRequest;

public class CatService {

  private CatApiResponse catApiResponse;
  private List<CatDTO> catList;
  private JsonArray catJsonArray;
  private JsonArrayBuilder jsonArrayBuilder;
  private static final Logger logger = Logger.getLogger(CatService.class.getName());

  private static final String ERROR_FETCH_CATS = "Erro ao recuperar todos os dados.";
  private static final String ERROR_NO_CAT_FOUND = "Nenhum dado encontrado para o ID: ";
  private static final String ERROR_READ_REQUEST_BODY = "Erro ao ler ID do corpo da solicitação.";
  private static final String ERROR_REQUIRE_GET_REQUEST = "Antes de chamar este método, certifique-se de realizar uma solicitação GET para /atividade-exec02/processa-imagem";

  private static final String LOG_FETCH_ALL_CATS = "Tentando recuperar os dados de todos os gatos.";
  private static final String LOG_FETCH_CAT_BY_ID = "Tentando recuperar o gato de id: ";
  private static final String LOG_READ_ID_FROM_REQUEST = "Tentando recuperar o id da requisição do cliente.";
  private static final String LOG_BUILD_JSON_OBJECT = "Construindo objeto JSON.";

  public CatService() {
    catApiResponse = new CatApiResponse();
    jsonArrayBuilder = Json.createArrayBuilder();
  }

  public JsonArray getAllCats() {
    try {
      logger.info(LOG_FETCH_ALL_CATS);
      catList = catApiResponse.parseData(catApiResponse.fetchData());

      for (CatDTO catDTO : catList) {
        JsonObject catObject = buildCatObject(catDTO);
        jsonArrayBuilder.add(catObject);
      }

      catJsonArray = jsonArrayBuilder.build();
      logger.info(LOG_BUILD_JSON_OBJECT);
    } catch (AplicacaoException e) {
      logger.severe(ERROR_FETCH_CATS);
      throw new AplicacaoException(ERROR_FETCH_CATS, e, 502);
    }

    return catJsonArray;
  }

  public JsonArray getCatById(String id) {
    boolean catFound = false;

    try {
      if (catList == null) {
        logger.severe(ERROR_REQUIRE_GET_REQUEST);
        throw new AplicacaoException(ERROR_REQUIRE_GET_REQUEST, new AplicacaoException(), 400);
      }
      
      logger.info(LOG_FETCH_CAT_BY_ID + id);
      
      for (CatDTO catDTO : catList) {
        if (catDTO.getId().equals(id)) {
          JsonObject catArray = buildCatObject(catDTO);
          jsonArrayBuilder.add(catArray);
          catFound = true;
        }
      }

      if (!catFound) {
        logger.severe(ERROR_NO_CAT_FOUND + id);
        throw new NoCatFoundException(ERROR_NO_CAT_FOUND + id, new NoCatFoundException(), 404);
      }
      
      catJsonArray = jsonArrayBuilder.build();
    } catch (NoCatFoundException e) {
      logger.severe(ERROR_NO_CAT_FOUND + id);
      throw new NoCatFoundException(ERROR_NO_CAT_FOUND + id, e, 404);
    } catch (AplicacaoException e) {
      logger.severe(ERROR_NO_CAT_FOUND + id);
      throw new NoCatFoundException(ERROR_NO_CAT_FOUND + id, e, 400);
    }

    return catJsonArray;
  }

  public String getIdFromRequest(HttpServletRequest request) {
    try {
      logger.info(LOG_READ_ID_FROM_REQUEST);
      JsonReader reader = Json.createReader(request.getReader());
      JsonObject requestBody = reader.readObject();
      return requestBody.getString("id");
    } catch (IOException e) {
      logger.severe(ERROR_READ_REQUEST_BODY);
      throw new AplicacaoException(ERROR_READ_REQUEST_BODY, e, 400);
    }
  }

  protected JsonObject buildCatObject(CatDTO catDTO) {
    return Json.createObjectBuilder()
        .add("id", catDTO.getId())
        .add("url", catDTO.getUrl())
        .add("width", catDTO.getWidth())
        .add("height", catDTO.getHeight())
        .build();
  }
}
