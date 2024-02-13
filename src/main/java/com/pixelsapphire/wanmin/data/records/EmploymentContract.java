package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.DatabaseException;
import com.pixelsapphire.wanmin.controller.Provider;
import com.pixelsapphire.wanmin.data.DictTuple;
import com.pixelsapphire.wanmin.data.DictTuple.DictTupleBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public class EmploymentContract implements DatabaseRecord {

    private final int id;
    private final @NotNull String type;
    private final @NotNull Employee employee;
    private final @NotNull Position position;
    private final @NotNull Date concluded;
    private final @Nullable Date terminated;

    private EmploymentContract(int id, @NotNull String type, @NotNull Employee employee, @NotNull Position position,
                               @NotNull Date concluded, @Nullable Date terminated) {
        this.id = id;
        this.type = type;
        this.employee = employee;
        this.position = position;
        this.concluded = concluded;
        this.terminated = terminated;
    }

    @Contract("_, _, _ -> new")
    public static @NotNull EmploymentContract fromRecord(@NotNull DictTuple record,
                                                         @NotNull Provider<Employee> employeeProvider,
                                                         @NotNull Provider<Position> positionProvider) {
        try {
            return new EmploymentContract(record.getInt("id"), record.getString("typ"),
                                          employeeProvider.getById(record.getInt("pracownik")),
                                          positionProvider.getById(record.getInt("stanowisko")),
                                          record.getDate("zawiazana"), record.getDate("zerwana"));
        } catch (IllegalArgumentException e) {
            throw new DatabaseException("Failed to create EmploymentContract from record" + record, e);
        }
    }

    public @NotNull String getType() {
        return type;
    }

    public @NotNull Employee getEmployee() {
        return employee;
    }

    public @NotNull Position getPosition() {
        return position;
    }

    public @NotNull Date getConcluded() {
        return concluded;
    }

    public @Nullable Date getTerminated() {
        return terminated;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public @NotNull DictTuple toRecord() {
        return new DictTupleBuilder().with("id", id)
                                     .with("typ", type)
                                     .with("pracownik", employee.getId())
                                     .with("stanowisko", position.getId())
                                     .with("zawiazana", concluded)
                                     .with("zerwana", terminated)
                                     .build();
    }
}
