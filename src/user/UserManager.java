package user;

import java.util.ArrayList;
import java.util.Scanner;

public class UserManager {

    private UserRepository repository;
    private Scanner sc;

    public UserManager() {
        repository = new UserRepositoryImp();
        sc = new Scanner(System.in);
    }

    public void addCustomer() {
        System.out.print("Enter name     : ");
        String name = sc.nextLine();
        System.out.print("Enter password : ");
        String password = sc.nextLine();
        System.out.print("Enter phone    : ");
        String phone = sc.nextLine();
        System.out.print("Enter address  : ");
        String address = sc.nextLine();

        repository.addUser(name, password, phone, address, "customer");
    }

    public void addAdmin() {
        System.out.print("Enter name     : ");
        String name = sc.nextLine();
        System.out.print("Enter password : ");
        String password = sc.nextLine();
        System.out.print("Enter phone    : ");
        String phone = sc.nextLine();
        System.out.print("Enter address  : ");
        String address = sc.nextLine();

        repository.addUser(name, password, phone, address, "admin");
    }

    public void viewAllUsers() {
        repository.viewAllUsers();
    }

    public void updateUser() {
        System.out.print("Enter user ID to update : ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("New name  : ");
        String newName = sc.nextLine();
        System.out.print("New phone : ");
        String newPhone = sc.nextLine();

        repository.updateUser(id, newName, newPhone);
    }

    public void deleteUser() {
        System.out.print("Enter user ID to delete : ");
        int id = sc.nextInt();
        sc.nextLine();
        repository.deleteUser(id);
    }

    public User login() {
        System.out.print("Enter name     : ");
        String name = sc.nextLine();
        System.out.print("Enter password : ");
        String password = sc.nextLine();

        User u = repository.login(name, password);

        if (u != null) {
            System.out.println("Welcome, " + u.getName() + "!");
            u.displayRole();
        } else {
            System.out.println("Invalid name or password!");
        }

        return u;
    }

    public User login(String name, String password) {
        return repository.login(name, password);
    }
    
    
    //Added later for UI:
    public void addCustomer(String name, String password, String phone, String address) {
        repository.addUser(name, password, phone, address, "customer");
    }

    public void addAdmin(String name, String password, String phone, String address) {
        repository.addUser(name, password, phone, address, "admin");
    }

    public ArrayList<User> getAllUsers() {
        return repository.getAllUsers();
    }

    public User getUserById(int id) {
        for (User u : repository.getAllUsers()) {
            if (u.getId() == id) return u;
        }
        return null;
    }
}