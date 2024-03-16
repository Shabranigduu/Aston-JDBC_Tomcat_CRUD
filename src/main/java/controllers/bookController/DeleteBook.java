package controllers.bookController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.BookService;

import java.io.IOException;

@WebServlet("/book")
public class DeleteBook extends HttpServlet {
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(!BookService.getInstance().delete(Integer.parseInt(req.getParameter("id")))){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Book not found");
        }
    }
}
