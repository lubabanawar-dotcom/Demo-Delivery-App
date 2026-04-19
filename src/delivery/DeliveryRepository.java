package delivery;
import java.util.List;

public interface DeliveryRepository {
	
	void save(Delivery delivery);
    Delivery findById(String id);
    List<Delivery> findAll();
    void update(Delivery delivery);

}
