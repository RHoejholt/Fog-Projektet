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
    private static final int MAX_DIST = 3500;
    private static final int PILLARID = 1;  //Vi bruger "pillar" da "post" har flere betydninger
    private static final int RAFTERID = 3; //SPÆR
    private static final int BEAMID = 2; //REMME
    private static final int MAX_PLANK_LENGTH = 6000;
    private static final int RAFTER_SEPARATION_DISTANCE = 550;

    private static ConnectionPool connectionPool;

    private List<OrderItem> orderItems = new ArrayList<>();

    private ProductMapper productMapper;

    public Calculator(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        this.productMapper = new ProductMapper(connectionPool);
    }

    // Beregn stolper og remme. Bægge beregner sker i denne metode da det ellers bliver noget rod med unit tests.
    public static int calcPillar(int carportLength)  {
        int quantity = 2 * (2 + (carportLength*10 - MAX_DIST - OVERHANG) / MAX_DIST);

        int extraPillars = 0;
        if (carportLength*10 > MAX_PLANK_LENGTH) {
            extraPillars += (carportLength - MAX_PLANK_LENGTH) / MAX_PLANK_LENGTH;
        }
        quantity += extraPillars; // Adds extra pillars if needed

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

    public static int calcBeams(int carportLength) {

        int quantity = 2;
        if (carportLength*10 > MAX_PLANK_LENGTH) {
            quantity += (carportLength*10 - MAX_PLANK_LENGTH) / MAX_PLANK_LENGTH;
        }
        quantity += quantity; // Adds extra pillars if needed

        return quantity;
    }

    // Rafters calculation
    public static int calcRafters(int carportWidth) {

        // Calculate the number of rafters based on the separation distance
        int quantityOfRafters = (carportWidth*10 + RAFTER_SEPARATION_DISTANCE - 1) / RAFTER_SEPARATION_DISTANCE; // Rounding up to include the last rafter

        return quantityOfRafters;
    }

}

