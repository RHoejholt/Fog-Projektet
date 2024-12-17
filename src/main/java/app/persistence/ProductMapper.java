package app.persistence;

import app.Main;
import app.entities.Product;
import app.entities.ProductVariant;
import app.entities.User;
import app.exception.DatabaseException;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProductMapper {

    private static ConnectionPool connectionPool;

    private static List<ProductVariant> listOfMatche;

    public ProductMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }



    public static ProductVariant getVariantsByProductIdAndMinLength(int productId, int minLength) throws DatabaseException {
        String sql = """
        SELECT pv.product_variant_id, pv.length, 
               p.product_id, p.name AS product_name, p.unit, p.price
        FROM product_variant pv
        JOIN product p ON pv.product_id = p.product_id
        WHERE pv.product_id = ?
        ORDER BY pv.length ASC
    """;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int length = rs.getInt("length");
                if (length >= minLength) {
                    // Fetch Product details
                    int fetchedProductId = rs.getInt("product_id");
                    String productName = rs.getString("product_name");
                    String unit = rs.getString("unit");
                    int price = rs.getInt("price");

                    Product product = new Product(fetchedProductId, productName, unit, price);

                    // Fetch ProductVariant details
                    int productVariantId = rs.getInt("product_variant_id");

                    return new ProductVariant(productVariantId, product, length);
                }
            }
            return null; // No matching ProductVariant found
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }


}
