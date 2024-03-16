package mappers;

import dto.PublisherDTO;
import entity.Publisher;

public class PublisherWriteUpdateMapper implements Mapper<PublisherDTO, Publisher> {
    @Override
    public Publisher map(PublisherDTO from) {
        return Publisher.builder()
                .name(from.getName())
                .address(from.getAddress())
                .build();
    }
}
