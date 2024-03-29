package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.DatabaseException;
import com.pixelsapphire.wanmin.data.DictTuple;
import com.pixelsapphire.wanmin.data.DictTuple.DictTupleBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Contractor implements DatabaseRecord {

    private final int id;
    private final @NotNull String name, address;
    private final String phone, email, nip;

    public Contractor(int id, @NotNull String name, @NotNull String address, String phone, String email, String nip) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.nip = nip;
    }

    @Contract("_ -> new")
    public static @NotNull Contractor fromRecord(@NotNull DictTuple record) {
        try {
            return new Contractor(record.getInt("id"), record.getString("nazwa"), record.getString("adres"),
                                  record.getString("telefon"), record.getString("email"), record.getString("NIP"));
        } catch (IllegalArgumentException e) {
            throw new DatabaseException("Failed to create Contractor from record" + record, e);
        }
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getNIP() {
        return nip;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public @NotNull DictTuple toRecord() {
        return new DictTupleBuilder().with("id", id)
                                     .with("nazwa", name)
                                     .with("adres", address)
                                     .with("telefon", phone)
                                     .with("email", email)
                                     .with("NIP", nip)
                                     .build();
    }
}
