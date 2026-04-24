package user;
import java.util.Scanner;

// ---- User Manager ----

//import java.util.ArrayList;

public class UserManager{
	
	private UserRepository repository;
	private Scanner sc;
	
	//constructor
	public UserManager() {
		repository = new UserRepositoryImp();
		sc = new Scanner(System.in);
	}
	
	//Add customer
	public void addCustomer() {
        System.out.print("Enter name     : ");
        String name = sc.nextLine();
        System.out.print("Enter password : ");
        String password = sc.nextLine();
        System.out.print("Enter phone    : ");
        String phone = sc.nextLine();

        try {
            if (name.trim().isEmpty() || password.trim().isEmpty() || phone.trim().isEmpty()) {
                throw new Exception("All fields are required!");
            }
            repository.addUser(name, password, phone, "customer");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
	
	
	
	//Add admin 
	 public void addAdmin() {
	        System.out.print("Enter name     : ");
	        String name = sc.nextLine();
	        System.out.print("Enter password : ");
	        String password = sc.nextLine();
	        System.out.print("Enter phone    : ");
	        String phone = sc.nextLine();

	        try {
	            if (name.trim().isEmpty() || password.trim().isEmpty() || phone.trim().isEmpty()) {
	                throw new Exception("All fields are required!");
	            }
	            repository.addUser(name, password, phone, "admin");
	        } catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }
	 
	 
	 //View all users
	 public void viewAllUsers() {
	        repository.viewAllUsers();
	    }
	 
	 //Update user
	 public void updateUser() {
	        System.out.print("Enter user ID to update : ");
	        int id = sc.nextInt();
	        sc.nextLine();
	        System.out.print("New name  : ");
	        String newName = sc.nextLine();
	        System.out.print("New phone : ");
	        String newPhone = sc.nextLine();

	        try {
	            if (newName.trim().isEmpty()) {
	                throw new Exception("Name cannot be empty!");
	            }
	            repository.updateUser(id, newName, newPhone);
	        } catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }
	 
	 //Delete user
	 public void deleteUser() {
	        System.out.print("Enter user ID to delete : ");
	        int id = sc.nextInt();
	        sc.nextLine();
	        repository.deleteUser(id);
	    }
	 
	 //Login
	 public User login() {
	        System.out.print("Enter name     : ");
	        String name = sc.nextLine();
	        System.out.print("Enter password : ");
	        String password = sc.nextLine();

	        User u = repository.login(name, password);
	        if (u != null) {
	            System.out.println("Welcome, " + u.getName() + "!");
	            u.displayRole();
	        }
	        return u;
	    }
	 
	 public User login(String name, String password) {
		    return repository.login(name, password);
		}
	 
	 //Menu call this from Main.java
	 
	 public void showMenu() {
        while (true) {
            System.out.println("\n=== User Management ===");
            System.out.println("1. Register Customer");
            System.out.println("2. Register Admin");
            System.out.println("3. View All Users");
            System.out.println("4. Update User");
            System.out.println("5. Delete User");
            System.out.println("6. Login");
            System.out.println("7. Back");
            System.out.print("Choose: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if      (choice == 1) addCustomer();
            else if (choice == 2) addAdmin();
            else if (choice == 3) viewAllUsers();
            else if (choice == 4) updateUser();
            else if (choice == 5) deleteUser();
            else if (choice == 6) login();
            else if (choice == 7) break;
            else System.out.println("Invalid choice!");
        }
    }	
	 //added later:
	 public void addCustomer(String name, String password, String phone) {
		    if (name.trim().isEmpty() || password.trim().isEmpty() || phone.trim().isEmpty()) {
		        System.out.println("All fields required!");
		        return;
		    }

		    repository.addUser(name, password, phone, "customer");
		}
}