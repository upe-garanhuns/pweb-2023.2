package br.upe.garanhus.esw.pweb.controle;

import java.io.IOException;

import br.upe.garanhus.esw.pweb.model.service.PicsumService;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Exec02Servlet2
 */
@WebServlet(name = "Exec02Servlet", urlPatterns = { "/processa-imagem" })
public class Exec02Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PicsumService service;

    /**
     * Default constructor. 
     */
    public Exec02Servlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() {
		service = new PicsumService();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		service.enviarResposta(response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
