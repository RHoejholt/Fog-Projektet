package app.controller;

import app.entities.OrderItem;
import app.services.Calculator;

import java.util.ArrayList;

public class OrderController {

    Calculator calculator = new Calculator();

    public static ArrayList<OrderItem> renderItemList(Order order){

        ArrayList<OrderItem> itemList = new ArrayList<OrderItem>();


        // calculate pillar
        // takes length of carport
        itemList.add.(Calculator.calcPillar(order.getLength()));

        // calculate raft
        itemList.add.(Calculator.calcRafters(order.getLength(), order.getWidth()));


        // calculate beams
        itemList.add.(Calculator.calcBeams(order.getLength(), order.getWidth()));


        return itemList;
    }



}
