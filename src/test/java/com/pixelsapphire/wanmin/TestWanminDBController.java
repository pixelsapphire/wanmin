package com.pixelsapphire.wanmin;

import com.pixelsapphire.wanmin.controller.WanminDBController;
import com.pixelsapphire.wanmin.data.records.MenuItem;
import com.pixelsapphire.wanmin.data.records.OrderItem;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestWanminDBController {

    @Test
    void testPositions() {
        final WanminDBController database = new WanminDBController("sbd147412", "sbd147412!".toCharArray());
        database.positions.getAll().forEach(p -> System.out.println(p.getId() + " " + p.getName() + " " + p.getSalary()));
    }

    @Test
    void testEmployees() {
        final WanminDBController database = new WanminDBController("sbd147412", "sbd147412!".toCharArray());
        database.employees.getAll().forEach(e -> System.out.println(e.getFirstName() + " " + e.getLastName() + " "));
    }

    @Test
    void testForeignInvoices() {
        final WanminDBController database = new WanminDBController("sbd147412", "sbd147412!".toCharArray());
        database.foreignInvoices.getAll().forEach(i -> System.out.println(i.getId() + " " + i.getContractor().getName() +
                                                                          " " + i.getItems().size() + " items: " +
                                                                          i.getItems().stream()
                                                                           .map(item -> item.getProduct().getName() + " " + item.getAmount() + item.getProduct().getUnit())
                                                                           .reduce("", (a, b) -> a + b + ", ")));
    }

    @Test
    void testMenus() {
        final WanminDBController database = new WanminDBController("sbd147412", "sbd147412!".toCharArray());
        database.menus.getAll().forEach(m -> System.out.println(m.getId() + " " + m.getName() + " " + m.getItems().size() + " items: " +
                                                                m.getItems().stream().map(item -> item.getName() + " " + item.getPrice())
                                                                 .reduce("", (a, b) -> a + b + ", ")));
    }

    @Test
    void testWaiterRole() {
        final WanminDBController database = new WanminDBController("sbd151886", "sbd151886!".toCharArray());
        assertTrue(database.isRoleEnabled("WM_KELNER"));
        assertFalse(database.isRoleEnabled("WM_ADMINISTRATOR"));
    }

    @Test
    void testAdminRole() {
        final WanminDBController database = new WanminDBController("sbd147412", "sbd147412!".toCharArray());
        assertTrue(database.isRoleEnabled("WM_ADMINISTRATOR"));
        assertTrue(database.isRoleEnabled("WM_KELNER"));
    }

    @Test
    void testAddingAndDeletingOrder () {
        final WanminDBController database = new WanminDBController("sbd147412", "sbd147412!".toCharArray());

        System.out.println("obecne zamowienia:");
        database.orders.getAll().forEach(p -> System.out.println(p.getId() + " stolik:" + p.getTable() + " klient" +
                p.getCustomer().getId() + " kelner:" + p.getWaiter().getId() + " czas:" + p.getTime() + " zaplacone: " + p.isPaid()));
        database.orders.addNewOrder(8, 21, 1);

        System.out.println("\nzamowienia po dodaniu:");
        database.orders.getAll().forEach(p -> System.out.println(p.getId() + " stolik:" + p.getTable() + " klient" +
                p.getCustomer().getId() + " kelner:" + p.getWaiter().getId() + " czas:" + p.getTime() + " zaplacone: " + p.isPaid()));

        database.orders.deleteOrder(database.orders.getFirstWhere( o -> o.getTable() == 8 && o.getWaiter().getId() == 21 && o.getCustomer().getId() == 1).getId());
        System.out.println("\nzamowienia po usunieciu:");
        database.orders.getAll().forEach(p -> System.out.println(p.getId() + " stolik:" + p.getTable() + " klient" +
                p.getCustomer().getId() + " kelner:" + p.getWaiter().getId() + " czas:" + p.getTime() + " zaplacone: " + p.isPaid()));
    }

    //
    public void testAddingOrderItemToOrder (int orderId, int menuItemId ) {
        final WanminDBController database = new WanminDBController("sbd147412", "sbd147412!".toCharArray());
        final var menuItem = database.menuItemProvider.getById(menuItemId);
        database.orders.addOrderItem(orderId, new OrderItem(0, 1, menuItem));
        database.orders.getFirstWhere(o -> o.getId() == orderId).getItems().forEach(orderItem -> System.out.println(orderItem.getId() +
                " " + orderItem.getMenuItem().getName() + " " + orderItem.getAmount()));
    }

    @Test
    public void testAddingOrderItemToOrder () {
        testAddingOrderItemToOrder(41, 2);

    }


}
