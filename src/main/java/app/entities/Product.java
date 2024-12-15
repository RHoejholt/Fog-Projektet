package app.entities;

public class Product {

    private int ProductId;
    private String ProductName;
    private String unit;
    private int price = 1;

    public Product(int productId, String productName, String unit, int price) {
        ProductId = productId;
        ProductName = productName;
        this.unit = unit;
        this.price = price;
    }

    public int getProductId() {
        return ProductId;
    }

    public String getProductName() {
        return ProductName;
    }

    public String getUnit() {
        return unit;
    }

    public int getPrice() {
        return price;
    }


}
