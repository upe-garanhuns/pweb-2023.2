package br.upe.garanhus.esw.pweb.controle;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import br.upe.garanhuns.esw.pweb.modelo.ErrorResponse;
import br.upe.garanhuns.esw.pweb.modelo.LoremPicsum;
import br.upe.garanhuns.esw.pweb.modelo.Service;

@WebServlet("/processa-imagem")
public class Exec02Servlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private final Service service = new Service();
  private static final Logger LOGGER = Logger.getLogger(Exec02Servlet.class.getName());

  private static final String APLICACAOJSON = "application/json";
  private static final int INTERNALSERVERERROR = 500;
  private static final String ERROINTERNO = "Erro interno";
  private static final String OCORREUERRO = "Ocorreu um erro: ";

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    try {
      List<LoremPicsum> photos = service.getPhotos();
      String jsonConvertida = service.convertObjectToJson(photos);

      response.setContentType(APLICACAOJSON);
      response.getWriter().write(jsonConvertida);
    } catch (Exception e) {
      handleException(response, e);
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    try {
      LoremPicsum postPhotos = service.postPhotos();

      String jsonConvertida = service.convertObjectToJson(postPhotos);

      response.setContentType(APLICACAOJSON);
      response.getWriter().write(jsonConvertida);
    } catch (Exception e) {
      handleException(response, e);
    }
  }

  private void handleException(HttpServletResponse response, Exception e) throws IOException {
    int codigo = INTERNALSERVERERROR;
    String mensagem = ERROINTERNO;
    String detalhe = (e.getCause() != null) ? e.getCause().getMessage() : e.getMessage();

    AplicacaoException appException = new AplicacaoException(codigo, mensagem, detalhe, e);
    LOGGER.log(Level.SEVERE, OCORREUERRO + e.getMessage(), e);

    ErrorResponse errorResponse = new ErrorResponse();
    String valor = errorResponse.generateJSONResponse(appException);

    response.setContentType(APLICACAOJSON);
    response.getWriter().write(valor);
    response.setStatus(codigo);
  }
}
