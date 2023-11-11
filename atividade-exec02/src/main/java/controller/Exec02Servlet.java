package controller;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MiauJSON;

@WebServlet("/processa-imagem")
public class Exec02Servlet extends HttpServlet {
  private static final String POSTS_API_URL = "https://api.thecatapi.com/v1/images/0XYvRd7oD";
  private static final String API_KEY =
      "live_aacy0EP7UttqLU0AUmhreGzXtPloAvgq6QPUoBhDABVwqwzsThEw48FiJlsztZCE";

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest httpRequest = HttpRequest.newBuilder().GET()
          .uri(URI.create("https://api.thecatapi.com/v1/images/search?limit=10")).build();

      HttpResponse<String> apiResponse =
          client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

      Jsonb jsonb = JsonbBuilder.create();
      List<MiauJSON> catImages = jsonb.fromJson(apiResponse.body(),
          new ArrayList<MiauJSON>() {}.getClass().getGenericSuperclass());

      String jsonResponse = jsonb.toJson(catImages);

      response.setContentType("application/json");


      response.getWriter().write(jsonResponse);
    } catch (Exception e) {
      e.printStackTrace();

    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest httpRequest = HttpRequest.newBuilder().GET()
          .uri(URI.create("https://api.thecatapi.com/v1/images/search")).build();

      HttpResponse<String> apiResponse =
          client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

      Jsonb jsonb = JsonbBuilder.create();
      List<MiauJSON> catImages = jsonb.fromJson(apiResponse.body(),
          new ArrayList<MiauJSON>() {}.getClass().getGenericSuperclass());

      String jsonResponse = jsonb.toJson(catImages);

      response.setContentType("application/json");


      response.getWriter().write(jsonResponse);
    } catch (Exception e) {
      e.printStackTrace();

    }
  }
}
