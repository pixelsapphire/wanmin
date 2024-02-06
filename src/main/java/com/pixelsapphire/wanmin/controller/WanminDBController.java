package com.pixelsapphire.wanmin.controller;

import com.pixelsapphire.wanmin.DatabaseException;
import com.pixelsapphire.wanmin.Wanmin;
import com.pixelsapphire.wanmin.data.DictTuple;
import com.pixelsapphire.wanmin.data.records.*;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("SqlSourceToSinkFlow")
public class WanminDBController {

    private final Connection connection;
    public final WanminCollection<Contractor> contractors = WanminCollection.flat(Contractor::fromRecord,
                                                                                  () -> executeReadOnly("SELECT * FROM wm_kontrahenci"));
    public final WanminCollection<Customer> customers = WanminCollection.flat(Customer::fromRecord,
                                                                              () -> executeReadOnly("SELECT * FROM wm_klienci"));
    public final WanminCollection<EmploymentContract> employmentContracts = WanminCollection.flat(EmploymentContract::fromRecord,
                                                                                                  () -> executeReadOnly("SELECT * FROM wm_umowy"));
    public final WanminCollection<Position> positions = WanminCollection.flat(Position::fromRecord,
                                                                              () -> executeReadOnly("SELECT * FROM wm_stanowiska"));
    public final WanminCollection<Product> products = WanminCollection.flat(Product::fromRecord,
                                                                            () -> executeReadOnly("SELECT * FROM wm_produkty"));

    public WanminDBController(@NotNull String username, char[] password) {
        this.connection = createConnection("jdbc:oracle:thin", "admlab2.cs.put.poznan.pl", 1521,
                                           "dblab03_students.cs.put.poznan.pl", username, password);
    }

    @SuppressWarnings("SameParameterValue")
    private static @NotNull Connection createConnection(@NotNull String driver, @NotNull String host, int port, @NotNull String service,
                                                        @NotNull String username, char @NotNull [] password) {
        final var connectionProps = new Properties();
        connectionProps.put("user", username);
        connectionProps.put("password", new String(password));
        DriverManager.setLoginTimeout(5);
        try {
            final var connection = DriverManager.getConnection(driver + ":@//" + host + ":" + port + "/" + service, connectionProps);
            Logger.getLogger(Wanmin.class.getName()).log(Level.INFO, "Połączono z bazą danych");
            return connection;
        } catch (SQLException e) {
            throw new DatabaseException("Nie udało się połączyć z bazą danych", e);
        }
    }

    public @NotNull List<DictTuple> executeReadOnly(@NotNull String query) {
        try (final var statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            try (final var result = statement.executeQuery(query)) {
                final List<DictTuple> list = new ArrayList<>();
                while (result.next()) list.add(DictTuple.from(result));
                return list;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Nie udało się wykonać zapytania", e);
        }
    }

    public <T> @NotNull List<T> executeReadOnly(@NotNull String query, @NotNull Function<DictTuple, T> mapper) {
        return executeReadOnly(query).stream().map(mapper).toList();
    }
}
