package app.services;

import app.entities.Order;
import app.entities.OrderItem;
import app.entities.Product;
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

    private Order order;
    private List<OrderItem> orderItems = new ArrayList<>();

    private ProductMapper productMapper;

    public Calculator(Order order, ConnectionPool connectionPool) {
        this.order = order;
        this.connectionPool = connectionPool;
        this.productMapper = new ProductMapper(connectionPool);
    }

    // Beregn stolper og remme. Bægge beregner sker i denne metode da det ellers bliver noget rod med unit tests.
    public void calcPillarAndBeams() throws DatabaseException {
        int carportLength = order.getLength();
        int quantity = 2 * (2 + (carportLength - MAX_DIST - OVERHANG) / MAX_DIST);

        quantity += calcExtraPillars(); // Adds extra pillars if needed

        ProductVariant productVariantPillar = ProductMapper.getVariantsByProductIdAndMinLength(PILLARID, 6000);

        if (productVariantPillar != null) {

            order.addOrderItem(new OrderItem(PILLARID, order, productVariantPillar, quantity, "Stolper nedgraves 90cm i jord"));
        }
        ProductVariant productVariantBeam = ProductMapper.getVariantsByProductIdAndMinLength(PILLARID, 6000);
        if (productVariantBeam  != null) {


            order.addOrderItem(new OrderItem(BEAMID, order, productVariantBeam, quantity, ""));
            }
        }

    // Rafters calculation
    public void calcRafters() throws DatabaseException {
        int carportWidth = order.getWidth(); // Width of the carport in mm

        // Calculate the number of rafters based on the separation distance
        int quantityOfRafters = (carportWidth + RAFTER_SEPARATION_DISTANCE - 1) / RAFTER_SEPARATION_DISTANCE; // Rounding up to include the last rafter

        // Fetch the appropriate product variant for rafters
        ProductVariant rafterVariant = ProductMapper.getVariantsByProductIdAndMinLength(RAFTERID, 0);

        if (rafterVariant == null) {
            throw new DatabaseException("No suitable rafter variant found for the given minimum length.");
        }

        // Add rafters as an OrderItem to the order
        order.addOrderItem(new OrderItem(RAFTERID, order, rafterVariant, quantityOfRafters, "Spær til tag"));
    }


    //Denne metode er seperat for at kunne unit testes
        private int calcExtraPillars() {
            int carportLength = order.getLength();
            int extraPillars = 0;
            if (carportLength >= MAX_PLANK_LENGTH) {
                extraPillars += (carportLength - MAX_PLANK_LENGTH) / MAX_PLANK_LENGTH;
            }
            return extraPillars;
    }
}

