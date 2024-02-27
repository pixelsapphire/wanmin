package com.pixelsapphire.wanmin.controller.collections;

import com.pixelsapphire.wanmin.controller.DatabaseExecutor;
import com.pixelsapphire.wanmin.data.DictTuple;
import com.pixelsapphire.wanmin.data.WanminCollection;
import com.pixelsapphire.wanmin.data.records.Employee;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EmployeesCollection extends WanminCollection<Employee> {

    public EmployeesCollection(@NotNull DatabaseExecutor controller) {
        super(controller);
    }

    @Override
    public @NotNull Employee elementFromRecord(@NotNull DictTuple record) {
        return Employee.fromRecord(record);
    }

    @Override
    public @NotNull DictTuple elementToRecord(@NotNull Employee employee) {
        return employee.toRecord();
    }

    @Override
    public @NotNull List<DictTuple> getRecords() {
        return controller.executeQuery("SELECT * FROM sbd147412.wm_pracownicy");
    }

    @Override
    public void saveRecord(@NotNull DictTuple r) {
        controller.executeDML("INSERT INTO sbd147412.wm_pracownicy (imie, nazwisko, login) VALUES (?,?,?)",
                              r.getString("imie"), r.getString("nazwisko"), r.getOptionalString("login").orElse(null));
    }

    public void deleteEmployee(int employeeId) {
        controller.executeDML("DELETE FROM sbd147412.wm_pracownicy WHERE ID = ?", employeeId);
    }

    public void updateEmployee(@NotNull Employee employee) {
        controller.executeDML("UPDATE sbd147412.wm_pracownicy SET imie = ?, nazwisko = ?, login = ? WHERE id = ?",
                              employee.getFirstName(), employee.getLastName(), employee.getUsername(), employee.getId());
    }
}
