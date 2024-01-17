package com.pixelsapphire.wanmin.data;

import com.pixelsapphire.wanmin.controller.Provider;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Employee {

    private final @NotNull String firstName, lastName;
    private Position position;
    private EmploymentContract contract;

    private Employee(@NotNull String firstName, @NotNull String lastName, @NotNull Position position, @NotNull EmploymentContract contract) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.contract = contract;
    }

    @Contract("_, _, _ -> new")
    public static @NotNull Employee fromResult(@NotNull ResultSet record, @NotNull Provider<Position> positionProvider,
                                               @NotNull Provider<EmploymentContract> contractProvider) throws SQLException {
        return new Employee(record.getString("imie"), record.getString("nazwisko"),
                            positionProvider.getByValue(record.getString("stanowisko")),
                            contractProvider.getByValue(record.getString("numer_umowy")));
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
}

