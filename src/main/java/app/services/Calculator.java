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
    private static final int RAFTERID = 3;
    private static final int BEAMID = 2;
    private static final int MAX_PLANK_LENGTH = 6000;
    private static final int RAFTER_SEPARATION_DISTANCE = 550;

    private static ConnectionPool connectionPool;

    private Order order;
    private List<OrderItem> orderItems = new ArrayList<>();


    public Calculator(Order order) {
        this.order = order;
    }

    // Beregn stolper og remme. Bægge beregner sker i denne metode da det ellers bliver noget rod med unit tests.
    public void calcPillarAndBeams() throws DatabaseException {
        int carportLength = order.getLength();
        int quantity = 2 * (2 + (carportLength - MAX_DIST - OVERHANG) / MAX_DIST);

        quantity += calcExtraPillars(); // Adds extra pillars if needed

        ProductVariant productVariant1 = new ProductVariant();
        ProductVariant productVariant2 = new ProductVariant();

        //ProductVariant productVariant = ProductMapper.getVariantsByProductIdAndMinLength(0, PILLARID, connectionPool);

        order.addOrderItem(OrderItem(PILLARID, order, productVariant1, quantity, "Stolper nedgraves 90cm i jord"));
        order.addOrderItem(OrderItem(BEAMID, order, productVariant2, quantity, ""));

    }

    //Spær
    public void calcRafters() throws DatabaseException {
        int carportWidth = order.getWidth();
        int quantityOfRafters = (carportWidth - 5) / RAFTER_SEPARATION_DISTANCE;



     //   ProductVariant productVariant = ProductMapper.getVariantsByProductIdAndMinLength(0, RAFTERID, connectionPool);

        order.addOrderItem(OrderItem(order, productVariant, quantityOfRafters, "Spær til tag"));
    }

    //Denne metode er seperat for at kunne unit testes
    private int calcExtraPillars() {
        int extraPillars = 0;
        int carportLength = order.getLength();
        if (carportLength >= MAX_PLANK_LENGTH) {
            extraPillars += (carportLength - MAX_PLANK_LENGTH) / MAX_PLANK_LENGTH;
        }
        return extraPillars;
    }
}
