package controllers.publisherController;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.PublisherDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.PublisherService;

import java.io.IOException;
import java.util.Scanner;

@WebServlet("/publisher")
public class UpdatePublisher extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        Scanner scanner = new Scanner(req.getInputStream());
        String json = scanner.useDelimiter("\\A").next();
        PublisherDTO publisherDTO = new ObjectMapper().readValue(json, PublisherDTO.class);
        if(!PublisherService.getInstance().update(id, publisherDTO)){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Publisher with id="+id+" not found");
        }
    }
}
