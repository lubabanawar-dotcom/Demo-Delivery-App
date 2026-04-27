package menu;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MenuRepositoryImp implements MenuRepository {

    private String filename = "menu.dat";

    private List<MenuItem> load() {
        File file = new File(filename);

        if (!file.exists()) return new ArrayList<>();

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            List<MenuItem> list = (List<MenuItem>) ois.readObject();
            ois.close();
            return list;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void save(List<MenuItem> list) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(list);
            oos.close();
        } catch (Exception e) {
            System.out.println("Error saving menu");
        }
    }

    @Override
    public void add(MenuItem item) {
        List<MenuItem> list = load();
        list.add(item);
        save(list);
    }

    @Override
    public List<MenuItem> getAll() {
        return load();
    }

    @Override
    public MenuItem getById(int id) {
        for (MenuItem m : load()) {
            if (m.getId() == id) return m;
        }
        return null;
    }

    @Override
    public void update(MenuItem updated) {
        List<MenuItem> list = load();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == updated.getId()) {
                list.set(i, updated);
                break;
            }
        }

        save(list);
    }

    @Override
    public void delete(int id) {
        List<MenuItem> list = load();
        list.removeIf(m -> m.getId() == id);
        save(list);
    }
}