package com.pixelsapphire.wanmin.data;

import com.pixelsapphire.wanmin.controller.DatabaseExecutor;
import com.pixelsapphire.wanmin.controller.Provider;
import com.pixelsapphire.wanmin.data.records.DatabaseRecord;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class WanminCollection<T extends DatabaseRecord> implements Provider<T> {

    protected final @NotNull DatabaseExecutor controller;

    protected WanminCollection(@NotNull DatabaseExecutor controller) {
        this.controller = controller;
    }

    @Contract(pure = true)
    public abstract @NotNull T elementFromRecord(@NotNull DictTuple record);

    @Contract(pure = true)
    public abstract @NotNull DictTuple elementToRecord(@NotNull T object);

    public abstract @NotNull List<DictTuple> getRecords();

    public abstract void saveRecord(@NotNull DictTuple record);

    public @NotNull Stream<T> getAll() {
        return getRecords().stream().map(this::elementFromRecord);
    }

    public @NotNull Stream<T> getAllWhere(@NotNull Predicate<T> predicate) {
        return getAll().filter(predicate);
    }

    public @NotNull T getFirstWhere(@NotNull Predicate<T> predicate) {
        return getAll().filter(predicate).findFirst().orElseThrow();
    }

    @Override
    public @NotNull T getById(int id) {
        return getAll().filter(record -> record.getId() == id).findFirst().orElseThrow();
    }

    public void add(@NotNull T object) {
        saveRecord(elementToRecord(object));
    }
}