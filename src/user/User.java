package user;

// ---- Abstract User ----
abstract class User{
	private String name;
	private int id;
	private String password;
	
	//Constructor
	User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
	}
	
	 // Getters and setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public void setName(String name) { this.name = name; }
    public void setPassword(String password) { this.password = password; }
	
    //Abstract Method
	abstract public void displayRole();
}