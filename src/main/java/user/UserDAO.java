package user;

import dto.DatabaseConnector;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public static User getUserByUsernameAndPassword(String username, String password) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String[] roles = resultSet.getString("roles").split(",");
                return new User(username, password, roles);
            }
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
