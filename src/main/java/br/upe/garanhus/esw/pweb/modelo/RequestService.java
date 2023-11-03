package br.upe.garanhus.esw.pweb.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

public class RequestService {

    private static final String URI_GET = "https://api.thecatapi.com/v1/images/search?limit=10";
    private static final String URI_POST = "https://api.thecatapi.com/v1/images/";
    private static final String MSG_ID_INVALIDO = "ID inválido.";
    
    private static final Logger LOGGER = Logger.getLogger(RequestService.class.getName());
    
    private HttpClient client;
    private Jsonb jsonb;
  
    public RequestService() {
        this.client = HttpClient.newBuilder().build();
        this.jsonb = JsonbBuilder.create();
    }
    
    public List<RequestModel> obterImagens() {
        List<RequestModel> imagens;
        
        HttpResponse<String> response = enviarRequisicao(URI_GET);
            
        imagens = jsonb.fromJson(
            response.body(), new ArrayList<RequestModel>(){}.getClass().getGenericSuperclass()
            );
            
        return imagens;
    }
    
    public RequestModel obterImagemPorId(IdDTO iddto) {
        HttpResponse<String> resposta = enviarRequisicao(URI_POST + iddto.getId());
            
        if(resposta.statusCode() == 400) {
            throw new AplicacaoException(500, MSG_ID_INVALIDO, new AplicacaoException());
        }
        
        RequestModel requestModel = jsonb.fromJson(resposta.body(), RequestModel.class);
            
        return requestModel;
    }
    
    public HttpResponse<String> enviarRequisicao(String uri) {

        try {
          HttpRequest requisicao = HttpRequest.newBuilder().uri(URI.create(uri)).GET().build();
          HttpResponse<String> resposta;
          
          resposta = client.send(requisicao, BodyHandlers.ofString());
          String resultado = resposta.body();
          
          LOGGER.log(Level.INFO, resultado);
          
          return resposta;
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          throw new AplicacaoException(500, e.getMessage(), e);
        } catch (Exception e) {
          throw new AplicacaoException(500, e.getMessage(), e);
        }
        
    }
    
    public String jsonBuilder(BufferedReader reader) throws IOException {
        StringBuilder json = new StringBuilder();
        String linha;
        
        while((linha = reader.readLine()) != null){
          json.append(linha);
        }
        
        return json.toString();
    }
  
}
