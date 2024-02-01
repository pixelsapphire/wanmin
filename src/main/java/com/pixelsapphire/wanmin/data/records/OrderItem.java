package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.DatabaseException;
import com.pixelsapphire.wanmin.controller.Provider;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItem implements DatabaseRecord {

    private final int id;
    private final float amount;
    private final @NotNull MenuItem menuItem;

    private OrderItem(int id, float amount, @NotNull MenuItem menuItem) {
        this.id = id;
        this.amount = amount;
        this.menuItem = menuItem;
    }

    @Contract("_, _ -> new")
    public static @NotNull OrderItem fromRecord(@NotNull ResultSet record, @NotNull Provider<MenuItem> menuItemProvider) {
        try {
            return new OrderItem(record.getInt("id"), record.getFloat("amount"), menuItemProvider.getByKey(record.getInt("pozycja")));
        } catch (SQLException e) {
            throw new DatabaseException("Failed to create OrderItem from record", e);
        }
    }

    public float getAmount() {
        return amount;
    }

    public @NotNull MenuItem getMenuItem() {
        return menuItem;
    }

    @Override
    public int getId() {
        return id;
    }
}
