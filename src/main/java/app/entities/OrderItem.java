package app.entities;

public class OrderItem {

    private int orderItemId;
    private Order order;
    private ProductVariant productVariant;
    private int quantity;
    private String description;

    public OrderItem(int orderItemId, Order order, ProductVariant productVariant, int quantity, String description) {
        this.orderItemId = orderItemId;
        this.order = order;
        this.productVariant = productVariant;
        this.quantity = quantity;
        this.description = description;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public Order getOrder() {
        return order;
    }

    public ProductVariant getProductVariant() {
        return productVariant;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public String getProductName() {
        return productVariant.getProduct().getProductName();
    }

    public int getProductLength() {
        return productVariant.getLength();
    }



    @Override
    public String toString() {
        return "OrderItem {" +
                "\n orderItemId=" + orderItemId +
                "\n, order=" + (order != null ? order.getOrderId() : "null") +
                "\n, productVariant=" + (productVariant != null ? productVariant.getProductVariantId() : "null") +
                "\n, quantity=" + quantity +
                "\n, description='" + description + '\'' +
                '}';
    }

}
