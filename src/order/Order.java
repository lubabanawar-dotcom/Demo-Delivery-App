package order;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Order implements Serializable {

    private int orderId;
    private int userId;
    private String address;
    private ArrayList<String> itemNames;
    private double totalPrice;
    private String status;
    private LocalDate orderDate;

    // Constructor
    public Order(int orderId, int userId, String address) {
        this.orderId = orderId;
        this.userId = userId;
        this.address = address;
        this.itemNames = new ArrayList<>();
        this.totalPrice = 0;
        this.status = "Cart";
        this.orderDate = LocalDate.now();
    }

    // ---- Getters ----
    public int getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }

    public String getAddress() {
        return address;
    }

    public ArrayList<String> getItemNames() {
        return itemNames;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    // ---- Setters ----
    public void setStatus(String status) {
        this.status = status;
    }

    // ---- Add Item ----
    public void addItem(String itemName, double price) {
        itemNames.add(itemName);
        totalPrice += price;
    }

    // ---- Remove Item ----
    public void removeItem(String itemName, double price) {
        if (itemNames.remove(itemName)) {
            totalPrice -= price;
        }
    }

    // ---- Display ----
    public void displayInfo() {
        System.out.println("Order ID    : " + orderId);
        System.out.println("User ID     : " + userId);
        System.out.println("Address     : " + address);
        System.out.println("Items       : " + itemNames);
        System.out.println("Total Price : " + totalPrice);
        System.out.println("Status      : " + status);
        System.out.println("Order Date  : " + orderDate);
    }
}