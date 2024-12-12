package app.persistence;

import app.entities.User;
import app.exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarportMapper {
    public static List<String> getDimensionOptions(String column, String table, ConnectionPool dbConnection) throws DatabaseException {
        // Define valid table and column names
        // This is a pretty limiting way of doing it. It's just a whitelist.
        // We should probably make a new method in the future that gives the admins an option to add/remove/create tables
        List<String> validTables = List.of("dimensioner_bredde", "dimensioner_laengde","tag_materiale","skur","spaer_og_rem");
        List<String> validColumns = List.of("bredde", "laengde","materiale","tag_type","skur");
      
        // Check if the inputs are valid
        if (!validTables.contains(table) || !validColumns.contains(column)) {
            throw new DatabaseException("Invalid table or column name");
        }

        String query = "SELECT " + column + " FROM " + table;
        List<String> options = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                options.add(rs.getString(column));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return options;
    }

    public static void submitFormToDatabase(String bredde, String laengde, String tagMateriale, String skur, String spaerOgRem, User username, ConnectionPool dbConnection) throws DatabaseException {
        String sql = "INSERT INTO orders (bredde, laengde, tag_materiale, skur, spaer_og_rem_materiale, date_placed, status, username) " +
                "VALUES (?, ?, ?, ?, ?, CURRENT_DATE, 'pending', ?)";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, Integer.parseInt(bredde));
            stmt.setInt(2, Integer.parseInt(laengde));
            stmt.setString(3, tagMateriale);
            stmt.setString(4, skur);
            stmt.setString(5, spaerOgRem);
            stmt.setString(6, username.getUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error inserting form data: " + e.getMessage());
        }
    }
}
