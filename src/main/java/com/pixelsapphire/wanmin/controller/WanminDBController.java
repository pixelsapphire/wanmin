package com.pixelsapphire.wanmin.controller;

import com.pixelsapphire.wanmin.DatabaseException;
import com.pixelsapphire.wanmin.Wanmin;
import com.pixelsapphire.wanmin.controller.collections.*;
import com.pixelsapphire.wanmin.data.DictTuple;
import com.pixelsapphire.wanmin.data.records.MenuItem;
import com.pixelsapphire.wanmin.data.records.OrderItem;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("SqlSourceToSinkFlow")
public class WanminDBController implements DatabaseExecutor {

    public final ContractorsCollection contractors = new ContractorsCollection(this);
    public final ProductsCollection products = new ProductsCollection(this);
    public final CustomersCollection customers = new CustomersCollection(this);
    public final PositionsCollection positions = new PositionsCollection(this);
    public final EmployeesCollection employees = new EmployeesCollection(this);
    public final RecipesCollection recipes = new RecipesCollection(this, products);
    public final ForeignInvoiceCollection foreignInvoices = new ForeignInvoiceCollection(this, contractors, products);
    public final StorageItemCollection storage = new StorageItemCollection(this, products, foreignInvoices);
    public final EmploymentContractsCollection employmentContracts = new EmploymentContractsCollection(this, employees, positions);
    private final @NotNull String username;
    private final Connection connection;
    public final Provider<MenuItem> menuItemProvider = id -> executeQuery("SELECT * FROM sbd147412.wm_menu_pozycje WHERE id = ?", id)
            .stream().map((r) -> MenuItem.fromRecord(r, recipes)).findFirst().orElseThrow();
    public final Provider<List<OrderItem>> orderItemsProvider = id -> executeQuery("SELECT * FROM sbd147412.wm_zamowienia_pozycje WHERE zamowienie = ?", id)
            .stream().map(r -> OrderItem.fromRecord(r, menuItemProvider)).toList();
    public final OrdersCollection orders = new OrdersCollection(this, employees, customers, orderItemsProvider);
    public final InvoicesCollection invoices = new InvoicesCollection(this, customers, orders);
    public final Provider<List<MenuItem>> menuItemsProvider = id -> executeQuery("SELECT * FROM sbd147412.wm_menu_pozycje WHERE menu = ?", id)
            .stream().map((r) -> MenuItem.fromRecord(r, recipes)).toList();
    public final MenuCollection menus = new MenuCollection(this, menuItemsProvider);

    public WanminDBController(@NotNull String username, char[] password) {
        this.username = username;
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

    public @NotNull String getUsername() {
        return username;
    }

    public int getEmployeeId() {
        return executeQuery("SELECT sbd147412.wm_my_id(?) AS id FROM dual", username.toUpperCase()).getFirst().getInt("id");
    }

    public boolean isRoleEnabled(@NotNull String role) throws DatabaseException {
        final var firstRecord = executeQuery("SELECT sbd147412.wm_rola_przyznana(?) AS przyznana FROM dual",
                                             role.toUpperCase()).getFirst();
        final var granted = firstRecord.getBoolean("przyznana");
        if (granted) executeDML("SET ROLE " + role.toUpperCase());
        return granted;
    }

    @Override
    public List<DictTuple> executeQuery(@NotNull String sql, Object @NotNull ... params) {
        try {
            final var statement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) statement.setObject(i + 1, params[i]);
            try (final var result = statement.executeQuery()) {
                final List<DictTuple> list = new ArrayList<>();
                while (result.next()) list.add(DictTuple.from(result));
                return list;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Nie udało się wykonać zapytania", e);
        }
    }

    @Override
    public void executeDML(@NotNull String sql, Object @NotNull ... params) {
        try {
            final var statement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++)
                statement.setObject(i + 1, params[i]);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Nie udało się wykonać polecenia DML", e);
        }
    }
}
