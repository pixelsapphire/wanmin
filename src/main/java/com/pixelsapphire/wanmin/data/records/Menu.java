package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.controller.Provider;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Menu implements DatabaseRecord {

    private final int id;
    private final @NotNull String name;
    private final @NotNull List<MenuItem> items;

    private Menu(int id, @NotNull String name, @NotNull List<MenuItem> items) {
        this.id = id;
        this.name = name;
        this.items = new ArrayList<>(items);
    }

    @Contract("_, _ -> new")
    public static @NotNull Menu fromRecord(@NotNull ResultSet record,
                                           @NotNull Provider<List<MenuItem>> itemsProvider) throws SQLException {
        return new Menu(record.getInt("id"), record.getString("name"),
                        itemsProvider.getByKey(record.getInt("id")));
    }

    public @NotNull List<MenuItem> getItems() {
        return Collections.unmodifiableList(items);
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
        return "wm_menu";
    }
}
