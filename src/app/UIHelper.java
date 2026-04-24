package app;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

class UIHelper {
    static final Color C_SIDEBAR  = new Color(0x1e2a38);
    static final Color C_ACTIVE   = new Color(0x2e7d32);
    static final Color C_BG       = new Color(0xf5f6fa);
    static final Color C_CARD     = Color.WHITE;
    static final Color C_PRIMARY  = new Color(0x2e7d32);
    static final Color C_DANGER   = new Color(0xc62828);
    static final Color C_DARK     = new Color(0x1a2332);
    static final Color C_SIDEBAR2 = new Color(0x263547);
    static final Font  F_MAIN     = new Font("Segoe UI", Font.PLAIN, 14);
    static final Font  F_BOLD     = new Font("Segoe UI", Font.BOLD, 14);
    static final Font  F_TITLE    = new Font("Segoe UI", Font.BOLD, 22);
    static final Font  F_BIG      = new Font("Segoe UI", Font.BOLD, 36);

    static JButton btn(String text, Color bg) {
        JButton b = new JButton(text);
        b.setFont(F_MAIN);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(new EmptyBorder(8, 18, 8, 18));
        b.addMouseListener(new MouseAdapter() {
            Color orig = bg;
            public void mouseEntered(MouseEvent e) { b.setBackground(orig.brighter()); }
            public void mouseExited(MouseEvent e)  { b.setBackground(orig); }
        });
        return b;
    }

    static JLabel lbl(String t) {
        JLabel l = new JLabel(t); l.setFont(F_MAIN); return l;
    }

    static JLabel wlbl(String t) {
        JLabel l = new JLabel(t); l.setFont(F_MAIN); l.setForeground(Color.WHITE); return l;
    }

    static JLabel sectionTitle(String t) {
        JLabel l = new JLabel(t); l.setFont(F_TITLE); l.setForeground(new Color(0x1e2a38));
        l.setBorder(new EmptyBorder(0, 0, 10, 0)); return l;
    }

    static void styleField(JComponent f) {
        f.setFont(F_MAIN);
        f.setBackground(new Color(0x1e2a38));
        f.setForeground(Color.WHITE);
        if (f instanceof JTextField) ((JTextField) f).setCaretColor(Color.WHITE);
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x3d5166), 1),
            new EmptyBorder(6, 10, 6, 10)));
    }

    static JTable styledTable(DefaultTableModel model) {
        JTable t = new JTable(model);
        t.setFont(F_MAIN);
        t.setRowHeight(28);
        t.setShowGrid(false);
        t.setIntercellSpacing(new Dimension(0, 0));
        t.setSelectionBackground(new Color(0x2e7d32, false));
        t.setSelectionForeground(Color.WHITE);
        t.setBackground(Color.WHITE);
        t.setForeground(new Color(0x1e2a38));
        t.getTableHeader().setFont(F_BOLD);
        t.getTableHeader().setBackground(new Color(0x1e2a38));
        t.getTableHeader().setForeground(Color.WHITE);
        t.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        t.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable tbl, Object val,
                    boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(tbl, val, sel, foc, row, col);
                if (!sel) c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(0xf0f4f8));
                setBorder(new EmptyBorder(0, 8, 0, 8));
                return c;
            }
        });
        return t;
    }

    /** Build a sidebar with nav items that switch an inner CardLayout. */
    static JPanel sidebar(String[][] navItems, CardLayout innerCL, JPanel innerCards,
                          ActionListener logoutAction) {
        JPanel side = new JPanel();
        side.setBackground(C_SIDEBAR);
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
        side.setPreferredSize(new Dimension(200, 0));
        side.setBorder(new EmptyBorder(20, 0, 20, 0));

        JLabel logo = new JLabel("🍔 QuickBite", SwingConstants.CENTER);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        logo.setBorder(new EmptyBorder(0, 0, 20, 0));
        side.add(logo);

        for (String[] item : navItems) {
            String icon = item[0], label = item[1], card = item[2];
            JButton nb = new JButton(icon + "  " + label);
            nb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            nb.setForeground(Color.WHITE);
            nb.setBackground(C_SIDEBAR);
            nb.setFocusPainted(false);
            nb.setBorderPainted(false);
            nb.setOpaque(true);
            nb.setHorizontalAlignment(SwingConstants.LEFT);
            nb.setMaximumSize(new Dimension(200, 44));
            nb.setBorder(new EmptyBorder(10, 20, 10, 10));
            nb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            nb.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { nb.setBackground(C_ACTIVE); }
                public void mouseExited(MouseEvent e)  { nb.setBackground(C_SIDEBAR); }
            });
            nb.addActionListener(e -> innerCL.show(innerCards, card));
            side.add(nb);
        }

        side.add(Box.createVerticalGlue());
        JButton logoutBtn = new JButton("🚪  Logout");
        logoutBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setBackground(C_DANGER);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setOpaque(true);
        logoutBtn.setMaximumSize(new Dimension(200, 44));
        logoutBtn.setBorder(new EmptyBorder(10, 20, 10, 10));
        logoutBtn.setHorizontalAlignment(SwingConstants.LEFT);
        logoutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(logoutAction);
        side.add(logoutBtn);
        return side;
    }

    /** Wrap a table in a standard content panel with title and optional top bar. */
    static JPanel contentPanel(String title, JComponent topBar, JTable table) {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBackground(C_BG);
        p.setBorder(new EmptyBorder(20, 20, 20, 20));
        JPanel north = new JPanel(new BorderLayout());
        north.setBackground(C_BG);
        north.add(sectionTitle(title), BorderLayout.NORTH);
        if (topBar != null) north.add(topBar, BorderLayout.SOUTH);
        p.add(north, BorderLayout.NORTH);
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(new Color(0xdde0e5)));
        p.add(sp, BorderLayout.CENTER);
        return p;
    }

    static void err(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
