package controllers.bookController;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.BookDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.BookService;


import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/book")
public class GetBook extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        BookDTO bookDTO = BookService.getInstance().getBookById(Integer.parseInt(req.getParameter("id")));
        String json = new ObjectMapper().writeValueAsString(bookDTO);
        PrintWriter pw = resp.getWriter();
        pw.print(json);
        pw.flush();
    }
}
