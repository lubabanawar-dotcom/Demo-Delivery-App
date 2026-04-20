package menu;

import java.io.Serializable;

public class MenuItem implements Serializable {
    private int id;
    private String name;
    private double price;
    private String category;
    private boolean available;
    private String description;

    public MenuItem(int id, String name, double price, String category, boolean available, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.available = available;
        this.description = description;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public boolean isAvailable() { return available; }
    public String getDescription() { return description; }

    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setCategory(String category) { this.category = category; }
    public void setAvailable(boolean available) { this.available = available; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "ID: " + id +
               ", Name: " + name +
               ", Price: " + price +
               ", Category: " + category +
               ", Available: " + available +
               ", Description: " + description;
    }
}

