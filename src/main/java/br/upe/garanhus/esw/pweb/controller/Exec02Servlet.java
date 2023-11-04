package br.upe.garanhus.esw.pweb.controller;

import java.io.IOException;
import br.upe.garanhus.esw.pweb.model.service.CatService;
import br.upe.garanhus.esw.pweb.model.service.ErrorService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Exec02Servlet
 */
@WebServlet(name = "Exec02Servlet", urlPatterns = {"/processa-imagem"})
public class Exec02Servlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private CatService service;

  public Exec02Servlet() {}

  public void init() {
    service = new CatService();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("application/json");
    ErrorService.handleRequest(request, response, 
        () -> service.getAllCats());
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json");
    ErrorService.handleRequest(request, response,
        () -> service.getCatById(service.getIdFromRequest(request)));

  }
}
