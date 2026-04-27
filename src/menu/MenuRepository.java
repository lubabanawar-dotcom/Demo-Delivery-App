package menu;

import java.util.List;

public interface MenuRepository {
    void add(MenuItem item);
    List<MenuItem> getAll();
    MenuItem getById(int id);
    void update(MenuItem item);
    void delete(int id);
}