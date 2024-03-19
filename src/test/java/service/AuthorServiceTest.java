package service;

import dao.AuthorDAO;
import dto.AuthorDTO;
import entity.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    @Mock
    AuthorDAO authorDAO;
    @InjectMocks
    AuthorService authorService;
    Author mockAuthor = Author.builder()
            .id(1)
            .firstname("firstname")
            .lastname("lastname")
            .build();

    Author mockAuthorWithoutId = Author.builder()
            .firstname("firstname")
            .lastname("lastname")
            .build();

    Author mockNewAddedAuthor = Author.builder()
            .id(1)
            .firstname("not_exist")
            .lastname("name")
            .build();

    AuthorDTO mockAuthorDTO = new AuthorDTO("firstname lastname");

    @Test
    void findByIdTest() {
        when(authorDAO.getById(1)).thenReturn(mockAuthor);
        Author author = authorService.findById(1);
        assertEquals(mockAuthor, author);
        verify(authorDAO, times(1)).getById(1);
    }

    @Test
    void getAuthorDTOTest() {
        AuthorDTO authorDTO = authorService.getAuthorDTO(mockAuthor);
        assertEquals(mockAuthorDTO, authorDTO);
    }

    @Test
    void findByNameOrElseAdd_withExistName_Test() {
        when(authorDAO.findByName("firstname lastname")).thenReturn(mockAuthor);
        Author author = authorService.findByNameOrElseAdd("firstname lastname");
        assertEquals(mockAuthor, author);
        verify(authorDAO, times(1)).findByName("firstname lastname");
        verify(authorDAO, never()).add("firstname lastname");
    }

    @Test
    void findByNameOrElseAdd_withNotExistName_Test() {
        when(authorDAO.add("not_exist name")).thenReturn(mockNewAddedAuthor);
        Author author = authorService.findByNameOrElseAdd("not_exist name");
        assertEquals(mockNewAddedAuthor, author);
        verify(authorDAO, times(1)).findByName("not_exist name");
        verify(authorDAO, times(1)).add("not_exist name");
    }

    @Test
    void add_withAuthor_Test() {
        when(authorDAO.add(mockAuthor)).thenReturn(mockAuthor);
        Author author = authorService.add(mockAuthor);
        assertEquals(mockAuthor, author);
        verify(authorDAO, times(1)).add(mockAuthor);
    }

    @Test
    void add_withAuthorDTO_Test() {
        when(authorDAO.add(any(Author.class))).thenReturn(mockAuthor);
        Author author = authorService.add(mockAuthorDTO);
        assertEquals(mockAuthor, author);
        verify(authorDAO, times(1)).add(any(Author.class));
    }

    @Test
    void getAuthorTest() {
        Author author = authorService.getAuthor(mockAuthorDTO);
        assertEquals(mockAuthorWithoutId, author);
    }

    @Test
    void update_whitExistId_Test() {
        when(authorDAO.getById(1)).thenReturn(mockAuthor);
        boolean result = authorService.update(1, mockAuthorDTO);
        assertTrue(result);
        verify(authorDAO, times(1)).getById(1);
        verify(authorDAO, times(1)).update(any(Author.class));
    }

    @Test
    void update_whitNotExistId_Test() {
        when(authorDAO.getById(2)).thenReturn(null);
        boolean result = authorService.update(2, mockAuthorDTO);
        assertFalse(result);
        verify(authorDAO, times(1)).getById(2);
        verify(authorDAO, never()).update(any(Author.class));
    }

    @Test
    void delete() {
        when(authorDAO.delete(1)).thenReturn(true);
        boolean result = authorService.delete(1);
        assertTrue(result);
        verify(authorDAO, times(1)).delete(1);
    }
}
