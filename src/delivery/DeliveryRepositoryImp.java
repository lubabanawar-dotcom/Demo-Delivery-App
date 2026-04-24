package delivery;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryRepositoryImp implements DeliveryRepository {

    private String filename = "delivery.dat";

    private List<Delivery> load() {
        File file = new File(filename);
        if (!file.exists()) return new ArrayList<>();

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            List<Delivery> list = (List<Delivery>) ois.readObject();
            ois.close();
            return list;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void save(List<Delivery> list) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(list);
            oos.close();
        } catch (Exception e) {
            System.out.println("Error saving delivery");
        }
    }

    @Override
    public void save(Delivery d) {
        List<Delivery> list = load();
        list.add(d);
        save(list);
    }

    @Override
    public Delivery findById(int id) {
        for (Delivery d : load()) {
            if (d.getDeliveryId() == id) return d;
        }
        return null;
    }

    @Override
    public Delivery findByOrderId(int orderId) {
        for (Delivery d : load()) {
            if (d.getOrderId() == orderId) return d;
        }
        return null;
    }

    @Override
    public List<Delivery> findByCustomerId(int customerId) {
        List<Delivery> result = new ArrayList<>();
        for (Delivery d : load()) {
            if (d.getCustomerId() == customerId) {
                result.add(d);
            }
        }
        return result;
    }

    @Override
    public List<Delivery> findAll() {
        return load();
    }

    @Override
    public void update(Delivery updated) {
        List<Delivery> list = load();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDeliveryId() == updated.getDeliveryId()) {
                list.set(i, updated);
                break;
            }
        }

        save(list);
    }

    @Override
    public void delete(int id) {
        List<Delivery> list = load();
        list.removeIf(d -> d.getDeliveryId() == id);
        save(list);
    }
}