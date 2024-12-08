package app.controller;

import app.entities.Order;
import app.entities.OrderItem;
import app.services.Calculator;

import java.util.ArrayList;

public class OrderController {

    public ArrayList<OrderItem> renderItemList(Order order) throws Exception {
        // Initialize Calculator with the order
        Calculator calculator = new Calculator(order);

        ArrayList<OrderItem> itemList = new ArrayList<>();

        // Calculate pillar and beams
        itemList.add(calculator.calcPillarAndBeams());

        // Calculate rafters
        itemList.add(calculator.calcRafters());

        return itemList;
    }
}
