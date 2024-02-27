package com.pixelsapphire.wanmin.controller.collections;

import com.pixelsapphire.wanmin.controller.DatabaseExecutor;
import com.pixelsapphire.wanmin.controller.Provider;
import com.pixelsapphire.wanmin.data.DictTuple;
import com.pixelsapphire.wanmin.data.WanminCollection;
import com.pixelsapphire.wanmin.data.records.Employee;
import com.pixelsapphire.wanmin.data.records.EmploymentContract;
import com.pixelsapphire.wanmin.data.records.Position;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EmploymentContractsCollection extends WanminCollection<EmploymentContract> {

    private final @NotNull Provider<Employee> employees;
    private final @NotNull Provider<Position> positions;

    public EmploymentContractsCollection(@NotNull DatabaseExecutor controller,
                                         @NotNull Provider<Employee> employees, @NotNull Provider<Position> positions) {
        super(controller);
        this.employees = employees;
        this.positions = positions;
    }

    @Override
    public @NotNull EmploymentContract elementFromRecord(@NotNull DictTuple record) {
        return EmploymentContract.fromRecord(record, employees, positions);
    }

    @Override
    public @NotNull DictTuple elementToRecord(@NotNull EmploymentContract contract) {
        return contract.toRecord();
    }

    @Override
    public @NotNull List<DictTuple> getRecords() {
        return controller.executeQuery("SELECT * FROM sbd147412.wm_umowy");
    }

    @Override
    public void saveRecord(@NotNull DictTuple r) {
        controller.executeDML("INSERT INTO sbd147412.wm_umowy (numer, typ, pracownik, stanowisko, zawiazana) VALUES (?,?,?,?,?)",
                              r.getString("numer"), r.getString("typ"), r.getInt("pracownik"),
                              r.getInt("stanowisko"), r.getDate("zawiazana"));
    }

    public void deleteContract(int contractId) {
        controller.executeDML("DELETE FROM sbd147412.wm_umowy WHERE ID = ?", contractId);
    }

    public void updateEmploymentContract(@NotNull EmploymentContract contract) {
        controller.executeDML("UPDATE sbd147412.wm_umowy SET numer = ?, typ = ?, pracownik = ?, stanowisko = ?, zawiazana = ?, zerwana = ? WHERE id = ?",
                              contract.getNumber(), contract.getType(), contract.getEmployee().getId(),
                              contract.getPosition().getId(), contract.getConcluded(), contract.getId(), contract.getTerminated());
    }
}
