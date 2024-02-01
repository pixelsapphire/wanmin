package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.DatabaseException;
import com.pixelsapphire.wanmin.controller.Provider;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Employee implements DatabaseRecord {

    private final int id;
    private final @NotNull String firstName, lastName;
    private final Position position;
    private final EmploymentContract contract;

    private Employee(int id, @NotNull String firstName, @NotNull String lastName, @NotNull Position position, @NotNull EmploymentContract contract) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.contract = contract;
    }

    @Contract("_, _, _ -> new")
    public static @NotNull Employee fromRecord(@NotNull ResultSet record, @NotNull Provider<Position> positionProvider,
                                               @NotNull Provider<EmploymentContract> contractProvider) {
        try {
            return new Employee(record.getInt("id"), record.getString("imie"), record.getString("nazwisko"),
                                positionProvider.getByKey(record.getString("stanowisko")),
                                contractProvider.getByKey(record.getString("numer_umowy")));
        } catch (SQLException e) {
            throw new DatabaseException("Failed to create Employee from record", e);
        }
    }

    public @NotNull String getFirstName() {
        return firstName;
    }

    public @NotNull String getLastName() {
        return lastName;
    }

    public @NotNull Position getPosition() {
        return position;
    }

    public @NotNull EmploymentContract getContract() {
        return contract;
    }

    @Override
    public int getId() {
        return id;
    }
}

