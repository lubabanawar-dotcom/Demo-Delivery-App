package delivery;
import java.util.List;

public class DeliveryManager {
	
	public DeliveryRepository repository;
	
	public DeliveryManager(DeliveryRepository repository)
	{
		this.repository=repository;
	}
	
	public void createNewDelivery(String orderId,String id,String address) 
	{
		Delivery d=new Delivery(orderId,id,address);
		repository.save(d);
		System.out.println("Delivery created for address: " + address);
		
	}
	
	public void completeDelivery(String id) 
	{
		Delivery d=repository.findById(id);
		
		if(d!=null)
		{
			d.setstatus("Delivered");
			System.out.println("Order " + id + " has been dropped off");
		}
	}
		public List<Delivery> getAllDeliveries()
		{
	        return repository.findAll();
		
	    }

}
