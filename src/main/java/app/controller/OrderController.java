package app.controller;

import app.entities.Order;
import app.entities.OrderItem;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import app.services.Calculator;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class OrderController {


    public static void addRoutes(Javalin app, ConnectionPool dbConnection) {
        app.get("/order", ctx -> showAllItems(ctx, dbConnection));  // Show the form with dynamic options
    }


    private static void showAllItems(Context ctx, ConnectionPool dbConnection) throws Exception {
        int id  = parseInt(ctx.queryParam("id"));
        //TODO: Check the order belongs to the active user
        Order order = OrderMapper.getOrderById(id, dbConnection);
        ctx.attribute("order", order);
        //TODO: Add variants and product to sql
       // calcItemList(order, dbConnection);
        ctx.render("order.html");
    }


    private static void calcItemList(Order order, ConnectionPool connectionPool) throws Exception {
        // Initialize Calculator with the order
        Calculator calculator = new Calculator(connectionPool);

        // Calculate pillar and beams
        calculator.calculateAndAddToOrder(order);
    }
}
