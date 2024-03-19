package dao;

import entity.Author;
import entity.Book;
import entity.Publisher;
import util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private static final String GET_BY_ID_SQL = """
            SELECT b.id, title, author_id, firstname, lastname, publisher_id, name, address
            from book b
            join author a on a.id = b.author_id
            join publisher p on p.id = b.publisher_id
            where b.id = ?
            """;
    private static final String CREATE_SQL = """
            INSERT INTO book(title, author_id, publisher_id)
            VALUES (?, ?, ?)
            """;
    private static final String GET_ALL_SQL = """
            SELECT b.id, title, author_id, firstname, lastname, publisher_id, name, address
             from book b
             join author a on a.id = b.author_id
             join publisher p on p.id = b.publisher_id
             """;

    private static final String UPDATE_SQL = """
            update book
                        set title = ?,
                            author_id = ?,
                            publisher_id = ?
                        WHERE id = ?
             """;

    private static final String DELETE_SQL = """
            delete from book
              WHERE id = ?
             """;


    public Book getById(Integer id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID_SQL)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Book book = null;

            while (resultSet.next()) {
                book = Book.builder()
                        .id(resultSet.getObject("id", Integer.class))
                        .title(resultSet.getObject("title", String.class))
                        .author(Author.builder()
                                .id(resultSet.getObject("author_id", Integer.class))
                                .firstname(resultSet.getObject("firstname", String.class))
                                .lastname(resultSet.getObject("lastname", String.class))
                                .build())
                        .publisher(Publisher.builder()
                                .id(resultSet.getObject("publisher_id", Integer.class))
                                .name(resultSet.getObject("name", String.class))
                                .address(resultSet.getObject("address", String.class))
                                .build())
                        .build();
            }

            return book;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Book> getAllBooks() {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            Book book = null;
            List<Book> listOfBooks = new ArrayList<>();

            while (resultSet.next()) {
                book = Book.builder()
                        .id(resultSet.getObject("id", Integer.class))
                        .title(resultSet.getObject("title", String.class))
                        .author(Author.builder()
                                .id(resultSet.getObject("author_id", Integer.class))
                                .firstname(resultSet.getObject("firstname", String.class))
                                .lastname(resultSet.getObject("lastname", String.class))
                                .build())
                        .publisher(Publisher.builder()
                                .id(resultSet.getObject("publisher_id", Integer.class))
                                .name(resultSet.getObject("name", String.class))
                                .address(resultSet.getObject("address", String.class))
                                .build())
                        .build();
                listOfBooks.add(book);
            }

            return listOfBooks;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Book add(Book book) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setInt(2, book.getAuthor().getId());
            preparedStatement.setInt(3, book.getPublisher().getId());

            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();

            if (keys.next()) {
                book.setId(keys.getInt("id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return book;
    }

    public Book update(Book book) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setInt(2, book.getAuthor().getId());
            preparedStatement.setInt(3, book.getPublisher().getId());
            preparedStatement.setInt(4, book.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return book;
    }

    public boolean delete(Integer id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
