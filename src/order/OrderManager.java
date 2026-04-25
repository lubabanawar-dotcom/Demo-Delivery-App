package order;

import java.util.ArrayList;

public class OrderManager {

    private OrderRepository repository;

    public OrderManager() {
        this.repository = new OrderRepositoryImp();
    }


    // Create new order
    public Order createOrder(int userId) {
        int newId = repository.getAllOrders().size() + 1;
        Order order = new Order(newId, userId);
        repository.saveOrder(order);
        return order;
    }

    // Add item (CONNECTED WITH MENU)
    public void addItem(int orderId, String itemName, double price) {
        Order order = repository.getOrderById(orderId);

        if (order == null) {
            System.out.println("Order not found!");
            return;
        }

        order.addItem(itemName, price);
        repository.updateOrder(order);
    }

    // Remove item
    public void removeItem(int orderId, String itemName, double price) {
        Order order = repository.getOrderById(orderId);

        if (order == null) {
            System.out.println("Order not found!");
            return;
        }

        order.removeItem(itemName, price);
        repository.updateOrder(order);
    }

    // Update status
    public void updateStatus(int orderId, String status) {
        Order order = repository.getOrderById(orderId);

        if (order == null) {
            System.out.println("Order not found!");
            return;
        }

        order.setStatus(status);
        repository.updateOrder(order);
    }

    // Cancel order
    public void cancelOrder(int orderId) {
        repository.deleteOrder(orderId);
    }

    // View one order
    public Order getOrder(int orderId) {
        return repository.getOrderById(orderId);
    }

    // View all orders
    public ArrayList<Order> getAllOrders() {
        return repository.getAllOrders();
    }
    
    //Added later:
    public ArrayList<Order> getOrdersByUser(int userId) {
        ArrayList<Order> result = new ArrayList<>();

        for (Order o : repository.getAllOrders()) {
            if (o.getUserId() == userId) {
                result.add(o);
            }
        }

        return result;
    }
}