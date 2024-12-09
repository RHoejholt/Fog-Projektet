package app.persistence;

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


    public List<ProductVariant> getVariantsByProductIdAndMinLength(int minLength, int productId, ConnectionPool connectionPool) throws DatabaseException {
    //hent korresponderene ting fra database
        List<ProductVariant> listOfMatches = new ArrayList<>();
       // listOfMatches.add();
    return listOfMatches;
    }

    public static int getVariantsByProductIdAndMinLength(int productId, int minLength) throws DatabaseException {
        String sql = "SELECT * FROM product_variant WHERE product_id=? ORDER BY length ASC";
        try (Connection connection = connectionPool.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int length = rs.getInt("length");
                if (length >= minLength) {
                    return rs.getInt("product_variant_id");
                }

            }
            return 0; //no matching length

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

}
