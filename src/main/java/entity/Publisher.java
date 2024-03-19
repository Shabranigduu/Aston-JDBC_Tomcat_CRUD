package entity;

import lombok.*;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class Publisher {

    private Integer id;
    private String name;
    private String address;
    private List<Book> books;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publisher publisher = (Publisher) o;
        return Objects.equals(name, publisher.name) && Objects.equals(address, publisher.address) && Objects.equals(books, publisher.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, books);
    }
}
