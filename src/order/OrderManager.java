package order;

import java.util.ArrayList;

public class OrderManager {

    private OrderRepository repository;

    public OrderManager() {
        this.repository = new OrderRepositoryImp();
    }

    // ---- Get Next Order ID  ----
    private int getNextOrderId() {
        int max = 0;

        for (Order o : repository.getAllOrders()) {
            if (o.getOrderId() > max) {
                max = o.getOrderId();
            }
        }
        return max + 1;
    }

    // ---- Create Final Order Only On Checkout ----
    public Order createOrder(int userId, String address, ArrayList<String> itemNames, ArrayList<Double> prices) {
        int newId = getNextOrderId();

        Order order = new Order(newId, userId, address);

        // add items safely and calculate total automatically
        for (int i = 0; i < itemNames.size() && i < prices.size(); i++) {
            order.addItem(itemNames.get(i), prices.get(i));
        }

        order.setStatus("Order Placed");

        repository.saveOrder(order);
        return order;
    }

    // ---- Get One Order ----
    public Order getOrder(int orderId) {
        return repository.getOrderById(orderId);
    }

    // ---- Get All Orders ----
    public ArrayList<Order> getAllOrders() {
        return repository.getAllOrders();
    }

    // ---- Get Orders By User ----
    public ArrayList<Order> getOrdersByUser(int userId) {
        ArrayList<Order> result = new ArrayList<>();

        for (Order o : repository.getAllOrders()) {
            if (o.getUserId() == userId) {
                result.add(o);
            }
        }

        return result;
    }

    // ---- Cancel Order ----
    public void cancelOrder(int orderId) {
        repository.deleteOrder(orderId);
    }

    // ---- Update Status ----
    public void updateStatus(int orderId, String status) {
        Order order = repository.getOrderById(orderId);

        if (order == null) {
            System.out.println("Order not found!");
            return;
        }

        order.setStatus(status);
        repository.updateOrder(order);
    }
}