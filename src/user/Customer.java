package user;

//---- Customer Class ----
class Customer extends User{
	Customer(int id, String name, String password) {
		super(id, name, password);
	}
	@Override
	public void displayRole() {
		System.out.println("Role: Customer");
	} 
}