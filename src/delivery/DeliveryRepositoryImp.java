package delivery;
import java.util.ArrayList;
import java.util.List;


public class DeliveryRepositoryImp implements DeliveryRepository {
	
	private List<Delivery> database = new ArrayList<>();

  
    public void save(Delivery d) 
    {
        database.add(d);
    }


    public Delivery findById(int id) 
    {
        for (Delivery d : database) 
        {
            if (d.getDeliveryId() == id) 
            {
                return d;
            }
        }
        return null;
    }

 
    public Delivery findByOrderId(int orderId) 
    {
        for (Delivery d : database) 
        {
            if (d.getOrderId() == orderId) 
            {
                return d;
            }
        }
        return null;
    }


    public List<Delivery> findByCustomerId(int customerId)
    {
        List<Delivery> result = new ArrayList<>();
        for (Delivery d : database) 
        {
            if (d.getCustomerId() == customerId) 
            {
                result.add(d);
            }
        }
        return result;
    }

 
    public List<Delivery> findAll() 
    {
        return new ArrayList<>(database);
    }

    
    public void update(Delivery updated) 
    {
        for (int i = 0; i < database.size(); i++) 
        {
            if (database.get(i).getDeliveryId() == updated.getDeliveryId()) 
            {
                database.set(i, updated);
                return;
            }
        }
    }

   
    public void delete(int id) 
    {
        for (int i = 0; i < database.size(); i++) 
        {
            if (database.get(i).getDeliveryId() == id) 
            {
                database.remove(i);
                return;
            }
        }
    }

}
