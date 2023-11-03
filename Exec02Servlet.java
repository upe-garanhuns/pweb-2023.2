package br.upe.garanhus.esw.pweb.controle;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import br.upe.garanhus.esw.pweb.modelo.AplicacaoException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet(urlPatterns = "/processa-imagem")
public class Exec02Servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Exec02Servlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            
            String apiUrl = "https://api.thecatapi.com/v1/images/search";
            URL url = new URL(apiUrl);
            
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
         
                InputStream inputStream = con.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder responseBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }

                reader.close();
                inputStream.close();

           
                String respostaAPI = responseBuilder.toString();

                response.setContentType("application/json");
                response.getWriter().write(respostaAPI);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); 
                response.getWriter().write("Erro ao acessar a API de gatos");
            }

            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erro interno do servidor");
        }
    }
}