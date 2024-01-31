package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.controller.Provider;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItem implements DatabaseRecord {

    private final int id;
    private final float amount;
    private MenuItem position;

    private OrderItem (int id, float amount, MenuItem position) {
        this.id = id;
        this.amount = amount;
        this.position = position;
    }

    @Contract ("_, _ -> new")
    public static @NotNull OrderItem fromRecord(@NotNull ResultSet record, Provider<MenuItem> positionProvider) throws SQLException {
        return new OrderItem (record.getInt("id"), record.getFloat("amount"), positionProvider.getById(record.getInt("pozycja")));
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public @NotNull String getTableName() {
        return "wm_zamowienia_pozycje";
    }
}
