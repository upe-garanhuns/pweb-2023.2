package br.upe.garanhus.esw.pweb.controle;

import java.io.IOException;
import java.util.List;
import br.upe.garanhus.esw.pweb.modelo.IdDTO;
import br.upe.garanhus.esw.pweb.modelo.RequestModel;
import br.upe.garanhus.esw.pweb.modelo.RequestService;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/processa-imagem")
public class Exec02Servlet extends HttpServlet {
  
	private static final long serialVersionUID = 1L;
	private transient Jsonb jsonb;
    private transient RequestService requestService;
       
    public Exec02Servlet() {
        super();
        this.jsonb = JsonbBuilder.create();
        this.requestService = new RequestService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {    
        List<RequestModel> imagens = requestService.obterImagens();
        
        String resultado = jsonb.toJson(imagens);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(resultado);
        response.setStatus(200);      
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String res = requestService.jsonBuilder(request.getReader());
        IdDTO iddto = jsonb.fromJson(res, IdDTO.class);
        
        RequestModel imagem = requestService.obterImagemPorId(iddto);
        String resultado = jsonb.toJson(imagem);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(resultado);
        response.setStatus(200);
    }

}
