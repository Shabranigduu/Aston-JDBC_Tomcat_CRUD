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
import java.util.Scanner;

@WebServlet("/book")
public class AddBook extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Scanner scanner = new Scanner(req.getInputStream(), "UTF-8");
        String json = scanner.useDelimiter("\\A").next();
        scanner.close();

        BookDTO bookDTO = new ObjectMapper().readValue(json, BookDTO.class);

        try {
            bookDTO = BookService.getInstance().add(bookDTO);
            String addJson = new ObjectMapper().writeValueAsString(bookDTO);
            resp.setContentType("application/json");
            PrintWriter pw = resp.getWriter();
            pw.print(addJson);
            pw.flush();
        }catch (NullPointerException e){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Publisher not found. Check publisher name or add this publisher before add this book");
        }

    }
}
