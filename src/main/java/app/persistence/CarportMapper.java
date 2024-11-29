package app.persistence;

import app.exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarportMapper {
    public static List<String> getDimensionOptions(String column, String table) throws DatabaseException {
        // Define valid table and column names
        // This is a pretty limiting way of doing it. It's just a whitelist.
        // We should probably make a new method in the future that gives the admins an option to add/remove/create tables
        List<String> validTables = List.of("dimensioner_bredde", "dimensioner_længde","tag_materiale");
        List<String> validColumns = List.of("bredde", "længde","materiale","tag_type");

        // Check if the inputs are valid
        if (!validTables.contains(table) || !validColumns.contains(column)) {
            throw new DatabaseException("Invalid table or column name");
        }

        String query = "SELECT " + column + " FROM " + table;
        List<String> options = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
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
}
