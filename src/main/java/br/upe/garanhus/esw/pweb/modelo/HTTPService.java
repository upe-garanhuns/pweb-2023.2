package br.upe.garanhus.esw.pweb.modelo;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HTTPService {
  private String apiURL;
  private HttpClient client;

  public HTTPService(String apiURL) {
    this.apiURL = apiURL;
    client = HttpClient.newHttpClient();
  }

  public String sendGetRequest() throws IOException, InterruptedException {
    HttpRequest httpRequest = HttpRequest.newBuilder().GET().header("accept", "application/json")
        .uri(URI.create(apiURL)).build();

    HttpResponse<String> apiResponse =
        client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    return apiResponse.body();
  }
}
