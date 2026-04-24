package app;

import javax.swing.*;
import java.awt.*;

import user.*;
import order.*;
import menu.*;
import delivery.*;

public class SimpleDeliveryUI extends JFrame {

    // Managers (package-accessible so child panels can use them)
    UserManager     userMgr  = new UserManager();
    OrderManager    orderMgr = new OrderManager();
    MenuItemManager menuMgr  = new MenuItemManager();
    DeliveryManager delMgr   = new DeliveryManager();

    // Current session state
    User   currentUser;
    String selectedRole;

    // Top-level card layout
    private CardLayout cl        = new CardLayout();
    private JPanel     mainPanel = new JPanel(cl);

    // Panels (created once, reused)
    private WelcomePanel  welcomePanel;
    private LoginPanel    loginPanel;
    private CustomerDash  customerDash;
    private ManagerDash   managerDash;
    private DeliveryDash  deliveryDash;

    public SimpleDeliveryUI() {
        setTitle("🍔 QuickBite Delivery");
        setSize(1100, 680);
        setMinimumSize(new Dimension(900, 580));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        seedUsers();
        seedMenu();
        buildUI();

        add(mainPanel);
        cl.show(mainPanel, "welcome");
    }

    private void seedUsers() {
        if (!userMgr.getAllUsers().isEmpty()) return;
        userMgr.addAdmin("admin", "admin123", "000-0000");
        userMgr.addCustomer("customer", "cust123", "111-1111");
    }

    private void seedMenu() {
        if (!menuMgr.getAllMenuItems().isEmpty()) return;
        menuMgr.addMenuItem("Classic Burger",     8.99,  "MAIN_COURSE", true, "Juicy beef patty with lettuce");
        menuMgr.addMenuItem("Margherita Pizza",   12.99, "MAIN_COURSE", true, "Classic tomato & mozzarella");
        menuMgr.addMenuItem("Spring Rolls",        4.99, "APPETIZER",   true, "Crispy vegetable rolls");
        menuMgr.addMenuItem("Chocolate Lava Cake", 5.99, "DESSERT",     true, "Warm chocolate cake");
        menuMgr.addMenuItem("Coca-Cola",            2.49, "DRINKS",     true, "Chilled soda");
        menuMgr.addMenuItem("Garlic Bread",         3.99, "SIDES",      true, "Toasted with garlic butter");
        menuMgr.addMenuItem("Caesar Salad",         7.49, "SIDES",      true, "Crispy romaine with dressing");
        menuMgr.addMenuItem("Family Combo",        24.99, "COMBO_MEAL", true, "2 burgers + 2 drinks + fries");
    }

    private void buildUI() {
        welcomePanel = new WelcomePanel(this);
        loginPanel   = new LoginPanel(this);
        customerDash = new CustomerDash(this);
        managerDash  = new ManagerDash(this);
        deliveryDash = new DeliveryDash(this);

        mainPanel.add(welcomePanel, "welcome");
        mainPanel.add(loginPanel,   "login");
        mainPanel.add(customerDash, "customer");
        mainPanel.add(managerDash,  "manager");
        mainPanel.add(deliveryDash, "delivery");
    }

    /** Called by WelcomePanel when a role card is clicked. */
    void showLogin(String roleKey) {
        selectedRole = roleKey;
        loginPanel.setRole(roleKey);
        cl.show(mainPanel, "login");
    }

    /** Called by LoginPanel on back button. */
    void showWelcome() {
        cl.show(mainPanel, "welcome");
    }

    /** Called by LoginPanel after successful authentication. */
    void onLoginSuccess(User u) {
        currentUser = u;
        if ("Delivery".equals(selectedRole)) {
            deliveryDash.onShow();
            cl.show(mainPanel, "delivery");
        } else if (u instanceof Customer) {
            customerDash.onShow();
            cl.show(mainPanel, "customer");
        } else if (u instanceof Admin) {
            managerDash.onShow();
            cl.show(mainPanel, "manager");
        } else {
            // Fallback: treat as customer
            customerDash.onShow();
            cl.show(mainPanel, "customer");
        }
    }

    /** Called by any dashboard's Logout button. */
    void logout() {
        currentUser = null;
        selectedRole = null;
        cl.show(mainPanel, "welcome");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimpleDeliveryUI().setVisible(true));
    }
}
