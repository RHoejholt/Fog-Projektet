package app.entities;

import java.util.ArrayList;

public class Order {


    private int length;
    private int width;
    private String status;
    private int orderId;
    private ArrayList<OrderItem> orderItems = new ArrayList<>();

    public Order(int width, int id, int length, String status) {
        this.width = width;
        this.orderId = id;
        this.length = length;
        this.status = status;
    }


    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public boolean addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        return true;
    }

    public int getSize() {
        return orderItems.size();
    }

    public int getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }
}
