package app;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

import user.*;
import static app.UIHelper.*;

class LoginPanel extends JPanel {

    private JTextField     userField = new JTextField();
    private JPasswordField passField = new JPasswordField();
    private JLabel         roleLbl   = new JLabel("Login", SwingConstants.CENTER);

    LoginPanel(SimpleDeliveryUI frame) {
        setLayout(new GridBagLayout());
        setBackground(C_DARK);

        JPanel form = new JPanel(new GridBagLayout()) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(C_SIDEBAR2);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        form.setOpaque(false);
        form.setPreferredSize(new Dimension(380, 380));
        form.setBorder(new EmptyBorder(30, 40, 30, 40));

        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0; g.gridy = 0;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1; g.insets = new Insets(8, 0, 8, 0);

        JLabel appLbl = new JLabel("🍔 QuickBite Delivery", SwingConstants.CENTER);
        appLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        appLbl.setForeground(new Color(0x4caf50));

        roleLbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        roleLbl.setForeground(Color.WHITE);

        styleField(userField); styleField(passField);

        JButton loginBtn = btn("Login", C_PRIMARY);
        loginBtn.setPreferredSize(new Dimension(300, 40));

        JButton backBtn = btn("← Back", new Color(0x455a64));
        backBtn.setPreferredSize(new Dimension(300, 36));

        form.add(appLbl,   g); g.gridy++;
        form.add(roleLbl,  g); g.gridy++;
        form.add(Box.createVerticalStrut(4), g); g.gridy++;
        form.add(wlbl("Username"), g); g.gridy++;
        form.add(userField,  g); g.gridy++;
        form.add(wlbl("Password"), g); g.gridy++;
        form.add(passField,  g); g.gridy++;
        form.add(Box.createVerticalStrut(6), g); g.gridy++;
        form.add(loginBtn,   g); g.gridy++;
        form.add(backBtn,    g);

        loginBtn.addActionListener(e -> doLogin(frame));
        passField.addActionListener(e -> doLogin(frame));
        backBtn.addActionListener(e -> { clearFields(); frame.showWelcome(); });

        add(form);
    }

    void setRole(String roleKey) {
        switch (roleKey) {
            case "Customer": roleLbl.setText("Customer Login"); break;
            case "Manager":  roleLbl.setText("Manager Login");  break;
            default:         roleLbl.setText("Delivery Person Login"); break;
        }
    }

    private void doLogin(SimpleDeliveryUI frame) {
        String name = userField.getText().trim();
        String pass = new String(passField.getPassword()).trim();
        if (name.isEmpty() || pass.isEmpty()) {
            err(this, "Please enter username and password.");
            return;
        }
        User u = frame.userMgr.login(name, pass);
        if (u == null) {
            err(this, "Invalid username or password.");
            return;
        }
        clearFields();
        frame.onLoginSuccess(u);
    }

    private void clearFields() {
        userField.setText("");
        passField.setText("");
    }
}
