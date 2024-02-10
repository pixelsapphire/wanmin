package com.pixelsapphire.wanmin.controller;

import com.pixelsapphire.wanmin.DatabaseException;
import com.pixelsapphire.wanmin.Wanmin;
import com.pixelsapphire.wanmin.data.DictTuple;
import com.pixelsapphire.wanmin.data.WanminCollection;
import com.pixelsapphire.wanmin.data.records.*;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("SqlSourceToSinkFlow")
public class WanminDBController {

    private final @NotNull String username;
    private final Connection connection;
    public final WanminCollection<Contractor> contractors = new WanminCollection<>(
            Contractor::fromRecord,
            () -> executeReadOnly("SELECT * FROM wm_kontrahenci"));
    public final WanminCollection<Product> products = new WanminCollection<>(
            Product::fromRecord,
            () -> executeReadOnly("SELECT * FROM wm_produkty"));
    public final WanminCollection<ForeignInvoice> foreignInvoices = new WanminCollection<>(
            (in) -> ForeignInvoice.fromRecord(in, contractors,
                                              id -> executeReadOnly("SELECT * FROM wm_faktury_obce_pozycje WHERE faktura = %d", id)
                                                      .stream().map((it) -> ForeignInvoiceItem.fromRecord(it, products)).toList()),
            () -> executeReadOnly("SELECT * FROM wm_faktury_obce"));
    public final WanminCollection<StorageItem> storage = new WanminCollection<>(
            (r) -> StorageItem.fromRecord(r, products, foreignInvoices),
            () -> executeReadOnly("SELECT * FROM wm_magazyn"));
    public final WanminCollection<Recipe> recipes = new WanminCollection<>(
            (r) -> Recipe.fromRecord(r, id -> executeReadOnly("SELECT * FROM WM_PRZEPISY_SKLADNIKI WHERE przepis = %d", id)
                    .stream().map((it) -> RecipeIngredient.fromRecord(it, products)).toList()),
            () -> executeReadOnly("SELECT * FROM wm_przepisy"));
    public final WanminCollection<Customer> customers = new WanminCollection<>(
            Customer::fromRecord,
            () -> executeReadOnly("SELECT * FROM wm_klienci"));
    public final WanminCollection<Invoice> invoices = new WanminCollection<>(
            (r) -> Invoice.fromRecord(r, customers),
            () -> executeReadOnly("SELECT * FROM wm_faktury"));
//    public final WanminCollection<Order> orders = new WanminCollection<>(
//            (r) -> Order.fromRecord(r, customers, id -> executeReadOnly("SELECT * FROM WM_zamowienia_pozycje WHERE zamowienie = %id", id)
//                    .stream().map((it) -> OrderItem.fromRecord(it, disz -> executeReadOnly("SELECT * FROM WM_MENU_POZYCJE") .stream().map(MenuItem::fromRecord).toList())).toList()),
//            () -> executeReadOnly("SELECT * FROM WM_ZAMOWIENIA"));
    public final WanminCollection<Position> positions = new WanminCollection<>(
            Position::fromRecord,
            () -> executeReadOnly("SELECT * FROM wm_stanowiska"));
    public final WanminCollection<Employee> employees = new WanminCollection<>(
            (r) -> Employee.fromRecord(r, positions),
            () -> executeReadOnly("SELECT * FROM wm_pracownicy"));
    public final WanminCollection<EmploymentContract> employmentContracts = new WanminCollection<>(
            (r) -> EmploymentContract.fromRecord(r, employees, positions),
            () -> executeReadOnly("SELECT * FROM wm_umowy"));
    public final WanminCollection<Menu> menus = new WanminCollection<>(
            (r) -> Menu.fromRecord(r, id -> executeReadOnly("SELECT * FROM wm_menu_pozycje WHERE menu = %d", id)
                    .stream().map(MenuItem::fromRecord).toList()),
            () -> executeReadOnly("SELECT * FROM wm_menu"));

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

    public boolean isRoleEnabled(@NotNull String role) throws DatabaseException {
        final var firstRecord = executeReadOnly("SELECT sbd147412.wm_rola_przyznana('%s') AS przyznana FROM dual",
                                                role.toUpperCase()).getFirst();
        final var granted = firstRecord.getBoolean("przyznana");
        if (granted) executeReadOnly("SET ROLE %s", role.toUpperCase());
        return granted;
    }

    public @NotNull List<DictTuple> executeReadOnly(@NotNull String query, Object... parameters) {
        try (final var statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            final String sql = String.format(query, parameters);
            try (final var result = statement.executeQuery(sql)) {
                final List<DictTuple> list = new ArrayList<>();
                while (result.next()) list.add(DictTuple.from(result));
                return list;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Nie udało się wykonać zapytania", e);
        }
    }
}
