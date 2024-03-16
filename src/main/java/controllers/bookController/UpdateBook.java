package controllers.bookController;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.BookDTO;
import dto.PublisherDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.BookService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

@WebServlet("/book")
public class UpdateBook extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Scanner scanner = new Scanner(req.getInputStream(), "UTF-8");
        String json = scanner.useDelimiter("\\A").next();
        scanner.close();

        BookDTO bookDTO = new ObjectMapper().readValue(json, BookDTO.class);

        BookDTO update = BookService.getInstance().update(Integer.parseInt(req.getParameter("id")), bookDTO);

        String updateJson = new ObjectMapper().writeValueAsString(update);

        resp.setContentType("application/json");
        PrintWriter pw = resp.getWriter();
        pw.print(updateJson);
        pw.flush();
    }
}
