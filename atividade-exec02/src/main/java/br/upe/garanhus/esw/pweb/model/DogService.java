package br.upe.garanhus.esw.pweb.model;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class DogService {

    private static final String URL = "https://api.thedogapi.com/v1/images/";
    private static final String API_KEY = "live_Z1huoXcBZNiwfNmF36y84vlwPamqydRSHTvG1WBQNVfCivAD3HXxiu4uJebMyfVb";

    private static final Logger logger = Logger.getLogger(DogService.class.getName());

    private static final String MSG_LOG_LIST = "Success listing the following items: ";
    private static final String MSG_LOG_CONSULT = "Success consulting the following item: ";
    private static final String MSG_ERROR_APPLICATION = "An error occurred in the application ";

    private static final String MSG_ERROR_ID_INVALID = "Invalid ID ";
    private static final String MSG_ERROR_API_CONNECTION = "An error occurred while connecting to the API ";

    private final HttpClient client = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();

    private HttpRequest createRequest(String url) {
        try{
            return HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("x-api-key", API_KEY)
                    .header("Accept", "application/json")
                    .GET()
                    .build();
        } catch (Exception e){
            logger.severe("Error while create request: " + e.getMessage());
            throw new AplicacaoException(500, MSG_ERROR_APPLICATION, e);
        }
    }

    private HttpResponse<String> sendRequest(HttpRequest request) throws IOException, InterruptedException {
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> list() {
        try {
            String urlWithParams = URL + "search?limit=10";
            HttpRequest request = createRequest(urlWithParams);
            HttpResponse<String> response = sendRequest(request);

            if (response.statusCode() == 404) {
                logger.severe(MSG_ERROR_API_CONNECTION + response.body());
                throw new AplicacaoException(response.statusCode(), MSG_ERROR_API_CONNECTION, new RuntimeException(response.body()));
            } else {
                logger.info(MSG_LOG_LIST + response.body());
                return response;
            }
        } catch (IOException e) {
            logger.severe("Error while listing: " + e.getMessage());
            throw new AplicacaoException(500, MSG_ERROR_APPLICATION, e);
        } catch (InterruptedException e) {
            logger.severe("Error while listing: " + e.getMessage());
            Thread.currentThread().interrupt();
            throw new AplicacaoException(500, MSG_ERROR_APPLICATION, e);
        }
    }

    public HttpResponse<String> consult(DogTO dogTO) {
        try {
            String urlWithParams = URL + dogTO.getId();
            HttpRequest request = createRequest(urlWithParams);
            HttpResponse<String> response = sendRequest(request);

            if (response.statusCode() == 400) {
                logger.severe(MSG_ERROR_ID_INVALID + response.body());
                throw new AplicacaoException(response.statusCode(), MSG_ERROR_ID_INVALID, new RuntimeException(response.body()));
            } else if (response.statusCode() == 404) {
                logger.severe(MSG_ERROR_API_CONNECTION + response.body());
                throw new AplicacaoException(response.statusCode(), MSG_ERROR_API_CONNECTION, new RuntimeException(response.body()));
            } else {
                logger.info(MSG_LOG_CONSULT + response.body());
                return response;
            }
        } catch (IOException e) {
            logger.severe("Error while consulting: " + e.getMessage());
            throw new AplicacaoException(500, MSG_ERROR_APPLICATION, e);
        } catch (InterruptedException e) {
            logger.severe("Error while consulting: " + e.getMessage());
            Thread.currentThread().interrupt();
            throw new AplicacaoException(500, MSG_ERROR_APPLICATION, e);
        }
    }
}
