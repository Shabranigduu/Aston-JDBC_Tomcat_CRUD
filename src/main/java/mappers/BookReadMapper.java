package mappers;

import dto.BookDTO;
import entity.Book;

public class BookReadMapper implements Mapper<Book, BookDTO> {
    @Override
    public BookDTO map(Book from) {
        return new BookDTO(from.getTitle(), from.getPublisher().getName(), from.getAuthor().getFirstname() + " " + from.getAuthor().getLastname());
    }


}
