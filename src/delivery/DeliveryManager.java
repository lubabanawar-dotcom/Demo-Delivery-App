package delivery;

import java.util.List;
import java.util.Random;

public class DeliveryManager {

    private DeliveryRepository repository;

    public DeliveryManager() {
        this.repository = new DeliveryRepositoryImp();
    }

    // ---- Assign Delivery Person ----
    private int assignDeliveryPerson() {
        Random random = new Random();
        return random.nextInt(10) + 1;
    }

    // ---- Create Delivery ----
    public void createDelivery(int orderId, int customerId, String address) {
        int newId = repository.findAll().size() + 1;

        Delivery d = new Delivery(newId, orderId, customerId, address);

        int assignedPerson = assignDeliveryPerson();
        d.setDeliveryPersonId(assignedPerson);

        repository.save(d);

        System.out.println("Delivery created successfully! Delivery ID: " + newId);
        System.out.println("Assigned to Delivery Person ID: " + assignedPerson);
    }

    // ---- Update Status ----
    public void updateStatus(int deliveryId, String newStatus) {
        Delivery d = repository.findById(deliveryId);

        if (d == null) {
            System.out.println("Delivery not found!");
            return;
        }

        d.setStatus(newStatus);
        repository.update(d);
    }

    // ---- Quick Status Updates ----
    public void markOnTheWay(int deliveryId) {
        updateStatus(deliveryId, "ON_THE_WAY");
    }

    public void markDelivered(int deliveryId) {
        updateStatus(deliveryId, "DELIVERED");
    }

    public void markFailed(int deliveryId) {
        updateStatus(deliveryId, "FAILED");
    }

    // ---- Get One Delivery ----
    public Delivery getDeliveryByOrderId(int orderId) {
        return repository.findByOrderId(orderId);
    }

    // ---- Get All Deliveries ----
    public List<Delivery> getAllDeliveries() {
        return repository.findAll();
    }
}