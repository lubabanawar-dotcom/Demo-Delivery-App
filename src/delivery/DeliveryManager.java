package delivery;
import java.util.List;
import java.util.Random;

public class DeliveryManager {
	
	private DeliveryRepository repository;

    public DeliveryManager() 
    {
        this.repository = new DeliveryRepositoryImp();
    }

    // Randomly assigns a delivery person ID between 1 and 10
    // Later this will be updated to pick from real delivery persons
    private int assignDeliveryPerson() 
    {
        Random random = new Random();
        return random.nextInt(10) + 1;
    }

    // Create a new delivery
    public void createDelivery(int orderId, int customerId, String address) 
    {
        int newId = repository.findAll().size() + 1;
        Delivery d = new Delivery(newId, orderId, customerId, address);

        int assignedPerson = assignDeliveryPerson();
        d.setDeliveryPersonId(assignedPerson);

        repository.save(d);
        System.out.println("Delivery created successfully! Delivery ID: " + newId);
        System.out.println("Assigned to Delivery Person ID: " + assignedPerson);
    }

    // Update delivery status
    public void updateStatus(int deliveryId, String newStatus) 
    {
        Delivery d = repository.findById(deliveryId);
        if (d == null) 
        {
            System.out.println("Delivery not found!");
            return;
        }
        d.setStatus(newStatus);
        repository.update(d);
        System.out.println("Delivery " + deliveryId + " status updated to: " + newStatus);
    }

    // Mark delivery as on the way
    public void markOnTheWay(int deliveryId) 
    {
        updateStatus(deliveryId, "ON_THE_WAY");
    }

    // Mark delivery as delivered
    public void markDelivered(int deliveryId) 
    {
        updateStatus(deliveryId, "DELIVERED");
    }

    // Mark delivery as failed
    public void markFailed(int deliveryId) 
    {
        updateStatus(deliveryId, "FAILED");
    }

    // View one delivery
    public void viewDelivery(int deliveryId) 
    {
        Delivery d = repository.findById(deliveryId);
        if (d == null) 
        {
            System.out.println("Delivery not found!");
            return;
        }
        d.displayInfo();
    }

    // View all deliveries
    public void viewAllDeliveries() 
    {
        List<Delivery> list = repository.findAll();
        if (list.isEmpty()) 
        {
            System.out.println("No deliveries found!");
            return;
        }
        for (Delivery d : list) 
        {
            d.displayInfo();
            System.out.println("------------------");
        }
    }

    // View all deliveries for one customer
    public void viewCustomerDeliveries(int customerId) 
    {
        List<Delivery> list = repository.findByCustomerId(customerId);
        if (list.isEmpty())
        {
            System.out.println("No deliveries found for this customer!");
            return;
        }
        for (Delivery d : list) 
        {
            d.displayInfo();
            System.out.println("------------------");
        }
    }

    // Cancel a delivery
    public void cancelDelivery(int deliveryId) 
    {
        Delivery d = repository.findById(deliveryId);
        if (d == null) 
        {
            System.out.println("Delivery not found!");
            return;
        }
        repository.delete(deliveryId);
        System.out.println("Delivery " + deliveryId + " has been cancelled.");
    }

    // Find delivery by order ID
    public void viewDeliveryByOrder(int orderId) 
    {
        Delivery d = repository.findByOrderId(orderId);
        if (d == null) 
        {
            System.out.println("No delivery found for Order ID: " + orderId);
            return;
        }
        d.displayInfo();
    }
    //added later:
    public List<Delivery> getAllDeliveries() {
        return repository.findAll();
    }
    public Delivery getDeliveryByOrderId(int orderId) {
        return repository.findByOrderId(orderId);
    }
}