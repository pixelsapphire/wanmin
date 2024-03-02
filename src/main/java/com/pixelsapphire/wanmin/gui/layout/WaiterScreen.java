package com.pixelsapphire.wanmin.gui.layout;

import com.pixelsapphire.wanmin.controller.WanminDBController;
import com.pixelsapphire.wanmin.data.records.Order;
import com.pixelsapphire.wanmin.gui.components.MenuView;
import com.pixelsapphire.wanmin.gui.renderers.CustomerListModel;
import com.pixelsapphire.wanmin.gui.renderers.CustomerListRenderer;
import com.pixelsapphire.wanmin.util.StreamAdapter;
import com.pixelsapphire.wanmin.util.SwingUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

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

            // TODO selecty (lista rozwijana) i przyciski dodające nowe potrawy - po jednym dla każdego zamówienia. + przycisk zatwierdź/dodaj potrawę łacznie z wpisaniem tej pozycji do bazy danych
            // TODO do każdego zamówienia przycisk zakończ i płać - ustawiający stan zamowienia w bazie danych ispaid na 1 i tworzący fakturę do tego zamówienia. Sprawdź czy dać zniżkę.

            final var table = SwingUtils.createTable(new String[]{"numer", "stolik", "klient"}, myOrders,
                                                     o -> new String[]{"#" + o.getId(), "" + o.getTable(), o.getCustomer().toString()},
                                                     i -> showOrderContents(myOrders.get(i)));
            add(table, Layout.params("gridy=0;fill=?;insets=8,0,8,8", SwingConstants.HORIZONTAL));

            final JButton addOrder = new JButton("Dodaj zamowienie");
            addOrder.addActionListener(e -> addNewOrder());
            add(addOrder, Layout.params("gridy=1;fill=?;insets=0,0,8,0", SwingConstants.HORIZONTAL));

            resizeToContent();
        }

        private void showOrderContents(@NotNull Order order) {
            extraPanel.removeAll();
            extraPanel.add(SwingUtils.createTable(new String[]{"pozycja", "ilość", "cena"}, order.getItems(),
                                                  i -> new String[]{i.getMenuItem().getName(), "" + i.getAmount(), "" + i.getMenuItem().getPrice()}),
                           Layout.params("insets=8,8,8,8"));
            extraPanel.add(new JLabel("Suma: " + order.getItems().stream().mapToDouble(i -> i.getAmount() * i.getMenuItem().getPrice()).sum()),
                           Layout.params("gridy=1;insets=8,8,8,8"));
            if (!extraPanelVisible) {
                add(extraPanel, Layout.params("gridx=1;height=2"));
                extraPanelVisible = true;
            }
            resizeToContent();
        }

        public void showCustomers() {

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
            //TODO: dodanie nowego (pustego) zamowienia do bazy danych. i określenie czy klient jest stałym klientem - lista rozwijana.
            final var newOrderWindow = new JFrame();

            //database.orders.addNewOrder(table,database.getEmployeeId(), customerId);

            final var customers = database.customers.getAll().toList();
            final var model = new CustomerListModel(customers);
            final var tableComboBox = new JComboBox<>(model);
            tableComboBox.setRenderer(new CustomerListRenderer());

            final JButton addButton = new JButton("Dodaj zamowienie");
            addButton.addActionListener(e -> {

            });
            add(addButton, Layout.params("gridy=1;fill=?;insets=0,0,8,0", SwingConstants.HORIZONTAL));

            newOrderWindow.setLayout(new GridBagLayout());
            var label = new JLabel("<html>" + "Podaj nr stolika: " + "</html>");
            newOrderWindow.add((label), Layout.params("insets=4,4,4,4"));
            JTextField textField = new JTextField();
            textField.setColumns(20);
            newOrderWindow.add(textField);
            label = new JLabel("<html>" + "Podaj nr karty klienta (lub -1 jesli nie posiada konta): " + "</html>");

            addButton.addActionListener(e -> {
                final int table = Integer.parseInt(textField.getText().trim());
                final var customer = tableComboBox.getSelectedItem();
            });
            add(addButton, Layout.params("gridy=1;fill=?;insets=0,0,8,0", SwingConstants.HORIZONTAL));
            newOrderWindow.add((label), Layout.params("insets=4,4,4,4"));
            newOrderWindow.add(tableComboBox, Layout.params("insets=4,4,4,4"));

            newOrderWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            newOrderWindow.setLocationRelativeTo(null);
            newOrderWindow.setResizable(false);
            newOrderWindow.setVisible(true);
            newOrderWindow.pack();
        }

    }
}
