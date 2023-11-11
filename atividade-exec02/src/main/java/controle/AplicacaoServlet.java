package controle;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.AplicacaoException;
import modelo.RespostaService;
import java.util.logging.Logger;

@WebServlet("/processa-imagem")
public class AplicacaoServlet extends HttpServlet {

  private static final String MENSAGEM_SUCESSO = "Resposta retornada ao usuario com sucesso";
  private static final String MENSAGEM_ERRO = "Ocorreu um erro ao processar a requsicao";
  private static final int CODIGO_SUCESSO = HttpServletResponse.SC_OK;
  private static final int CODIGO_ERRO_SERVIDOR = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
  private static final String JSON_CONTENT_TYPE = "application/json";
  private static final RespostaService respostaService = new RespostaService();
  private static final Jsonb jsonb = JsonbBuilder.create();
  private static final Logger logger = Logger.getLogger(AplicacaoServlet.class.getName());

  @Override
  protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta) {
    try {
      JsonArray respostaApi = respostaService.listarImagens();
      enviarResposta(resposta, respostaApi, CODIGO_SUCESSO);
      logger.info(MENSAGEM_SUCESSO);
    } catch (InterruptedException interruptedException) {
      Thread.currentThread().interrupt();
      JsonObject respostaErro =
          respostaService.criarRespostaErro(
              CODIGO_ERRO_SERVIDOR, MENSAGEM_ERRO, interruptedException);
      enviarResposta(resposta, respostaErro, CODIGO_ERRO_SERVIDOR);
      logger.severe(MENSAGEM_ERRO);
      throw new AplicacaoException(MENSAGEM_ERRO, interruptedException);
    } catch (Exception exception) {
      JsonObject respostaErro =
          respostaService.criarRespostaErro(CODIGO_ERRO_SERVIDOR, MENSAGEM_ERRO, exception);
      enviarResposta(resposta, respostaErro, CODIGO_ERRO_SERVIDOR);
      logger.severe(MENSAGEM_ERRO);
      throw new AplicacaoException(MENSAGEM_ERRO, exception);
    }
  }

  @Override
  protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta) {
    try {
      JsonObject respostaApi = respostaService.buscarImagem(requisicao);
      enviarResposta(resposta, respostaApi, CODIGO_SUCESSO);
      logger.info(MENSAGEM_SUCESSO);
    } catch (InterruptedException interruptedException) {
      Thread.currentThread().interrupt();
      JsonObject respostaErro =
          respostaService.criarRespostaErro(
              CODIGO_ERRO_SERVIDOR, MENSAGEM_ERRO, interruptedException);
      enviarResposta(resposta, respostaErro, CODIGO_ERRO_SERVIDOR);
      logger.severe(MENSAGEM_ERRO);
      throw new AplicacaoException(MENSAGEM_ERRO, interruptedException);
    } catch (Exception exception) {
      JsonObject respostaErro =
          respostaService.criarRespostaErro(CODIGO_ERRO_SERVIDOR, MENSAGEM_ERRO, exception);
      enviarResposta(resposta, respostaErro, CODIGO_ERRO_SERVIDOR);
      logger.severe(MENSAGEM_ERRO);
      throw new AplicacaoException(MENSAGEM_ERRO, exception);
    }
  }

  private static <T> void enviarResposta(
      HttpServletResponse resposta, T objetoResposta, int codigo) {
    try {
      resposta.setContentType(JSON_CONTENT_TYPE);
      resposta.getWriter().write(jsonb.toJson(objetoResposta));
      resposta.setStatus(codigo);
    } catch (Exception exception) {
      throw new AplicacaoException(MENSAGEM_ERRO, exception);
    }
  }
}
