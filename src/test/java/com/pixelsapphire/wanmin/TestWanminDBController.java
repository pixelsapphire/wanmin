package com.pixelsapphire.wanmin;

import com.pixelsapphire.wanmin.controller.WanminDBController;
import org.junit.jupiter.api.Test;

public class TestWanminDBController {

    @Test
    void testPositions() {
        final WanminDBController database = new WanminDBController("sbd147412", "sbd147412".toCharArray());
        database.positions.getAll().forEach(p -> System.out.println(p.getId() + " " + p.getName() + " " + p.getSalary()));
    }
}
