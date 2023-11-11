package modelo;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ConsultaService {

  private static final String MENSAGEM_SUCESSO =
      "Consulta a API realizada com sucesso. Resultado: ";
  private static final String LISTAR_IMAGENS_URL = "https://picsum.photos/v2/list";
  private static final String BUSCAR_IMAGEM_URL = "https://picsum.photos/id/ID_REQUERIDO/info";
  private static final Duration TIMEOUT_REQUISICAO = Duration.ofSeconds(15);
  private static final Jsonb jsonb = JsonbBuilder.create();
  private static final Logger logger = Logger.getLogger(ConsultaService.class.getName());
  private static final HttpClient cliente =
      HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build();

  public List<ImagemTO> listarImagens() throws IOException, InterruptedException {
    String resultado = criarResposta(LISTAR_IMAGENS_URL);
    String mensagemLog = MENSAGEM_SUCESSO + resultado;
    logger.info(mensagemLog);
    return jsonb.fromJson(
        resultado, new ArrayList<ImagemTO>() {}.getClass().getGenericSuperclass());
  }

  public ImagemTO buscarImagem(int id) throws IOException, InterruptedException {
    String urlCompleta = BUSCAR_IMAGEM_URL.replace("ID_REQUERIDO", String.valueOf(id));
    String resultado = criarResposta(urlCompleta);
    String mensagemLog = MENSAGEM_SUCESSO + resultado;
    logger.info(mensagemLog);
    return jsonb.fromJson(resultado, ImagemTO.class);
  }

  private static String criarResposta(String url) throws IOException, InterruptedException {
    HttpRequest requisicao = criarRequisicao(url);
    HttpResponse<String> result = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());
    return result.body();
  }

  private static HttpRequest criarRequisicao(String url) {
    return HttpRequest.newBuilder().GET().uri(URI.create(url)).timeout(TIMEOUT_REQUISICAO).build();
  }
}
