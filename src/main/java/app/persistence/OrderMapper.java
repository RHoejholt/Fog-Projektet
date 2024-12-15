package app.persistence;

import app.entities.Order;
import app.exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper {


    public static Order getOrderById(int orderId, ConnectionPool dbConnection) throws DatabaseException {
        String sql = "SELECT order_id, bredde, laengde, status FROM orders WHERE order_id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, orderId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int width = rs.getInt("bredde");
                    int length = rs.getInt("laengde");
                    String status = rs.getString("status");

                    return new Order(width, orderId, length, status);
                } else {
                    throw new DatabaseException("No order found with ID: " + orderId);
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error fetching order by ID: " + e.getMessage(), e);
        }
    }
}
