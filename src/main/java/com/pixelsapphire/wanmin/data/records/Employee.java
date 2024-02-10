package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.DatabaseException;
import com.pixelsapphire.wanmin.controller.Provider;
import com.pixelsapphire.wanmin.data.DictTuple;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Employee implements DatabaseRecord {

    private final int id;
    private final @NotNull String firstName, lastName, username;

    private Employee(int id, @NotNull String firstName, @NotNull String lastName, @NotNull String username) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    @Contract("_, _ -> new")
    public static @NotNull Employee fromRecord(@NotNull DictTuple record, @NotNull Provider<Position> positionProvider) {
        try {
            return new Employee(record.getInt("id"), record.getString("imie"), record.getString("nazwisko"), record.getString("username"));
        } catch (IllegalArgumentException e) {
            throw new DatabaseException("Failed to create Employee from record", e);
        }
    }

    public @NotNull String getFirstName() {
        return firstName;
    }

    public @NotNull String getLastName() {
        return lastName;
    }

    @Override
    public int getId() {
        return id;
    }

    public @NotNull String getUsername() {
        return username;
    }
}

