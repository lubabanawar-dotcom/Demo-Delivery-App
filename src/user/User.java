package user;

import java.io.Serializable;

public abstract class User implements Serializable {
    private String name;
    private int id;
    private String password;
    private String phone;
    private String address;

    public User(int id, String name, String password, String phone, String address) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }

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

    public String getAddress() {
        return address;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public abstract void displayRole();

    public void displayInfo() {
        System.out.println("ID      : " + id);
        System.out.println("Name    : " + name);
        System.out.println("Phone   : " + phone);
        System.out.println("Address : " + address);
        displayRole();
    }
}