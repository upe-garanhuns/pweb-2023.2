package br.upe.garanhuns.esw.pweb.controller;

import java.io.IOException;

import br.upe.garanhuns.esw.pweb.model.ImgService;
import jakarta.json.JsonArray;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "Exec02Servlet", urlPatterns = { "/processa-imagem" })
public class Exec02Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ImgService service;

    public Exec02Servlet() {
        
    }

	public void init(){
		service = new ImgService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("/application/json");
		
		response.getWriter().write(service.getAllImages().toString());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("/application/json");
		
		JsonArray imgJsonArray = service.getImgId(service.getIdRequest(request));
		response.getWriter().write(imgJsonArray.toString());
	}

}