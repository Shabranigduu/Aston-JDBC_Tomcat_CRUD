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

    private AuthorService authorService = new AuthorService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        Author author = authorService.findById(id);
        if (author == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Author with id=" + id + " not found");
        } else {
            AuthorDTO authorDTO = authorService.getAuthorDTO(author);
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
        authorService.add(authorDTO);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        String jsonReq = getBody(req);
        AuthorDTO authorDTO = new ObjectMapper().readValue(jsonReq, AuthorDTO.class);
        if (!authorService.update(id, authorDTO)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Author with id=" + id + " not found");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        if (!authorService.delete(id)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Author with id=" + id + " not found");
        }
    }
}
