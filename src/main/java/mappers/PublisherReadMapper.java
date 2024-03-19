package mappers;

import dto.PublisherDTO;
import entity.Publisher;

public class PublisherReadMapper implements Mapper<Publisher, PublisherDTO> {


    @Override
    public PublisherDTO map(Publisher from) {
        return new PublisherDTO(from.getName(), from.getAddress());
    }
}
