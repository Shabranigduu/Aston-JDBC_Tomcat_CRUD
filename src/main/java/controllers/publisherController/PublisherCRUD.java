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

import static util.ServletUtil.getBody;

@WebServlet("/publisher")
public class PublisherCRUD extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        Publisher publisher = PublisherService.getInstance().findById(Integer.parseInt(req.getParameter("id")));
        PublisherDTO publisherDTO = PublisherService.getInstance().getPublisherDTO(publisher);
        String json = new ObjectMapper().writeValueAsString(publisherDTO);
        PrintWriter pw = resp.getWriter();
        pw.print(json);
        pw.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = getBody(req);
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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        String json = getBody(req);
        PublisherDTO publisherDTO = new ObjectMapper().readValue(json, PublisherDTO.class);
        if (!PublisherService.getInstance().update(id, publisherDTO)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Publisher with id=" + id + " not found");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        if (!PublisherService.getInstance().delete(id)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Publisher with id=" + id + " not found");
        }
    }
}
