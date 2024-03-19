package service;

import dao.BookDAO;
import dto.BookDTO;
import entity.Author;
import entity.Book;
import entity.Publisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    BookDAO bookDAO;
    @InjectMocks
    BookService bookService;
    Book mockBook = Book.builder()
            .id(1)
            .title("title")
            .publisher(Publisher.builder().name("publisher").build())
            .author(Author.builder().firstname("author").lastname("name").build())
            .build();

    Book mockBookWithoutId = Book.builder()
            .title("title")
            .publisher(new Publisher())
            .author(new Author())
            .build();


    BookDTO mockBookDTO = new BookDTO("title", "publisher", "author name");

    @Test
    void findByIdTest() {
        when(bookDAO.getById(1)).thenReturn(mockBook);
        BookDTO bookDTO = bookService.findById(1);
        assertEquals(mockBookDTO, bookDTO);
        verify(bookDAO, times(1)).getById(1);
    }



    @Test
    void add_withBookDTO_Test() {
        when(bookDAO.add(any(Book.class))).thenReturn(mockBook);
        BookDTO book = bookService.add(mockBookDTO);
        assertEquals(mockBookDTO, book);
        verify(bookDAO, times(1)).add(any(Book.class));
    }



    @Test
    void update_whitNotExistId_Test() {
        when(bookDAO.getById(2)).thenReturn(null);
        BookDTO result = bookService.update(2, mockBookDTO);
        assertNull(result);
        verify(bookDAO, times(1)).getById(2);
        verify(bookDAO, never()).update(any(Book.class));
    }

    @Test
    void delete() {
        when(bookDAO.delete(1)).thenReturn(true);
        boolean result = bookService.delete(1);
        assertTrue(result);
        verify(bookDAO, times(1)).delete(1);
    }

}