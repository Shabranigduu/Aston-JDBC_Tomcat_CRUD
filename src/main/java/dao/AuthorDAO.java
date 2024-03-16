package dao;

import entity.Author;
import entity.Publisher;
import util.ConnectionManager;

import java.sql.*;
import java.util.StringTokenizer;

public class AuthorDAO {

    private AuthorDAO() {
    }

    private static final AuthorDAO INSTANCE = new AuthorDAO();

    public static AuthorDAO getInstance() {
        return INSTANCE;
    }

    private static final String GET_BY_ID_SQL = """
            SELECT b.id, title, author_id, firstname, lastname, publisher_id, name, address
            from book b
            join author a on a.id = b.author_id
            join publisher p on p.id = b.publisher_id
            where b.id = ?
            """;
    private static final String CREATE_SQL = """
            INSERT INTO author(firstname, lastname)
            VALUES (?, ?)
            """;
    private static final String GET_ALL_SQL = """
            SELECT b.id, title, author_id, firstname, lastname, publisher_id, name, address
             from book b
             join author a on a.id = b.author_id
             join publisher p on p.id = b.publisher_id
             """;

    private static final String UPDATE = """
            update ticket
                        set passenger_no = ?,
                            passenger_name = ?,
                            flight_id = ?,
                            seat_no = ?,
                            cost = ?
                        WHERE id = ?
             """;

    private static final String FIND_BY_NAME_SQL = """
            select *
            from author
            where firstname = ? or CONCAT(firstname, ' ', lastname) = ? or CONCAT(lastname, ' ', firstname) = ? or lastname = ?;
            """;

    public Author findByName(String name) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME_SQL)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            Author author = null;

            while (resultSet.next()) {
                author = Author.builder()
                        .id(resultSet.getInt("id"))
                        .firstname(resultSet.getString("firstname"))
                        .lastname(resultSet.getString("lastname"))
                        .build();
            }
            return author;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Author getById(Integer id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID_SQL)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Author author = null;

            while (resultSet.next()) {
                author = Author.builder()
                        .id(resultSet.getInt("id"))
                        .firstname(resultSet.getString("firstname"))
                        .lastname(resultSet.getString("lastname"))
                        .build();
            }

            return author;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Author add(Author author) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, author.getFirstname());
            preparedStatement.setString(2, author.getLastname());

            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();
            if (keys.next()) {
                author.setId(keys.getInt("id"));
            }
            return author;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Author add(String name) {
        StringTokenizer tokenizer = new StringTokenizer(name, " ");
        String firstname = "";
        String lastname = "";
        if (tokenizer.hasMoreTokens()) {
            firstname = tokenizer.nextToken();
        }
        if (tokenizer.hasMoreTokens()) {
            lastname = tokenizer.nextToken();
        }
        Author author = Author.builder()
                .firstname(firstname)
                .lastname(lastname)
                .build();

        return add(author);

    }

}
