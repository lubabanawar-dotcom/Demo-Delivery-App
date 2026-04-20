package user;

//---- Admin class ----
public class Admin extends User{
	public Admin(int id, String name, String password, String phone) {
     super(id, name, password, phone);
 }
	
 @Override
 public void displayRole() {
     System.out.println("Role: Admin");
 }
}