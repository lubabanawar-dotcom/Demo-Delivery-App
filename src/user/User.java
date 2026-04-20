package user;
import java.io.Serializable; //if binary file, obj can be saved to binary file

// ---- Abstract class User ----
abstract class User implements Serializable{
	private String name;
	private int id;
	private String password;
	private String phone;
	
	//Constructor
	User(int id, String name, String password, String phone) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.phone= phone;
	}
	
	 // Getters and setters
    public int getId() { 
    	return id;
    }
    public String getName() { 
    	return name; 
    }
    public String getPassword() { 
    	return password; 
    }
    public String getPhone() { 
    	return phone; 
    }
    public void setName(String name) { 
    	this.name = name; 
    }
    public void setPassword(String password) { 
    	this.password = password;
    }
    public void setPhone(String phone) { 
    	this.phone = phone;
    }
	
    //Abstract Method
	abstract public void displayRole();
	
	//Show user info
	public void displayInfo() {
		System.out.println("ID     :" +id);
		System.out.println("Name   :" +name);
		System.out.println("Phone  :" +phone);
		displayRole(); 
		
		
	}
	
	
}