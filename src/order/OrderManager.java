package order;

import java.util.Scanner;
import java.util.ArrayList;

public class OrderManager {
	private OrderRepositoryImp repository; 
	
	private Scanner sc; 
	
	public OrderManager() {
		repository = new OrderRepositoryImp();
		sc = new Scanner(System.in);
	}
	
	
	// Place a new order (Scanner-based)
	public void placeOrder() {
		System.out.print("Enter your User ID    : ");
        int userId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter total price     : ");
        double totalPrice = sc.nextDouble();
        sc.nextLine();
        
		int newId = repository.getAllOrders().size()+1;
		
		Order newOrder = new Order(newId, userId, totalPrice, "Pending");
		repository.saveOrder(newOrder);
		
		System.out.println("Order placed successfully! Your Order ID is: " + newId);
		 
	}
	
	// Cancel Order
	public void cancelOrder() {
		System.out.print("Enter Order ID: ");
        int orderId = sc.nextInt();
        sc.nextLine();
        
		Order currentOrder = repository.getOrderById(orderId);
		
		if (currentOrder == null) {
			System.out.println("Order not found ...");
			return;
		}
		
		repository.deleteOrder(orderId);
		System.out.println("Order " + orderId + " has been cancelled.");
	}
	
	
	
	// Update Order Status (Scanner-based)
	public void updateOrderStatus() {
		System.out.print("Enter Order ID: ");
        int orderId = sc.nextInt();
        sc.nextLine();
        
		Order currentOrder = repository.getOrderById(orderId);
		
		if (currentOrder == null) {
			System.out.println("Order not found ...");
			return;
		}
		
		System.out.println("Current status: " + currentOrder.getStatus());
		
        System.out.print("Enter new status (Pending / Confirmed / Delivered): ");
        String newStatus = sc.nextLine();

        
        currentOrder.setStatus(newStatus);
        
        repository.updateOrder(currentOrder);
        System.out.println("Status updated to: " + newStatus);
	}
	
	
	// Add Items
	public void addItemToOrder() {
		System.out.print("Enter Order ID: ");
        int orderId = sc.nextInt();
        sc.nextLine();
        
		Order currentOrder = repository.getOrderById(orderId);
		
		if (currentOrder == null) {
			System.out.println("Order not found ...");
			return;
		}
		
		System.out.print("Enter item name to add: ");
		String itemName = sc.nextLine();

		currentOrder.getItemNames().add(itemName);
		
		repository.updateOrder(currentOrder);
		System.out.println(itemName + " added to Order " + orderId);
	}
	
	
	// Remove Items 
	public void removeItemFromOrder() {
		System.out.print("Enter Order ID: ");
        int orderId = sc.nextInt();
        sc.nextLine();
        
		Order currentOrder = repository.getOrderById(orderId);
		
		if (currentOrder == null) {
			System.out.println("Order not found ...");
			return;
		}
		
		
		
		System.out.println("Current items: " + currentOrder.getItemNames());
        System.out.print("Enter item name to remove: ");
        String itemName = sc.nextLine();
        
		if (!currentOrder.getItemNames().contains(itemName)) {
		    System.out.println("Item not found in this order!");
		    return;
		}
		currentOrder.getItemNames().remove(itemName);
		
		repository.updateOrder(currentOrder);
		System.out.println(itemName + " removed from Order " + orderId);
	}
	
	
	// View one specific order
	public void viewOrder() {
		
		System.out.print("Enter Order ID to view: ");
        int orderId = sc.nextInt();
        sc.nextLine();
        
		Order currentOrder = repository.getOrderById(orderId);
		
		if (currentOrder == null) {
			System.out.println("Order not found ...");
			return;
		}
		currentOrder.displayInfo();
	}
	
	// View all orders
	public void viewAllOrders() {
        ArrayList<Order> orders = repository.getAllOrders();

        if (orders.isEmpty()) {
            System.out.println("No orders found!");
            return;
        }
        
        System.out.println("\n===== All Orders =====");
        
        for(int i = 0; i < orders.size(); i++) {
        	orders.get(i).displayInfo();
            System.out.println("----------------------");
        }
	}
	 
	public void showMenu() {
		 while(true) {
			System.out.println("\n=== Order Management ===");
            System.out.println("1. Place Order");
            System.out.println("2. Cancel Order");
            System.out.println("3. Update Order Status");
            System.out.println("4. Add Item to Order");
            System.out.println("5. Remove Item from Order");
            System.out.println("6. View One Order");
            System.out.println("7. View All Orders");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            
            int choice = sc.nextInt();
            sc.nextLine();
                  
            if      (choice == 1) placeOrder();
            else if (choice == 2) cancelOrder();
            else if (choice == 3) updateOrderStatus();
            else if (choice == 4) addItemToOrder();
            else if (choice == 5) removeItemFromOrder();
            else if (choice == 6) viewOrder();
            else if (choice == 7) viewAllOrders();
            else if (choice == 0) break;
            else System.out.println("Invalid choice!");
            
		 } 
	}

    // ---- Non-Scanner API methods for UI ----

    /** Place an order directly (no Scanner). */
    public void placeOrder(int userId, double totalPrice, ArrayList<String> items) {
        int newId = repository.getAllOrders().size() + 1;
        Order newOrder = new Order(newId, userId, totalPrice, "Pending");
        newOrder.setItemNames(items);
        repository.saveOrder(newOrder);
        System.out.println("Order placed! ID: " + newId);
    }

    /** Update order status directly (no Scanner). */
    public void updateOrderStatus(int orderId, String newStatus) {
        Order currentOrder = repository.getOrderById(orderId);
        if (currentOrder == null) {
            System.out.println("Order not found ...");
            return;
        }
        currentOrder.setStatus(newStatus);
        repository.updateOrder(currentOrder);
    }

    /** Get all orders belonging to a specific user. */
    public ArrayList<Order> getOrdersByUserId(int userId) {
        ArrayList<Order> all = repository.getAllOrders();
        ArrayList<Order> result = new ArrayList<>();
        for (Order o : all) {
            if (o.getUserId() == userId) {
                result.add(o);
            }
        }
        return result;
    }

    /** Get all orders (no Scanner). */
    public ArrayList<Order> getAllOrders() {
        return repository.getAllOrders();
    }
}
