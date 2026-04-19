package delivery;
import java.util.ArrayList;
import java.util.List;


public class DeliveryRepositoryImp {
	
	private List<Delivery> database = new ArrayList<>();

    
    public void save(Delivery d) {
        database.add(d);
    }

    public Delivery findById(String id) 
    {
        return database.stream()
            .filter(d -> d.getid().equals(id))
            .findFirst()
            .orElse(null);
    }

    public List<Delivery> findAll() {
        return new ArrayList<>(database);
    }

    public void update(Delivery d) {

    }

}
