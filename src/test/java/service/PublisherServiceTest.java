package service;

import dao.PublisherDAO;
import dto.PublisherDTO;
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
class PublisherServiceTest {
    @Mock
    PublisherDAO publisherDAO;
    @InjectMocks
    PublisherService publisherService;
    Publisher mockPublisher = Publisher.builder()
            .id(1)
            .name("name")
            .address("address")
            .build();

    Publisher mockPublisherWithoutId = Publisher.builder()
            .name("name")
            .address("address")
            .build();

    Publisher mockNewAddedPublisher = Publisher.builder()
            .id(1)
            .name("not_exist")
            .address("address")
            .build();

    PublisherDTO mockPublisherDTO = new PublisherDTO("name", "address");

    @Test
    void findByIdTest() {
        when(publisherDAO.getById(1)).thenReturn(mockPublisher);
        Publisher publisher = publisherService.findById(1);
        assertEquals(mockPublisher, publisher);
        verify(publisherDAO, times(1)).getById(1);
    }

    @Test
    void getPublisherDTOTest() {
        PublisherDTO publisherDTO = publisherService.getPublisherDTO(mockPublisher);
        assertEquals(mockPublisherDTO, publisherDTO);
    }

    @Test
    void findByNameOrElseAdd_withExistName_Test() {
        when(publisherDAO.findByName("name")).thenReturn(mockPublisher.getId());
        when(publisherDAO.getById(1)).thenReturn(mockPublisher);
        Publisher publisher = publisherService.findByName("name");
        assertEquals(mockPublisher, publisher);
        verify(publisherDAO, times(1)).findByName("name");
    }

    @Test
    void findByNameOrElseAdd_withNotExistName_Test() {
        when(publisherDAO.findByName("not_exist name")).thenReturn(null);
        Publisher publisher = publisherService.findByName("not_exist name");
        assertNull(publisher);
        verify(publisherDAO, times(1)).findByName("not_exist name");
    }

    @Test
    void add_withPublisher_Test() {
        when(publisherDAO.add(mockPublisher)).thenReturn(mockPublisher);
        Publisher publisher = publisherService.add(mockPublisher);
        assertEquals(mockPublisher, publisher);
        verify(publisherDAO, times(1)).add(mockPublisher);
    }

    @Test
    void add_withPublisherDTO_Test() {
        when(publisherDAO.add(any(Publisher.class))).thenReturn(mockPublisher);
        Publisher publisher = publisherService.add(mockPublisherDTO);
        assertEquals(mockPublisher, publisher);
        verify(publisherDAO, times(1)).add(any(Publisher.class));
    }

    @Test
    void getPublisherTest() {
        Publisher publisher = publisherService.getPublisher(mockPublisherDTO);
        assertEquals(mockPublisherWithoutId, publisher);
    }

    @Test
    void update_whitExistId_Test() {
        when(publisherDAO.getById(1)).thenReturn(mockPublisher);
        boolean result = publisherService.update(1, mockPublisherDTO);
        assertTrue(result);
        verify(publisherDAO, times(1)).getById(1);
        verify(publisherDAO, times(1)).update(any(Publisher.class));
    }

    @Test
    void update_whitNotExistId_Test() {
        when(publisherDAO.getById(2)).thenReturn(null);
        boolean result = publisherService.update(2, mockPublisherDTO);
        assertFalse(result);
        verify(publisherDAO, times(1)).getById(2);
        verify(publisherDAO, never()).update(any(Publisher.class));
    }

    @Test
    void delete() {
        when(publisherDAO.delete(1)).thenReturn(true);
        boolean result = publisherService.delete(1);
        assertTrue(result);
        verify(publisherDAO, times(1)).delete(1);
    }

}