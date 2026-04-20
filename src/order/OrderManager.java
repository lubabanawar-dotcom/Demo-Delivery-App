package order;

import java.util.ArrayList;

public class OrderManager {
	private OrderRepositoryImp repository;
	
	//Constructor
	public OrderManager() {
        this.repository = new OrderRepositoryImp();
	}
	
	//place order
	public void placeOrder(int userId, double totalPrice, String status) {
		int newId = repository.getAllOrders().size()+1;
		
		Order order = new Order(newId, userId, totalPrice, status);
		repository.saveOrder(order);
		 System.out.println("Order placed successfully! Your Order ID is: " + newId);
	}
	
	//cancel order
	public void cancelOrder(int orderId) {
		Order order = repository.getOrderById(orderId);
		
		if (order == null) {
			System.out.println("Order not found!");
	        return;
		}
		repository.deleteOrder(orderId);
		System.out.println("Order " + orderId + " has been cancelled.");
	}
	
	//update order
	public void updateOrder(int orderId, String newStatus) {
		Order order = repository.getOrderById(orderId);
		
		if (order == null) {
			System.out.println("Order not found!");
			return;
		}
		
		order.setStatus(newStatus);
		repository.updateOrder(order);
		System.out.println("Order " + orderId + " status updated to: " + newStatus);
	}
	
	//Add Item
	public void addItemToOrder(int orderId, String itemName) {
		Order order = repository.getOrderById(orderId);
		
		if (order == null) {
			System.out.println("Order not found!");
			return;
		}
		
		order.getItemNames().add(itemName);
		
		repository.updateOrder(order);
		System.out.println(itemName + " added to Order " + orderId);
	}
	
	//remove item
	public void removeItemFromOrder() {
		
	}
	
	//View Order
	public void viewOrder(int orderId) {
		Order order = repository.getOrderById(orderId);
		
		if(order == null) {
			System.out.println("Order not found!");
			return;
		}
		
		order.displayInfo();
	}
	
	///All Orders
	public void viewAllOrders() {
	    ArrayList<Order> orders = repository.getAllOrders();

	    // Add this check!
	    if (orders.isEmpty()) {
	        System.out.println("No orders found!");
	        return;
	    }

	    for (Order order : orders) {
	        order.displayInfo();
	        System.out.println("------------------");
	    }
	}

}

