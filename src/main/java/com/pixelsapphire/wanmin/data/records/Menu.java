package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.DatabaseException;
import com.pixelsapphire.wanmin.controller.Provider;
import com.pixelsapphire.wanmin.data.DictTuple;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
    public static @NotNull Menu fromRecord(@NotNull DictTuple record,
                                           @NotNull Provider<List<MenuItem>> itemsProvider) {
        try {
            return new Menu(record.getInt("id"), record.getString("name"),
                            itemsProvider.getById(record.getInt("id")));
        } catch (IllegalArgumentException e) {
            throw new DatabaseException("Failed to create Menu from record", e);
        }
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
}
