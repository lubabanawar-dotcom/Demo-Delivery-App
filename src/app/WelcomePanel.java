package app;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

import static app.UIHelper.*;

class WelcomePanel extends JPanel {

    WelcomePanel(SimpleDeliveryUI frame) {
        setLayout(new BorderLayout());
        setBackground(C_DARK);

        // Header
        JLabel title = new JLabel("🍔 QuickBite Delivery", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 38));
        title.setForeground(Color.WHITE);

        JLabel sub = new JLabel("Fast · Fresh · Delivered to Your Door", SwingConstants.CENTER);
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        sub.setForeground(new Color(0xaabbcc));

        JPanel header = new JPanel(new GridLayout(2, 1, 0, 8));
        header.setBackground(C_DARK);
        header.setBorder(new EmptyBorder(50, 0, 40, 0));
        header.add(title);
        header.add(sub);

        // Role cards
        JPanel cards = new JPanel(new GridLayout(1, 3, 30, 0));
        cards.setBackground(C_DARK);
        cards.setBorder(new EmptyBorder(0, 80, 80, 80));
        cards.add(roleCard("🛒", "Customer",       "Browse menu & place orders",     "Customer",  frame));
        cards.add(roleCard("📋", "Manager",        "Manage menu, orders & users",    "Manager",   frame));
        cards.add(roleCard("🚚", "Delivery Person", "View & update your deliveries", "Delivery",  frame));

        add(header, BorderLayout.NORTH);
        add(cards,  BorderLayout.CENTER);
    }

    private JPanel roleCard(String icon, String role, String desc, String key, SimpleDeliveryUI frame) {
        JPanel card = new JPanel(new GridBagLayout()) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(C_SIDEBAR2);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(30, 20, 30, 20));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0; g.gridy = 0; g.insets = new Insets(6, 0, 6, 0);

        JLabel iconL = new JLabel(icon, SwingConstants.CENTER);
        iconL.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        iconL.setForeground(Color.WHITE);

        JLabel roleL = new JLabel(role, SwingConstants.CENTER);
        roleL.setFont(new Font("Segoe UI", Font.BOLD, 20));
        roleL.setForeground(Color.WHITE);

        JLabel descL = new JLabel("<html><center>" + desc + "</center></html>", SwingConstants.CENTER);
        descL.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        descL.setForeground(new Color(0xaabbcc));

        JButton selectBtn = btn("Select  →", C_PRIMARY);
        selectBtn.addActionListener(e -> frame.showLogin(key));

        card.add(iconL, g); g.gridy++;
        card.add(roleL, g); g.gridy++;
        card.add(descL, g); g.gridy++;
        card.add(Box.createVerticalStrut(12), g); g.gridy++;
        card.add(selectBtn, g);

        return card;
    }
}
