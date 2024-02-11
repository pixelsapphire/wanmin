package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.DatabaseException;
import com.pixelsapphire.wanmin.data.DictTuple;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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

    @Contract("_ -> new")
    public static @NotNull MenuItem fromRecord(@NotNull DictTuple record) {
        try {
            return new MenuItem(record.getInt("id"), record.getString("nazwa"), record.getFloat("cena"),
                                record.getString("kategoria"));
        } catch (IllegalArgumentException e) {
            throw new DatabaseException("Failed to create MenuPosition from record" + record, e);
        }
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
}
