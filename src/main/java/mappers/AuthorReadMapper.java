package mappers;

import dto.AuthorDTO;
import entity.Author;

public class AuthorReadMapper implements Mapper<Author, AuthorDTO> {
    @Override
    public AuthorDTO map(Author from) {
        return new AuthorDTO(from.getFirstname() + " " + from.getLastname());
    }
}
