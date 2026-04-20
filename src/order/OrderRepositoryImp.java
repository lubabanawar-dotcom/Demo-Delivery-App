package order;


import java.io.*;
import java.util.ArrayList;

public class OrderRepositoryImp implements OrderRepository{
	private String filename = "orders.dat";
	
	//load
	private ArrayList<Order> loadOrders(){
		File file = new File (filename);

		
		//File does not exist
		if (!file.exists()) {
			return new ArrayList<>();
		}
		
		//File exists
		try{
			FileInputStream fileIn = new FileInputStream(filename);
			ObjectInputStream reader = new ObjectInputStream(fileIn);
			
			ArrayList<Order> orders = (ArrayList<Order>)reader.readObject(); 
			reader.close();
			return orders;
		}
		catch(Exception e) {
			System.out.println("Error loading orders : "+e.getMessage()); 
			return new ArrayList<>();
		}
	}
	
	//Save All Orders
	private void saveAllOrders(ArrayList<Order> orders) {
		try {
			FileOutputStream fileOut = new FileOutputStream(filename);
			ObjectOutputStream writer = new ObjectOutputStream(fileOut);
			
			writer.writeObject(orders);
			writer.close();
		}
		catch(Exception e) {
			System.out.println("Error saving orders : "+e.getMessage());
		}		
	}
	
	//Return All orders
	
	@Override
	public ArrayList<Order> getAllOrders() {
		return loadOrders();
	}
	
	//Save one new order
	
	@Override
	 public void saveOrder(Order newOrder) {
		ArrayList<Order> orders = loadOrders();
		orders.add(newOrder);
		saveAllOrders(orders);
	}
	
	
	// Find and return ONE order by its ID 
	
	@Override
	public Order getOrderById(int id) {	
		ArrayList<Order> orders = loadOrders();
		
		for(int i = 0; i < orders.size(); i++) {
			Order current = orders.get(i);	
			if (current.getOrderId() == id) {
				return current;
			}
		}
		return null;
	}
	
	
	// Replace an existing order with an updated one
	
	@Override
	public void updateOrder(Order updatedOrder) {
		ArrayList<Order> orders = loadOrders();
		
		for(int i = 0; i<orders.size(); i++) {
			Order current = orders.get(i);
			if(current.getOrderId() == updatedOrder.getOrderId()) {
				orders.set(i, updatedOrder); 
				break;
			}
		}	
		saveAllOrders(orders);		
	}
	
	
	// Delete an order by its ID
	
	@Override
	public  void deleteOrder(int orderId) {
		ArrayList<Order> orders = loadOrders();
		
		for(int i = 0; i < orders.size(); i++) {
			Order current = orders.get(i);
			if (current.getOrderId() == orderId) {
				orders.remove(i);
				break;
			}
		}
		saveAllOrders(orders);
	}
}