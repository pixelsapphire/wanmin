package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.DatabaseException;
import com.pixelsapphire.wanmin.controller.Provider;
import com.pixelsapphire.wanmin.data.DictTuple;
import com.pixelsapphire.wanmin.data.DictTuple.DictTupleBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class OrderItem implements DatabaseRecord {

    private final int id, amount;
    private final @NotNull MenuItem menuItem;

    public OrderItem(int id, int amount, @NotNull MenuItem menuItem) {
        this.id = id;
        this.amount = amount;
        this.menuItem = menuItem;
    }

    public OrderItem(int amount, @NotNull MenuItem menuItem) {
        this(DatabaseRecord.ID_UNINITIALIZED, amount, menuItem);
    }

    @Contract("_, _ -> new")
    public static @NotNull OrderItem fromRecord(@NotNull DictTuple record, @NotNull Provider<MenuItem> menuItemProvider) {
        try {
            return new OrderItem(record.getInt("id"), record.getInt("ilosc"), menuItemProvider.getById(record.getInt("pozycja")));
        } catch (IllegalArgumentException e) {
            throw new DatabaseException("Failed to create OrderItem from record" + record, e);
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

    @Override
    public @NotNull DictTuple toRecord() {
        return new DictTupleBuilder().with("id", id)
                                     .with("ilosc", amount)
                                     .with("pozycja", menuItem.getId())
                                     .build();
    }
}
