# Food Delivery System — Java Swing UI Prompt for Codex

## Project Overview

This is a Java 17 project called **FoodDeliverySystem** built in Eclipse. The backend logic is already complete across four packages:

| Package | Key Classes |
|---|---|
| `user` | `User` (abstract), `Customer`, `Admin`, `UserManager`, `UserRepositoryImp` |
| `order` | `Order`, `OrderManager`, `OrderRepositoryImp` |
| `menu` | `MenuItem`, `MenuItemManager`, `MenuRepositoryImp` |
| `delivery` | `Delivery`, `DeliveryManager`, `DeliveryRepositoryImp` |

Data is persisted to binary files: `users.dat`, `orders.dat`.

There is already a rough prototype UI at `src/app/SimpleDeliveryUI.java` — **replace it entirely** with a professional implementation described below.

---

## Task

Build a complete, professional Java Swing UI in `src/app/SimpleDeliveryUI.java` that integrates with all existing backend manager classes. The UI must have **no hardcoded data** — everything goes through the manager classes.

---

## UI Requirements

### General Design Standards
- Use `FlatLaf` or a custom `UIManager` theme for a modern flat look (dark sidebar, clean cards)
- Font: use `new Font("Segoe UI", Font.PLAIN, 14)` throughout
- Color palette:
  - Sidebar background: `#1e2a38`
  - Sidebar text: `#ffffff`
  - Active sidebar item: `#2e7d32` (green)
  - Main panel background: `#f5f6fa`
  - Card/panel background: `#ffffff`
  - Primary button: `#2e7d32` with white text
  - Danger button: `#c62828` with white text
- All buttons must be rounded (`setBackground`, `setBorderPainted(false)`, `setFocusPainted(false)`)
- Use `JTable` with alternating row colors and hidden grid lines for all data tables
- Use `CardLayout` for switching between role dashboards

---

### Screen 1 — Welcome / Role Selection
- Centered logo/title: **"🍔 QuickBite Delivery"** in large bold font
- Three stylized role cards (not plain buttons):
  - **Customer**
  - **Manager**
  - **Delivery Person**
- Each card has an icon, role title, and a subtle hover effect
- Clicking a card navigates to that role's Login screen

---

### Screen 2 — Login Screen (shared for all roles)
- Clean centered form with:
  - App logo at top
  - Username `JTextField`
  - Password `JPasswordField`
  - **Login** button (calls `UserManager.login()`)
  - **Back** link/button
- On successful login, navigate to the correct dashboard based on `instanceof Customer` or `instanceof Admin`
- On failure, show a styled `JOptionPane` error dialog

---

### Screen 3 — Customer Dashboard

Left sidebar with navigation items:
- 🏠 Home
- 🍽️ Browse Menu
- 🛒 My Cart
- 📦 My Orders
- 🚪 Logout

**Browse Menu Panel:**
- Display `MenuItemManager.viewMenu()` data in a styled `JTable` (columns: Name, Category, Price, Available)
- Filter bar at top to filter by category (dropdown `JComboBox`)
- **Add to Cart** button adds selected row to cart

**My Cart Panel:**
- Shows items added by customer in a `JTable`
- Shows running total at the bottom
- **Remove Item** and **Place Order** buttons
- Place Order calls `OrderManager.placeOrder()` logic (programmatically, not via Scanner — refactor or call repository directly)

**My Orders Panel:**
- Calls `OrderManager` to load orders filtered by logged-in user ID
- Shows order ID, items, total, status, date in a table

---

### Screen 4 — Manager Dashboard

Left sidebar:
- 📋 Menu Management
- 📦 All Orders
- 👥 User Management
- 🚪 Logout

**Menu Management Panel:**
- Full CRUD table for menu items
- Form fields below table: Name, Price, Category (`JComboBox`), Description, Available (`JCheckBox`)
- Buttons: **Add Item**, **Update Selected**, **Delete Selected**
- All operations call `MenuItemManager` methods

**All Orders Panel:**
- Displays all orders via `OrderManager.viewAllOrders()` logic
- Dropdown to update order status (Pending / Confirmed / Delivered)
- **Update Status** button calls `OrderManager.updateOrderStatus()` logic

**User Management Panel:**
- Table showing all users from `UserManager.viewAllUsers()` logic
- Buttons: **Add Customer**, **Add Admin**, **Delete Selected**

---

### Screen 5 — Delivery Person Dashboard

Left sidebar:
- 📦 My Deliveries
- ✅ Mark Delivered
- 🚪 Logout

**My Deliveries Panel:**
- Table showing deliveries assigned to logged-in delivery person ID
- Columns: Delivery ID, Order ID, Customer ID, Address, Status, Date
- Calls `DeliveryManager.viewAllDeliveries()` and filters by delivery person ID

**Actions:**
- Select a row → **Mark On The Way** (calls `DeliveryManager.markOnTheWay()`)
- Select a row → **Mark Delivered** (calls `DeliveryManager.markDelivered()`)

---

## Architecture Notes

- `SimpleDeliveryUI` is the main `JFrame`
- Create one instance of each manager at startup:
  ```java
  UserManager userManager = new UserManager();
  OrderManager orderManager = new OrderManager();
  MenuItemManager menuManager = new MenuItemManager();
  DeliveryManager deliveryManager = new DeliveryManager();
  ```
- Store the logged-in `User` object in a field `private User currentUser;`
- Since `OrderManager` and `UserManager` currently use `Scanner` internally, **bypass Scanner** by calling the repository directly or adding new non-Scanner methods to the managers (e.g., `placeOrder(int userId, double total)`)
- Keep all existing backend classes unchanged if possible

---

## Refactoring Required in Backend

Add the following non-Scanner overloaded methods (do not remove existing Scanner-based ones):

```java
// In OrderManager:
public void placeOrder(int userId, double totalPrice, ArrayList<String> items)
public void updateOrderStatus(int orderId, String newStatus)
public ArrayList<Order> getOrdersByUserId(int userId)

// In UserManager:
public void addCustomer(String name, String password, String phone)
public void addAdmin(String name, String password, String phone)
public ArrayList<User> getAllUsers()

// In MenuItemManager:
public ArrayList<MenuItem> getAllMenuItems()
public MenuItem getItemById(int id)
```

---

## Deliverables

1. Updated `src/app/SimpleDeliveryUI.java` — complete professional UI
2. Updated `src/order/OrderManager.java` — with non-Scanner methods added
3. Updated `src/user/UserManager.java` — with non-Scanner methods added
4. Updated `src/menu/MenuItemManager.java` — with helper getters added
5. Updated `src/app/Main.java` — launches `SimpleDeliveryUI` via `SwingUtilities.invokeLater`

---

## Sample Main Entry Point

```java
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimpleDeliveryUI().setVisible(true));
    }
}
```

---

## Notes

- Do NOT use any external libraries except standard Java Swing/AWT
- Java version: 17
- Project is an Eclipse project (no Maven/Gradle)
- All `.dat` files are created automatically at runtime — no setup needed
- Keep code in the existing package structure
