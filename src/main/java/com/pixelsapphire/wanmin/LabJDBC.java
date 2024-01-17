package com.pixelsapphire.wanmin;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings({"SameParameterValue"})
public class LabJDBC {

    public static void main(String[] args) {

        try (final Connection connection = createConnection("jdbc:oracle:thin", "admlab2.cs.put.poznan.pl", 1521,
                                                            "dblab03_students.cs.put.poznan.pl", "sbd147412", "sbd147412")) {
            Exercises.ex1(connection);
            Exercises.ex2(connection);
        } catch (SQLException e) {
            if (e.getErrorCode() != 17002) Logger.getLogger(LabJDBC.class.getName()).log(Level.SEVERE, "Wystąpił błąd SQL", e);
            System.exit(-1);
        }
    }

    private static @NotNull Connection createConnection(@NotNull String driver, @NotNull String host, int port, @NotNull String service,
                                                        @NotNull String username, @NotNull String password) throws SQLException {
        final var connectionProps = new Properties();
        connectionProps.put("user", username);
        connectionProps.put("password", password);
        try {
            final var connection = DriverManager.getConnection(driver + ":@//" + host + ":" + port + "/" + service, connectionProps);
            Logger.getLogger(LabJDBC.class.getName()).log(Level.INFO, "Połączono z bazą danych");
            return connection;
        } catch (SQLException e) {
            Logger.getLogger(LabJDBC.class.getName()).log(Level.SEVERE, "Nie udało się połączyć z bazą danych", e);
            throw e;
        }
    }
}

