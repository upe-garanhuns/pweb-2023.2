package modelo;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class RespostaService {

  private static final ConsultaService consultaService = new ConsultaService();
  private static final Jsonb jsonb = JsonbBuilder.create();

  public JsonArray listarImagens() throws IOException, InterruptedException {
    List<ImagemTO> listaImagens = consultaService.listarImagens();
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    for (ImagemTO imagem : listaImagens) {
      JsonObject imagemJson = jsonb.fromJson(jsonb.toJson(imagem), JsonObject.class);
      arrayBuilder.add(imagemJson);
    }
    return arrayBuilder.build();
  }

  public JsonObject buscarImagem(HttpServletRequest requisicao)
      throws IOException, InterruptedException {
    String corpoRequisicao = extrairCorpoRequisicao(requisicao);
    int idRequerido = extrairIdRequerido(corpoRequisicao);
    ImagemTO imagem = consultaService.buscarImagem(idRequerido);
    return jsonb.fromJson(jsonb.toJson(imagem), JsonObject.class);
  }

  public JsonObject criarRespostaErro(int codigo, String mensagem, Throwable causa) {
    return Json.createObjectBuilder()
        .add("codigo: ", codigo)
        .add("mensagem: ", mensagem)
        .add("detalhe: ", causa.getClass().getName())
        .build();
  }

  private static String extrairCorpoRequisicao(HttpServletRequest requisicao) throws IOException {
    BufferedReader bfReader = requisicao.getReader();
    StringBuilder corpoRequisicao = new StringBuilder();
    String bfLinha;
    while ((bfLinha = bfReader.readLine()) != null) {
      corpoRequisicao.append(bfLinha);
    }
    return corpoRequisicao.toString();
  }

  private static int extrairIdRequerido(String corpoRequisicao) {
    JsonObject jsonObject = jsonb.fromJson(corpoRequisicao, JsonObject.class);
    return jsonObject.getInt("id");
  }
}
