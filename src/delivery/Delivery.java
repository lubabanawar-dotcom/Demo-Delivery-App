package delivery;
import java.io.Serializable;
import java.time.LocalDateTime;


public class Delivery implements Serializable{
	
	    private int deliveryId;
	    private int orderId;
	    private int customerId;
	    private int deliveryPersonId;
	    private String address;
	    private String status;
	    private LocalDateTime deliveryDateTime;
	
public Delivery(int deliveryId, int orderId, int customerId, String address) {
	    this.deliveryId = deliveryId;
	    this.orderId = orderId;
	    this.customerId = customerId;
	    this.address = address;
	    this.status = "PENDING";
	    this.deliveryPersonId = 0; 
	    this.deliveryDateTime = LocalDateTime.now();
	    }

public int getDeliveryId() 
	    { 
	    	return deliveryId; 
	    	
	    }
	    public int getOrderId() 
	    { 
	    	return orderId; 
	    	
	    }
	    public int getCustomerId() 
	    { 
	    	return customerId; 
	    	
	    }
	    public int getDeliveryPersonId() 
	    { 
	    	return deliveryPersonId; 
	    	
	    }
	    public String getAddress() 
	    { 
	    	return address; 
	    	
	    }
	    public String getStatus() 
	    { 
	    	return status; 
	    	
	    }
	    public LocalDateTime getDeliveryDateTime() 
	    { 
	    	return deliveryDateTime; 
	    	
	    }

	    public void setStatus(String status) 
	    { 
	    	this.status = status; 
	    	
	    }
	    public void setAddress(String address) 
	    { 
	    	this.address = address; 
	    	
	    }
	    public void setDeliveryPersonId(int deliveryPersonId)
	    { 
	    	this.deliveryPersonId = deliveryPersonId; 
	    	
	    }

	    public void displayInfo() 
	    {
	        System.out.println("Delivery ID: " + deliveryId);
	        System.out.println("Order ID: " + orderId);
	        System.out.println("Customer ID: " + customerId);
	        System.out.println("Delivery Person ID: " + deliveryPersonId);
	        System.out.println("Address: " + address);
	        System.out.println("Status: " + status);
	        System.out.println("Date & Time: " + deliveryDateTime);
	    }
	}
