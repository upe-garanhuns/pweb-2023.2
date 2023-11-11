package br.upe.garanhus.esw.pweb.modelo.servicos;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;

public class ConsomeAPI {
	// API URL
	private static final String API_URL_LISTA_IMAGENS = "https://api.thecatapi.com/v1/images/search?limit=10";
	private static final String API_URL_UNICA_IMAGEM = "https://api.thecatapi.com/v1/images/";
	
	private HttpClient httpClient = HttpClient.newHttpClient();
	private static final Logger logger = Logger.getLogger(ConsomeAPI.class.getName());

	public String coletaListaJSON() throws URISyntaxException, IOException, InterruptedException {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(API_URL_LISTA_IMAGENS))
					.GET()
					.build();
			
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			
			int codigoResposta = response.statusCode();
			logger.info("Codido de status da resposta: " + codigoResposta);
			String retornoResposta = response.body();
			
			JsonbConfig config = new JsonbConfig();
			config.withStrictIJSON(true);
			
			Jsonb jsonb = JsonbBuilder.newBuilder().withConfig(config).build();
			
			Imagem[] imagensArray = jsonb.fromJson(retornoResposta, Imagem[].class);		
			String imagensJson = jsonb.toJson(imagensArray);
			
			return imagensJson;
	}
	
	public String coletaImagemJSON(String imagemId) throws URISyntaxException, IOException, InterruptedException {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(API_URL_UNICA_IMAGEM + imagemId))
					.GET()
					.build();
			
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			
			int codigoResposta = response.statusCode();
			logger.info("Codido de status da resposta: " + codigoResposta);
			String retornoResposta = response.body();
			
			JsonbConfig config = new JsonbConfig();
			config.withStrictIJSON(true);
			
			Jsonb jsonb = JsonbBuilder.newBuilder().withConfig(config).build();
			Imagem imagem = jsonb.fromJson(retornoResposta, Imagem.class);
			String imagemJson = jsonb.toJson(imagem);
			
			return imagemJson;
	}
}
