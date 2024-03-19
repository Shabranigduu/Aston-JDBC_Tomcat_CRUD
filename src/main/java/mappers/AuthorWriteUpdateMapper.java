package mappers;

import dto.AuthorDTO;
import entity.Author;

import java.util.StringTokenizer;

public class AuthorWriteUpdateMapper implements Mapper<AuthorDTO, Author> {
    @Override
    public Author map(AuthorDTO from) {
        StringTokenizer tokenizer = new StringTokenizer(from.getName(), " ");
        String firstname = "";
        String lastname = "";
        if (tokenizer.hasMoreTokens()) {
            firstname = tokenizer.nextToken();
        }
        if (tokenizer.hasMoreTokens()) {
            lastname = tokenizer.nextToken();
        }
        return Author.builder()
                .firstname(firstname)
                .lastname(lastname)
                .build();
    }
}
