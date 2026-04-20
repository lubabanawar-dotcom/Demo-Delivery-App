package user;

public interface UserRepository {
	
	void addUser(String name, String password, String phone, String role);
    void viewAllUsers();
    void updateUser(int id, String newName, String newPhone);
    void deleteUser(int id);
    User login(String name, String password);

}
