package menu;

import java.util.ArrayList;
import java.util.List;

public class MenuRepositoryImp implements MenuRepository {
    private List<MenuItem> menuItems = new ArrayList<>();

    @Override
    public void add(MenuItem item) {
        menuItems.add(item);
    }

    @Override
    public List<MenuItem> getAll() {
        return menuItems;
    }

    @Override
    public MenuItem getById(int id) {
        for (MenuItem item : menuItems) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    @Override
    public List<MenuItem> getByCategory(String category) {
        List<MenuItem> result = new ArrayList<>();
        for (MenuItem item : menuItems) {
            if (item.getCategory().equals(category) && item.isAvailable()) {
                result.add(item);
            }
        }
        return result;
    }

    @Override
    public void update(MenuItem updatedItem) {
        for (int i = 0; i < menuItems.size(); i++) {
            if (menuItems.get(i).getId() == updatedItem.getId()) {
                menuItems.set(i, updatedItem);
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        for (int i = 0; i < menuItems.size(); i++) {
            if (menuItems.get(i).getId() == id) {
                menuItems.remove(i);
                return;
            }
        }
    }

    @Override
    public void displayMenuByCategory() {
        String[] categories = {"APPETIZER", "MAIN_COURSE", "DESSERT", "DRINKS", "SIDES", "COMBO_MEAL"};

        for (String category : categories) {
            System.out.println("\n========== " + category + " ==========");
            List<MenuItem> items = getByCategory(category);

            if (items.isEmpty()) {
                System.out.println("No items available in this category.");
            } else {
                for (MenuItem item : items) {
                    System.out.println(item);
                    System.out.println("------------------");
                }
            }
        }
    }
}
