package dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Author;
import entity.Publisher;
import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BookDTO{

    private String title;
    private String publisher;
    private String author;


    @Override
    public String toString() {
        return "BookDTO{" +
                "title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDTO bookDTO = (BookDTO) o;
        return Objects.equals(title, bookDTO.title) && Objects.equals(publisher, bookDTO.publisher) && Objects.equals(author, bookDTO.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, publisher, author);
    }
}
