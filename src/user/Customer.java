package user;

//---- Customer Class ----
public class Customer extends User{
	Customer(int id, String name, String password, String phone) {
		super(id, name, password, phone);
	}
	@Override
	public void displayRole() {
		System.out.println("Role: Customer");
	} 
}