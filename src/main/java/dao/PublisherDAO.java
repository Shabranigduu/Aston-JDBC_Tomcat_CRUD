package dao;

import entity.Publisher;
import util.ConnectionManager;

import java.sql.*;

public class PublisherDAO {

    private static final String FIND_BY_NAME_SQL = """
            SELECT id
            from publisher p            
            where p.name = ?
            """;

    private static final String GET_BY_ID_SQL = """
            SELECT id, name, address
            from publisher p            
            where p.id = ?
            """;

    private static final String CREATE_SQL = """
            INSERT INTO publisher(name, address)
            VALUES (?, ?)
            """;

    private static final String UPDATE_SQL = """
            UPDATE publisher
            SET name = ?,
                address = ?
            WHERE id = ?
            """;

    private static final String DELETE_SQL = """
            DELETE FROM publisher
            WHERE id = ?
            """;

    public Integer findByName(String name) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME_SQL)) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            Integer id = -1;

            if (resultSet.next()) {
                id = resultSet.getObject("id", Integer.class);
            }

            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Publisher getById(Integer id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID_SQL)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Publisher publisher = null;

            while (resultSet.next()) {
                publisher = Publisher.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .address(resultSet.getString("address"))
                        .build();
            }

            return publisher;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Publisher add(Publisher publisher) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, publisher.getName());
            preparedStatement.setString(2, publisher.getAddress());

            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();
            if (keys.next()) {
                publisher.setId(keys.getInt("id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return publisher;
    }

    public void update(Publisher publisher) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, publisher.getName());
            preparedStatement.setString(2, publisher.getAddress());
            preparedStatement.setInt(3, publisher.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
