package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.DatabaseException;
import com.pixelsapphire.wanmin.data.DictTuple;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Employee implements DatabaseRecord {

    private final int id;
    private final @NotNull String firstName, lastName;
    private final @Nullable String username;

    private Employee(int id, @NotNull String firstName, @NotNull String lastName, @Nullable String username) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    @Contract("_ -> new")
    public static @NotNull Employee fromRecord(@NotNull DictTuple record) {
        try {
            return new Employee(record.getInt("id"), record.getString("imie"), record.getString("nazwisko"),
                                record.getOptionalString("login").orElse(null));
        } catch (IllegalArgumentException e) {
            throw new DatabaseException("Failed to create Employee from record " + record, e);
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

    public @Nullable String getUsername() {
        return username;
    }
}

