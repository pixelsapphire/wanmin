package com.pixelsapphire.wanmin.gui.layout;

import com.pixelsapphire.wanmin.controller.WanminDBController;
import com.pixelsapphire.wanmin.data.records.*;
import com.pixelsapphire.wanmin.gui.components.MenuView;
import com.pixelsapphire.wanmin.gui.auxiliary.BasicListModel;
import com.pixelsapphire.wanmin.gui.auxiliary.BasicListRenderer;
import com.pixelsapphire.wanmin.gui.components.NumberField;
import com.pixelsapphire.wanmin.util.Pointer;
import com.pixelsapphire.wanmin.util.StreamAdapter;
import com.pixelsapphire.wanmin.util.SwingUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class WaiterScreen extends Layout {

    private final @NotNull WanminDBController database;
    private final @NotNull MainPanel mainPanel = new MainPanel();

    public WaiterScreen(@NotNull JFrame window, @NotNull WanminDBController database) {
        super(window);
        this.database = database;
    }

    @Override
    protected void init(@NotNull JFrame window) {
        window.add(new SideBar(), Layout.params("gridx=0;gridy=0;fill=?;insets=0,0,0,24", SwingConstants.VERTICAL));
        window.add(mainPanel, Layout.params("gridx=1;gridy=0;fill=?;insets=0,0,0,0", SwingConstants.VERTICAL));
    }

    private class SideBar extends JPanel {

        private SideBar() {

            setLayout(new GridBagLayout());
            final var fill = GridBagConstraints.HORIZONTAL;
            int y = 0;
            add(new JLabel("Witaj, " + database.getUsername()), Layout.params("gridy=?;fill=?;insets=24,24,24,24", y++, fill));

            final JButton myOrders = new JButton("Moje zamowienia");
            myOrders.addActionListener(e -> mainPanel.showMyOrders());
            add(myOrders, Layout.params("gridy=?;fill=?;insets=0,24,8,24", y++, fill));

            final JButton customers = new JButton("Klienci");
            customers.addActionListener(e -> mainPanel.showCustomers());
            add(customers, Layout.params("gridy=?;fill=?;insets=0,24,8,24", y++, fill));

            final JButton menu = new JButton("Menu");
            menu.addActionListener(e -> mainPanel.showMenus());
            add(menu, Layout.params("gridy=?;fill=?;insets=0,24,8,24", y, fill));
        }
    }

    private class MainPanel extends JPanel {

        private final JPanel extraPanel;
        private boolean extraPanelVisible;
        private Menu selectedMenu;

        private MainPanel() {
            setLayout(new GridBagLayout());
            extraPanel = new JPanel();
            extraPanel.setLayout(new GridBagLayout());
            extraPanelVisible = false;
        }

        public void showMyOrders() {

            removeAll();

            final int waiterId = database.getEmployeeId();
            final List<Order> myOrders = database.orders.getAllWhere(o -> o.getWaiter().getId() == waiterId && !o.isPaid()).toList();

            final Order[] selectedOrder = {null};
            final var table = SwingUtils.createTable(new String[]{"numer", "stolik", "klient"}, myOrders,
                                                     o -> new String[]{"#" + o.getId(), "" + o.getTable(), o.getCustomer().toString()},
                                                     i -> {selectedOrder[0] = myOrders.get(i); showOrderContents(selectedOrder[0]);});
            add(table, Layout.params("gridy=0;fill=?;insets=8,0,8,8", SwingConstants.HORIZONTAL));

            final JButton addOrder = new JButton("Dodaj zamowienie");
            addOrder.addActionListener(e -> addNewOrder());
            //add(addOrder, Layout.params("gridy=1;fill=?;insets=0,0,8,0", SwingConstants.HORIZONTAL));

            final JButton deleteOrder = new JButton("Usun zamowienie");
            deleteOrder.addActionListener(e -> {
                if (selectedOrder[0] != null) {
                    database.orders.deleteOrder(selectedOrder[0].getId());
                    showMyOrders();
                }
//                else {
//                    //najpierw zaznacz zamowienie!
//                }
            });


            final JButton changeTable = new JButton("Zmien stolik");
            changeTable.addActionListener(e -> {
                final var changeTableWindow = new JFrame();
                changeTableWindow.setLayout(new GridBagLayout());
                var label = new JLabel("<html>" + "Nowy numer stolika: " + "</html>");
                JTextField newTableField = new JTextField();
                newTableField.setColumns(3);
                changeTableWindow.add(label);
                changeTableWindow.add(newTableField);
                JButton button = new JButton("Zatwierdz");
                button.addActionListener( i -> {
                    database.orders.updateOrder(new Order(selectedOrder[0].getId(), Integer.parseInt(newTableField.getText().trim()), selectedOrder[0].getWaiter(),
                            selectedOrder[0].getTime(), selectedOrder[0].getCustomer(), selectedOrder[0].isPaid(),new ArrayList<OrderItem>()));
                    changeTableWindow.dispose();
                    showMyOrders();
                });
                changeTableWindow.add(button);

                changeTableWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                changeTableWindow.setLocationRelativeTo(null);
                changeTableWindow.setResizable(false);
                changeTableWindow.setVisible(true);
                changeTableWindow.pack();
            });

            add(deleteOrder, Layout.params("gridy=3;fill=?;insets=0,0,8,0", SwingConstants.HORIZONTAL));
            add(addOrder, Layout.params("gridy=1;fill=?;insets=0,0,8,0", SwingConstants.HORIZONTAL));
            add(changeTable,Layout.params("gridy=2;fill=?;insets=0,0,8,0", SwingConstants.HORIZONTAL));

            resizeToContent();
        }

        private void showOrderContents(@NotNull Order order) {

            extraPanel.removeAll();

            List<OrderItem> items = order.getItems();
            final Pointer<Float> total = new Pointer<>((float) items.stream().mapToDouble(i -> i.getAmount() * i.getMenuItem().getPrice()).sum());
            final Pointer<OrderItem> item = Pointer.nullptr();
            extraPanel.add(SwingUtils.createTable(new String[]{"pozycja", "ilosc", "cena"}, items,
                            i -> new String[]{i.getMenuItem().getName(), "" + i.getAmount(), "" + i.getMenuItem().getPrice()},
                            i -> item.set(items.get(i))),
                           Layout.params("insets=8,8,8,8"));

            extraPanel.add(new JLabel("Suma: " + total.get() + "\n"),
                    Layout.params("gridy=1;insets=8,8,8,8"));
            extraPanel.add(createPayButton(order, total),
                    Layout.params("gridy=2;insets=8,8,8,8;align=right"));

            final JPanel itemButtons = new JPanel();
            itemButtons.setLayout(new GridBagLayout());

            final JButton addButton = new JButton("Dodaj pozycje");
            addButton.addActionListener(a -> addNewOrderItem(order.getId()));
            itemButtons.add(addButton, Layout.params("fill=?", SwingConstants.HORIZONTAL));

            final JButton deleteButton = new JButton("Usun pozycje");
            deleteButton.addActionListener(a -> {
                if(item.isNotNull()){
                    database.orders.deleteOrderItem(item.get().getId());
                    showOrderContents(database.orders.getFirstWhere(o -> o.getId() == order.getId()));
                }
                else {
                    //System.out.println("select an Item");
                }
            });
            itemButtons.add(deleteButton, Layout.params("gridy=1;fill=?", SwingConstants.HORIZONTAL));

            final JButton editButton = new JButton("Edytuj pozycje");
            editButton.addActionListener(a -> {
                if(item.isNotNull()){
                    modifyOrderItem(item.get(), order.getId());
                    showOrderContents(database.orders.getFirstWhere(o -> o.getId() == order.getId()));
                }
            });
            itemButtons.add(editButton, Layout.params("gridy=2;fill=?", SwingConstants.HORIZONTAL));

            extraPanel.add(itemButtons, Layout.params("gridx=1"));

            add(extraPanel, Layout.params("gridx=1;height=2"));
            extraPanelVisible = true;
            resizeToContent();
        }

        public void showCustomers() {

            removeAll();

            final var customers = database.customers.getAll().toList();
            DefaultTableModel model = new DefaultTableModel(
                    new String[]{"numer", "imie", "nazwisko", "akcje"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Allow editing only in first name (index 1) and last name (index 2) columns
                    return column == 1 || column == 2;
                }
            };
            for (Customer c : customers) {
                model.addRow(new String[]{"#" + c.getId(), c.getFirstName(), c.getLastName(), ""});
            }

            final var table = new JTable(model);

            // Add table selection listener (optional)
            table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                }
            });

            add(table, Layout.params("gridy=0;fill=?;insets=8,0,8,8", SwingConstants.HORIZONTAL));

            final JButton saveButton = new JButton("Zapisz zmiany");
            saveButton.addActionListener(e -> {
                int rowCount = model.getRowCount();
                for (int i = 0; i < rowCount; i++) {
                    String id = (String) model.getValueAt(i, 0); // Extract ID
                    int customerId = Integer.parseInt(id.trim().substring(1));
                    String firstName = (String) model.getValueAt(i, 1); // Get modified first name
                    String lastName = (String) model.getValueAt(i, 2); // Get modified last name

                    // Update customer in database
                    database.customers.updateCustomer(new Customer(customerId, firstName, lastName,database.customers.getById(customerId).getPoints()));
                }
                showCustomers();
            });

            add(saveButton, Layout.params("gridy=2;fill=?;insets=0,0,8,0", SwingConstants.HORIZONTAL));

            final JButton addOrder = new JButton("Dodaj nowego klienta");
            addOrder.addActionListener(e -> addNewCustomer());
            add(addOrder, Layout.params("gridy=1;fill=?;insets=0,0,8,0", SwingConstants.HORIZONTAL));

            resizeToContent();
        }

        public void showMenus() {
            removeAll();
            StreamAdapter.wrap(database.menus.getAll()).forEachIndexed((i, m) -> {
                final var button = new JButton(m.getName());
                button.addActionListener(e -> new MenuView(m));
                add(button, Layout.params("gridx=0;gridy=?;fill=?;insets=0,24,8,24", i, SwingConstants.HORIZONTAL));
            });
            resizeToContent();
        }

        private void addNewOrder() {
            final var newOrderWindow = new JFrame();

            final var customers = database.customers.getAll().toList();
            final var model = new BasicListModel<>(customers);
            final var tableComboBox = new JComboBox<>(model);
            final BasicListRenderer<Customer> renderer = (c) -> "id: " + c.getId() + ", " + c.getFirstName() + ", " + c.getLastName();
            tableComboBox.setRenderer(renderer);

            final JButton addButton = new JButton("Dodaj zamowienie");

            newOrderWindow.setLayout(new GridBagLayout());
            var label = new JLabel("<html>" + "Podaj nr stolika: " + "</html>");
            newOrderWindow.add((label), Layout.params("insets=4,4,4,4"));
            JTextField textField = new JTextField();
            textField.setColumns(5);
            newOrderWindow.add(textField);
            label = new JLabel("<html>" + "Klient: " + "</html>");

            addButton.addActionListener(e -> {
                final int table = Integer.parseInt(textField.getText().trim());
                final var customer = model.getSelectedItem();
                database.orders.addNewOrder(table, database.getEmployeeId(), customer.getId());
                newOrderWindow.dispose();
                showMyOrders();
            });
            newOrderWindow.add(addButton, Layout.params("gridy=1;fill=?;insets=0,0,8,0", SwingConstants.HORIZONTAL));
            newOrderWindow.add((label), Layout.params("insets=4,4,4,4"));
            newOrderWindow.add(tableComboBox, Layout.params("insets=4,4,4,4"));

            newOrderWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            newOrderWindow.setLocationRelativeTo(null);
            newOrderWindow.setResizable(false);
            newOrderWindow.setVisible(true);
            newOrderWindow.pack();
        }

        private void addNewOrderItem(int orderId) {
            final var newOrderItemWindow = new JFrame();

            final var menus = database.menus.getAll().toList();
            final var menusModel = new BasicListModel<>(menus);
            final var menusTableComboBox = new JComboBox<>(menusModel);
            final BasicListRenderer<Menu> menusRenderer = Menu::getName;
            menusTableComboBox.setRenderer(menusRenderer);

            final List<MenuItem> dishes = selectedMenu != null ? database.menus.getById(selectedMenu.getId()).getItems() : List.of();
            final Pointer<BasicListModel<MenuItem>> dishesModel = new Pointer<>(new BasicListModel<>(dishes));
            final var dishesTableComboBox = new JComboBox<>(dishesModel.get());
            final BasicListRenderer<MenuItem> dishesRenderer = (d) -> d.getName() + ", cena: " + d.getPrice();
            dishesTableComboBox.setRenderer(dishesRenderer);
            dishesTableComboBox.setEnabled(selectedMenu != null);

            menusTableComboBox.addActionListener(e -> {
                selectedMenu = menusModel.getSelectedItem();
                dishesTableComboBox.setEnabled(selectedMenu != null);
                dishesModel.set(new BasicListModel<>(selectedMenu != null ? database.menus.getById(selectedMenu.getId()).getItems() : List.of()));
                dishesTableComboBox.setModel(dishesModel.get());

                newOrderItemWindow.revalidate();
                newOrderItemWindow.repaint();
                newOrderItemWindow.pack();
            });



            final JButton addButton = new JButton("Dodaj pozycje");

            newOrderItemWindow.setLayout(new GridBagLayout());

            var label = new JLabel("<html>" + "Podaj ilosc: " + "</html>");
            newOrderItemWindow.add((label), Layout.params("insets=4,4,4,4"));
            final var quantityField = new NumberField(1);
            quantityField.setColumns(5);

            addButton.addActionListener(e -> {
                final int quantity = quantityField.getInt();
                database.orders.addOrderItem(orderId, new OrderItem(quantity, dishesModel.get().getSelectedItem()));
                newOrderItemWindow.dispose();
                showOrderContents(database.orders.getFirstWhere( o -> o.getId() == orderId));
            });

            newOrderItemWindow.add(addButton, Layout.params("gridy=1;fill=?;insets=0,0,8,0", SwingConstants.HORIZONTAL));
            newOrderItemWindow.add((label), Layout.params("insets=4,4,4,4"));
            newOrderItemWindow.add(quantityField);
            newOrderItemWindow.add(menusTableComboBox, Layout.params("insets=4,4,4,4"));
            newOrderItemWindow.add(dishesTableComboBox, Layout.params("insets=4,4,4,4"));

            newOrderItemWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            newOrderItemWindow.setLocationRelativeTo(null);
            newOrderItemWindow.setResizable(false);
            newOrderItemWindow.setVisible(true);
            newOrderItemWindow.pack();
        }

        private void modifyOrderItem (OrderItem item, int orderId) {
            final var newOrderItemWindow = new JFrame();

            final var menus = database.menus.getAll().toList();
            final var menusModel = new BasicListModel<>(menus);
            final var menusTableComboBox = new JComboBox<>(menusModel);
            final BasicListRenderer<Menu> menusRenderer = Menu::getName;
            menusTableComboBox.setRenderer(menusRenderer);

            final List<MenuItem> dishes = selectedMenu != null ? database.menus.getById(selectedMenu.getId()).getItems() : List.of();
            final Pointer<BasicListModel<MenuItem>> dishesModel = new Pointer<>(new BasicListModel<>(dishes));
            final var dishesTableComboBox = new JComboBox<>(dishesModel.get());
            final BasicListRenderer<MenuItem> dishesRenderer = (d) -> d.getName() + ", cena: " + d.getPrice();
            dishesTableComboBox.setRenderer(dishesRenderer);
            dishesTableComboBox.setEnabled(selectedMenu != null);

            menusTableComboBox.addActionListener(e -> {
                selectedMenu = menusModel.getSelectedItem();
                dishesTableComboBox.setEnabled(selectedMenu != null);
                dishesModel.set(new BasicListModel<>(selectedMenu != null ? database.menus.getById(selectedMenu.getId()).getItems() : List.of()));
                dishesTableComboBox.setModel(dishesModel.get());

                newOrderItemWindow.revalidate();
                newOrderItemWindow.repaint();
                newOrderItemWindow.pack();
            });



            final JButton addButton = new JButton("Zatwierdz zmiany");

            newOrderItemWindow.setLayout(new GridBagLayout());

            var label = new JLabel("<html>" + "Podaj ilosc: " + "</html>");
            newOrderItemWindow.add((label), Layout.params("insets=4,4,4,4"));
            final var quantityField = new NumberField(1);
            quantityField.setColumns(5);

            addButton.addActionListener(e -> {
                final int quantity = quantityField.getInt();
                database.orders.updateOrderItem(orderId, new OrderItem(item.getId(), quantity, dishesModel.get().getSelectedItem()));
                newOrderItemWindow.dispose();
                showOrderContents(database.orders.getFirstWhere( o -> o.getId() == orderId));
            });

            newOrderItemWindow.add(addButton, Layout.params("gridy=1;fill=?;insets=0,0,8,0", SwingConstants.HORIZONTAL));
            newOrderItemWindow.add((label), Layout.params("insets=4,4,4,4"));
            newOrderItemWindow.add(quantityField);
            newOrderItemWindow.add(menusTableComboBox, Layout.params("insets=4,4,4,4"));
            newOrderItemWindow.add(dishesTableComboBox, Layout.params("insets=4,4,4,4"));

            newOrderItemWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            newOrderItemWindow.setLocationRelativeTo(null);
            newOrderItemWindow.setResizable(false);
            newOrderItemWindow.setVisible(true);
            newOrderItemWindow.pack();
        }

        private void addNewCustomer() {
            final var newCustomerWindow = new JFrame();

            final JButton addButton = new JButton("Dodaj klienta");

            newCustomerWindow.setLayout(new GridBagLayout());
            var label = new JLabel("<html>" + "Imie: " + "</html>");
            newCustomerWindow.add((label), Layout.params("insets=4,4,4,4"));
            JTextField firstNameField = new JTextField(), lastNameField = new JTextField();
            firstNameField.setColumns(20);
            lastNameField.setColumns(30);
            newCustomerWindow.add(firstNameField);
            label = new JLabel("<html>" + "Nazwisko: " + "</html>");

            addButton.addActionListener(e -> {
                database.customers.add(new Customer(firstNameField.getText(), lastNameField.getText(), 0));
                newCustomerWindow.dispose();
                showCustomers();
            });
            newCustomerWindow.add(addButton, Layout.params("gridx=0;gridy=2;gridwidth=2;fill=?;insets=8,8,8,8;", SwingConstants.HORIZONTAL));
            //newCustomerWindow.add(addButton, Layout.params("gridx=0;gridy=2;gridwidth=2;fill=?;insets=8,8,8,8;anchor=?;", SwingConstants.HORIZONTAL, SwingConstants.CENTER));
            newCustomerWindow.add((label), Layout.params("insets=4,4,4,4"));
            newCustomerWindow.add(lastNameField);

            newCustomerWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            newCustomerWindow.setLocationRelativeTo(null);
            newCustomerWindow.setResizable(false);
            newCustomerWindow.setVisible(true);
            newCustomerWindow.pack();
        }
    }

    private @NotNull JButton createPayButton(@NotNull Order order, @NotNull Pointer<Float> total) {
        final JButton payButton = new JButton("Zaplac");
        payButton.addActionListener( p -> {
            database.orders.payOrder(order);
            Customer customer = order.getCustomer();
            float discount = 0.0f;
            int points = customer.getPoints();
            if (customer.getPoints() >= 100) {
                discount = total.get() * 0.1f;
                total.set(total.get() - discount);
                points -= 100;
            }
            points += (int) (total.get() / 10);
            database.customers.updateCustomer( new Customer(customer.getId(), customer.getFirstName(), customer.getLastName(), points));
            database.invoices.add(new Invoice(customer, order, new Date(), discount));
            mainPanel.showMyOrders();});
        return payButton;
    }
}
