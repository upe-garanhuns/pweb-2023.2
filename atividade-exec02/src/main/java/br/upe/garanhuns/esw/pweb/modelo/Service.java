package br.upe.garanhuns.esw.pweb.modelo;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import br.upe.garanhus.esw.pweb.controle.AplicacaoException;

public class Service {
  private static final Logger LOGGER = Logger.getLogger(Service.class.getName());
  private static final String APLICACAOJSON = "application/json";
  private static final String ACEITO = "accept";
  private static final String PICSUMLIST = "https://picsum.photos/v2/list";
  private static final String ERROAPI = "Erro ao processar a requisição da API: ";
  private static final String ERROREQUISICAO = "Erro na requisição";
  private static final int INTERNALSERVERERROR = 500;
  private static final String ERROINTERNO = "Erro interno";
  private static final String ERROJSON = "Erro ao converter objeto para JSON: ";
  private static final String FALHAJSON = "Falha ao converter objeto para JSON";
  private static final String ERROOBJETO = "Erro ao converter JSON para Objeto: ";
  private static final String FALHAOBJETO = "Falha ao converter JSON para Objeto";
  private static final String PICSUM = "https://picsum.photos/id/";
  private static final String INFO = "/info";

  public List<LoremPicsum> getPhotos() {
    try {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest httpRequest = HttpRequest.newBuilder().GET().header(ACEITO, APLICACAOJSON)
          .uri(URI.create(PICSUMLIST)).build();

      HttpResponse<String> apiResponse =
          client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

      String json = apiResponse.body();
      Jsonb jsonb = JsonbBuilder.create();
      List<LoremPicsum> photos = jsonb.fromJson(json, new ArrayList<LoremPicsum>() {
        private static final long serialVersionUID = 1L;
      }.getClass().getGenericSuperclass());

      return photos;
    } catch (IOException | InterruptedException e) {
      LOGGER.log(Level.INFO, ERROAPI + e.getMessage());
      throw new AplicacaoException(INTERNALSERVERERROR, ERROINTERNO, ERROREQUISICAO, e);
    }
  }

  public LoremPicsum postPhotos() throws IOException, InterruptedException {
    try {
      int randomId = ThreadLocalRandom.current().nextInt(31);

      HttpClient client = HttpClient.newHttpClient();
      HttpRequest httpRequest = HttpRequest.newBuilder().GET().header(ACEITO, APLICACAOJSON)
          .uri(URI.create(PICSUM + randomId + INFO)).build();

      HttpResponse<String> apiResponse =
          client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

      Jsonb jsonb = JsonbBuilder.create();
      LoremPicsum picsum = jsonb.fromJson(apiResponse.body(), LoremPicsum.class);
      return picsum;
    } catch (IOException | InterruptedException e) {
      LOGGER.log(Level.INFO, ERROAPI + e.getMessage());
      throw new AplicacaoException(INTERNALSERVERERROR, ERROINTERNO, ERROREQUISICAO, e);
    }
  }

  public String convertObjectToJson(Object object) {
    try {
      Jsonb jsonb = JsonbBuilder.create();
      return jsonb.toJson(object);
    } catch (Exception e) {
      LOGGER.log(Level.INFO, ERROJSON + e.getMessage());
      throw new AplicacaoException(INTERNALSERVERERROR, ERROINTERNO, FALHAJSON, e);
    }
  }

  public static LoremPicsum fromJson(String json) {
    try {
      Jsonb jsonb = JsonbBuilder.create();
      LoremPicsum picsum = jsonb.fromJson(json, LoremPicsum.class);
      return picsum;
    } catch (Exception e) {
      LOGGER.log(Level.INFO, ERROOBJETO + e.getMessage());
      throw new AplicacaoException(INTERNALSERVERERROR, ERROINTERNO, FALHAOBJETO, e);
    }
  }
}
