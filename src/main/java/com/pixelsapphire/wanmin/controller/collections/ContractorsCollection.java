package com.pixelsapphire.wanmin.controller.collections;

import com.pixelsapphire.wanmin.controller.DatabaseExecutor;
import com.pixelsapphire.wanmin.data.DictTuple;
import com.pixelsapphire.wanmin.data.WanminCollection;
import com.pixelsapphire.wanmin.data.records.Contractor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ContractorsCollection extends WanminCollection<Contractor> {

    public ContractorsCollection(@NotNull DatabaseExecutor controller) {
        super(controller);
    }

    @Override
    @Contract(pure = true)
    public @NotNull Contractor elementFromRecord(@NotNull DictTuple record) {
        return Contractor.fromRecord(record);
    }

    @Override
    @Contract(pure = true)
    public @NotNull DictTuple elementToRecord(@NotNull Contractor contractor) {
        return contractor.toRecord();
    }

    @Override
    public @NotNull List<DictTuple> getRecords() {
        return controller.executeQuery("SELECT * FROM sbd147412.wm_kontrahenci");
    }

    @Override
    public void saveRecord(@NotNull DictTuple r) {
        controller.executeDML("INSERT INTO sbd147412.wm_kontrahenci (nazwa, adres, telefon, email, NIP) VALUES (?,?,?,?,?)",
                              r.getString("nazwa"), r.getString("adres"), r.getOptionalString("telefon").orElse(null),
                              r.getOptionalString("email").orElse(null), r.getOptionalString("NIP").orElse(null));
    }
}
