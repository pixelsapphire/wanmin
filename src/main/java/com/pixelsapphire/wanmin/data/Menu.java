package com.pixelsapphire.wanmin.data;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Menu {
    private final int id;
    private final @NotNull String name;

    private Menu(int id, @NotNull String name) {
        this.id = id;
        this.name =name;
    }

    @Contract("_ -> new")
    public static @NotNull Menu fromRecord(@NotNull ResultSet record) throws SQLException {
        return new Menu(record.getInt("id"), record.getString("name"));
    }
}
