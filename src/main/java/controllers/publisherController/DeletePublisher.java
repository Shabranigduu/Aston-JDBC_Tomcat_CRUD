package controllers.publisherController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.PublisherService;

import java.io.IOException;

@WebServlet("/publisher")
public class DeletePublisher extends HttpServlet {
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(!PublisherService.getInstance().delete(Integer.parseInt(req.getParameter("id")))){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Publisher not found");
        }
    }
}
