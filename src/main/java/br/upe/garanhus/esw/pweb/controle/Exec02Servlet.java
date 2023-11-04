package br.upe.garanhus.esw.pweb.controle;

import java.io.IOException;
import br.upe.garanhus.esw.pweb.modelo.servico.DogService;
import br.upe.garanhus.esw.pweb.modelo.servico.ErrorService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(name = "Exec02Servlet", urlPatterns = { "/processa-imagem" })
public class Exec02Servlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private transient DogService service;
	
	public Exec02Servlet() {
		 // TODO Auto-generated constructor stub
	}
	
	@Override
	public void init() {
		service = new DogService();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
		
		response.setContentType("application/json");
		ErrorService.handleRequest(request, response,() -> service.getAllDogs());
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		      throws ServletException, IOException {
		
		response.setContentType("application/json");
		ErrorService.handleRequest(request, response,
		        () -> service.getDogById(service.getIdFromRequest(request)));

	}
}
