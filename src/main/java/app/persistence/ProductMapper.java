package app.persistence;

import app.entities.ProductVariant;
import app.exception.DatabaseException;

import java.util.ArrayList;
import java.util.List;


public class ProductMapper {



    private static List<ProductVariant> listOfMatche;


    public List<ProductVariant> getVariantsByProductIdAndMinLength(int minLength, int productId, ConnectionPool connectionPool) throws DatabaseException {
    //hent korresponderene ting fra database
        List<ProductVariant> listOfMatches = new ArrayList<>();
        listOfMatches.add();
    return listOfMatches;
    }
}
