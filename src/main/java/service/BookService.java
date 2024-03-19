package service;

import dao.BookDAO;
import dto.BookDTO;
import entity.Book;
import mappers.BookReadMapper;
import mappers.BookWriteUpdateMapper;

import java.util.List;
import java.util.stream.Collectors;

public class BookService {
    
    private BookDAO bookDAO = new BookDAO();

    private final BookReadMapper bookReadMapper = new BookReadMapper();

    private final BookWriteUpdateMapper bookWriteUpdateMapper = new BookWriteUpdateMapper();


    public BookDTO findById(Integer id) {
        Book book = bookDAO.getById(id);
        if (book == null) {
            return null;
        }
        return bookReadMapper.map(book);
    }

    public List<BookDTO> getAllBooks() {
        List<Book> listOfBook = bookDAO.getAllBooks();
        return listOfBook.stream()
                .map(bookReadMapper::map)
                .collect(Collectors.toList());
    }

    public BookDTO update(Integer id, BookDTO bookDTO){
        Book bookToUpdate = bookDAO.getById(id);
        if (bookToUpdate == null){
            return null;
        }
        Book from = bookWriteUpdateMapper.map(bookDTO);
        bookToUpdate.setTitle(from.getTitle());
        bookToUpdate.setAuthor(from.getAuthor());
        bookToUpdate.setPublisher(from.getPublisher());

        return bookReadMapper.map(bookDAO.update(bookToUpdate));
    }

    public BookDTO add(BookDTO bookDTO){
        Book book = bookWriteUpdateMapper.map(bookDTO);
        book = bookDAO.add(book);

        return bookReadMapper.map(book);
    }

    public boolean delete(Integer id){
        return bookDAO.delete(id);
    }
}
