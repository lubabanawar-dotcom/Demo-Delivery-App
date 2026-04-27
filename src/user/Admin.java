package user;

public class Admin extends User {
    public Admin(int id, String name, String password, String phone, String address) {
        super(id, name, password, phone, address);
    }

    @Override
    public void displayRole() {
        System.out.println("Role: Admin");
    }
}