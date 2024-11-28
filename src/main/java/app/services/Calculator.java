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

    private static final int PILLARID = 1;
    private static final int RAFTERID  = 2;
    private static final int BEAMSID  = 3;

    private static List<OrderItem> orderItems = new ArrayList<>();
    private static int width = 500;
    private static int length = 720;
    private ConnectionPool connectionPool;

    public Calculator(int width, int length) {
        this.width = width;
        this.length = length;
    }

    public void calcCarport(Order order) throws DatabaseException
    {
        calcPillar(order);
        calcBeams(order);
        calcRafters(order);
    }

    //Stolper. Det engelske ord "Pillar" foretrækkes, da "post" og "pole" har flere betydninger.
    public static void calcPillar(Order order)
    {
        int quantity = 2 * (2 + (length-maxDist-overhang) / maxDist);

        //ProductVariant productVariant = ProductMapper.getVariantsByProductIdAndMinLength(0, PILLARID, connectionPool);
        //OrderItem orderItem = new OrderItem(0, order, productVariant, quantity, "Stopler nedgraves 90cm i jord");
        //orderItems.add(orderItem);
    }

    //Spær
    public void calcRafters(Order order)
    {
        int quantity = (length-5) / 55;
    }



    //Remme
    public void calcBeams(Order order)
    {
        int quantity;
        if(length<=600) {
            quantity = 2;
        } else {
            quantity = 2 + (length-600) / 600;
        }
        int extraPillars;
        if (quantity>2 && length != 700 && length != 630){
            extraPillars = 2;
        }

    }







}
