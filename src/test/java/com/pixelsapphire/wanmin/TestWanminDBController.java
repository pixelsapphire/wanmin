package com.pixelsapphire.wanmin;

import com.pixelsapphire.wanmin.controller.WanminDBController;
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
    void testRole() {
        final WanminDBController database = new WanminDBController("sbd151886", "sbd151886!".toCharArray());
        assertTrue(database.isRoleEnabled("sbd151886.wm_kelner"));
        assertFalse(database.isRoleEnabled("sbd147412.wm_administrator"));
    }
}
