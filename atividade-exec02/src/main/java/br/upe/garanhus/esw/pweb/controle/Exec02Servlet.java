package br.upe.garanhus.esw.pweb.controle;

import java.io.IOException;
import java.net.URISyntaxException;

import br.upe.garanhus.esw.pweb.AplicacaoException;
import br.upe.garanhus.esw.pweb.modelo.servicos.ConsomeAPI;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/processa-imagem")
public class Exec02Servlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private ConsomeAPI consome = new ConsomeAPI();
	private AplicacaoException appEx = new AplicacaoException();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("application/json");
		try {
			String imagens = consome.coletaListaJSON();
			resp.getWriter().write(imagens);
		} catch (IOException | URISyntaxException | InterruptedException e) {
			String exResp = appEx.capturaExcecao(e, resp);
			resp.getWriter().write(exResp);
			Thread.currentThread().interrupt();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("application/json");
		try {
			String imagem = consome.coletaImagemJSON("0XYvRd7oD"); // passar id de escolha.
			resp.getWriter().write(imagem);
		} catch (IOException | URISyntaxException | InterruptedException e) {
			String exResp = appEx.capturaExcecao(e, resp);
			resp.getWriter().write(exResp);
			Thread.currentThread().interrupt();
		}
	}
}
