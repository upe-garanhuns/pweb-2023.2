package br.upe.garanhus.esw.pweb.modelo;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

public class Exec02Servico {

  private static final int CLIENT_ERROR = 400;
  private static final int SERVER_ERROR = 500;

  private static final String API_URL_GERAL = "https://api.pexels.com/v1/curated?per_page=5";
  private static final String API_URL_COM_TEMA = "https://api.pexels.com/v1/search?query=";
  private static final String API_URL_COM_ID = "https://api.pexels.com/v1/photos/";
  private static final String API_CHAVE =
      "kgg7BLG7TSfMuxECP4wpUeSsuXXSPPQN7fdHUruYQ25KVkHdfUFMp0Pz";
  private static final String HTTP_AUTORIZACAO = "authorization";
  private static final String CINCO_POR_PAGINA = "&per_page=5";

  private static final String ERRO_THREAD = "Erro em thread";
  private static final String PESQUISA_SEM_PARAMETRO =
      "O termo de busca para pesquisa na url não foi informado";
  private static final String ID_NAO_ENCONTRADO =
      "A imagem de id informado não foi encontrada no servidor";
  private static final String URL_INVALIDO =
      "O caminho URL informado possui caractere(s) inválido(s), a exemplo de caractere(s) não-alfanumérico(s)";

  private HttpClient client = HttpClient.newHttpClient();
  private HttpRequest request = null;
  private HttpResponse<String> response = null;

  private static final System.Logger logger = System.getLogger("logger");
  private Jsonb jsonb = JsonbBuilder.create();
  private String regex = "^.*[^a-zA-Z0-9 ].*$";

  public String objParaJson(Object obj) {
    return jsonb.toJson(obj);
  }

  public String consertarCaminho(String caminho) {

    caminho = caminho.replace("/", "").trim();

    if (caminho.matches(regex) && !caminho.isBlank()) {
      throw new AplicacaoException(URL_INVALIDO);
    }

    return caminho;

  }

  public boolean isNumerico(String str) {

    try {

      int numero = Integer.parseInt(str);
      return true;

    } catch (Exception e) {

      return false;

    }

  }

  public Pagina buscarImagemGenerica() throws IOException, InterruptedException {

    try {

      request = HttpRequest.newBuilder().GET().header(HTTP_AUTORIZACAO, API_CHAVE)
          .uri(URI.create(API_URL_GERAL)).build();
      response = client.send(request, BodyHandlers.ofString());
      String resultadoJson = response.body();
      logger.log(System.Logger.Level.INFO, resultadoJson);
      return jsonb.fromJson(resultadoJson, Pagina.class);

    } catch (InterruptedException e) {

      String jsonExcecao = FormataErro.paraJson(response.statusCode(), ERRO_THREAD, e.getMessage());
      logger.log(System.Logger.Level.ERROR, jsonExcecao);
      Thread.currentThread().interrupt();
      return null;

    }

  }

  public Pagina buscarImagemPorTema(String tema) throws IOException, InterruptedException {

    try {

      request = HttpRequest.newBuilder().GET().header(HTTP_AUTORIZACAO, API_CHAVE)
          .uri(URI.create(API_URL_COM_TEMA + tema + CINCO_POR_PAGINA)).build();
      response = client.send(request, BodyHandlers.ofString());
      String resultadoJson = response.body();

      if (tema.isBlank()) {
        throw new AplicacaoException(PESQUISA_SEM_PARAMETRO);
      }

      logger.log(System.Logger.Level.INFO, resultadoJson);
      return jsonb.fromJson(resultadoJson, Pagina.class);

    } catch (InterruptedException e) {

      String jsonExcecao = FormataErro.paraJson(response.statusCode(), ERRO_THREAD, e.getMessage());
      logger.log(System.Logger.Level.ERROR, jsonExcecao);
      Thread.currentThread().interrupt();
      return null;

    }

  }

  public Foto buscarImagemPorId(Integer id) throws IOException {

    try {

      request = HttpRequest.newBuilder().GET().header(HTTP_AUTORIZACAO, API_CHAVE)
          .uri(URI.create(API_URL_COM_ID + String.valueOf(id))).build();
      response = client.send(request, BodyHandlers.ofString());
      String resultadoJson = response.body();

      if (response.statusCode() >= CLIENT_ERROR && response.statusCode() < SERVER_ERROR) {
        throw new AplicacaoException(ID_NAO_ENCONTRADO);
      }

      logger.log(System.Logger.Level.INFO, resultadoJson);
      return jsonb.fromJson(resultadoJson, Foto.class);

    } catch (InterruptedException e) {

      String jsonExcecao = FormataErro.paraJson(response.statusCode(), ERRO_THREAD, e.getMessage());
      logger.log(System.Logger.Level.ERROR, jsonExcecao);
      Thread.currentThread().interrupt();
      return null;

    }

  }

}
