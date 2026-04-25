package app;

import user.*;
import menu.*;
import menu.MenuItem;
import order.*;
import delivery.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SimpleUI extends JFrame {

    CardLayout layout = new CardLayout();
    JPanel main = new JPanel(layout);

    // Managers
    UserManager userManager = new UserManager();
    MenuItemManager menuManager = new MenuItemManager();
    OrderManager orderManager = new OrderManager();
    DeliveryManager deliveryManager = new DeliveryManager();

    // Logged in state
    User loggedUser;
    Order currentOrder;

    // Login fields
    JTextField loginNameField;
    JPasswordField loginPassField;

    // Customer address
    String customerAddress = "";

    // Track panel labels
    JLabel statusLabel;
    JLabel totalLabel;
    JLabel addressLabel;
    JLabel deliveryLabel;

    // Table Models
    DefaultTableModel userModel =
            new DefaultTableModel(new String[]{"ID", "Name", "Phone", "Role"}, 0);

    DefaultTableModel orderModel =
            new DefaultTableModel(new String[]{"Order ID", "User ID", "Total", "Order Status", "Delivery Status"}, 0);

    DefaultTableModel deliveryModel =
            new DefaultTableModel(new String[]{"Delivery ID", "Order ID", "Customer ID", "Address", "Status"}, 0);

    DefaultTableModel menuModel =
            new DefaultTableModel(new String[]{"ID", "Name", "Price"}, 0);

    DefaultTableModel cartModel =
            new DefaultTableModel(new String[]{"Name", "Price"}, 0);

    DefaultTableModel trackModel =
            new DefaultTableModel(new String[]{"Item", "Price"}, 0);

    public SimpleUI() {
        setTitle("Simple Delivery App");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        main.add(loginPanel(), "login");
        main.add(registerPanel(), "register");
        main.add(customerPanel(), "customer");
        main.add(adminPanel(), "admin");
        main.add(historyPanel(), "history");
        main.add(trackPanel(), "track");

        add(main);
        layout.show(main, "login");

        loadMenu();
    }

    // ---------------- LOGIN ----------------
    JPanel loginPanel() {
        JPanel p = new JPanel(new GridLayout(6, 2, 10, 10));

        loginNameField = new JTextField();
        loginPassField = new JPasswordField();
        JComboBox<String> roleBox = new JComboBox<>(new String[]{"Customer", "Admin"});

        JButton login = new JButton("Login");
        JButton register = new JButton("Register");

        p.add(new JLabel("Name"));
        p.add(loginNameField);

        p.add(new JLabel("Password"));
        p.add(loginPassField);

        p.add(new JLabel("Role"));
        p.add(roleBox);

        p.add(login);
        p.add(register);

        login.addActionListener(e -> {
            String selectedRole = roleBox.getSelectedItem().toString();

            loggedUser = userManager.login(
                    loginNameField.getText(),
                    new String(loginPassField.getPassword())
            );

            if (loggedUser != null) {

                if (selectedRole.equals("Admin") && loggedUser instanceof Admin) {
                    loadAdminData();
                    layout.show(main, "admin");

                } else if (selectedRole.equals("Customer") && loggedUser instanceof Customer) {
                    currentOrder = orderManager.createOrder(loggedUser.getId());

                    customerAddress = JOptionPane.showInputDialog(this, "Enter Delivery Address:");
                    if (customerAddress == null || customerAddress.trim().isEmpty()) {
                        customerAddress = "No Address";
                    }

                    layout.show(main, "customer");

                } else {
                    JOptionPane.showMessageDialog(this, "Wrong role selected!");
                }

            } else {
                JOptionPane.showMessageDialog(this, "Invalid login");
            }
        });

        register.addActionListener(e -> layout.show(main, "register"));

        return p;
    }

    // ---------------- REGISTER ----------------
    JPanel registerPanel() {
        JPanel p = new JPanel(new GridLayout(7, 2, 10, 10));

        JTextField name = new JTextField();
        JTextField pass = new JTextField();
        JTextField phone = new JTextField();
        JTextField address = new JTextField();
        JComboBox<String> roleBox = new JComboBox<>(new String[]{"Customer", "Admin"});

        JButton save = new JButton("Register");
        JButton back = new JButton("Back");

        p.add(new JLabel("Name"));
        p.add(name);

        p.add(new JLabel("Password"));
        p.add(pass);

        p.add(new JLabel("Phone"));
        p.add(phone);

        p.add(new JLabel("Address"));
        p.add(address);

        p.add(new JLabel("Role"));
        p.add(roleBox);

        p.add(save);
        p.add(back);

        save.addActionListener(e -> {
            String role = roleBox.getSelectedItem().toString();

            if (name.getText().isEmpty() || pass.getText().isEmpty()
                    || phone.getText().isEmpty() || address.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields required!");
                return;
            }

            if (role.equals("Admin")) {
                userManager.addAdmin(name.getText(), pass.getText(), phone.getText());
            } else {
                userManager.addCustomer(name.getText(), pass.getText(), phone.getText());
            }

            JOptionPane.showMessageDialog(this, role + " Registered!");
            layout.show(main, "login");
        });

        back.addActionListener(e -> layout.show(main, "login"));

        return p;
    }

    // ---------------- CUSTOMER ----------------
    JPanel customerPanel() {
        JPanel p = new JPanel(new BorderLayout());

        JTable menuTable = new JTable(menuModel);
        JTable cartTable = new JTable(cartModel);

        JButton add = new JButton("Add");
        JButton remove = new JButton("Remove");
        JButton checkout = new JButton("Checkout");
        JButton history = new JButton("My Orders");
        JButton logout = new JButton("Logout");

        JPanel bottom = new JPanel();
        bottom.add(add);
        bottom.add(remove);
        bottom.add(checkout);
        bottom.add(history);
        bottom.add(logout);

        p.add(new JLabel("Customer Panel", SwingConstants.CENTER), BorderLayout.NORTH);
        p.add(new JScrollPane(menuTable), BorderLayout.WEST);
        p.add(new JScrollPane(cartTable), BorderLayout.CENTER);
        p.add(bottom, BorderLayout.SOUTH);

        add.addActionListener(e -> {
            int r = menuTable.getSelectedRow();

            if (r != -1) {
                String name = menuModel.getValueAt(r, 1).toString();
                double price = Double.parseDouble(menuModel.getValueAt(r, 2).toString());

                cartModel.addRow(new Object[]{name, price});
                orderManager.addItem(currentOrder.getOrderId(), name, price);
            }
        });

        remove.addActionListener(e -> {
            int r = cartTable.getSelectedRow();

            if (r != -1) {
                String name = cartModel.getValueAt(r, 0).toString();
                double price = Double.parseDouble(cartModel.getValueAt(r, 1).toString());

                cartModel.removeRow(r);
                orderManager.removeItem(currentOrder.getOrderId(), name, price);
            }
        });

        checkout.addActionListener(e -> {
            Order o = orderManager.getOrder(currentOrder.getOrderId());

            deliveryManager.createDelivery(o.getOrderId(), loggedUser.getId(), customerAddress);

            loadTrackOrder(o.getOrderId());

            cartModel.setRowCount(0);
            currentOrder = orderManager.createOrder(loggedUser.getId());

            layout.show(main, "track");
        });

        history.addActionListener(e -> {
            loadCustomerHistory();
            layout.show(main, "history");
        });

        logout.addActionListener(e -> {
            cartModel.setRowCount(0);
            loginNameField.setText("");
            loginPassField.setText("");
            layout.show(main, "login");
        });

        return p;
    }

    // ---------------- TRACK CURRENT ORDER ----------------
    JPanel trackPanel() {
        JPanel p = new JPanel(new BorderLayout());

        JTable table = new JTable(trackModel);

        statusLabel = new JLabel("Order Placed", SwingConstants.CENTER);
        totalLabel = new JLabel("Total: 0", SwingConstants.CENTER);
        addressLabel = new JLabel("Address: ", SwingConstants.CENTER);
        deliveryLabel = new JLabel("Delivery: Pending", SwingConstants.CENTER);

        JButton back = new JButton("Back to Menu");

        JPanel info = new JPanel(new GridLayout(4, 1));
        info.add(statusLabel);
        info.add(totalLabel);
        info.add(addressLabel);
        info.add(deliveryLabel);

        p.add(new JLabel("Current Order", SwingConstants.CENTER), BorderLayout.NORTH);
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        p.add(info, BorderLayout.EAST);
        p.add(back, BorderLayout.SOUTH);

        back.addActionListener(e -> layout.show(main, "customer"));

        return p;
    }

    void loadTrackOrder(int orderId) {
        trackModel.setRowCount(0);

        Order o = orderManager.getOrder(orderId);
        Delivery d = deliveryManager.getDeliveryByOrderId(orderId);

        for (String item : o.getItemNames()) {
            trackModel.addRow(new Object[]{item, ""});
        }

        statusLabel.setText("Order Placed");
        totalLabel.setText("Total: " + o.getTotalPrice());

        if (d != null) {
            addressLabel.setText("Address: " + d.getAddress());
            deliveryLabel.setText("Delivery: " + d.getStatus());
        } else {
            addressLabel.setText("Address: " + customerAddress);
            deliveryLabel.setText("Delivery: Pending");
        }
    }

    // ---------------- CUSTOMER HISTORY ----------------
    JPanel historyPanel() {
        JPanel p = new JPanel(new BorderLayout());

        JTable table = new JTable(orderModel);
        JButton back = new JButton("Back");

        p.add(new JLabel("My Orders", SwingConstants.CENTER), BorderLayout.NORTH);
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        p.add(back, BorderLayout.SOUTH);

        back.addActionListener(e -> layout.show(main, "customer"));

        return p;
    }

    void loadCustomerHistory() {
        orderModel.setRowCount(0);

        for (Order o : orderManager.getOrdersByUser(loggedUser.getId())) {
            Delivery d = deliveryManager.getDeliveryByOrderId(o.getOrderId());

            String deliveryStatus = "Not Created";
            if (d != null) {
                deliveryStatus = d.getStatus();
            }

            orderModel.addRow(new Object[]{
                    o.getOrderId(),
                    o.getUserId(),
                    o.getTotalPrice(),
                    o.getStatus(),
                    deliveryStatus
            });
        }
    }

    // ---------------- ADMIN ----------------
    JPanel adminPanel() {
        JPanel p = new JPanel(new BorderLayout());

        JTable users = new JTable(userModel);
        JTable orders = new JTable(orderModel);
        JTable deliveries = new JTable(deliveryModel);
        JTable menuTable = new JTable(menuModel);

        JButton load = new JButton("Refresh");
        JButton addMenu = new JButton("Add Menu Item");
        JButton deleteMenu = new JButton("Delete Menu Item");
        JButton logout = new JButton("Logout");

        JPanel top = new JPanel();
        top.add(load);
        top.add(addMenu);
        top.add(deleteMenu);
        top.add(logout);

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Customers", new JScrollPane(users));
        tabs.add("Orders", new JScrollPane(orders));
        tabs.add("Deliveries", new JScrollPane(deliveries));
        tabs.add("Menu", new JScrollPane(menuTable));

        p.add(top, BorderLayout.NORTH);
        p.add(tabs, BorderLayout.CENTER);

        load.addActionListener(e -> {
            loadAdminData();
            loadMenu();
        });

        addMenu.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Item Name:");
            String priceText = JOptionPane.showInputDialog("Price:");

            if (name != null && priceText != null) {
                double price = Double.parseDouble(priceText);
                int id = menuManager.viewAllItemsReturn().size() + 1;

                menuManager.addMenuItem(id, name, price, "FAST", true, "Added by admin");
                loadMenu();
            }
        });

        deleteMenu.addActionListener(e -> {
            int row = menuTable.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select an item first!");
                return;
            }

            int id = Integer.parseInt(menuModel.getValueAt(row, 0).toString());
            menuManager.removeMenuItem(id);
            loadMenu();
        });

        logout.addActionListener(e -> {
            loginNameField.setText("");
            loginPassField.setText("");
            layout.show(main, "login");
        });

        return p;
    }

    void loadAdminData() {
        userModel.setRowCount(0);
        orderModel.setRowCount(0);
        deliveryModel.setRowCount(0);

        for (User u : userManager.getAllUsers()) {
            String role = (u instanceof Admin) ? "Admin" : "Customer";

            userModel.addRow(new Object[]{
                    u.getId(),
                    u.getName(),
                    u.getPhone(),
                    role
            });
        }

        for (Order o : orderManager.getAllOrders()) {
            Delivery d = deliveryManager.getDeliveryByOrderId(o.getOrderId());

            String deliveryStatus = "-";
            if (d != null) {
                deliveryStatus = d.getStatus();
            }

            orderModel.addRow(new Object[]{
                    o.getOrderId(),
                    o.getUserId(),
                    o.getTotalPrice(),
                    o.getStatus(),
                    deliveryStatus
            });
        }

        for (Delivery d : deliveryManager.getAllDeliveries()) {
            deliveryModel.addRow(new Object[]{
                    d.getDeliveryId(),
                    d.getOrderId(),
                    d.getCustomerId(),
                    d.getAddress(),
                    d.getStatus()
            });
        }
    }

    // ---------------- LOAD MENU ----------------
    void loadMenu() {
        menuModel.setRowCount(0);

        List<MenuItem> items = menuManager.viewAllItemsReturn();

        for (MenuItem i : items) {
            menuModel.addRow(new Object[]{i.getId(), i.getName(), i.getPrice()});
        }
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {
        SimpleUI ui = new SimpleUI();

        if (ui.menuManager.viewAllItemsReturn().isEmpty()) {
            ui.menuManager.addMenuItem(1, "Burger", 250, "FAST", true, "Test");
        }

        SwingUtilities.invokeLater(() -> ui.setVisible(true));
    }
}