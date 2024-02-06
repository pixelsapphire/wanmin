package com.pixelsapphire.wanmin.controller;

import com.pixelsapphire.wanmin.data.DictTuple;
import com.pixelsapphire.wanmin.data.records.DatabaseRecord;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class WanminCollection<T extends DatabaseRecord> {

    @Contract("_, _ -> new")
    public static @NotNull <T extends DatabaseRecord> WanminCollection<T> flat(@NotNull Function<DictTuple, T> recordFactory,
                                                                               @NotNull Supplier<List<DictTuple>> databaseAccessor) {
        return new FlatWanminCollection<>(recordFactory, databaseAccessor);
    }

    public abstract Stream<T> getAll();

    public static class FlatWanminCollection<T extends DatabaseRecord> extends WanminCollection<T> {

        private final @NotNull Function<DictTuple, T> recordFactory;
        private final @NotNull Supplier<List<DictTuple>> databaseAccessor;

        public FlatWanminCollection(@NotNull Function<DictTuple, T> recordFactory,
                                    @NotNull Supplier<List<DictTuple>> databaseAccessor) {
            this.recordFactory = recordFactory;
            this.databaseAccessor = databaseAccessor;
        }

        @Override
        public Stream<T> getAll() {
            return databaseAccessor.get().stream().map(recordFactory);
        }
    }
}
