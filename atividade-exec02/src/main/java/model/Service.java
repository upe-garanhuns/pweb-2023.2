package model;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

public class Service {
  private static final String POSTS_API_URL = "https://api.thecatapi.com/v1/images/search";
  private static final String API_KEY =
      "live_LJjurPowCWwlSWdlaAg7yOGIleAkezYbvyzBQPOg7jT9tWqcnqRcqljhE9Bnlrnn";

  public static void main(String[] args) throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "application/json")
        .header("live_LJjurPowCWwlSWdlaAg7yOGIleAkezYbvyzBQPOg7jT9tWqcnqRcqljhE9Bnlrnn", API_KEY)
        .uri(URI.create(POSTS_API_URL)).build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    System.out.println(response.body());
    Jsonb jsonb = JsonbBuilder.create();

    MiauJSON catApiResponse = jsonb.fromJson(response.body(), MiauJSON.class);

    System.out.println("Cat ID: " + catApiResponse.getId());
    System.out.println("Cat URL: " + catApiResponse.getUrl());
    System.out.println("Width: " + catApiResponse.getWidth());
    System.out.println("Height: " + catApiResponse.getHeight());

  }

  public static void doPost() throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody())
        .header("accept", "application/json")
        .header("live_LJjurPowCWwlSWdlaAg7yOGIleAkezYbvyzBQPOg7jT9tWqcnqRcqljhE9Bnlrnn", API_KEY)
        .uri(URI.create(POSTS_API_URL)).build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    System.out.println("Response from POST request:");
    System.out.println(response.body());
  }
}
