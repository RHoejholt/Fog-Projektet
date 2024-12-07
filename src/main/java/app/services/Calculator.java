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

    private static final int OVERHANG = 130;
    private static final int MAX_DIST = 350;
    private static final int PILLARID = 1;  //Vi bruger "pillar" da "post" har flere betydninger
    private static final int RAFTERID = 2;
    private static final int MAX_PLANK_LENGTH = 600;
    private static final int RAFTER_SEPARATION_DISTANCE = 55;

    private static ConnectionPool connectionPool;

    private Order order;
    private List<OrderItem> orderItems = new ArrayList<>();
    private int extraPillars;

    public Calculator(Order order) {
        this.order = order;
    }

    // Beregn stolper og remme. Bægge beregner sker i denne metode da det ellers bliver noget rod med unit tests.
    public OrderItem calcPillarAndBeams() throws DatabaseException {
        int carportLength = order.getLength();
        int quantity = 2 * (2 + (carportLength - MAX_DIST - OVERHANG) / MAX_DIST);

        extraPillars = 0;
        calcBeams(); // Adds extra pillars if needed
        quantity += extraPillars;

        ProductVariant productVariant = ProductMapper.getVariantsByProductIdAndMinLength(0, PILLARID, connectionPool);
        return new OrderItem(order, productVariant, quantity, "Stolper nedgraves 90cm i jord");
    }

    //Spær
    public OrderItem calcRafters() throws DatabaseException {
        int carportWidth = order.getWidth();
        int quantityOfRafters = (carportWidth - 5) / RAFTER_SEPARATION_DISTANCE;

        ProductVariant productVariant = ProductMapper.getVariantsByProductIdAndMinLength(0, RAFTERID, connectionPool);
        return new OrderItem(order, productVariant, quantityOfRafters, "Spær til tag");
    }

    //Denne metode er seperat for at kunne unit testes
    private void calcBeams() {
        int carportLength = order.getLength();
        if (carportLength >= MAX_PLANK_LENGTH) {
            extraPillars += (carportLength - MAX_PLANK_LENGTH) / MAX_PLANK_LENGTH;
        }
    }
}
