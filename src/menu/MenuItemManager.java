package menu;

import java.util.List;

public class MenuItemManager {
    private MenuRepositoryImp repository;

    public MenuItemManager() {
        this.repository = new MenuRepositoryImp();
    }


    public void addMenuItem(int id, String name, double price, String category, boolean available, String description) {
        MenuItem item = new MenuItem(id, name, price, category, available, description);
        repository.add(item);
        System.out.println(name + " added to menu successfully!");
    }

 
    public void removeMenuItem(int id) {
        MenuItem item = repository.getById(id);
        if (item == null) {
            System.out.println("Item not found!");
            return;
        }
        repository.delete(id);
        System.out.println("Item removed successfully!");
    }

 
    public void updatePrice(int id, double newPrice) {
        MenuItem item = repository.getById(id);
        if (item == null) {
            System.out.println("Item not found!");
            return;
        }
        item.setPrice(newPrice);
        repository.update(item);
        System.out.println("Price updated to " + newPrice + " successfully!");
    }

 
    public void updateCategory(int id, String newCategory) {
        MenuItem item = repository.getById(id);
        if (item == null) {
            System.out.println("Item not found!");
            return;
        }
        item.setCategory(newCategory);
        repository.update(item);
        System.out.println("Category updated successfully!");
    }

    
    public void updateDescription(int id, String newDescription) {
        MenuItem item = repository.getById(id);
        if (item == null) {
            System.out.println("Item not found!");
            return;
        }
        item.setDescription(newDescription);
        repository.update(item);
        System.out.println("Description updated successfully!");
    }

 
    public void markUnavailable(int id) {
        MenuItem item = repository.getById(id);
        if (item == null) {
            System.out.println("Item not found!");
            return;
        }
        item.setAvailable(false);
        repository.update(item);
        System.out.println("Item marked as unavailable.");
    }


    public void markAvailable(int id) {
        MenuItem item = repository.getById(id);
        if (item == null) {
            System.out.println("Item not found!");
            return;
        }
        item.setAvailable(true);
        repository.update(item);
        System.out.println("Item marked as available.");
    }

    public void viewItem(int id) {
        MenuItem item = repository.getById(id);
        if (item == null) {
            System.out.println("Item not found!");
            return;
        }
        System.out.println(item);
    }

    public void viewMenu() {
        repository.displayMenuByCategory();
    }

    public void viewByCategory(String category) {
        List<MenuItem> items = repository.getByCategory(category);
        if (items.isEmpty()) {
            System.out.println("No items found in category: " + category);
            return;
        }
        for (MenuItem item : items) {
            System.out.println(item);
            System.out.println("------------------");
        }
    }

    public void viewAllItems() {
        List<MenuItem> items = repository.getAll();
        if (items.isEmpty()) {
            System.out.println("No menu items found!");
            return;
        }
        for (MenuItem item : items) {
            System.out.println(item);
            System.out.println("------------------");
        }
    }
}

