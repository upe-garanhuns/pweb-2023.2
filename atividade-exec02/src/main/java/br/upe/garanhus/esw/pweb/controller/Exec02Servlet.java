package br.upe.garanhus.esw.pweb.controller;

import br.upe.garanhus.esw.pweb.model.domain.cat.Cat;
import br.upe.garanhus.esw.pweb.model.infra.ApplicationException;
import br.upe.garanhus.esw.pweb.model.services.CatService;
import br.upe.garanhus.esw.pweb.model.util.JsonConverter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/processa-imagem")
public class Exec02Servlet extends HttpServlet {

  private static final Logger LOGGER = Logger.getLogger(Exec02Servlet.class.getName());
  private final transient CatService catService = new CatService();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    try {
      PrintWriter out = resp.getWriter();
      List<Cat> cats = this.catService.get10Cats();
      String response = JsonConverter.toJson(cats);

      LOGGER.log(Level.INFO, response);
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.setStatus(HttpServletResponse.SC_OK);
      out.print(response);
    } catch (IOException e) {
      throw new ApplicationException(
          "An IOException has ocurred, please make sure you're searching correctly", e,
          HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    try {
      PrintWriter out = resp.getWriter();
      String id = JsonConverter.bufferedReaderToJson(req.getReader()).getString("id");
      String response = JsonConverter.toJson(catService.findCatById(id));

      LOGGER.log(Level.INFO, response);
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.setStatus(HttpServletResponse.SC_OK);
      out.write(response);
    } catch (IOException e) {
      throw new ApplicationException(
          "An IOException has ocurred, please make sure you're searching correctly", e,
          HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
