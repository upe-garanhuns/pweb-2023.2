package br.upe.garanhus.esw.pweb.controle;

import java.io.IOException;
import br.upe.garanhus.esw.pweb.modelo.AplicacaoException;
import br.upe.garanhus.esw.pweb.modelo.Exec02Servico;
import br.upe.garanhus.esw.pweb.modelo.FormataErro;
import br.upe.garanhus.esw.pweb.modelo.Foto;
import br.upe.garanhus.esw.pweb.modelo.Pagina;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/processa-imagem", "/processa-imagem/*"})
public class Exec02Servlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private static final int BAD_REQUEST = 400;
  private static final int NOT_FOUND = 404;

  private static final String ERRO_URL = "Erro no caminho da URL";
  private static final String ERRO_ID_NAO_ENCONTRADO = "Erro ao procurar imagem por id";
  private static final String ID_PRECISA_SER_INT =
      "O id informado é inválido. Somente se aceita ids de números inteiros";

  private static final String TIPO_DE_CONTEUDO = "content-type";
  private static final String APLICACAO_JSON = "application/json";

  private final transient Exec02Servico servico = new Exec02Servico();
  private static final System.Logger logger = System.getLogger("logger");

  public Exec02Servlet() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    try {

      response.setHeader(TIPO_DE_CONTEUDO, APLICACAO_JSON);
      String caminho = request.getPathInfo();

      if (caminho == null) {
        Pagina respPagina = servico.buscarImagemGenerica();
        response.getWriter().append(servico.objParaJson(respPagina));
        return;
      }

      caminho = servico.consertarCaminho(caminho);
      Pagina respPagina = servico.buscarImagemPorTema(caminho);
      response.getWriter().append(servico.objParaJson(respPagina));

    } catch (Exception e) {

      response.setStatus(BAD_REQUEST);
      String jsonExcecao = FormataErro.paraJson(response.getStatus(), ERRO_URL, e.getMessage());
      logger.log(System.Logger.Level.ERROR, jsonExcecao);
      response.getWriter().append(jsonExcecao);

    }

  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    try {

      String caminho = request.getPathInfo();
      caminho = servico.consertarCaminho(caminho);
      response.setHeader(TIPO_DE_CONTEUDO, APLICACAO_JSON);

      if (!servico.isNumerico(caminho)) {
        response.setStatus(BAD_REQUEST);
        throw new AplicacaoException(ID_PRECISA_SER_INT);
      }

      Foto respFoto = servico.buscarImagemPorId(Integer.parseInt(caminho));
      response.getWriter().append(servico.objParaJson(respFoto));

    } catch (Exception e) {

      if (response.getStatus() != BAD_REQUEST) {
        response.setStatus(NOT_FOUND);
      }

      String jsonExcecao =
          FormataErro.paraJson(response.getStatus(), ERRO_ID_NAO_ENCONTRADO, e.getMessage());
      logger.log(System.Logger.Level.ERROR, jsonExcecao);
      response.getWriter().append(jsonExcecao);

    }

  }

}
