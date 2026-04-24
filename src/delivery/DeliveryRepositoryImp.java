package delivery;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryRepositoryImp implements DeliveryRepository {

    private String filename = "deliveries.dat";

    // ---- Load all deliveries from file ----
    private ArrayList<Delivery> loadDeliveries() {
        File file = new File(filename);
        if (!file.exists()) return new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Delivery> list = (ArrayList<Delivery>) ois.readObject();
            ois.close();
            return list;
        } catch (Exception e) {
            System.out.println("Error loading deliveries: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // ---- Save all deliveries to file ----
    private void saveAll(ArrayList<Delivery> list) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            oos.close();
        } catch (Exception e) {
            System.out.println("Error saving deliveries: " + e.getMessage());
        }
    }

    @Override
    public void save(Delivery d) {
        ArrayList<Delivery> list = loadDeliveries();
        list.add(d);
        saveAll(list);
    }

    @Override
    public Delivery findById(int id) {
        for (Delivery d : loadDeliveries())
            if (d.getDeliveryId() == id) return d;
        return null;
    }

    @Override
    public Delivery findByOrderId(int orderId) {
        for (Delivery d : loadDeliveries())
            if (d.getOrderId() == orderId) return d;
        return null;
    }

    @Override
    public List<Delivery> findByCustomerId(int customerId) {
        List<Delivery> result = new ArrayList<>();
        for (Delivery d : loadDeliveries())
            if (d.getCustomerId() == customerId) result.add(d);
        return result;
    }

    @Override
    public List<Delivery> findAll() {
        return loadDeliveries();
    }

    @Override
    public void update(Delivery updated) {
        ArrayList<Delivery> list = loadDeliveries();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDeliveryId() == updated.getDeliveryId()) {
                list.set(i, updated);
                break;
            }
        }
        saveAll(list);
    }

    @Override
    public void delete(int id) {
        ArrayList<Delivery> list = loadDeliveries();
        list.removeIf(d -> d.getDeliveryId() == id);
        saveAll(list);
    }
}
