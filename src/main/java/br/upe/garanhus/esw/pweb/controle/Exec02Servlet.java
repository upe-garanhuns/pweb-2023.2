package br.upe.garanhus.esw.pweb.controle;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import br.upe.garanhus.esw.pweb.modelo.PersonagemTO;
import br.upe.garanhus.esw.pweb.modelo.RickMortyException;
import br.upe.garanhus.esw.pweb.modelo.RickMortyService;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/processa-imagem", name = "processa-imagem")
public class Exec02Servlet extends HttpServlet {

  private static final String UTF_8 = "UTF-8";
  private static final String APPLICATION_JSON = "application/json";

  private static final Logger logger = Logger.getLogger(Exec02Servlet.class.getName());
  private static final Jsonb jsonb = JsonbBuilder.create();

  private static final String MSG_ERRO_INESPERADO = "Ocorreu um erro inesperado ao processar sua solicitação";

  private final RickMortyService rickMortyService = new RickMortyService();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    try {
      
      List<PersonagemTO> personagens = rickMortyService.listar();
      this.prepararResponseSucesso(request, response, jsonb.toJson(personagens));
      
    } catch (Exception e) {
      this.tratarErros(e, response);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    try {
      final String id = request.getParameter("id");

      final PersonagemTO personagem = rickMortyService.recuperar(id);
      this.prepararResponseSucesso(request, response, jsonb.toJson(personagem));

    } catch (Exception e) {
      this.tratarErros(e, response);
    }
  }

  private void tratarErros(Exception e, HttpServletResponse response) {
    PrintWriter out;

    try {

      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      ErroTO erro = new ErroTO(HttpServletResponse.SC_BAD_REQUEST, e);

      out = response.getWriter();
      out.write(jsonb.toJson(erro));
      out.flush();

    } catch (IOException e1) {
      logger.log(Level.SEVERE, "Ocorreu um erro ao obter o writer", e);
      response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
      throw new RickMortyException(MSG_ERRO_INESPERADO, e);
    }

  }

  private void prepararResponseSucesso(HttpServletRequest request, HttpServletResponse response, String json) {
    response.setContentType(APPLICATION_JSON);
    response.setCharacterEncoding(UTF_8);
    response.setStatus(HttpServletResponse.SC_OK);

    logger.log(Level.FINE, json);

    try {
      PrintWriter out = response.getWriter();

      out.print(json);
      out.flush();
    } catch (IOException e) {
      this.tratarErros(e, response);
    }

  }
}
