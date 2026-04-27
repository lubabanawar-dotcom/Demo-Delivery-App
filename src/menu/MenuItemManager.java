package menu;

import java.util.List;

public class MenuItemManager {
    private MenuRepositoryImp repository;

    public MenuItemManager() {
        this.repository = new MenuRepositoryImp();
    }

    // ---------------- Add Menu Item ----------------
    public void addMenuItem(int id, String name, double price, boolean available) {
        MenuItem item = new MenuItem(id, name, price, available);
        repository.add(item);
    }

    // ---------------- Remove Menu Item ----------------
    public void removeMenuItem(int id) {
        repository.delete(id);
    }

    // ---------------- Update Price ----------------
    public void updatePrice(int id, double newPrice) {
        MenuItem item = repository.getById(id);
        if (item != null) {
            item.setPrice(newPrice);
            repository.update(item);
        }
    }

    // ---------------- Mark Unavailable ----------------
    public void markUnavailable(int id) {
        MenuItem item = repository.getById(id);
        if (item != null) {
            item.setAvailable(false);
            repository.update(item);
        }
    }

    // ---------------- Mark Available ----------------
    public void markAvailable(int id) {
        MenuItem item = repository.getById(id);
        if (item != null) {
            item.setAvailable(true);
            repository.update(item);
        }
    }

    // ---------------- View All ----------------
    public List<MenuItem> viewAllItemsReturn() {
        return repository.getAll();
    }

    // ---------------- Get Next Safe Menu ID ----------------
    public int getNextMenuId() {
        List<MenuItem> items = repository.getAll();

        int max = 0;
        for (MenuItem item : items) {
            if (item.getId() > max) {
                max = item.getId();
            }
        }

        return max + 1;
    }
}