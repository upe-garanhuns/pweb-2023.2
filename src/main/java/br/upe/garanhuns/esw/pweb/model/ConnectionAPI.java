package br.upe.garanhuns.esw.pweb.model;

import java.io.IOException;

import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class ConnectionAPI {
	private static HttpClient client;
	private static HttpRequest request;
	
	private static List<ImgDTO> images;
	
	private static final String url = "https://picsum.photos/v2/list";
	
	public ConnectionAPI() {
		ConnectionAPI.client = HttpClient.newHttpClient();
		ConnectionAPI.request = HttpRequest.newBuilder().uri(URI.create(url)).build();
		
		ConnectionAPI.images = new ArrayList<>();		
		}
	
	public List<ImgDTO> transformData(String response) {
		
		try {
			JsonReader jsonReader = Json.createReader(new StringReader(response));
			JsonArray jsonArray = jsonReader.readArray();
			
			images.clear();
			
			for(JsonObject imgJson : jsonArray.getValuesAs(JsonObject.class)){
				String id = imgJson.getString("id");
				String author = imgJson.getString("author");
				int width = imgJson.getInt("width");
				int height = imgJson.getInt("height");
				String url = imgJson.getString("url");
				String downloadUrl = imgJson.getString("download_url");
				
				ImgDTO imgDTO = new ImgDTO(id, author, width, height, url, downloadUrl);
				images.add(imgDTO);
			}
			
		} catch (Exception e) {
			throw new AplicacaoException("Erro ao analisar os dados das imagens.", e);
		}
		
		return images;
	}
	
	public String findData() {
		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
			if(response.statusCode() == 200) {
				return response.body();
			} else {
				throw new AplicacaoException("Erro na requisição");
			}
		} catch (IOException | InterruptedException e) {
			throw new AplicacaoException("Erro ao buscar dados das imagens" + e);
		}
	}
}
