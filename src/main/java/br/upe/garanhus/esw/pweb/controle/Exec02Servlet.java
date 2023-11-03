package br.upe.garanhus.esw.pweb.controle;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Random;
import br.upe.garanhus.esw.pweb.modelo.AplicacaoException;
import br.upe.garanhus.esw.pweb.modelo.HTTPService;
import br.upe.garanhus.esw.pweb.modelo.JSONConverter;
import br.upe.garanhus.esw.pweb.modelo.PicsumObj;

@WebServlet(urlPatterns = {"/processa-imagem"})
public class Exec02Servlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final String RESPONSETYPE = "application/json";
  private static final int FILENOTFOUND = 404;
  private static final String RESOURCENOTFOUND = "Recurso não encontrado";
  private static final String WRONGURL = "O URL da API está incorreto";


  public Exec02Servlet() {
    super();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HTTPService httpService = new HTTPService("https://picsum.photos/v2/list");
    String apiResponse = null;
    try {
      apiResponse = httpService.sendGetRequest();
    } catch (IOException | InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    JSONConverter jsonService = new JSONConverter();
    List<PicsumObj> picsumList = jsonService.processApiResponse(apiResponse);
    String jsonConvertida = jsonService.convertToJson(picsumList);
    response.setContentType(RESPONSETYPE);
    response.getWriter().write(jsonConvertida);
    if (response.getStatus() == FILENOTFOUND) {
      throw new AplicacaoException(FILENOTFOUND, RESOURCENOTFOUND, WRONGURL);
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Random random = new Random();
    int randomId = random.nextInt(29);
    HTTPService httpService = new HTTPService("https://picsum.photos/id/" + randomId + "/info");
    String apiResponse = null;
    try {
      apiResponse = httpService.sendGetRequest();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }

    JSONConverter jsonService = new JSONConverter();
    PicsumObj picsumObj = jsonService.processSingleApiResponse(apiResponse);
    String jsonConvertida = jsonService.convertSingleObjectToJson(picsumObj);
    response.setContentType("application/json");
    response.getWriter().write(jsonConvertida);
    if (response.getStatus() == FILENOTFOUND) {
      throw new AplicacaoException(FILENOTFOUND, RESOURCENOTFOUND, WRONGURL);
    }
  }
}
