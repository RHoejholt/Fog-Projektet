package app.persistence;

import app.entities.User;
import app.exception.DatabaseException;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public static void createUser(String username, String password, ConnectionPool pool) throws DatabaseException {
        String sql = "insert into users (username, password, role) VALUES (?,?,?);";
        //Hashing and salting passwords for security.
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        try (Connection connection = pool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, hashedPassword);
            ps.setString(3, "customer");

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved oprettelse af bruger");
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public static User login(String username, String password, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM users WHERE username=?";
        try (Connection connection = connectionPool.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedHashedPassword = rs.getString("password");
                if (BCrypt.checkpw(password, storedHashedPassword)) {
                    int id = rs.getInt("user_id");
                    String role = rs.getString("role");
                    return new User(id, username, role);
                } else {
                    // Catching wrong passwords.
                    throw new DatabaseException("Kodeord matcher ikke. Prøv igen");
                }
            } else {
                // Catching wrong usernames.
                throw new DatabaseException("Brugernavn matcher ikke. Prøv igen");
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
