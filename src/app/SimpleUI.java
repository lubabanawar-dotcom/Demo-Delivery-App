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

    User loggedUser;
    Order currentOrder;

    DefaultTableModel menuModel = new DefaultTableModel(new String[]{"Name", "Price"}, 0);
    DefaultTableModel cartModel = new DefaultTableModel(new String[]{"Name", "Price"}, 0);

    public SimpleUI() {
        setTitle("Simple Delivery App");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        main.add(loginPanel(), "login");
        main.add(registerPanel(), "register");
        main.add(customerPanel(), "customer");

        add(main);
        layout.show(main, "login");

        loadMenu();
    }

    // ---------------- LOGIN ----------------
    JPanel loginPanel() {
        JPanel p = new JPanel(new GridLayout(5, 2, 10, 10));

        JTextField name = new JTextField();
        JPasswordField pass = new JPasswordField();

        JButton login = new JButton("Login");
        JButton register = new JButton("Register");

        p.add(new JLabel("Name"));
        p.add(name);
        p.add(new JLabel("Password"));
        p.add(pass);
        p.add(login);
        p.add(register);

        login.addActionListener(e -> {
            loggedUser = userManager.login(name.getText(), new String(pass.getPassword()));

            if (loggedUser != null) {
                currentOrder = orderManager.createOrder(loggedUser.getId());
                layout.show(main, "customer");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid login");
            }
        });

        register.addActionListener(e -> layout.show(main, "register"));

        return p;
    }

    // ---------------- REGISTER ----------------
    JPanel registerPanel() {
        JPanel p = new JPanel(new GridLayout(5, 2, 10, 10));

        JTextField name = new JTextField();
        JTextField pass = new JTextField();
        JTextField phone = new JTextField();

        JButton save = new JButton("Register");
        JButton back = new JButton("Back");

        p.add(new JLabel("Name"));
        p.add(name);
        p.add(new JLabel("Password"));
        p.add(pass);
        p.add(new JLabel("Phone"));
        p.add(phone);
        p.add(save);
        p.add(back);

        save.addActionListener(e -> {
        	if (name.getText().isEmpty() || pass.getText().isEmpty() || phone.getText().isEmpty()) {
        	    JOptionPane.showMessageDialog(this, "All fields required!");
        	} else {
        	    userManager.addCustomer(name.getText(), pass.getText(), phone.getText());
        	    JOptionPane.showMessageDialog(this, "User Registered!");
        	}
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
        JButton logout = new JButton("Logout");

        JPanel bottom = new JPanel();
        bottom.add(add);
        bottom.add(remove);
        bottom.add(checkout);
        bottom.add(logout);

        p.add(new JLabel("Customer Panel", SwingConstants.CENTER), BorderLayout.NORTH);
        p.add(new JScrollPane(menuTable), BorderLayout.WEST);
        p.add(new JScrollPane(cartTable), BorderLayout.CENTER);
        p.add(bottom, BorderLayout.SOUTH);

        // ADD ITEM
        add.addActionListener(e -> {
            int r = menuTable.getSelectedRow();
            if (r != -1) {
                String name = menuModel.getValueAt(r, 0).toString();
                double price = Double.parseDouble(menuModel.getValueAt(r, 1).toString());

                cartModel.addRow(new Object[]{name, price});
                orderManager.addItem(currentOrder.getOrderId(), name, price);
            }
        });

        // REMOVE ITEM
        remove.addActionListener(e -> {
            int r = cartTable.getSelectedRow();
            if (r != -1) {
                String name = cartModel.getValueAt(r, 0).toString();
                double price = Double.parseDouble(cartModel.getValueAt(r, 1).toString());

                cartModel.removeRow(r);
                orderManager.removeItem(currentOrder.getOrderId(), name, price);
            }
        });

        // CHECKOUT
        checkout.addActionListener(e -> {
            Order o = orderManager.getOrder(currentOrder.getOrderId());

            JOptionPane.showMessageDialog(this, "Total: " + o.getTotalPrice());

            // create delivery
            deliveryManager.createDelivery(o.getOrderId(), loggedUser.getId(), "Default Address");

            cartModel.setRowCount(0);
            currentOrder = orderManager.createOrder(loggedUser.getId());
        });

        // LOGOUT
        logout.addActionListener(e -> {
            cartModel.setRowCount(0);
            layout.show(main, "login");
        });

        return p;
    }

    // ---------------- LOAD MENU ----------------
    void loadMenu() {
        List<MenuItem> items = menuManager.viewAllItemsReturn();

        for (MenuItem i : items) {
            menuModel.addRow(new Object[]{i.getName(), i.getPrice()});
        }
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {
    	  SimpleUI ui = new SimpleUI();

    	    ui.menuManager.addMenuItem(1, "Burger", 250, "FAST", true, "Test");

    	    SwingUtilities.invokeLater(() -> ui.setVisible(true));
    }
}