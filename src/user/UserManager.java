package user;

// ---- User Manager ----

import java.util.ArrayList;

class UserManager{
	
	private ArrayList<Customer> customers = new ArrayList<>();
	private ArrayList<Admin> admins = new ArrayList<>();
	
	private final String customerFile = "customers.txt";
	private final String adminFile = "admins.txt";
	
	//Load customers from file
	public void loadData() {

		try {
			
		}
		catch(Exception e) {
			
		}
	}
	
	//Load Admin from file
	
	//Save users to file
	
	//Save Admin to file
	
	//Register new user
	
	
	//Login
	public User login(String name, String password) throws Exception{
		for (Customer c : customers) {
			if(c.getName().equalsIgnoreCase(name) && c.getPassword().equals(password))
				return c;
		}
		for (Admin a : admins) {
	        if (a.getName().equalsIgnoreCase(name) && a.getPassword().equals(password))
	            return a;
		}
		throw new Exception ("Invalid Login");
	}
	
	
	
}