package app.entities;

public class Order {


    private int length;
    private int width;
    private int id;

    public Order(int width, int id, int length) {
        this.width = width;
        this.id = id;
        this.length = length;
    }


    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }
}
