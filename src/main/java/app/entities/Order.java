package app.entities;

public class Order {


    private int length;
    private int width;
    private int status;
    private int id;

    public Order(int width, int id, int length, int status) {
        this.width = width;
        this.id = id;
        this.length = length;
        this.status = status;
    }


    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }
}
