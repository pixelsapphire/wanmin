package com.pixelsapphire.wanmin.gui.layout;

import com.pixelsapphire.wanmin.controller.WanminDBController;
import com.pixelsapphire.wanmin.data.records.Invoice;
import com.pixelsapphire.wanmin.data.records.Order;
import com.pixelsapphire.wanmin.util.ListAdapter;
import com.pixelsapphire.wanmin.util.StreamAdapter;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;
import java.util.stream.Stream;

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

            final JButton invoices = new JButton("Faktury");
            invoices.addActionListener(e -> mainPanel.showInvoices());
            add(invoices, Layout.params("gridy=?;fill=?;insets=0,24,8,24", y++, fill));

            final JButton customers = new JButton("Klienci");
            customers.addActionListener(e -> mainPanel.showCustomers());
            add(customers, Layout.params("gridy=?;fill=?;insets=0,24,8,24", y++, fill));

            final JButton menu = new JButton("Menu");
            menu.addActionListener(e -> mainPanel.showMenu());
            add(menu, Layout.params("gridy=?;fill=?;insets=0,24,8,24", y++, fill));

            final JButton recipes = new JButton("Przepisy");
            recipes.addActionListener(e -> mainPanel.showRecipes());
            add(recipes, Layout.params("gridy=?;fill=?;insets=0,24,8,24", y, fill));
        }
    }

    private class MainPanel extends JPanel {

        private MainPanel() {
            setLayout(new GridBagLayout());
        }

        public void showMyOrders() { // I WILL HAVE ORDER ~Zhongli

            removeAll();

            final int waiterId = database.getEmployeeId();
            final Stream<Order> myOrders = database.orders.getAllWhere(o -> o.getWaiter().getId() == waiterId && !o.isPaid());
            StreamAdapter.wrap(myOrders).forEachIndexed((i, o) -> add(new JLabel("<html>" + (i + 1) + ". " + o.toString().replace("\n", "<br>") + "</html>"),
                                                                      Layout.params("gridx=0;gridy=?;fill=?;insets=0,0,0,0", i, SwingConstants.HORIZONTAL)));

            final JButton addOrder = new JButton("Dodaj zamowienie");
            addOrder.addActionListener(e -> addNewOrder());
            add(addOrder, Layout.params("gridx=0;gridy=?;fill=?;insets=0,0,0,0", getComponentCount(), SwingConstants.HORIZONTAL));

            resizeToContent();
        }

        public void showInvoices() {
            removeAll();

            final List<Invoice> myInvoices = database.invoices.getAllWhere(i -> i.getOrder().getWaiter().getId() == database.employees.getFirstWhere(e -> e.getUsername().equals(database.getUsername())).getId()
                                                                                && i.getOrder().isPaid()).toList();
            ListAdapter.wrap(myInvoices).forEachIndexed((i, o) -> {
                String s = "" + (i + 1);

                add(new JTextArea(s + o.toString()), Layout.params("gridx=0;gridy=0;fill=?;insets=0,0,0,0", SwingConstants.HORIZONTAL));
            });
        }

        public void showCustomers() {

        }

        public void showMenu() {
            removeAll();
            database.menus.getAll().forEach(menu -> {
                final var menuPanel = new JPanel();
                menuPanel.setLayout(new GridBagLayout());
                menuPanel.add(new JLabel(menu.getName()), Layout.params("gridx=0;gridy=0;fill=?;insets=0,0,0,0", SwingConstants.HORIZONTAL));
                add(menuPanel, Layout.params("gridx=0;gridy=0;fill=?;insets=0,0,0,0", SwingConstants.HORIZONTAL));
            });
        }

        public void showRecipes() {
            removeAll();
            database.recipes.getAll().forEach(recipe -> {
                final var recipePanel = new JPanel();
                recipePanel.setLayout(new GridBagLayout());
                recipePanel.add(new JLabel("recipe"), Layout.params("gridx=0;gridy=0;fill=?;insets=0,0,0,0", SwingConstants.HORIZONTAL));
                add(recipePanel, Layout.params("gridx=0;gridy=0;fill=?;insets=0,0,0,0", SwingConstants.HORIZONTAL));
            });
        }

        private void addNewOrder() {
            System.out.println(" obslugiwanie add order.");
        }
    }
}
