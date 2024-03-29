package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.DatabaseException;
import com.pixelsapphire.wanmin.data.DictTuple;
import com.pixelsapphire.wanmin.data.DictTuple.DictTupleBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Position implements DatabaseRecord {

    private final int id;
    private final @NotNull String name;
    private final float salary;

    public Position(int id, @NotNull String name, float salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    @Contract("_ -> new")
    public static @NotNull Position fromRecord(@NotNull DictTuple record) {
        try {
            return new Position(record.getInt("id"), record.getString("nazwa"), record.getFloat("pensja"));
        } catch (IllegalArgumentException e) {
            throw new DatabaseException("Failed to create Position from record" + record, e);
        }
    }

    public @NotNull String getName() {
        return name;
    }

    public float getSalary() {
        return salary;
    }

    @Override
    public int getId() {
        return id;
    }

    @Contract("-> new")
    public @NotNull DictTuple toRecord() {
        return new DictTupleBuilder().with("id", id)
                                     .with("nazwa", name)
                                     .with("pensja", salary)
                                     .build();
    }
}
