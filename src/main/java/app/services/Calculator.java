package app.services;


import app.entities.Order;
import app.entities.OrderItem;
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
    private static int length = 720;

    private ConnectionPool connectionPool;

    private static int maxPlankLength = 600;
    private static int raftersSeperationDistance = 55;

    public Calculator(int width, int length) {
        this.width = width;
        this.length = length;
    }

    public void calcCarport(Order order) throws DatabaseException
    {
        calcBeams(order);
        calcRafters(order);
        calcPillar(order);
    }

    //Stolper. Det engelske ord "Pillar" foretrækkes, da "post" og "pole" har flere betydninger.
    public static void calcPillar(Order order)
    {
        int quantity = 2 * (2 + (order.getLength()-maxDist-overhang) / maxDist);
        quantity =+ extraPillars;
        //ProductVariant productVariant = ProductMapper.getVariantsByProductIdAndMinLength(0, PILLARID, connectionPool);
        //OrderItem orderItem = new OrderItem(0, order, productVariant, quantity, "Stopler nedgraves 90cm i jord");
        //orderItems.add(orderItem);
    }

    //Spær
    public void calcRafters(Order order)
    {
        int quantity = (order.getLength()-5) / raftersSeperationDistance;
    }



    //Remme
    public void calcBeams(Order order)
    {
        int quantity;
        if(order.getLength()<=maxPlankLength) {
            quantity = 2;
        } else {
            quantity = 2 + (order.getLength()-maxPlankLength) / maxPlankLength;
        }

        if (quantity>2 && order.getLength() != maxPlankLength + 100 && length != maxPlankLength + 30){
            extraPillars = 2;
        }else{
            extraPillars = 0;
        }

    }







}
