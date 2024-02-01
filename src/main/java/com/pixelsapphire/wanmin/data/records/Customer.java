package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.DatabaseException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer implements DatabaseRecord {

    private final int cardNumber;
    private final @NotNull String firstName, lastName;
    private final int points;

    private Customer(int cardNumber, @NotNull String firstName, @NotNull String lastName, int points) {
        this.cardNumber = cardNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.points = points;
    }

    @Contract("_ -> new")
    public static @NotNull Customer fromRecord(@NotNull ResultSet record) {
        try {
            return new Customer(record.getInt("numer_karty"), record.getString("imie"),
                                record.getString("nazwisko"), record.getInt("punkty"));
        } catch (SQLException e) {
            throw new DatabaseException("Failed to create Customer from record", e);
        }
    }

    @Override
    public int getId() {
        return cardNumber;
    }

    public @NotNull String getFirstName() {
        return firstName;
    }

    public @NotNull String getLastName() {
        return lastName;
    }

    public int getPoints() {
        return points;
    }
}
