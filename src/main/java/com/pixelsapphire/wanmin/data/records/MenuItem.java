package com.pixelsapphire.wanmin.data.records;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuItem implements DatabaseRecord {

    private final int id;
    private final float price;
    private final @NotNull String category, name;

    private MenuItem(int id, @NotNull String name, float price, @NotNull String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    @Contract("_, _ -> new")
    public static @NotNull MenuItem formRecord(@NotNull ResultSet record) throws SQLException {
        return new MenuItem(record.getInt("id"), record.getString("nazwa"), record.getFloat("cena"),
                            record.getString("kategoria"));
    }

    public float getPrice() {
        return price;
    }

    public @NotNull String getCategory() {
        return category;
    }

    public @NotNull String getName() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public @NotNull String getTableName() {
        return "wm_menu_pozycje";
    }
}
