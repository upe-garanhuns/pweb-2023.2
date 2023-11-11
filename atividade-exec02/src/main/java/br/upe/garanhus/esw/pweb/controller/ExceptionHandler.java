package br.upe.garanhus.esw.pweb.controller;

import br.upe.garanhus.esw.pweb.model.dto.ExceptionDTO;
import br.upe.garanhus.esw.pweb.model.infra.ApplicationException;
import br.upe.garanhus.esw.pweb.model.util.JsonConverter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ExceptionHandler", urlPatterns = "/exception-handler")
public class ExceptionHandler extends HttpServlet {

  private static final Logger LOGGER = Logger.getLogger(ExceptionHandler.class.getName());

  @Override
  protected final void doGet(HttpServletRequest req, HttpServletResponse resp) {
    processError(req, resp);
  }

  @Override
  protected final void doPost(HttpServletRequest req, HttpServletResponse resp) {
    processError(req, resp);
  }

  private void processError(HttpServletRequest req, HttpServletResponse resp) {
    try {
      PrintWriter out = resp.getWriter();
      ApplicationException exception = (ApplicationException) req.getAttribute(
          "jakarta.servlet.error.exception");
      String errorMessage = exception.getMessage();
      String exceptionClass = exception.getCause().getClass().getName();
      Integer statusCode = exception.getCode();

      ExceptionDTO exceptionDTO = new ExceptionDTO(
          statusCode,
          errorMessage,
          exceptionClass
      );

      LOGGER.log(Level.SEVERE, () -> exceptionClass + " " + errorMessage);
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.setStatus(statusCode);

      out.write(JsonConverter.toJson(exceptionDTO));

    } catch (IOException e) {
      throw new ApplicationException(
          "An IOException has ocurred, please make sure you're searching correctly", e,
          HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
