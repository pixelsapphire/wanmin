package com.pixelsapphire.wanmin.data;

import com.pixelsapphire.wanmin.data.records.DatabaseRecord;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface WanminCollection<T extends DatabaseRecord> {

    @Contract("_, _ -> new")
    static @NotNull <T extends DatabaseRecord> WanminCollection<T> flat(@NotNull Function<DictTuple, T> recordFactory,
                                                                        @NotNull Supplier<List<DictTuple>> databaseAccessor) {
        return new FlatWanminCollection<>(recordFactory, databaseAccessor);
    }

    Stream<T> getAll();

    class FlatWanminCollection<T extends DatabaseRecord> implements WanminCollection<T> {

        private final @NotNull Function<DictTuple, T> recordFactory;
        private final @NotNull Supplier<List<DictTuple>> databaseAccessor;

        private FlatWanminCollection(@NotNull Function<DictTuple, T> recordFactory, @NotNull Supplier<List<DictTuple>> databaseAccessor) {
            this.recordFactory = recordFactory;
            this.databaseAccessor = databaseAccessor;
        }

        @Override
        public Stream<T> getAll() {
            return databaseAccessor.get().stream().map(recordFactory);
        }
    }

    class CompositeWanminCollection<T extends DatabaseRecord> implements WanminCollection<T> {

        @Override
        public Stream<T> getAll() {
            return Stream.empty();
        }
    }
}
