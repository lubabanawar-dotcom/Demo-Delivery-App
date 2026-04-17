package order;

import java.util.ArrayList;
import java.time.LocalDate;
import java.io.Serializable;

public class Order implements Serializable {
	private int orderId;
	private int userId;
	private ArrayList<String> itemNames;
	
	private double totalPrice;
	private String status;
	private LocalDate orderDate;
	
	//Constructor
	public Order(int orderId, int userId, double totalPrice, String status){
		this.orderId = orderId; 
		this.userId = userId;
		this.itemNames = new ArrayList<>();
		this.totalPrice = totalPrice;
		this.status = status;
		this.orderDate = LocalDate.now();
	}
	
	// Getters
	public int getOrderId() {
	    return orderId;
	}
	public int getUserId() {
		return userId;
	}
	public ArrayList<String> getItemNames(){
		return itemNames;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public String getStatus() {
		return status;
	}
	public LocalDate getOrderDate() {
		return orderDate;
	}
	

	// Setters
	public void setUserId(int userId) {
	    this.userId = userId;
	}
	public void setItemNames(ArrayList<String> itemNames) {
		this.itemNames = itemNames;
	}
	public void setTotalPrice(double totalPrice) {
	    this.totalPrice = totalPrice;
	}
	public void setStatus(String status) {
	    this.status = status;
	}

	
	// Display
	public void displayInfo() {
		System.out.println("Order ID : "+orderId);
		System.out.println("User ID : "+userId);
		System.out.println("Items : "+itemNames);
		System.out.println("Total Price : "+totalPrice);
		System.out.println("Status : "+status);
		System.out.println("Order Date : "+orderDate);
	}
}
