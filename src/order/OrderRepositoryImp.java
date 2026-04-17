package order;

import java.io.*;
import java.util.ArrayList;

public class OrderRepositoryImp implements OrderRepository {
private String filename = "orders.dat";
	
	private ArrayList<Order> loadOrders(){
		File file = new File (filename);
		if (!file.exists()) {                   // if file doesn't exist yet
	        return new ArrayList<>();           // return empty list (first time running)
	    }
		try {
	        FileInputStream fis = new FileInputStream(filename);   // opens the file
	        ObjectInputStream ois = new ObjectInputStream(fis);    // prepares to read objects
	        ArrayList<Order> orders = (ArrayList<Order>) ois.readObject(); // reads the list
	        ois.close();                         // always close when done
	        return orders;                       // give back the loaded list
	    } catch (Exception e) {
	        System.out.println("Error loading orders: " + e.getMessage());
	        return new ArrayList<>();            // if something goes wrong, return empty list
	    }
	}
	private void saveAllOrders(ArrayList<Order> orders) {
		try {
	        FileOutputStream fos = new FileOutputStream(filename);   // opens/creates the file
	        ObjectOutputStream oos = new ObjectOutputStream(fos);    // prepares to write objects
	        oos.writeObject(orders);             // writes the whole ArrayList into the file
	        oos.close();                         // always close when done
	    } catch (Exception e) {
	        System.out.println("Error saving orders: " + e.getMessage());
	    }
	}
	public void saveOrder(Order order) {
		ArrayList<Order> orders = loadOrders();  // step 1 - load existing orders
	    orders.add(order);                       // step 2 - add the new one
	    saveAllOrders(orders);                   // step 3 - save everything back
	}
	public Order getOrderById(int id) {
        ArrayList<Order> orders = loadOrders();
        for (Order order: orders) {
        	if(order.getOrderId() == id)
        		return order;
        }
		return null;
    }
	public ArrayList<Order> getAllOrders(){
		return loadOrders();
	}
	
	// Update an existing order
    public void updateOrder(Order order) {
    	ArrayList<Order> orders = loadOrders();          // load all orders
        for (int i = 0; i < orders.size(); i++) {        // loop through
            if (orders.get(i).getOrderId() == order.getOrderId()) {  // find matching ID
                orders.set(i, order);                    // replace with updated order
                break;                                   // stop looping
            }
        }
        saveAllOrders(orders); 
    }

    // Delete an order by ID
    public void deleteOrder(int id) {
    	ArrayList<Order> orders = loadOrders();          // load all orders
        for (int i = 0; i < orders.size(); i++) {        // loop through
            if (orders.get(i).getOrderId() == id) {      // find matching ID
                orders.remove(i);                        // remove it
                break;                                   // stop looping
            }
        }
        saveAllOrders(orders);       
    }
}