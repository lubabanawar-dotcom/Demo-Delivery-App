package user;

import java.util.ArrayList;
import java.io.*;

public class UserRepositoryImp implements UserRepository {

    private String filename = "users.dat";

    private ArrayList<User> loadUsers() {
        ArrayList<User> users = new ArrayList<>();
        File file = new File(filename);

        if (!file.exists()) {
            return users;
        }

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
            users = (ArrayList<User>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            System.out.println("Error loading users: " + e.getMessage());
        }

        return users;
    }

    private void saveAllUsers(ArrayList<User> users) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(users);
            oos.close();
        } catch (Exception e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    private int getNextId() {
        ArrayList<User> users = loadUsers();
        if (users.isEmpty()) return 1;
        return users.get(users.size() - 1).getId() + 1;
    }

    @Override
    public void addUser(String name, String password, String phone, String address, String role) {
        ArrayList<User> users = loadUsers();
        int newId = getNextId();

        if (role.equalsIgnoreCase("admin")) {
            users.add(new Admin(newId, name, password, phone, address));
        } else {
            users.add(new Customer(newId, name, password, phone, address));
        }

        saveAllUsers(users);
        System.out.println("User added successfully!");
    }

    @Override
    public void viewAllUsers() {
        ArrayList<User> users = loadUsers();

        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        System.out.println("\nAll Users");
        for (User u : users) {
            u.displayInfo();
            System.out.println("------------------");
        }
    }

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

    @Override
    public void deleteUser(int id) {
        ArrayList<User> users = loadUsers();
        users.removeIf(u -> u.getId() == id);
        saveAllUsers(users);
        System.out.println("User deleted successfully.");
    }

    @Override
    public User login(String name, String password) {
        ArrayList<User> users = loadUsers();

        for (User u : users) {
            if (u.getName().equalsIgnoreCase(name) && u.getPassword().equals(password)) {
                return u;
            }
        }

        return null;
    }

    //Added later for UI:
    @Override
    public ArrayList<User> getAllUsers() {
        return loadUsers();
    }
}