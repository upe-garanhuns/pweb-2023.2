package br.upe.garanhus.esw.pweb.controle;


import br.upe.garanhus.esw.pweb.model.DogService;
import br.upe.garanhus.esw.pweb.model.DogTO;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static br.upe.garanhus.esw.pweb.model.JsonService.*;

@WebServlet(urlPatterns = "/processa-imagem")
public class Exec02Servlet extends HttpServlet {
    private final DogService dogService = new DogService();
    private final transient Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String jsonOutput = processResponseToJson(dogService.list().body());
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            resp.getWriter().append(jsonOutput);
        } catch (Exception ex) {
            sendJsonErrorResponse(resp, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String processedRequest = takeDataRequest(req).toString();
            DogTO dog = jsonb.fromJson(processedRequest, DogTO.class);
            String consult = dogService.consult(dog).body();

            dog = createDogFromJson(consult);
            String output = jsonb.toJson(dog);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            resp.getWriter().append(output);
        } catch (Exception e) {
            sendJsonErrorResponse(resp, e);
        }
    }

    private StringBuilder takeDataRequest(HttpServletRequest request) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line;
        StringBuilder sb = new StringBuilder();

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb;
    }
}
