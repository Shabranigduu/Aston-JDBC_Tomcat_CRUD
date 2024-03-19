package controllers.bookController;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.BookDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.BookService;

import java.io.IOException;
import java.io.PrintWriter;

import static util.ServletUtil.getBody;

@WebServlet("/book")
public class BookCRUD extends HttpServlet {

    private BookService bookService = new BookService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        BookDTO bookDTO = bookService.findById(Integer.parseInt(req.getParameter("id")));
        String json = new ObjectMapper().writeValueAsString(bookDTO);
        PrintWriter pw = resp.getWriter();
        pw.print(json);
        pw.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = getBody(req);
        BookDTO bookDTO = new ObjectMapper().readValue(json, BookDTO.class);
        try {
            bookDTO = bookService.add(bookDTO);
            String addJson = new ObjectMapper().writeValueAsString(bookDTO);
            resp.setContentType("application/json");
            PrintWriter pw = resp.getWriter();
            pw.print(addJson);
            pw.flush();
        } catch (NullPointerException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Publisher not found. Check publisher name or add this publisher before add this book");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = getBody(req);
        BookDTO bookDTO = new ObjectMapper().readValue(json, BookDTO.class);
        BookDTO update = bookService.update(Integer.parseInt(req.getParameter("id")), bookDTO);
        String updateJson = new ObjectMapper().writeValueAsString(update);
        resp.setContentType("application/json");
        PrintWriter pw = resp.getWriter();
        pw.print(updateJson);
        pw.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!bookService.delete(Integer.parseInt(req.getParameter("id")))) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Book not found");
        }
    }
}
