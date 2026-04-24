package app;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;

import menu.MenuItem;
import order.Order;
import static app.UIHelper.*;

class CustomerDash extends JPanel {

    private SimpleDeliveryUI frame;

    private CardLayout   innerCL    = new CardLayout();
    private JPanel       innerCards = new JPanel(innerCL);

    private DefaultTableModel menuModel  = new DefaultTableModel(
        new String[]{"ID","Name","Category","Price","Available"}, 0) {
        public boolean isCellEditable(int r,int c){return false;}
    };
    private DefaultTableModel cartModel  = new DefaultTableModel(
        new String[]{"Item","Price"}, 0) {
        public boolean isCellEditable(int r,int c){return false;}
    };
    private DefaultTableModel ordModel   = new DefaultTableModel(
        new String[]{"Order ID","Items","Total","Status","Date"}, 0) {
        public boolean isCellEditable(int r,int c){return false;}
    };

    private ArrayList<Object[]> cart = new ArrayList<>(); // {String name, double price}
    private JLabel cartTotalLbl = new JLabel("Total: $0.00");

    CustomerDash(SimpleDeliveryUI frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(C_BG);

        String[][] nav = {
            {"🏠","Home","home"},
            {"🍽️","Browse Menu","browse"},
            {"🛒","My Cart","cart"},
            {"📦","My Orders","orders"}
        };
        add(sidebar(nav, innerCL, innerCards, e -> {
            cart.clear();
            frame.logout();
        }), BorderLayout.WEST);

        innerCards.setBackground(C_BG);
        innerCards.add(buildHome(),    "home");
        innerCards.add(buildBrowse(),  "browse");
        innerCards.add(buildCart(),    "cart");
        innerCards.add(buildOrders(),  "orders");
        add(innerCards, BorderLayout.CENTER);
    }

    void onShow() {
        cart.clear();
        updateCartTable();
        refreshMenu();
        refreshOrders();
        innerCL.show(innerCards, "browse");
    }

    // ---- HOME ----
    private JPanel buildHome() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(C_BG);
        GridBagConstraints g = new GridBagConstraints();
        g.gridx=0; g.gridy=0; g.insets=new Insets(10,0,10,0);
        JLabel h = new JLabel("Welcome to QuickBite! 🍔");
        h.setFont(new Font("Segoe UI", Font.BOLD, 28));
        h.setForeground(new Color(0x1e2a38));
        JLabel s = new JLabel("Browse our delicious menu and place your order.");
        s.setFont(F_MAIN); s.setForeground(Color.GRAY);
        p.add(h,g); g.gridy++; p.add(s,g);
        return p;
    }

    // ---- BROWSE MENU ----
    private JPanel buildBrowse() {
        JTable menuTbl = styledTable(menuModel);

        String[] cats = {"All","APPETIZER","MAIN_COURSE","DESSERT","DRINKS","SIDES","COMBO_MEAL"};
        JComboBox<String> catBox = new JComboBox<>(cats);
        catBox.setFont(F_MAIN);
        catBox.addActionListener(e -> {
            String s = (String) catBox.getSelectedItem();
            populateMenu("All".equals(s) ? null : s);
        });

        JButton addCartBtn = btn("➕ Add to Cart", C_PRIMARY);
        addCartBtn.addActionListener(e -> {
            int row = menuTbl.getSelectedRow();
            if (row < 0) { err(this,"Select a menu item first."); return; }
            int id = (int) menuModel.getValueAt(row, 0);
            MenuItem item = frame.menuMgr.getItemById(id);
            if (item == null || !item.isAvailable()) { err(this,"Item is not available."); return; }
            cart.add(new Object[]{item.getName(), item.getPrice()});
            updateCartTable();
            JOptionPane.showMessageDialog(frame, "\"" + item.getName() + "\" added to cart!",
                "Cart", JOptionPane.INFORMATION_MESSAGE);
        });

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        topBar.setBackground(C_BG);
        topBar.add(lbl("Category:")); topBar.add(catBox); topBar.add(addCartBtn);

        return contentPanel("🍽️  Browse Menu", topBar, menuTbl);
    }

    // ---- CART ----
    private JPanel buildCart() {
        JTable cartTbl = styledTable(cartModel);
        cartTotalLbl.setFont(new Font("Segoe UI", Font.BOLD, 15));
        cartTotalLbl.setForeground(C_PRIMARY);

        JButton removeBtn = btn("🗑 Remove Item", C_DANGER);
        removeBtn.addActionListener(e -> {
            int row = cartTbl.getSelectedRow();
            if (row < 0) { err(this,"Select an item to remove."); return; }
            cart.remove(row);
            updateCartTable();
        });

        JButton placeBtn = btn("✅ Place Order", C_PRIMARY);
        placeBtn.addActionListener(e -> {
            if (cart.isEmpty()) { err(this,"Your cart is empty!"); return; }

            // Ask for delivery address
            String address = JOptionPane.showInputDialog(frame,
                "Enter delivery address:", "Delivery Address",
                JOptionPane.QUESTION_MESSAGE);
            if (address == null || address.trim().isEmpty()) {
                err(this, "Delivery address is required."); return;
            }

            ArrayList<String> names = new ArrayList<>();
            double total = 0;
            for (Object[] r : cart) { names.add((String)r[0]); total += (double)r[1]; }

            // Place the order
            frame.orderMgr.placeOrder(frame.currentUser.getId(), total, names);

            // Get the newly created order's ID (last order for this user)
            java.util.ArrayList<order.Order> userOrders =
                frame.orderMgr.getOrdersByUserId(frame.currentUser.getId());
            int newOrderId = userOrders.isEmpty() ? 1 :
                userOrders.get(userOrders.size() - 1).getOrderId();

            // Create a delivery record assigned to a delivery person
            frame.delMgr.createDelivery(newOrderId, frame.currentUser.getId(), address.trim());

            cart.clear();
            updateCartTable();
            refreshOrders();
            JOptionPane.showMessageDialog(frame,
                "Order placed! A delivery person has been assigned.",
                "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        bottom.setBackground(C_BG);
        bottom.add(cartTotalLbl); bottom.add(removeBtn); bottom.add(placeBtn);

        JPanel p = contentPanel("🛒  My Cart", null, cartTbl);
        p.add(bottom, BorderLayout.SOUTH);
        return p;
    }

    // ---- MY ORDERS ----
    private JPanel buildOrders() {
        JTable ordTbl = styledTable(ordModel);
        JButton refBtn = btn("🔄 Refresh", new Color(0x1565c0));
        refBtn.addActionListener(e -> refreshOrders());
        JPanel top = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        top.setBackground(C_BG); top.add(refBtn);
        return contentPanel("📦  My Orders", top, ordTbl);
    }

    // ---- Data helpers ----
    void refreshMenu() { populateMenu(null); }

    private void populateMenu(String cat) {
        menuModel.setRowCount(0);
        for (MenuItem i : frame.menuMgr.getAllMenuItems()) {
            if (cat == null || i.getCategory().equals(cat)) {
                menuModel.addRow(new Object[]{
                    i.getId(), i.getName(), i.getCategory(),
                    String.format("$%.2f", i.getPrice()),
                    i.isAvailable() ? "✅ Yes" : "❌ No"
                });
            }
        }
    }

    private void updateCartTable() {
        cartModel.setRowCount(0);
        double total = 0;
        for (Object[] r : cart) {
            cartModel.addRow(new Object[]{r[0], String.format("$%.2f", r[1])});
            total += (double) r[1];
        }
        cartTotalLbl.setText(String.format("Total: $%.2f", total));
    }

    void refreshOrders() {
        ordModel.setRowCount(0);
        if (frame.currentUser == null) return;
        for (Order o : frame.orderMgr.getOrdersByUserId(frame.currentUser.getId())) {
            ordModel.addRow(new Object[]{
                o.getOrderId(), o.getItemNames(),
                String.format("$%.2f", o.getTotalPrice()),
                o.getStatus(), o.getOrderDate()
            });
        }
    }
}
