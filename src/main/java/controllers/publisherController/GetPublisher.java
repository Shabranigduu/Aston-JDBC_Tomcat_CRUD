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

@WebServlet("/publisher")
public class GetPublisher extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Publisher publisher = PublisherService.getInstance().findById(Integer.parseInt(req.getParameter("id")));
        PublisherDTO publisherDTO = PublisherService.getInstance().getPublisherDTO(publisher);

        String json = new ObjectMapper().writeValueAsString(publisherDTO);

        PrintWriter pw = resp.getWriter();
        pw.print(json);
        pw.flush();
    }
}
