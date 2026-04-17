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
        menuItems.removeIf(item -> item.getId() == id);
    }
}