package mappers;

import dto.BookDTO;

import entity.Book;

import service.AuthorService;
import service.PublisherService;


public class BookWriteUpdateMapper implements Mapper<BookDTO, Book> {

    @Override
    public Book map(BookDTO from) {
        return Book.builder()
                .title(from.getTitle())
                .author(new AuthorService().findByNameOrElseAdd(from.getAuthor()))
                .publisher(new PublisherService().findByName(from.getPublisher()))
                .build();
    }


}
