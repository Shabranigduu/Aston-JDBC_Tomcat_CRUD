package controllers.publisherController;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.PublisherDTO;
import entity.Publisher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.PublisherService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

@WebServlet("/publisher")
public class AddPublisher extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Scanner scanner = new Scanner(req.getInputStream(), "UTF-8");
        String json = scanner.useDelimiter("\\A").next();
        scanner.close();

        PublisherDTO publisherDTO = new ObjectMapper().readValue(json, PublisherDTO.class);

        Publisher publisher = PublisherService.getInstance().getPublisher(publisherDTO);
        publisher = PublisherService.getInstance().add(publisher);

        publisherDTO = PublisherService.getInstance().getPublisherDTO(publisher);
        String jsonResponse = new ObjectMapper().writeValueAsString(publisherDTO);

        resp.setContentType("application/json");

        PrintWriter pw = resp.getWriter();
        pw.print(jsonResponse);
        pw.flush();
    }
}
