package br.upe.garanhus.esw.pweb.model.util;

import br.upe.garanhus.esw.pweb.model.domain.cat.Cat;
import br.upe.garanhus.esw.pweb.model.dto.CatDTO;
import br.upe.garanhus.esw.pweb.model.dto.ExceptionDTO;
import br.upe.garanhus.esw.pweb.model.infra.ApplicationException;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.stream.JsonParsingException;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public final class JsonConverter {

  private static final String CAT_ID = "id";
  private static final String CAT_URL = "url";
  private static final String CAT_WIDTH = "width";
  private static final String CAT_HEIGHT = "height";
  private static final String NOT_SUPPORTED = "request entity is in a format not supported";

  private JsonConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static Cat convertJsonToCat(String json) {
    JsonObject jsonObject;
    try (JsonReader jsonReader = Json.createReader(new StringReader(json))) {
      jsonObject = jsonReader.readObject();
    } catch (Exception e) {
      throw new ApplicationException(NOT_SUPPORTED, e.getCause(),
          HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE
      );
    }

    CatDTO catDTO = new CatDTO(
        jsonObject.getString(CAT_ID),
        jsonObject.getString(CAT_URL),
        jsonObject.getInt(CAT_WIDTH),
        jsonObject.getInt(CAT_HEIGHT)
    );

    return new Cat(catDTO);
  }

  public static List<Cat> convertJsonToCatList(String json) {
    List<Cat> catList = new ArrayList<>();
    JsonReader jsonReader = Json.createReader(new StringReader(json));
    JsonArray jsonArray = jsonReader.readArray();

    for (JsonObject jsonObject : jsonArray.getValuesAs(JsonObject.class)) {
      CatDTO catDTO = new CatDTO(
          jsonObject.getString(CAT_ID),
          jsonObject.getString(CAT_URL),
          jsonObject.getInt(CAT_WIDTH),
          jsonObject.getInt(CAT_HEIGHT)
      );
      catList.add(new Cat(catDTO));
    }

    jsonReader.close();
    return catList;
  }

  public static JsonObject bufferedReaderToJson(BufferedReader bufferedReader) {
    try {
      StringBuilder jsonBody = new StringBuilder();
      String line;

      while ((line = bufferedReader.readLine()) != null) {
        jsonBody.append(line);
      }

      JsonReader jsonReader = Json.createReader(new StringReader(jsonBody.toString()));
      JsonObject jsonObject = jsonReader.readObject();
      jsonReader.close();

      return jsonObject;

    } catch (JsonParsingException | IOException e) {
      throw new ApplicationException(NOT_SUPPORTED, e,
          HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE
      );
    }
  }

  public static String toJson(ExceptionDTO exceptionDTO) {
    JsonObject exceptionJson = Json.createObjectBuilder()
        .add("code", exceptionDTO.code())
        .add("message", exceptionDTO.message())
        .add("error", exceptionDTO.error())
        .build();

    return exceptionJson.toString();
  }

  public static String toJson(List<Cat> cats) {
    JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

    for (Cat cat : cats) {
      JsonObject catJsonObject = Json.createObjectBuilder()
          .add(CAT_ID, cat.getId())
          .add(CAT_URL, cat.getUrl())
          .add(CAT_WIDTH, cat.getWidth())
          .add(CAT_HEIGHT, cat.getHeight())
          .build();
      jsonArrayBuilder.add(catJsonObject);
    }
    return jsonArrayBuilder.build().toString();
  }

  public static String toJson(Cat cat) {
    JsonObject catJsonObject = Json.createObjectBuilder()
        .add(CAT_ID, cat.getId())
        .add(CAT_URL, cat.getUrl())
        .add(CAT_WIDTH, cat.getWidth())
        .add(CAT_HEIGHT, cat.getHeight())
        .build();

    return catJsonObject.toString();
  }
}
