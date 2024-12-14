package app.services;

import app.entities.Order;
import app.entities.OrderItem;
import app.entities.ProductVariant;
import app.exception.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.ProductMapper;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    //Alle mål i mm.
    private static final int OVERHANG = 1300;
    private static final int maxDistanceBetweenPillars = 3500;
    private static final int PILLARID = 1;  //Vi bruger "pillar" da "post" har flere betydninger
    private static final int RAFTERID = 3; //SPÆR
    private static final int BEAMID = 2; //REMME
    private static final int MAX_PLANK_LENGTH = 6000;
    private static final int maxDistanceBetweenRafters = 505; //55cm, minus the width of the rafter itself

    private static ConnectionPool connectionPool;

    private List<OrderItem> orderItems = new ArrayList<>();

    private ProductMapper productMapper;

    public Calculator(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        this.productMapper = new ProductMapper(connectionPool);
    }

    // Beregn stolper og remme. Bægge beregner sker i denne metode da det ellers bliver noget rod med unit tests.
    public static int calcPillar(int carportLength)  {
        // The whole quantity is first multiplied by 2, since there's two symmetrical sides of the carport.
        // In the paranthesis, the amount needed for one side is calculated. We need at least 2 in each side for the carport to have at least 4 total.
        // Then we multiply the ordered carport's length by 10 to convert cm to mm
        // Then we subtract the overhang since 1,3m of the carport's length will always be overhang that we do not need to account for.
        // Then we divide by the max seperation distance, and since the result is put into an integer, it will automatically be rounded down
        // So if the unsupported distance is greater than the maximum allowed, it will round down to at least 1, and be added to quantity
        int quantity = 2 * (2 + (carportLength*10 - OVERHANG) / maxDistanceBetweenPillars);

        // Here we check if any beam seams are unsupported, and add a pillar to each side for each unsupported beam seam.
        if (carportLength*10 > (MAX_PLANK_LENGTH+1250)) {
            quantity += carportLength / MAX_PLANK_LENGTH;
        }

        return quantity;
    }

    public void calculateAndAddToOrder(Order order) throws DatabaseException {
        ProductVariant productVariantPillar = ProductMapper.getVariantsByProductIdAndMinLength(PILLARID, 6000);

        if (productVariantPillar != null) {

            order.addOrderItem(new OrderItem(PILLARID, order, productVariantPillar, calcPillar(order.getLength()), "Stolper nedgraves 90cm i jord"));
        }

        ProductVariant productVariantBeam = ProductMapper.getVariantsByProductIdAndMinLength(PILLARID, 6000);
        if (productVariantBeam != null) {


            order.addOrderItem(new OrderItem(BEAMID, order, productVariantBeam, calcBeams(order.getLength()), ""));
        }

        // Fetch the appropriate product variant for rafters
        ProductVariant rafterVariant = ProductMapper.getVariantsByProductIdAndMinLength(RAFTERID, 0);

        if (rafterVariant == null) {
            throw new DatabaseException("No suitable rafter variant found for the given minimum length.");
        }

        // Add rafters as an OrderItem to the order
        order.addOrderItem(new OrderItem(RAFTERID, order, rafterVariant, calcRafters(order.getWidth()), "Spær til tag"));

    }

    // Beams calculation (Remme)
    //This does not need a "seperation distance", since the beams do not lay in parallel, but in extension of each other
    public static int calcBeams(int carportLength) {

        int quantity = 2;
        if (carportLength*10 > MAX_PLANK_LENGTH) {
            quantity += (carportLength*10 - MAX_PLANK_LENGTH) / MAX_PLANK_LENGTH;
        }

        return quantity;
    }

    // Rafters calculation (Spær)
    public static int calcRafters(int carportWidth) {

        // Calculate the number of rafters based on the separation distance
        int quantityOfRafters = (carportWidth*10 + maxDistanceBetweenRafters) / maxDistanceBetweenRafters; // Rounding up to include the last rafter

        return quantityOfRafters;
    }

}

