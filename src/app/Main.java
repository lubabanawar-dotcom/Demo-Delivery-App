package app;

import java.util.Arrays;
import order.Order;
import order.OrderManager;

public class Main {
public static void main(String[] args) {


    OrderManager manager = new OrderManager();

    Order order1 = new Order(1, 101, Arrays.asList(1,2), 500.0, "PLACED");

    manager.addOrder(order1);

    for (Order o : manager.getAllOrders()) {
        System.out.println("Order ID: " + o.getId());
    }
}

}
