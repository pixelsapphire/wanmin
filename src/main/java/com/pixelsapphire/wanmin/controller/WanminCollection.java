package com.pixelsapphire.wanmin.controller;

import com.pixelsapphire.wanmin.data.records.DatabaseRecord;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class WanminCollection<T extends DatabaseRecord> {

    @Contract("_, _ -> new")
    public static @NotNull <T extends DatabaseRecord> WanminCollection<T> flat(@NotNull String tableName,
                                                                               @NotNull Function<ResultSet, T> recordFactory) {
        return new FlatWanminCollection<>(tableName, recordFactory);
    }

    public abstract Stream<T> getAll();

    public static class FlatWanminCollection<T extends DatabaseRecord> extends WanminCollection<T> {

        private final @NotNull String tableName;
        private final @NotNull Function<ResultSet, T> recordFactory;

        public FlatWanminCollection(@NotNull String tableName, @NotNull Function<ResultSet, T> recordFactory) {
            this.tableName = tableName;
            this.recordFactory = recordFactory;
        }

        @Override
        public Stream<T> getAll() {
            return Stream.empty();
        }
    }
}
