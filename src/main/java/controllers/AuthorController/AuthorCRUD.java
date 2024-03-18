package controllers.AuthorController;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.AuthorDTO;
import entity.Author;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AuthorService;

import java.io.IOException;
import java.io.PrintWriter;

import static util.ServletUtil.getBody;

@WebServlet("/author")
public class AuthorCRUD extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        Author author = AuthorService.getInstance().findById(id);
        if (author == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Author with id=" + id + " not found");
        } else {
            AuthorDTO authorDTO = AuthorService.getInstance().getAuthorDTO(author);
            resp.setContentType("application/json");
            String json = new ObjectMapper().writeValueAsString(authorDTO);
            PrintWriter pw = resp.getWriter();
            pw.print(json);
            pw.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String jsonReq = getBody(req);
        AuthorDTO authorDTO = new ObjectMapper().readValue(jsonReq, AuthorDTO.class);
        AuthorService.getInstance().add(authorDTO);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        String jsonReq = getBody(req);
        AuthorDTO authorDTO = new ObjectMapper().readValue(jsonReq, AuthorDTO.class);
        if (!AuthorService.getInstance().update(id, authorDTO)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Author with id=" + id + " not found");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        if (!AuthorService.getInstance().delete(id)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Author with id=" + id + " not found");
        }
    }
}
