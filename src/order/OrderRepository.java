package order;
import java.util.ArrayList;

public interface OrderRepository {
    void saveOrder(Order order);
    Order getOrderById(int id);
    ArrayList<Order> getAllOrders();
    void updateOrder(Order order);
    void deleteOrder(int id);
}
