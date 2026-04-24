package app;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

import delivery.Delivery;
import static app.UIHelper.*;

class DeliveryDash extends JPanel {

    private SimpleDeliveryUI frame;
    private CardLayout innerCL    = new CardLayout();
    private JPanel     innerCards = new JPanel(innerCL);

    private DefaultTableModel delModel = new DefaultTableModel(
        new String[]{"Del ID","Order ID","Cust ID","Address","Status","Date/Time"}, 0) {
        public boolean isCellEditable(int r,int c){return false;}
    };

    DeliveryDash(SimpleDeliveryUI frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(C_BG);

        String[][] nav = {
            {"📦","My Deliveries","deliveries"},
            {"✅","Mark Delivered","deliveries"}
        };
        add(sidebar(nav, innerCL, innerCards, e -> frame.logout()), BorderLayout.WEST);

        innerCards.setBackground(C_BG);
        innerCards.add(buildDeliveries(), "deliveries");
        add(innerCards, BorderLayout.CENTER);
    }

    void onShow() {
        refreshTable();
        innerCL.show(innerCards, "deliveries");
    }

    private JPanel buildDeliveries() {
        JTable t = styledTable(delModel);

        JButton wayBtn = btn("🚚 Mark On The Way", new Color(0x1565c0));
        JButton doneBtn = btn("✅ Mark Delivered",  C_PRIMARY);
        JButton refBtn  = btn("🔄 Refresh",         new Color(0x607d8b));

        wayBtn.addActionListener(e -> {
            int row = t.getSelectedRow();
            if (row < 0) { err(this,"Select a delivery."); return; }
            int id = (int) delModel.getValueAt(row, 0);
            frame.delMgr.markOnTheWay(id);
            refreshTable();
        });

        doneBtn.addActionListener(e -> {
            int row = t.getSelectedRow();
            if (row < 0) { err(this,"Select a delivery."); return; }
            int id = (int) delModel.getValueAt(row, 0);
            frame.delMgr.markDelivered(id);
            refreshTable();
        });

        refBtn.addActionListener(e -> refreshTable());

        JLabel infoLbl = new JLabel(
            "<html><i>Showing all deliveries assigned to your ID. " +
            "Select a row and use the buttons below.</i></html>");
        infoLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        infoLbl.setForeground(Color.GRAY);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(C_BG);
        topBar.add(infoLbl, BorderLayout.CENTER);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        btnRow.setBackground(C_BG);
        btnRow.add(wayBtn); btnRow.add(doneBtn); btnRow.add(refBtn);

        JPanel p = contentPanel("📦  My Deliveries", topBar, t);
        p.add(btnRow, BorderLayout.SOUTH);
        return p;
    }

    void refreshTable() {
        delModel.setRowCount(0);
        if (frame.currentUser == null) return;

        // Try to show deliveries assigned to this person's ID first
        List<Delivery> list = frame.delMgr.getDeliveriesByPersonId(frame.currentUser.getId());

        // If none assigned specifically, show ALL deliveries (for demo usability)
        if (list.isEmpty()) {
            list = frame.delMgr.getAllDeliveries();
        }

        for (Delivery d : list) {
            delModel.addRow(new Object[]{
                d.getDeliveryId(), d.getOrderId(), d.getCustomerId(),
                d.getAddress(), d.getStatus(), d.getDeliveryDateTime()
            });
        }
    }
}
