package order;

import java.io.*;
import java.util.ArrayList;

public class OrderRepositoryImp {
private String filename = "orders.dat";
	
	private ArrayList<Order> loadOrders(){
		return null;
		// 1. Check if file exists
        // 2. If yes, open it and read the ArrayList
        // 3. If no, return empty ArrayList
        // 4. Use try-catch for safety
	}
	private void saveAllOrders(ArrayList<Order> orders) {
		// 1. Open the file for writing
        // 2. Write the whole ArrayList
        // 3. Close the file
        // 4. Use try-catch for safety
	}
	public void saveOrder(Order order) {
		// 1. Load existing orders
        // 2. Add the new order to the list
        // 3. Save everything back
	}
	public Order getOrderById(int id) {
		return null;
        // 1. Load all orders
        // 2. Loop through and find matching ID
        // 3. Return it, or null if not found
    }
	public ArrayList<Order> getAllOrders(){
		return null;
		 // Just load and return everything
	}
	// Update an existing order
    public void updateOrder(Order order) {
        // 1. Load all orders
        // 2. Loop through and find matching ID
        // 3. Replace it with the updated one
        // 4. Save everything back
    }

    // Delete an order by ID
    public void deleteOrder(int id) {
        // 1. Load all orders
        // 2. Loop through and remove matching ID
        // 3. Save everything back
    }
}
