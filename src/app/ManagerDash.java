package app;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;

import menu.MenuItem;
import order.Order;
import user.User;
import user.Customer;
import user.Admin;
import static app.UIHelper.*;

class ManagerDash extends JPanel {

    private SimpleDeliveryUI frame;
    private CardLayout innerCL    = new CardLayout();
    private JPanel     innerCards = new JPanel(innerCL);

    // Menu Management models & fields
    private DefaultTableModel mgrMenuModel = new DefaultTableModel(
        new String[]{"ID","Name","Category","Price","Avail","Description"}, 0) {
        public boolean isCellEditable(int r,int c){return false;}
    };
    private JTextField mgrName  = new JTextField(10);
    private JTextField mgrPrice = new JTextField(6);
    private JTextField mgrDesc  = new JTextField(14);
    private JComboBox<String> mgrCat = new JComboBox<>(
        new String[]{"APPETIZER","MAIN_COURSE","DESSERT","DRINKS","SIDES","COMBO_MEAL"});
    private JCheckBox mgrAvail = new JCheckBox("Available", true);

    // All Orders model & status combo
    private DefaultTableModel mgrOrdModel = new DefaultTableModel(
        new String[]{"Order ID","User ID","Items","Total","Status","Date"}, 0) {
        public boolean isCellEditable(int r,int c){return false;}
    };
    private JComboBox<String> statusCombo =
        new JComboBox<>(new String[]{"Pending","Confirmed","Delivered"});

    // User Management model
    private DefaultTableModel mgrUsrModel = new DefaultTableModel(
        new String[]{"ID","Name","Phone","Role"}, 0) {
        public boolean isCellEditable(int r,int c){return false;}
    };

    ManagerDash(SimpleDeliveryUI frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(C_BG);

        String[][] nav = {
            {"📋","Menu Management","menu"},
            {"📦","All Orders","orders"},
            {"👥","User Management","users"}
        };
        add(sidebar(nav, innerCL, innerCards, e -> frame.logout()), BorderLayout.WEST);

        innerCards.setBackground(C_BG);
        innerCards.add(buildMenuMgmt(),  "menu");
        innerCards.add(buildAllOrders(), "orders");
        innerCards.add(buildUserMgmt(),  "users");
        add(innerCards, BorderLayout.CENTER);
    }

    void onShow() {
        refreshMenuTable();
        refreshOrdTable();
        refreshUsrTable();
        innerCL.show(innerCards, "menu");
    }

    // ---- MENU MANAGEMENT ----
    private JPanel buildMenuMgmt() {
        JTable t = styledTable(mgrMenuModel);
        t.getSelectionModel().addListSelectionListener(e -> {
            int row = t.getSelectedRow();
            if (row >= 0) fillMenuForm(row);
        });

        styleField(mgrName); styleField(mgrPrice); styleField(mgrDesc);
        mgrCat.setFont(F_MAIN);
        mgrAvail.setBackground(C_BG); mgrAvail.setFont(F_MAIN);

        JButton addBtn = btn("➕ Add",    C_PRIMARY);
        JButton updBtn = btn("✏️ Update", new Color(0x1565c0));
        JButton delBtn = btn("🗑 Delete", C_DANGER);
        JButton clrBtn = btn("✖ Clear",  new Color(0x607d8b));

        addBtn.addActionListener(e -> {
            try {
                String name = mgrName.getText().trim();
                if (name.isEmpty()) { err(this,"Name is required."); return; }
                double price = Double.parseDouble(mgrPrice.getText().trim());
                frame.menuMgr.addMenuItem(name, price,
                    (String)mgrCat.getSelectedItem(), mgrAvail.isSelected(), mgrDesc.getText().trim());
                refreshMenuTable(); clearMenuForm();
            } catch (NumberFormatException ex) { err(this,"Invalid price."); }
        });

        updBtn.addActionListener(e -> {
            int row = t.getSelectedRow();
            if (row < 0) { err(this,"Select an item."); return; }
            try {
                int id = (int) mgrMenuModel.getValueAt(row, 0);
                double price = Double.parseDouble(mgrPrice.getText().trim());
                frame.menuMgr.updateMenuItem(id, mgrName.getText().trim(), price,
                    (String)mgrCat.getSelectedItem(), mgrAvail.isSelected(), mgrDesc.getText().trim());
                refreshMenuTable(); clearMenuForm();
            } catch (NumberFormatException ex) { err(this,"Invalid price."); }
        });

        delBtn.addActionListener(e -> {
            int row = t.getSelectedRow();
            if (row < 0) { err(this,"Select an item."); return; }
            int id = (int) mgrMenuModel.getValueAt(row, 0);
            frame.menuMgr.deleteMenuItem(id);
            refreshMenuTable(); clearMenuForm();
        });

        clrBtn.addActionListener(e -> clearMenuForm());

        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        form.setBackground(C_BG);
        form.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0xdde0e5)), "Item Details"));
        form.add(lbl("Name:")); form.add(mgrName);
        form.add(lbl("Price:")); form.add(mgrPrice);
        form.add(lbl("Category:")); form.add(mgrCat);
        form.add(lbl("Desc:")); form.add(mgrDesc);
        form.add(mgrAvail);
        form.add(addBtn); form.add(updBtn); form.add(delBtn); form.add(clrBtn);

        JPanel p = contentPanel("📋  Menu Management", null, t);
        p.add(form, BorderLayout.SOUTH);
        return p;
    }

    private void fillMenuForm(int row) {
        mgrName.setText((String) mgrMenuModel.getValueAt(row, 1));
        mgrCat.setSelectedItem(mgrMenuModel.getValueAt(row, 2));
        String priceStr = ((String) mgrMenuModel.getValueAt(row, 3)).replace("$","");
        mgrPrice.setText(priceStr);
        mgrAvail.setSelected("✅ Yes".equals(mgrMenuModel.getValueAt(row, 4)));
        mgrDesc.setText((String) mgrMenuModel.getValueAt(row, 5));
    }

    private void clearMenuForm() {
        mgrName.setText(""); mgrPrice.setText(""); mgrDesc.setText("");
        mgrCat.setSelectedIndex(0); mgrAvail.setSelected(true);
    }

    void refreshMenuTable() {
        mgrMenuModel.setRowCount(0);
        for (MenuItem i : frame.menuMgr.getAllMenuItems()) {
            mgrMenuModel.addRow(new Object[]{
                i.getId(), i.getName(), i.getCategory(),
                String.format("$%.2f", i.getPrice()),
                i.isAvailable() ? "✅ Yes" : "❌ No",
                i.getDescription()
            });
        }
    }

    // ---- ALL ORDERS ----
    private JPanel buildAllOrders() {
        JTable t = styledTable(mgrOrdModel);
        statusCombo.setFont(F_MAIN);
        JButton updBtn  = btn("✏️ Update Status", new Color(0x1565c0));
        JButton refBtn  = btn("🔄 Refresh", new Color(0x607d8b));

        updBtn.addActionListener(e -> {
            int row = t.getSelectedRow();
            if (row < 0) { err(this,"Select an order."); return; }
            int id = (int) mgrOrdModel.getValueAt(row, 0);
            String ns = (String) statusCombo.getSelectedItem();
            frame.orderMgr.updateOrderStatus(id, ns);
            refreshOrdTable();
        });

        refBtn.addActionListener(e -> refreshOrdTable());

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        topBar.setBackground(C_BG);
        topBar.add(lbl("New Status:")); topBar.add(statusCombo);
        topBar.add(updBtn); topBar.add(refBtn);

        return contentPanel("📦  All Orders", topBar, t);
    }

    void refreshOrdTable() {
        mgrOrdModel.setRowCount(0);
        for (Order o : frame.orderMgr.getAllOrders()) {
            mgrOrdModel.addRow(new Object[]{
                o.getOrderId(), o.getUserId(), o.getItemNames(),
                String.format("$%.2f", o.getTotalPrice()),
                o.getStatus(), o.getOrderDate()
            });
        }
    }

    // ---- USER MANAGEMENT ----
    private JPanel buildUserMgmt() {
        JTable t = styledTable(mgrUsrModel);

        JTextField uName  = new JTextField(10); styleField(uName);
        JTextField uPass  = new JTextField(8);  styleField(uPass);
        JTextField uPhone = new JTextField(10); styleField(uPhone);

        JButton addCustBtn  = btn("➕ Add Customer", C_PRIMARY);
        JButton addAdminBtn = btn("➕ Add Admin",    new Color(0x1565c0));
        JButton delBtn      = btn("🗑 Delete",       C_DANGER);
        JButton refBtn      = btn("🔄 Refresh",      new Color(0x607d8b));

        addCustBtn.addActionListener(e -> {
            try {
                frame.userMgr.addCustomer(uName.getText().trim(),
                    uPass.getText().trim(), uPhone.getText().trim());
                refreshUsrTable();
                uName.setText(""); uPass.setText(""); uPhone.setText("");
            } catch (Exception ex) { err(this, ex.getMessage()); }
        });

        addAdminBtn.addActionListener(e -> {
            try {
                frame.userMgr.addAdmin(uName.getText().trim(),
                    uPass.getText().trim(), uPhone.getText().trim());
                refreshUsrTable();
                uName.setText(""); uPass.setText(""); uPhone.setText("");
            } catch (Exception ex) { err(this, ex.getMessage()); }
        });

        delBtn.addActionListener(e -> {
            int row = t.getSelectedRow();
            if (row < 0) { err(this,"Select a user."); return; }
            int id = (int) mgrUsrModel.getValueAt(row, 0);
            frame.userMgr.deleteUser(id);
            refreshUsrTable();
        });

        refBtn.addActionListener(e -> refreshUsrTable());

        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        form.setBackground(C_BG);
        form.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0xdde0e5)), "Add User"));
        form.add(lbl("Name:")); form.add(uName);
        form.add(lbl("Password:")); form.add(uPass);
        form.add(lbl("Phone:")); form.add(uPhone);
        form.add(addCustBtn); form.add(addAdminBtn); form.add(delBtn); form.add(refBtn);

        JPanel p = contentPanel("👥  User Management", null, t);
        p.add(form, BorderLayout.SOUTH);
        return p;
    }

    void refreshUsrTable() {
        mgrUsrModel.setRowCount(0);
        for (User u : frame.userMgr.getAllUsers()) {
            String role = (u instanceof Admin) ? "Admin" : "Customer";
            mgrUsrModel.addRow(new Object[]{u.getId(), u.getName(), u.getPhone(), role});
        }
    }
}
