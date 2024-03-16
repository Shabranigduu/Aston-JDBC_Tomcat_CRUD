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
import java.util.List;

@WebServlet("/book/all")
public class GetAllBooks extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        List<BookDTO> listOfBookDTO = BookService.getInstance().getAllBooks();
        String json = new ObjectMapper().writeValueAsString(listOfBookDTO);

        PrintWriter pw = resp.getWriter();
        pw.print(json);
        pw.flush();

        super.doGet(req, resp);
    }
}
