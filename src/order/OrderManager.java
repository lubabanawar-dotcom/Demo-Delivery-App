package order;

import java.util.ArrayList;
import java.util.List;

public class OrderManager {

private List<Order> orders = new ArrayList<>();

public void addOrder(Order order) {
    orders.add(order);
}

public List<Order> getAllOrders() {
    return orders;
}

}
