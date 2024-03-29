package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.DatabaseException;
import com.pixelsapphire.wanmin.data.DictTuple;
import com.pixelsapphire.wanmin.data.DictTuple.DictTupleBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Customer implements DatabaseRecord {

    private final int cardNumber;
    private final @NotNull String firstName, lastName;
    private final int points;

    public Customer(int cardNumber, @NotNull String firstName, @NotNull String lastName, int points) {
        this.cardNumber = cardNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.points = points;
    }

    public Customer(@NotNull String firstName, @NotNull String lastName, int points) {
        this(DatabaseRecord.ID_UNINITIALIZED, firstName, lastName, points);
    }

    @Contract("_ -> new")
    public static @NotNull Customer fromRecord(@NotNull DictTuple record) {
        try {
            return new Customer(record.getInt("id"), record.getString("imie"),
                                record.getString("nazwisko"), record.getInt("punkty"));
        } catch (IllegalArgumentException e) {
            throw new DatabaseException("Failed to create Customer from record" + record, e);
        }
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

    @Override
    public int getId() {
        return cardNumber;
    }

    @Override
    public @NotNull DictTuple toRecord() {
        return new DictTupleBuilder().with("id", cardNumber)
                                     .with("imie", firstName)
                                     .with("nazwisko", lastName)
                                     .with("punkty", points)
                                     .build();
    }

    @Override
    public String toString() {
        //if (points == -1) return "klient niezarejestrowany";
        return firstName + " " + lastName + " [#" + cardNumber + "]";
    }
}
