package app.services;


import app.entities.Order;
import app.entities.OrderItem;
import app.entities.ProductVariant;
import app.exception.DatabaseException;
import app.persistence.ConnectionPool;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

    private static int overhang = 130; //total længde af ræm der hænger ud fra stolper
    private static int maxDist = 350; //maks længde mellem 2 stopler

    private static int extraPillars;
    private static final int PILLARID = 1;
    private static final int RAFTERID  = 2;
    private static final int BEAMSID  = 3;

    private static List<OrderItem> orderItems = new ArrayList<>();


    // Ønsket længde og bredde for din carport
    private static int width = 500;
    private static int carportLength = 720;

    private static ConnectionPool connectionPool;

    private static int maxPlankLength = 600;
    private static int raftersSeperationDistance = 55;

    public Calculator(int width, int carportLength) {
        this.width = width;
        this.carportLength = carportLength;
    }

    public void calcCarport(Order order) throws DatabaseException
    {
        calcBeams(order);
        calcRafters(order);
    }

    //Stolper. Det engelske ord "Pillar" foretrækkes, da "post" og "pole" har flere betydninger.
    public static OrderItem calcPillarAndBeams(int length)
    {
        int quantity = 2 * (2 + (carportLength-maxDist-overhang) / maxDist);


        extraPillars = 0;
        calcBeams(carportLength);
        quantity += extraPillars;
        ProductVariant productVariant = ProductMapper.getVariantsByProductIdAndMinLength(0, PILLARID, connectionPool);
        OrderItem orderItem = new OrderItem(0, order, productVariant, quantity, "Stopler nedgraves 90cm i jord");
        //orderItems.add(orderItem);

        return orderItem;
    }

    //Spær
    public OrderItem calcRafters(Order order)
    {
        int quantityOfRafters = (order.getLength()-5) / raftersSeperationDistance;
        OrderItem orderItem = new OrderItem(order, )
        return
    }



    //Remme
    public static int calcBeams(int carportLength) {
        int quantityOfBeams = 2;

        if (carportLength >= maxPlankLength) {
            quantityOfBeams = 2 + (carportLength - maxPlankLength) / maxPlankLength;
            calcExtraPillars(carportLength);
        }
        return quantityOfBeams;
    }

    public int calcExtraPillars (int carportLength) {
        if (carportLength <= maxPlankLength + 100) {
            extraPillars = 2;
        }
        return extraPillars;
    }





}
