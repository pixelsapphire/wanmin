package com.pixelsapphire.wanmin.data;

import com.pixelsapphire.wanmin.controller.Provider;
import com.pixelsapphire.wanmin.data.records.DatabaseRecord;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class WanminCollection<T extends DatabaseRecord> implements Provider<T> {

    private final @NotNull Function<DictTuple, T> recordFactory;
    private final @NotNull Supplier<List<DictTuple>> databaseAccessor;

    public WanminCollection(@NotNull Function<DictTuple, T> recordFactory, @NotNull Supplier<List<DictTuple>> databaseAccessor) {
        this.recordFactory = recordFactory;
        this.databaseAccessor = databaseAccessor;
    }

    public Stream<T> getAll() {
        return databaseAccessor.get().stream().map(recordFactory);
    }

    @Override
    public @NotNull T getById(int id) {
        return getAll().filter(record -> record.getId() == id).findFirst().orElseThrow();
    }
}