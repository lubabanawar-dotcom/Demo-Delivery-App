package user;


//---- Admin class ----
class Admin extends User{
	public Admin(int id, String name, String password) {
     super(id, name, password);
 }
	
 @Override
 public void displayRole() {
     System.out.println("Role: Admin");
 }
}