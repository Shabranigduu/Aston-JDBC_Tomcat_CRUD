package entity;

import lombok.*;

import java.util.List;

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
}
