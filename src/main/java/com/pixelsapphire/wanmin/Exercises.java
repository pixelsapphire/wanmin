package com.pixelsapphire.wanmin;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Exercises {

    public static void ex1(@NotNull Connection connection) throws SQLException {

        try (final var statement = connection.createStatement()) {
            try (final var result = statement.executeQuery("SELECT * FROM PRACOWNICY")) {

                final List<Employee> employees = new ArrayList<>();
                while (result.next()) employees.add(Employee.fromResult(result));

                System.out.printf("Zatrudniono %d pracowników, w tym:\n", employees.size());
                final Set<Integer> teams = employees.stream().map(Employee::getTeam).collect(Collectors.toSet());
                for (final var team : teams)
                    System.out.printf("  %d w zespole %d,\n", employees.stream().filter(employee -> employee.getTeam() == team).count(), team);
            }
        }
    }

    public static void ex2(@NotNull Connection connection) throws SQLException {

        try (final var statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            try (final var result = statement.executeQuery("SELECT * FROM pracownicy WHERE etat = 'ASYSTENT' ORDER BY placa_pod DESC")) {

                System.out.println("Najmniej zarabiający asystent:");
                result.last();
                System.out.println(Employee.fromResult(result));

                System.out.println("Trzeci najmniej zarabiający asystent:");
                result.relative(-2);
                System.out.println(Employee.fromResult(result));

                System.out.println("Przedostatni asystent w rankingu najmniej zarabiających asystentów:");
                result.next();
                System.out.println(Employee.fromResult(result));
            }
        }
    }
}

