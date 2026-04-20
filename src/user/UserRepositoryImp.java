package user;
import java.util.ArrayList;
import java.io.*;

public class UserRepositoryImp implements UserRepository {

    private String filename = "users.dat";

    // ---- Load all users from file ----
    private ArrayList<User> loadUsers() {
        ArrayList<User> users = new ArrayList<>();

        // Check if file exists first
        File file = new File(filename);
        if (!file.exists()) {
            return users; 
        }

        try {
            // Open the binary file for reading
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);

            // Read the whole list from the file at once
            users = (ArrayList<User>) ois.readObject();

            // Close the file
            ois.close();

        } catch (Exception e) {
            System.out.println("Error loading users: " + e.getMessage());
        }

        return users;
    }

    // ---- Save all users to file ----
    private void saveAllUsers(ArrayList<User> users) {
        try {
            // Open the binary file for writing
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // Write the whole list to the file at once
            oos.writeObject(users);

            // Close the file
            oos.close();

        } catch (Exception e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }
    
    // ---- Get next available ID ----
    private int getNextId() {
        ArrayList<User> users = loadUsers();
        if (users.isEmpty()) {
            return 1;
        }
        return users.get(users.size() - 1).getId() + 1;
    }
    
 // ---- Add user ----
    @Override
    public void addUser(String name, String password, String phone, String role) {
        ArrayList<User> users = loadUsers(); 
        int newId = getNextId();

        if (role.equalsIgnoreCase("admin")) {
            users.add(new Admin(newId, name, password, phone));
        } else {
            users.add(new Customer(newId, name, password, phone));
        }

        saveAllUsers(users);                  
        System.out.println("User added successfully!");
    }
    
 // ---- View all users ----
    @Override
    public void viewAllUsers() {
        ArrayList<User> users = loadUsers();

        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        System.out.println("\n All Users ");
        for (User u : users) {
            u.displayInfo();
        }
    }
    

    // ---- Update user ----
    @Override
    public void updateUser(int id, String newName, String newPhone) {
        ArrayList<User> users = loadUsers();

        for (User u : users) {
            if (u.getId() == id) {
                u.setName(newName);
                u.setPhone(newPhone);
                saveAllUsers(users);
                System.out.println("User updated successfully.");
                return;
            }
        }
        System.out.println("User with ID " + id + " not found!");
    }
    
    // ---- Delete user ----
    @Override
    public void deleteUser(int id) {
        ArrayList<User> users = loadUsers();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                users.remove(i);
                
                saveAllUsers(users);
                System.out.println("User deleted successfully.");
                return;
            }
        }
        System.out.println("User with ID " + id + " not found!");
    }
    
    // ---- Login ----
    @Override
    public User login(String name, String password) {
        ArrayList<User> users = loadUsers();

        for (User u : users) {
            if (u.getName().equalsIgnoreCase(name)
                    && u.getPassword().equals(password)) {
                return u;
            }
        }
        System.out.println("Invalid name or password!");
        return null;
    }
    
}