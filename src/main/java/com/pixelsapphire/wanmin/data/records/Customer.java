package com.pixelsapphire.wanmin.data.records;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer implements DatabaseRecord {

    private final int cardNumber;
    private final @NotNull String name, surname;
    private final int points;

    private Customer(int cardNumber, @NotNull String name, @NotNull String surname, int points) {
        this.cardNumber = cardNumber;
        this.name = name;
        this.surname = surname;
        this.points = points;
    }

    @Contract("_ -> new")
    public static @NotNull Customer fromRecord(@NotNull ResultSet record) throws SQLException {
        return new Customer(record.getInt("numer_karty"), record.getString("imie"),
                record.getString("nazwisko"), record.getInt("punkty"));
    }

    @Override
    public int getId() {
        return cardNumber;
    }

    @Override
    public @NotNull String getTableName() {
        return "wm_klienci";
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getPoints() {
        return points;
    }
}
