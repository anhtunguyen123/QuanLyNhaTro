package com.example.quanlynhatro;

public class Room {

    private int id;
    private String name;
    private String price;
    private String quantity;
    private String area;
    private String status;

    public Room(int id, String name, String price, String quantity, String area, String status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.area = area;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getArea() {
        return area;
    }

    public String getStatus() {
        return status;
    }
}
