package br.upe.garanhus.esw.pweb.modelo;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import java.util.ArrayList;
import java.util.List;

public class JSONConverter {
  private Jsonb jsonb = JsonbBuilder.create();
  private static final int INTERNALSERVERERROR = 500;
  private static final String PROCESSINGJSONERROR = "Erro ao processar a resposta JSON da API";
  private static final String APIERROR = "Erro no consumo da API";
  private static final String CONVERTINGTOJSONERROR =
      "Erro ao converter objetos(s) Picsum para JSON";
  private static final String CONVERTINGTOJSONDETAIL = "Algo na conversão não está certo";


  public List<PicsumObj> processApiResponse(String jsonResponse) throws JsonbException {
    try {
      return jsonb.fromJson(jsonResponse, new ArrayList<PicsumObj>() {
        private static final long serialVersionUID = 1L;
      }.getClass().getGenericSuperclass());
    } catch (JsonbException e) {
      throw new AplicacaoException(INTERNALSERVERERROR, PROCESSINGJSONERROR, APIERROR);
    }
  }

  public String convertToJson(List<PicsumObj> picsumList) throws JsonbException {
    try {
      return jsonb.toJson(picsumList);
    } catch (JsonbException e) {
      throw new AplicacaoException(INTERNALSERVERERROR, CONVERTINGTOJSONERROR,
          CONVERTINGTOJSONDETAIL);
    }
  }

  public PicsumObj processSingleApiResponse(String jsonResponse) throws JsonbException {
    try {
      return jsonb.fromJson(jsonResponse, PicsumObj.class);
    } catch (JsonbException e) {
      throw new AplicacaoException(INTERNALSERVERERROR, PROCESSINGJSONERROR, CONVERTINGTOJSONDETAIL);
    }
  }

  public String convertSingleObjectToJson(PicsumObj picsumObj) throws JsonbException {
    try {
      return jsonb.toJson(picsumObj);
    } catch (JsonbException e) {
      throw new AplicacaoException(INTERNALSERVERERROR, CONVERTINGTOJSONERROR,
          CONVERTINGTOJSONDETAIL);
    }
  }
}

