package app;

import user.*;
import menu.*;
import menu.MenuItem;
import order.*;
import delivery.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SimpleUI extends JFrame {

    CardLayout layout = new CardLayout();
    JPanel main = new JPanel(layout);

    // ---------------- Managers ----------------
    UserManager userManager = new UserManager();
    MenuItemManager menuManager = new MenuItemManager();
    OrderManager orderManager = new OrderManager();
    DeliveryManager deliveryManager = new DeliveryManager();

    // ---------------- Logged User ----------------
    User loggedUser;

    // ---------------- Cart (temporary, not saved as order yet) ----------------
    ArrayList<String> cartItems = new ArrayList<>();
    ArrayList<Double> cartPrices = new ArrayList<>();
    double cartTotal = 0;

    // ---------------- Login Fields ----------------
    JTextField loginNameField;
    JPasswordField loginPassField;

    // ---------------- Track Labels ----------------
    JLabel statusLabel;
    JLabel totalLabel;
    JLabel addressLabel;
    JLabel deliveryLabel;
    JLabel timeLabel;

    // ---------------- Time Format ----------------
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd MMM yyyy | hh:mm a");

    // ---------------- Table Models ----------------
    DefaultTableModel userModel =
            new DefaultTableModel(new String[]{"ID", "Name", "Phone", "Address", "Role"}, 0);

    DefaultTableModel orderModel =
            new DefaultTableModel(new String[]{"Order ID", "User ID", "Total", "Order Date", "Order Status", "Delivery Status"}, 0);

    DefaultTableModel deliveryModel =
            new DefaultTableModel(new String[]{"Delivery ID", "Order ID", "Customer Name", "Phone", "Address", "Status", "Time"}, 0);

    DefaultTableModel menuModel =
            new DefaultTableModel(new String[]{"ID", "Name", "Price"}, 0);

    DefaultTableModel cartModel =
            new DefaultTableModel(new String[]{"Name", "Price"}, 0);

    DefaultTableModel trackModel =
            new DefaultTableModel(new String[]{"Item", "Price"}, 0);

    public SimpleUI() {
        setTitle("Simple Delivery App");
        setSize(1100, 650);
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

    // ---------------- LOGIN PANEL ----------------
    JPanel loginPanel() {
        JPanel wrapper = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Food Delivery System", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 10, 0));

        JPanel centerWrap = new JPanel(new GridBagLayout());

        JPanel form = new JPanel(new GridLayout(8, 1, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        loginNameField = new JTextField();
        loginPassField = new JPasswordField();
        JComboBox<String> roleBox = new JComboBox<>(new String[]{"Customer", "Admin"});
        JCheckBox showPass = new JCheckBox("Show Password");

        char defaultEcho = loginPassField.getEchoChar();

        showPass.addActionListener(e -> {
            if (showPass.isSelected()) loginPassField.setEchoChar((char) 0);
            else loginPassField.setEchoChar(defaultEcho);
        });

        JButton login = new JButton("Login");
        JButton register = new JButton("Register");

        login.setPreferredSize(new Dimension(220, 45));
        register.setPreferredSize(new Dimension(220, 45));

        form.add(new JLabel("Name"));
        form.add(loginNameField);
        form.add(new JLabel("Password"));
        form.add(loginPassField);
        form.add(showPass);
        form.add(new JLabel("Role"));
        form.add(roleBox);

        JPanel buttons = new JPanel(new GridLayout(2, 1, 12, 12));
        buttons.add(login);
        buttons.add(register);

        JPanel box = new JPanel(new BorderLayout(15, 15));
        box.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        box.add(form, BorderLayout.CENTER);
        box.add(buttons, BorderLayout.SOUTH);

        centerWrap.add(box);

        wrapper.add(title, BorderLayout.NORTH);
        wrapper.add(centerWrap, BorderLayout.CENTER);

        login.addActionListener(e -> {
            String selectedRole = roleBox.getSelectedItem().toString();

            loggedUser = userManager.login(loginNameField.getText(), new String(loginPassField.getPassword()));

            if (loggedUser == null) {
                JOptionPane.showMessageDialog(this, "Invalid login!");
                return;
            }

            if (selectedRole.equals("Admin") && loggedUser instanceof Admin) {
                loadAdminData();
                layout.show(main, "admin");
            } else if (selectedRole.equals("Customer") && loggedUser instanceof Customer) {
                cartItems.clear();
                cartPrices.clear();
                cartTotal = 0;
                cartModel.setRowCount(0);
                layout.show(main, "customer");
            } else {
                JOptionPane.showMessageDialog(this, "Wrong role selected!");
            }
        });

        register.addActionListener(e -> layout.show(main, "register"));
        return wrapper;
    }

    // ---------------- REGISTER PANEL ----------------
    JPanel registerPanel() {
        JPanel p = new JPanel(new GridLayout(7, 2, 10, 10));
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

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

            if (role.equals("Admin")) userManager.addAdmin(name.getText(), pass.getText(), phone.getText(), address.getText());
            else userManager.addCustomer(name.getText(), pass.getText(), phone.getText(), address.getText());

            JOptionPane.showMessageDialog(this, role + " Registered!");

            name.setText("");
            pass.setText("");
            phone.setText("");
            address.setText("");
            roleBox.setSelectedIndex(0);

            loginNameField.setText("");
            loginPassField.setText("");

            layout.show(main, "login");
        });

        back.addActionListener(e -> layout.show(main, "login"));
        return p;
    }

    // ---------------- CUSTOMER PANEL ----------------
    JPanel customerPanel() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTable menuTable = new JTable(menuModel);
        JTable cartTable = new JTable(cartModel);

        JButton add = new JButton("Add");
        JButton remove = new JButton("Remove");
        JButton checkout = new JButton("Checkout");
        JButton history = new JButton("My Orders");
        JButton logout = new JButton("Logout");

        add.setPreferredSize(new Dimension(105, 48));
        remove.setPreferredSize(new Dimension(105, 48));
        checkout.setPreferredSize(new Dimension(220, 48));

        add.setFont(new Font("Arial", Font.BOLD, 15));
        remove.setFont(new Font("Arial", Font.BOLD, 15));
        checkout.setFont(new Font("Arial", Font.BOLD, 15));

        JPanel left = new JPanel(new BorderLayout(5, 5));
        JLabel menuTitle = new JLabel("Menu", SwingConstants.CENTER);
        menuTitle.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel menuButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        menuButtons.add(add);
        menuButtons.add(remove);

        left.add(menuTitle, BorderLayout.NORTH);
        left.add(new JScrollPane(menuTable), BorderLayout.CENTER);
        left.add(menuButtons, BorderLayout.SOUTH);

        JPanel right = new JPanel(new BorderLayout(5, 5));
        JLabel cartTitle = new JLabel("Cart", SwingConstants.CENTER);
        cartTitle.setFont(new Font("Arial", Font.BOLD, 16));
        right.add(cartTitle, BorderLayout.NORTH);
        right.add(new JScrollPane(cartTable), BorderLayout.CENTER);
        right.add(checkout, BorderLayout.SOUTH);

        JPanel center = new JPanel(new GridLayout(1, 2, 15, 15));
        center.add(left);
        center.add(right);

        JPanel bottom = new JPanel();
        bottom.add(history);
        bottom.add(logout);

        JLabel title = new JLabel("Customer Panel", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        p.add(title, BorderLayout.NORTH);
        p.add(center, BorderLayout.CENTER);
        p.add(bottom, BorderLayout.SOUTH);

        add.addActionListener(e -> {
            int r = menuTable.getSelectedRow();
            if (r != -1) {
                String name = menuModel.getValueAt(r, 1).toString();
                double price = Double.parseDouble(menuModel.getValueAt(r, 2).toString());

                cartItems.add(name);
                cartPrices.add(price);
                cartTotal += price;
                cartModel.addRow(new Object[]{name, price});
            }
        });

        remove.addActionListener(e -> {
            int r = cartTable.getSelectedRow();
            if (r != -1) {
                cartTotal -= Double.parseDouble(cartModel.getValueAt(r, 1).toString());
                cartItems.remove(r);
                cartPrices.remove(r);
                cartModel.removeRow(r);
            }
        });

        checkout.addActionListener(e -> {
            if (cartItems.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Cart is empty!");
                return;
            }

            String latestAddress = userManager.getUserById(loggedUser.getId()).getAddress();

            Order placedOrder = orderManager.createOrder(loggedUser.getId(), latestAddress, cartItems, cartTotal);
            deliveryManager.createDelivery(placedOrder.getOrderId(), loggedUser.getId(), latestAddress);

            loadTrackOrder(placedOrder.getOrderId());

            cartItems.clear();
            cartPrices.clear();
            cartTotal = 0;
            cartModel.setRowCount(0);

            layout.show(main, "track");
        });

        history.addActionListener(e -> {
            loadCustomerHistory();
            layout.show(main, "history");
        });

        logout.addActionListener(e -> {
            cartItems.clear();
            cartPrices.clear();
            cartTotal = 0;
            cartModel.setRowCount(0);
            loginNameField.setText("");
            loginPassField.setText("");
            layout.show(main, "login");
        });

        return p;
    }

    // ---------------- TRACK PANEL ----------------
    JPanel trackPanel() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Current Order", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JTable table = new JTable(trackModel);
        table.setRowHeight(22);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(400, 170));

        statusLabel = new JLabel("• Order Status: Order Placed");
        totalLabel = new JLabel("• Total Cost: 0");
        addressLabel = new JLabel("• Address: ");
        deliveryLabel = new JLabel("• Delivery: Pending");
        timeLabel = new JLabel("• Delivery Time: -");

        Font normalInfo = new Font("Arial", Font.BOLD, 12);
        Font totalInfo = new Font("Arial", Font.BOLD, 15);

        statusLabel.setFont(normalInfo);
        addressLabel.setFont(normalInfo);
        deliveryLabel.setFont(normalInfo);
        timeLabel.setFont(normalInfo);
        totalLabel.setFont(totalInfo);

        JPanel info = new JPanel(new GridLayout(5, 1, 1, 1));
        info.add(statusLabel);
        info.add(addressLabel);
        info.add(deliveryLabel);
        info.add(timeLabel);
        info.add(totalLabel);

        JButton back = new JButton("Back to Menu");

        JPanel center = new JPanel(new BorderLayout(4, 4));
        center.add(scroll, BorderLayout.NORTH);
        center.add(info, BorderLayout.CENTER);

        p.add(title, BorderLayout.NORTH);
        p.add(center, BorderLayout.CENTER);
        p.add(back, BorderLayout.SOUTH);

        back.addActionListener(e -> layout.show(main, "customer"));
        return p;
    }

    void loadTrackOrder(int orderId) {
        trackModel.setRowCount(0);

        Order o = orderManager.getOrder(orderId);
        Delivery d = deliveryManager.getDeliveryByOrderId(orderId);

        double runningTotal = 0;

        for (int i = 0; i < o.getItemNames().size(); i++) {
            String item = o.getItemNames().get(i);
            double price = (i < cartPrices.size()) ? cartPrices.get(i) : 0;
            runningTotal += price;
            trackModel.addRow(new Object[]{item, price});
        }

        trackModel.addRow(new Object[]{"TOTAL", runningTotal});

        statusLabel.setText("• Order Status: " + o.getStatus());

        if (d != null) {
            addressLabel.setText("• Address: " + d.getAddress());
            deliveryLabel.setText("• Delivery: " + d.getStatus());
            timeLabel.setText("• Delivery Time: " + d.getDeliveryDateTime().format(timeFormat));
        }

        totalLabel.setText("• Total Cost: " + o.getTotalPrice());
    }

    // ---------------- HISTORY PANEL ----------------
    JPanel historyPanel() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

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
            String deliveryStatus = (d != null) ? d.getStatus() : "Not Created";

            orderModel.addRow(new Object[]{
                    o.getOrderId(), o.getUserId(), o.getTotalPrice(),
                    o.getOrderDate(), o.getStatus(), deliveryStatus
            });
        }
    }

    // ---------------- ADMIN PANEL ----------------
    JPanel adminPanel() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTable users = new JTable(userModel);
        JTable orders = new JTable(orderModel);
        JTable deliveries = new JTable(deliveryModel);
        JTable menuTable = new JTable(menuModel);

        JButton refresh = new JButton("Refresh");
        JButton addMenu = new JButton("Add Menu Item");
        JButton deleteMenu = new JButton("Delete Menu Item");
        JButton updatePrice = new JButton("Update Price");
        JButton logout = new JButton("Logout");

        refresh.setPreferredSize(new Dimension(120, 35));
        addMenu.setPreferredSize(new Dimension(150, 35));
        deleteMenu.setPreferredSize(new Dimension(150, 35));
        updatePrice.setPreferredSize(new Dimension(150, 35));
        logout.setPreferredSize(new Dimension(120, 35));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        top.add(refresh);
        top.add(logout);

        JPanel menuButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        menuButtons.add(addMenu);
        menuButtons.add(deleteMenu);
        menuButtons.add(updatePrice);
        menuButtons.setVisible(false);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Arial", Font.BOLD, 14));

        tabs.add("Users", new JScrollPane(users));
        tabs.add("Orders", new JScrollPane(orders));
        tabs.add("Deliveries", new JScrollPane(deliveries));
        tabs.add("Menu", new JScrollPane(menuTable));

        tabs.addChangeListener(e -> {
            int index = tabs.getSelectedIndex();
            String titleText = tabs.getTitleAt(index);
            menuButtons.setVisible(titleText.equals("Menu"));
        });

        JPanel north = new JPanel(new BorderLayout());
        north.add(top, BorderLayout.NORTH);
        north.add(menuButtons, BorderLayout.SOUTH);

        p.add(north, BorderLayout.NORTH);
        p.add(tabs, BorderLayout.CENTER);

        refresh.addActionListener(e -> {
            loadAdminData();
            loadMenu();
        });

        addMenu.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Item Name:");
            String priceText = JOptionPane.showInputDialog("Price:");

            if (name != null && priceText != null) {
                try {
                    double price = Double.parseDouble(priceText);
                    int id = menuManager.getNextMenuId();
                    menuManager.addMenuItem(id, name, price, true);
                    loadMenu();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid price!");
                }
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

        updatePrice.addActionListener(e -> {
            int row = menuTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select an item first!");
                return;
            }

            int id = Integer.parseInt(menuModel.getValueAt(row, 0).toString());
            String priceText = JOptionPane.showInputDialog("New Price:");

            try {
                double newPrice = Double.parseDouble(priceText);
                menuManager.updatePrice(id, newPrice);
                loadMenu();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid price!");
            }
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
            userModel.addRow(new Object[]{u.getId(), u.getName(), u.getPhone(), u.getAddress(), role});
        }

        for (Order o : orderManager.getAllOrders()) {
            Delivery d = deliveryManager.getDeliveryByOrderId(o.getOrderId());
            String deliveryStatus = (d != null) ? d.getStatus() : "-";

            orderModel.addRow(new Object[]{
                    o.getOrderId(), o.getUserId(), o.getTotalPrice(),
                    o.getOrderDate(), o.getStatus(), deliveryStatus
            });
        }

        for (Delivery d : deliveryManager.getAllDeliveries()) {
            User customer = userManager.getUserById(d.getCustomerId());

            deliveryModel.addRow(new Object[]{
                    d.getDeliveryId(),
                    d.getOrderId(),
                    customer != null ? customer.getName() : "Unknown",
                    customer != null ? customer.getPhone() : "Unknown",
                    d.getAddress(),
                    d.getStatus(),
                    d.getDeliveryDateTime().format(timeFormat)
            });
        }
    }

    void loadMenu() {
        menuModel.setRowCount(0);
        List<MenuItem> items = menuManager.viewAllItemsReturn();

        for (MenuItem i : items) {
            menuModel.addRow(new Object[]{i.getId(), i.getName(), i.getPrice()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimpleUI().setVisible(true));
    }
}