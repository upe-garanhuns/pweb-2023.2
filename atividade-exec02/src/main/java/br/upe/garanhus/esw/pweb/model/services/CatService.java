package br.upe.garanhus.esw.pweb.model.services;

import br.upe.garanhus.esw.pweb.model.domain.cat.Cat;
import br.upe.garanhus.esw.pweb.model.infra.ApplicationException;
import br.upe.garanhus.esw.pweb.model.util.JsonConverter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.NotSerializableException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class CatService {

  private static final String API_URL = "https://api.thecatapi.com/v1/images/";
  private static final String API_SEARCH = "search?limit=10";
  private static final String UNKNOWN_ERROR = "Oops! Something went wrong.";
  private static final String NOT_FOUND = "Image has been rejected. Please review the upload guidelines or get in touch for more information.";
  private static final String NOT_FOUND2 = "Couldn't find an image matching the passed 'id' of ";
  private static final String CAT_NOT_FOUND = "Cat not found";
  private static final String NULL_ID = "Null id is not supported";
  private static final String JSON_ERROR = "Illegal character in request, please make sure the json is in the correct format";
  private final HttpClient client = HttpClient.newHttpClient();

  public final Cat findCatById(String id) {
    if (id.isBlank()) {
      throw new ApplicationException(
          NULL_ID,
          new NotSerializableException(NULL_ID),
          HttpServletResponse.SC_BAD_REQUEST
      );
    }
    String response = sendAPIRequest(API_URL + id).body();
    if (NOT_FOUND.equals(response) || response.equals(NOT_FOUND2 + id)) {
      throw new ApplicationException(
          CAT_NOT_FOUND,
          new NotSerializableException(CAT_NOT_FOUND),
          HttpServletResponse.SC_NOT_FOUND
      );
    }
    return JsonConverter.convertJsonToCat(response);
  }

  public List<Cat> get10Cats() {
    HttpResponse<String> response = sendAPIRequest(API_URL + API_SEARCH);
    return JsonConverter.convertJsonToCatList(response.body());
  }

  public HttpResponse<String> sendAPIRequest(String url) {
    try {
      HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
      return client.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new ApplicationException(UNKNOWN_ERROR, e,
          HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    } catch (Exception e) {
      throw new ApplicationException(JSON_ERROR, e.getCause(),
          HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
