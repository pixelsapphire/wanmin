package com.pixelsapphire.wanmin.data;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Contractor {

    private final @NotNull String name, address;
    private String phone, email, nip;

    private Contractor(@NotNull String name, @NotNull String address, String phone, String email, String nip) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.nip = nip;
    }

    @Contract("_ -> new")
    public static @NotNull Contractor fromRecord(@NotNull ResultSet record) throws SQLException {
        return new Contractor(record.getString("nazwa"), record.getString("adres"),
                              record.getString("telefon"), record.getString("email"), record.getString("NIP"));
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
}
