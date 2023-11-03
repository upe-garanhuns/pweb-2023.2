package br.upe.garanhus.esw.pweb.model.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

import jakarta.servlet.http.HttpServletResponse;

public class PicsumService {

	private static final String URL = "https://picsum.photos/v2/list";
	private static HttpClient client;
	private static HttpRequest request;
	private static HttpResponse<String> response;
	private static final Logger logger = Logger.getLogger(PicsumService.class.getName());
	
	public PicsumService() {
		PicsumService.client = HttpClient.newHttpClient();
		PicsumService.request = HttpRequest.newBuilder().uri(URI.create(URL)).build();
		PicsumService.response = null;
	}
	
	public void enviarResposta(HttpServletResponse servletResponse){
		try {
			PicsumService.response = client.send(request, HttpResponse.BodyHandlers.ofString());

			if (response != null) {
                logger.info("RESPOSTA" + response.body());
                servletResponse.setContentType("application/json");
                PrintWriter out = servletResponse.getWriter();
                out.println(response.body());			
			} else {
				servletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();

		}

	}
	
}
