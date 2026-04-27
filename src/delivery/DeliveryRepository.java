package delivery;

import java.util.List;

public interface DeliveryRepository {
    void save(Delivery delivery);
    Delivery findById(int id);
    Delivery findByOrderId(int orderId);
    List<Delivery> findByCustomerId(int customerId);
    List<Delivery> findAll();
    void update(Delivery delivery);
    void delete(int id);
}