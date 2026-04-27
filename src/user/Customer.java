package user;

public class Customer extends User {
    public Customer(int id, String name, String password, String phone, String address) {
        super(id, name, password, phone, address);
    }

    @Override
    public void displayRole() {
        System.out.println("Role: Customer");
    }
}