package com.pixelsapphire.wanmin.data;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class DictTuple {

    private final @NotNull Map<String, Object> values;

    private DictTuple(@NotNull Map<String, Object> values) {
        this.values = values;
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull DictTuple from(@NotNull ResultSet record) throws SQLException {
        final int fields = record.getMetaData().getColumnCount();
        final Map<String, Object> values = new HashMap<>(fields);
        for (int i = 1; i <= fields; ++i) values.put(record.getMetaData().getColumnName(i).toLowerCase(), record.getObject(i));
        return new DictTuple(values);
    }

    public int getInt(@NotNull String key) {
        final String k = key.toLowerCase();
        if (values.get(k) instanceof Number) return ((Number) values.get(k)).intValue();
        throw new IllegalArgumentException("Value for key " + key + " is not an integer");
    }

    public float getFloat(@NotNull String key) {
        final String k = key.toLowerCase();
        if (values.get(k) instanceof Number) return ((Number) values.get(k)).floatValue();
        throw new IllegalArgumentException("Value for key " + key + " is not a float");
    }

    public boolean getBoolean(@NotNull String key) {
        final String k = key.toLowerCase();
        if (values.get(k) instanceof Number) return ((Number) values.get(k)).intValue() != 0;
        if (values.get(k) instanceof Boolean) return ((Boolean) values.get(k));
        throw new IllegalArgumentException("Value for key " + key + " is not a boolean");
    }

    public @NotNull String getString(@NotNull String key) {
        final String k = key.toLowerCase();
        if (values.get(k) instanceof String) return (String) values.get(k);
        throw new IllegalArgumentException("Value for key " + key + " is not a string");
    }

    public @NotNull Date getDate(@NotNull String key) {
        final String k = key.toLowerCase();
        if (values.get(k) instanceof Date) return (Date) values.get(k);
        if (values.get(k) instanceof Timestamp)
            return Date.valueOf(((Timestamp) values.get(k)).toLocalDateTime().toLocalDate());
        throw new IllegalArgumentException("Value for key " + key + " is not a date");
    }
}
