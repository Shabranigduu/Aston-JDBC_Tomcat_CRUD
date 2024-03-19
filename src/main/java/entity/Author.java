package entity;

import lombok.*;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class Author {

    private Integer id;
    private String firstname;
    private String lastname;
    private List<Book> books;

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", books=" + books +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(firstname, author.firstname) && Objects.equals(lastname, author.lastname) && Objects.equals(books, author.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, books);
    }
}
