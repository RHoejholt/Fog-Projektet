package app.controller;

import app.entities.Order;
import app.entities.OrderItem;
import app.services.Calculator;

import java.util.ArrayList;

public class OrderController {

    Calculator calculator = new Calculator();

    public static ArrayList<OrderItem> renderItemList(Order order){

        ArrayList<OrderItem> itemList = new ArrayList<OrderItem>();

        // calculate pillar
        // takes length of carport
        itemList.add(Calculator.calcPillarAndBeams(order.getLength()));

        // calculate raft
        itemList.add(Calculator.calcRafters(order.getLength(), order.getWidth()));





        return itemList;
    }



}
