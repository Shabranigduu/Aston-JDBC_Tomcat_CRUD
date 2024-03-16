package entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class Book {

    private Integer id;
    private String title;
    private Publisher publisher;
    private Author author;


}
