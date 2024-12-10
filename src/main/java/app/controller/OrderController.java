package app.controller;

import app.entities.Order;
import app.entities.OrderItem;
import app.persistence.ConnectionPool;
import app.services.Calculator;

import java.util.ArrayList;

public class OrderController {
    private ConnectionPool connectionPool;

    public ArrayList<OrderItem> renderItemList(Order order) throws Exception {
        // Initialize Calculator with the order
        Calculator calculator = new Calculator(order, connectionPool);

        ArrayList<OrderItem> itemList = new ArrayList<>();

        // Calculate pillar and beams
        calculator.calcPillarAndBeams();

        // Calculate rafters
        calculator.calcRafters();

        return itemList;
    }
}
