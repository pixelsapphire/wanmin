package com.pixelsapphire.wanmin.controller.collections;

import com.pixelsapphire.wanmin.controller.DatabaseExecutor;
import com.pixelsapphire.wanmin.data.DictTuple;
import com.pixelsapphire.wanmin.data.WanminCollection;
import com.pixelsapphire.wanmin.data.records.Position;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PositionsCollection extends WanminCollection<Position> {

    public PositionsCollection(@NotNull DatabaseExecutor controller) {
        super(controller);
    }

    @Override
    public @NotNull Position elementFromRecord(@NotNull DictTuple record) {
        return Position.fromRecord(record);
    }

    @Override
    public @NotNull DictTuple elementToRecord(@NotNull Position position) {
        return position.toRecord();
    }

    @Override
    public @NotNull List<DictTuple> getRecords() {
        return controller.executeQuery("SELECT * FROM sbd147412.wm_stanowiska");
    }

    @Override
    public void saveRecord(@NotNull DictTuple r) {
        controller.executeDML("INSERT INTO sbd147412.wm_stanowiska (nazwa, pensja) VALUES (?,?)",
                              r.getString("nazwa"), r.getFloat("pensja"));
    }

    public void deletePosition (int positionId) {
        controller.executeDML("DELETE FROM sbd147412.wm_stanowiska where id = ?", positionId);
    }

    public void updatePosition (@NotNull Position position) {
        controller.executeDML("UPDATE sbd147412.wm_stanowiska SET nazwa = ?, pensja = ? WHERE id = ?",
                position.getName(), position.getSalary(), position.getId());
    }
}
