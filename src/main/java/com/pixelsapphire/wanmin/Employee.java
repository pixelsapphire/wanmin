package com.pixelsapphire.wanmin;

import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Employee {

    private final int id, team;
    private final @NotNull String surname, job;
    private final float salary;

    private Employee(int id, @NotNull String surname, @NotNull String job, @NotNull float salary, int team) {
        this.id = id;
        this.surname = surname;
        this.job = job;
        this.salary = salary;
        this.team = team;
    }

    public static Employee fromResult(@NotNull ResultSet record) throws SQLException {
        return new Employee(record.getInt("id_prac"), record.getString("nazwisko"), record.getString("etat"),
                            record.getFloat("placa_pod"), record.getInt("id_zesp"));
    }

    public int getId() {
        return id;
    }

    public @NotNull String getSurname() {
        return surname;
    }

    public @NotNull String getJob() {
        return job;
    }

    public float getSalary() {
        return salary;
    }

    public int getTeam() {
        return team;
    }

    @Override
    public String toString() {
        return String.format("%4d %-12s %-10s %6s %4d", id, surname, job, salary, team);
    }
}

