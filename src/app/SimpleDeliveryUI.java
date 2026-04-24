//package app;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//
//public class SimpleDeliveryUI extends JFrame {
//    CardLayout cardLayout = new CardLayout();
//    JPanel mainPanel = new JPanel(cardLayout);
//
//    DefaultTableModel cartModel = new DefaultTableModel(new String[]{"Item","Price","Quantity"},0);
//    DefaultTableModel sharedMenuModel = new DefaultTableModel(new String[]{"Item","Price"},0);
//    DefaultTableModel sharedOrderModel = new DefaultTableModel(new String[]{"Order","Status"},0);
//
//    public SimpleDeliveryUI(){
//        setTitle("Demo Delivery App");
//        setSize(850,520);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//        initializeData();
//
//        mainPanel.add(createWelcomePanel(),"welcome");
//        mainPanel.add(createLoginPanel("Customer"),"customerLogin");
//        mainPanel.add(createLoginPanel("Manager"),"managerLogin");
//        mainPanel.add(createLoginPanel("Deliveryman"),"deliveryLogin");
//        mainPanel.add(createCustomerPanel(),"customerDash");
//        mainPanel.add(createManagerPanel(),"managerDash");
//        mainPanel.add(createDeliveryPanel(),"deliveryDash");
//        add(mainPanel);
//        cardLayout.show(mainPanel,"welcome");
//    }
//
//    void initializeData(){
//    	//read data from menu file
//        sharedMenuModel.addRow(new Object[]{"Burger","250"});
//        sharedMenuModel.addRow(new Object[]{"Pizza","500"});
//        sharedMenuModel.addRow(new Object[]{"Pasta","300"});
//    }
//
//    JPanel createWelcomePanel(){
//        JPanel p = new JPanel(new GridLayout(4,1,10,10));
//        p.setBorder(BorderFactory.createEmptyBorder(60,220,60,220));
//        JButton c = new JButton("Customer");
//        JButton m = new JButton("Manager");
//        JButton d = new JButton("Deliveryman");
//        p.add(new JLabel("Food Delivery App",SwingConstants.CENTER));
//        p.add(c);p.add(m);p.add(d);
//        c.addActionListener(e->cardLayout.show(mainPanel,"customerLogin"));
//        m.addActionListener(e->cardLayout.show(mainPanel,"managerLogin"));
//        d.addActionListener(e->cardLayout.show(mainPanel,"deliveryLogin"));
//        return p;
//    }
//    
//    JPanel createLoginPanel(String role){
//        JPanel p = new JPanel(new GridLayout(4,2,10,10));
//        p.setBorder(BorderFactory.createEmptyBorder(80,260,80,260));
//        JTextField user = new JTextField();
//        JPasswordField pass = new JPasswordField();
//        JButton login = new JButton("Login");
//        JButton back = new JButton("Back");
//        p.add(new JLabel(role+" Login")); p.add(new JLabel());
//        p.add(new JLabel("Username")); p.add(user);
//        p.add(new JLabel("Password")); p.add(pass);
//        p.add(login); p.add(back);
//        login.addActionListener(e->{
//        	
//        	String userName = user.getText();
//        	String password=new String(pass.getPassword());
//        	if(role.equals("Customer") && userName.equals("customer") && password.equals("123"))
//        		   cardLayout.show(mainPanel,"customerDash"); 
//        	else if(role.equals("Manager") && userName.equals("manager")  && password.equals("ABC")) 
//        			cardLayout.show(mainPanel,"managerDash"); 
//        	else cardLayout.show(mainPanel,"deliveryDash");
//        	user.setText("");
//        	pass.setText(""); 
//        	});
//        back.addActionListener(e->{
//        	user.setText("");
//        	pass.setText("");
//        	cardLayout.show(mainPanel,"welcome");
//        	});
//        return p;
//    }
//    
//
//    JPanel createCustomerPanel(){
//        JPanel p=new JPanel(new BorderLayout());
//        JTable menu=new JTable(sharedMenuModel);
//        JTable cart=new JTable(cartModel);
//        JButton add=new JButton("Add To Cart");
//        JButton del=new JButton("Delete From Cart");
//        JButton checkout=new JButton("Checkout");
//        JButton logout=new JButton("Logout");
//        JPanel bottom=new JPanel(); bottom.add(add);bottom.add(del);bottom.add(checkout);bottom.add(logout);
//        p.add(new JLabel("Customer Dashboard",SwingConstants.CENTER),BorderLayout.NORTH);
//        p.add(new JScrollPane(menu),BorderLayout.WEST);
//        p.add(new JScrollPane(cart),BorderLayout.CENTER);
//        p.add(bottom,BorderLayout.SOUTH);
//        add.addActionListener(e->{
//        	int r=menu.getSelectedRow(); 
//        	if(r!=-1) 
//        		cartModel.addRow(new Object[]{menu.getValueAt(r,0),menu.getValueAt(r,1)});
//        	});
//        del.addActionListener(e->{
//        	int r=cart.getSelectedRow(); 
//        	if(r!=-1) 
//        		cartModel.removeRow(r);
//        	});
//        checkout.addActionListener(e->{
//	        	if(cartModel.getRowCount()>0)
//	        	{
//	        		sharedOrderModel.addRow(new Object[]{"Customer Order","Pending"});
//	        		cartModel.setRowCount(0);
//	        		JOptionPane.showMessageDialog(this,"Order Placed");
//	        	}
//	        	else 
//	        		JOptionPane.showMessageDialog(this,"Cart is empty");
//        	});
//        logout.addActionListener(e->{
//        	cartModel.setRowCount(0);
//        	cardLayout.show(mainPanel,"welcome");
//        	});
//        return p;
//    }
//
//    JPanel createManagerPanel(){
//        JPanel p=new JPanel(new BorderLayout());
//        JTable table=new JTable(sharedMenuModel);
//        JTextField item=new JTextField(10),price=new JTextField(5);
//        JButton add=new JButton("Add"),del=new JButton("Delete"),logout=new JButton("Logout");
//        JPanel south=new JPanel(); 
//         south.add(new JLabel("Item")); south.add(item);
//         south.add(new JLabel("Price"));south.add(price);
//         south.add(add); south.add(del);south.add(logout);
//        p.add(new JLabel("Manager Dashboard",SwingConstants.CENTER),BorderLayout.NORTH);
//        p.add(new JScrollPane(table),BorderLayout.CENTER);
//        p.add(south,BorderLayout.SOUTH);
//        add.addActionListener(e->{
//        	if(!item.getText().trim().isEmpty()&&!price.getText().trim().isEmpty())
//        	{
//        		sharedMenuModel.addRow(new Object[]{item.getText(),price.getText()});
//        		item.setText("");
//        		price.setText("");
//        	}
//        });
//        del.addActionListener(e->{
//        	int r=table.getSelectedRow(); 
//        	if(r!=-1) sharedMenuModel.removeRow(r);
//        });
//        logout.addActionListener(e->cardLayout.show(mainPanel,"welcome"));
//        return p;
//    }
//
//    JPanel createDeliveryPanel(){
//        JPanel p=new JPanel(new BorderLayout());
//        JTable table=new JTable(sharedOrderModel);
//        JButton done=new JButton("Mark Delivered"),logout=new JButton("Logout");
//        JPanel south=new JPanel(); 
//        south.add(done);
//        south.add(logout);
//        p.add(new JLabel("Deliveryman Dashboard",SwingConstants.CENTER),BorderLayout.NORTH);
//        p.add(new JScrollPane(table),BorderLayout.CENTER);
//        p.add(south,BorderLayout.SOUTH);
//        done.addActionListener(e->{
//        	int r=table.getSelectedRow(); 
//        	if(r!=-1) sharedOrderModel.setValueAt("Delivered",r,1);
//        	});
//        logout.addActionListener(e->cardLayout.show(mainPanel,"welcome"));
//        return p;
//    }
//
//    public static void main(String[] args){
//        SwingUtilities.invokeLater(()->new SimpleDeliveryUI().setVisible(true));
//    }
//}
