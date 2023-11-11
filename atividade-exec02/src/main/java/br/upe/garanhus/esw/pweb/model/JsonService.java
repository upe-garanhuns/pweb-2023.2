package br.upe.garanhus.esw.pweb.model;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class JsonService {

    private JsonService(){}
    private static final Jsonb jsonb = JsonbBuilder.create();

    public static String processResponseToJson(String json) {
        List<DogTO> dogTOS = jsonb.fromJson(json, new ArrayList<DogTO>() {
        }.getClass().getGenericSuperclass());
        return jsonb.toJson(dogTOS);
    }

    public static DogTO createDogFromJson(String body) {
        JsonObject jsonObject = jsonb.fromJson(body, JsonObject.class);
        DogTO dog = new DogTO();
        dog.setId(jsonObject.getString("id"));
        dog.setUrl(jsonObject.getString("url"));
        dog.setWidth(String.valueOf(jsonObject.getInt("width")));
        dog.setHeight(String.valueOf(jsonObject.getInt("height")));
        return dog;
    }

    public static void sendJsonErrorResponse(HttpServletResponse resp, Exception e) {
        int statusCode = 500;
        String message = "Application Error";
        String detail = e.getCause().getClass().getName();

        if (e instanceof AplicacaoException) {
            AplicacaoException appEx = (AplicacaoException) e;
            statusCode = appEx.getCodigo();
            message = appEx.getMessage();
            detail = appEx.getCause().getClass().getName();
        }

        JsonObject jsonError = Json.createObjectBuilder()
                .add("codigo", statusCode)
                .add("mensagem", message)
                .add("detalhe", detail)
                .build();

        resp.setStatus(statusCode);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            out.print(jsonError.toString());
        } catch (IOException ex) {
            throw new AplicacaoException(resp.getStatus(), ex.getMessage(), ex);
        }
    }
}
