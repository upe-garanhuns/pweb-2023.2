package br.upe.garanhus.esw.pweb.modelo.servico;

import java.io.IOException;
import java.util.function.Supplier;

import br.upe.garanhus.esw.pweb.modelo.ApplicationException;
import br.upe.garanhus.esw.pweb.modelo.DTO.ErrorDTO;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ErrorService {
	private static  String detalhe;
	private static int httpStatusCode;
	private static ErrorDTO errorResponse;
	private static JsonObject errorJson;
	private static JsonArray dogJsonArray;

	  public ErrorService() {
			 // TODO Auto-generated constructor stub
	  }

	  public static JsonObject toJson(ErrorDTO errorDTO) {
	    JsonObjectBuilder builder = Json.createObjectBuilder()
	        .add("codigo", errorDTO.getCodigo())
	        .add("mensagem", errorDTO.getMensagem())
	        .add("detalhe", errorDTO.getDetalhe());
	    
	    return builder.build();
	  }

	  public static void handleErrorResponse(HttpServletResponse response, ApplicationException e)
	      throws IOException {

	    httpStatusCode = determineHttpStatusCode(e);
	    detalhe = e.getCause().toString();

	    response.setStatus(httpStatusCode);
	    
	    errorResponse = new ErrorDTO(httpStatusCode, e.getMessage(), detalhe);
	    errorJson = ErrorService.toJson(errorResponse);
	    
	    response.getWriter().write(errorJson.toString());
	  }

	  public static void handleRequest(HttpServletRequest request, HttpServletResponse response,
	      Supplier<JsonArray> action) throws IOException {
	    try {
	      dogJsonArray = action.get();
	      response.getWriter().write(dogJsonArray.toString());
	    } catch (ApplicationException e) {
	      handleErrorResponse(response, e);
	    }
	  }

	  private static Integer determineHttpStatusCode(ApplicationException e) {
	    if (e.getHttpStatusCode() != null)
	      return e.getHttpStatusCode();

	    return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
	  }

}
