package order;

import java.io.*;
import java.util.ArrayList;

public class OrderRepositoryImp implements OrderRepository {

    // File
    private String filename = "orders.dat";

    // ---- Load Orders ----
    private ArrayList<Order> loadOrders() {
        File file = new File(filename);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            ArrayList<Order> orders = (ArrayList<Order>) ois.readObject();
            ois.close();
            return orders;
        } catch (Exception e) {
            System.out.println("Error loading orders: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // ---- Save All Orders ----
    private void saveAllOrders(ArrayList<Order> orders) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(orders);
            oos.close();
        } catch (Exception e) {
            System.out.println("Error saving orders: " + e.getMessage());
        }
    }

    // ---- Save One Order ----
    @Override
    public void saveOrder(Order order) {
        ArrayList<Order> orders = loadOrders();
        orders.add(order);
        saveAllOrders(orders);
    }

    // ---- Get Order By ID ----
    @Override
    public Order getOrderById(int id) {
        for (Order order : loadOrders()) {
            if (order.getOrderId() == id) {
                return order;
            }
        }
        return null;
    }

    // ---- Get All Orders ----
    @Override
    public ArrayList<Order> getAllOrders() {
        return loadOrders();
    }

    // ---- Update Order ----
    @Override
    public void updateOrder(Order updatedOrder) {
        ArrayList<Order> orders = loadOrders();

        for (Order o : orders) {
            if (o.getOrderId() == updatedOrder.getOrderId()) {
                o.setStatus(updatedOrder.getStatus());
                break;
            }
        }

        saveAllOrders(orders);
    }

 // ---- Delete Order ----
    @Override
    public void deleteOrder(int id) {
        ArrayList<Order> orders = loadOrders();

        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getOrderId() == id) {
                orders.remove(i);
                break;
            }
        }

        saveAllOrders(orders);
    }
}