package service;

import dao.AuthorDAO;
import dto.AuthorDTO;
import entity.Author;
import mappers.AuthorReadMapper;
import mappers.AuthorWriteUpdateMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {
    Author mockAuthor;
    Author mockNewAddedAuthor;
    AuthorDTO mockAuthorDTO;
    MockedStatic<AuthorDAO> mockedStatic;
    @Mock
    AuthorReadMapper authorReadMapper;
    @Mock
    AuthorWriteUpdateMapper authorWriteUpdateMapper;
    @Mock
    AuthorDAO authorDAO;
    @InjectMocks
    AuthorService authorService;


    @BeforeEach
    void setup() {

        // Инициализация статического мока для AuthorDAO
        mockedStatic = Mockito.mockStatic(AuthorDAO.class);

        // Создание мока для объекта AuthorDAO
        AuthorDAO mockAuthorDAO = Mockito.mock(AuthorDAO.class);

        // Настройка статического мока для возвращения нашего мок объекта
        mockedStatic.when(AuthorDAO::getInstance).thenReturn(mockAuthorDAO);
        mockAuthor = Author.builder()
                .id(1)
                .firstname("firstname")
                .lastname("lastname")
                .build();

        mockNewAddedAuthor = Author.builder()
                .id(1)
                .firstname("not_exist")
                .lastname("name")
                .build();

        mockAuthorDTO = new AuthorDTO("firstname lastname");

//        given(mockAuthorDAO.getById(1)).willReturn(mockAuthor);
//        given(mockAuthorDAO.findByName("firstname lastname")).willReturn(mockAuthor);
//        given(mockAuthorDAO.findByName("not_exist name")).willReturn(null);
//        given(mockAuthorDAO.add("not_exist name")).willReturn(mockNewAddedAuthor);
//        given(mockAuthorDAO.add(mockAuthor)).willReturn(mockAuthor);
//        given(mockAuthorDAO.delete(1)).willReturn(true);


    }

    @Test
    void findByIdTest() {
        when(mockedStatic
        Author author = AuthorService.getInstance().findById(1);
        assertEquals(mockAuthor, author);
    }

    @Test
    void findByNameOrElseAdd_withExistName() {
        Author author = AuthorService.getInstance().findByNameOrElseAdd("firstname lastname");
        assertEquals(mockAuthor, author);
    }

    @Test
    void findByNameOrElseAdd_withNotExistName() {
        Author author = AuthorService.getInstance().findByNameOrElseAdd("not_exist name");
        assertEquals(mockNewAddedAuthor, author);
    }

    @Test
    void addAuthorTest(){
        Author author = AuthorService.getInstance().add(mockAuthor);
        assertEquals(mockAuthor, author);
    }


    @Test
    void getAuthorDTOTest() {
        AuthorDTO authorDTO = AuthorService.getInstance().getAuthorDTO(mockAuthor);
        assertEquals(mockAuthorDTO, authorDTO);
    }

    @Test
    void deleteTest() {
        boolean result = AuthorService.getInstance().delete(1);
        assertEquals(true, result);
    }

    @AfterEach
    void tearDown(){
        mockedStatic.close();
    }
}