package service;

import dao.PublisherDAO;
import dto.PublisherDTO;
import entity.Publisher;
import mappers.PublisherReadMapper;
import mappers.PublisherWriteUpdateMapper;

public class PublisherService {

    private static final PublisherService INSTANCE = new PublisherService();

    public static PublisherService getInstance() {
        return INSTANCE;
    }

    private final PublisherReadMapper publisherReadMapper = new PublisherReadMapper();
    private final PublisherWriteUpdateMapper publisherWriteUpdateMapper = new PublisherWriteUpdateMapper();

    public Publisher findByName(String name){
     return findById(PublisherDAO.getInstance().findByName(name));
    }
    public Publisher findById(Integer id){
        return PublisherDAO.getInstance().getById(id);
    }

    public Publisher add(Publisher publisher){
        return PublisherDAO.getInstance().add(publisher);
    }
    public Publisher add(PublisherDTO publisherDTO){
        return PublisherDAO.getInstance().add(getPublisher(publisherDTO));
    }
    public Publisher getPublisher(PublisherDTO publisherDTO){
        return publisherWriteUpdateMapper.map(publisherDTO);
    }

    public PublisherDTO getPublisherDTO(Publisher publisher){
        return publisherReadMapper.map(publisher);
    }

    public boolean update(Integer id, PublisherDTO publisherDTO){
        Publisher publisherToUpdate = PublisherDAO.getInstance().getById(id);
        if(publisherToUpdate==null){
            return false;
        }
        Publisher updateFrom = publisherWriteUpdateMapper.map(publisherDTO);
        publisherToUpdate.setName(updateFrom.getName());
        publisherToUpdate.setAddress(updateFrom.getAddress());
        PublisherDAO.getInstance().update(publisherToUpdate);
        return true;
    }

    public boolean delete(Integer id){
        return PublisherDAO.getInstance().delete(id);
    }

}
