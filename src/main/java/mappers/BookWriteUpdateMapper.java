package mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.BookDTO;

import entity.Book;

import service.AuthorService;
import service.PublisherService;


public class BookWriteUpdateMapper implements Mapper<BookDTO, Book> {

    @Override
    public Book map(BookDTO from) {
               return Book.builder()
                       .title(from.getTitle())
                       .author(AuthorService.getInstance().findByNameOrElseAdd(from.getAuthor()))
                       .publisher(PublisherService.getInstance().findByName(from.getPublisher()))
                       .build();
    }


}
