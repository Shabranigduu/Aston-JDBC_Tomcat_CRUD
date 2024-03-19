package service;

import dao.PublisherDAO;
import dto.PublisherDTO;
import entity.Publisher;
import mappers.PublisherReadMapper;
import mappers.PublisherWriteUpdateMapper;

public class PublisherService {

    PublisherDAO publisherDAO = new PublisherDAO();
    private final PublisherReadMapper publisherReadMapper = new PublisherReadMapper();
    private final PublisherWriteUpdateMapper publisherWriteUpdateMapper = new PublisherWriteUpdateMapper();

    public Publisher findByName(String name) {
        return publisherDAO.getById(publisherDAO.findByName(name));
    }

    public Publisher findById(Integer id) {
        return publisherDAO.getById(id);
    }

    public Publisher add(Publisher publisher) {
        return publisherDAO.add(publisher);
    }

    public Publisher add(PublisherDTO publisherDTO) {
        return publisherDAO.add(getPublisher(publisherDTO));
    }

    public Publisher getPublisher(PublisherDTO publisherDTO) {
        return publisherWriteUpdateMapper.map(publisherDTO);
    }

    public PublisherDTO getPublisherDTO(Publisher publisher) {
        return publisherReadMapper.map(publisher);
    }

    public boolean update(Integer id, PublisherDTO publisherDTO) {
        Publisher publisherToUpdate = publisherDAO.getById(id);
        if (publisherToUpdate == null) {
            return false;
        }
        Publisher updateFrom = publisherWriteUpdateMapper.map(publisherDTO);
        publisherToUpdate.setName(updateFrom.getName());
        publisherToUpdate.setAddress(updateFrom.getAddress());
        publisherDAO.update(publisherToUpdate);
        return true;
    }

    public boolean delete(Integer id) {
        return publisherDAO.delete(id);
    }

}
