package com.pixelsapphire.wanmin.data;

import com.pixelsapphire.wanmin.util.ObjectUtil;
import oracle.sql.TIMESTAMP;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
        throw new IllegalArgumentException("Value for key " + key + " (" + ObjectUtil.getClass(values.get(k)) + ") is not an integer");
    }

    public float getFloat(@NotNull String key) {
        final String k = key.toLowerCase();
        if (values.get(k) instanceof Number) return ((Number) values.get(k)).floatValue();
        throw new IllegalArgumentException("Value for key " + key + " (" + ObjectUtil.getClass(values.get(k)) + ") is not a float");
    }

    public boolean getBoolean(@NotNull String key) {
        final String k = key.toLowerCase();
        if (values.get(k) instanceof Number) return ((Number) values.get(k)).intValue() != 0;
        if (values.get(k) instanceof Boolean) return ((Boolean) values.get(k));
        throw new IllegalArgumentException("Value for key " + key + " (" + ObjectUtil.getClass(values.get(k)) + ") is not a boolean");
    }

    public @NotNull String getString(@NotNull String key) {
        final String k = key.toLowerCase();
        if (values.get(k) instanceof String) return (String) values.get(k);
        throw new IllegalArgumentException("Value for key " + key + " (" + ObjectUtil.getClass(values.get(k)) + ") is not a string");
    }

    public @NotNull Optional<String> getOptionalString(@NotNull String key) {
        final String k = key.toLowerCase();
        if (!values.containsKey(k) || values.get(k) == null) return Optional.empty();
        if (values.get(k) instanceof String) return Optional.of((String) values.get(k));
        throw new IllegalArgumentException("Value for key " + key + " (" + ObjectUtil.getClass(values.get(k)) + ") is not a string");
    }

    public @NotNull Date getDate(@NotNull String key) {
        final String k = key.toLowerCase();
        if (values.get(k) instanceof Date) return (Date) values.get(k);
        if (values.get(k) instanceof Timestamp)
            return Date.valueOf(((Timestamp) values.get(k)).toLocalDateTime().toLocalDate());
        if (values.get(k) instanceof TIMESTAMP)
            try {
                return Date.valueOf(((TIMESTAMP) values.get(k)).timestampValue().toLocalDateTime().toLocalDate());
            } catch (SQLException ignored) {
            }
        throw new IllegalArgumentException("Value for key " + key + " (" + ObjectUtil.getClass(values.get(k)) + ") is not a date");
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        for (final var entry : values.entrySet()) sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(", ");
        sb.setLength(sb.length() - 2);
        return sb.append("}").toString();
    }

    public static class DictTupleBuilder {

        private final @NotNull Map<String, Object> values;

        public DictTupleBuilder() {
            this.values = new HashMap<>();
        }

        public DictTupleBuilder with(@NotNull String key, @Nullable Object value) {
            values.put(key.toLowerCase(), value);
            return this;
        }

        public @NotNull DictTuple build() {
            return new DictTuple(values);
        }
    }
}
