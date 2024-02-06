package com.pixelsapphire.wanmin;

import com.pixelsapphire.wanmin.controller.WanminDBController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestWanminDBController {

    @Test
    void testPositions() {
        final WanminDBController database = new WanminDBController("sbd147412", "sbd147412".toCharArray());
        database.positions.getAll().forEach(p -> System.out.println(p.getId() + " " + p.getName() + " " + p.getSalary()));
    }

    @Test
    void testRole() {
        final WanminDBController database = new WanminDBController("sbd151886", "sbd151886".toCharArray());
        assertTrue(database.isRoleEnabled("wm_kelner"));
        assertFalse(database.isRoleEnabled("wm_administrator"));
    }
}
