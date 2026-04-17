package menu;
import java.util.List;

public class MenuItemManager {

    private MenuRepository menuRepository;

    public MenuItemManager(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public void addMenuItem(MenuItem item) {
        menuRepository.add(item);
    }

    public List<MenuItem> viewMenu() {
        return menuRepository.getAll();
    }

    public MenuItem findItem(int id) {
        return menuRepository.getById(id);
    }

    public void updateItem(MenuItem item) {
        menuRepository.update(item);
    }

    public void deleteItem(int id) {
        menuRepository.delete(id);
    }
}