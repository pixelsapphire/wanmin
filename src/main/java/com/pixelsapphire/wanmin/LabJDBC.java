package com.pixelsapphire.wanmin;

import com.pixelsapphire.wanmin.controller.WanminDBController;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings({"SameParameterValue"})
public class LabJDBC {

    public static void main(String[] args) {
        final var controller = new WanminDBController("sbd147412", "sbd147412");
    }

}

