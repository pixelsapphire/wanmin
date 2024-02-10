package com.pixelsapphire.wanmin.util;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.*;

public class StreamAdapter<T> implements Stream<T> {

    private final @NotNull Stream<T> stream;

    private StreamAdapter(@NotNull Stream<T> stream) {
        this.stream = stream;
    }

    public static <T> @NotNull StreamAdapter<T> wrap(@NotNull Stream<T> stream) {
        return new StreamAdapter<>(stream);
    }

    @Override
    public StreamAdapter<T> filter(Predicate<? super T> predicate) {
        return wrap(stream.filter(predicate));
    }

    @Override
    public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
        return wrap(stream.map(mapper));
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        return stream.mapToInt(mapper);
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        return stream.mapToLong(mapper);
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        return stream.mapToDouble(mapper);
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return wrap(stream.flatMap(mapper));
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return stream.flatMapToInt(mapper);
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return stream.flatMapToLong(mapper);
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return stream.flatMapToDouble(mapper);
    }

    @Override
    public Stream<T> distinct() {
        return wrap(stream.distinct());
    }

    @Override
    public Stream<T> sorted() {
        return wrap(stream.sorted());
    }

    @Override
    public Stream<T> sorted(Comparator<? super T> comparator) {
        return wrap(stream.sorted(comparator));
    }

    @Override
    public Stream<T> peek(Consumer<? super T> action) {
        return wrap(stream.peek(action));
    }

    @Override
    public Stream<T> limit(long maxSize) {
        return wrap(stream.limit(maxSize));
    }

    @Override
    public Stream<T> skip(long n) {
        return wrap(stream.skip(n));
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        stream.forEach(action);
    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {
        stream.forEachOrdered(action);
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return stream.toArray();
    }

    @NotNull
    @Override
    public <U> U[] toArray(IntFunction<U[]> generator) {
        return stream.toArray(generator);
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return stream.reduce(identity, accumulator);
    }

    @NotNull
    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return stream.reduce(accumulator);
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        return stream.reduce(identity, accumulator, combiner);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return stream.collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return stream.collect(collector);
    }

    @NotNull
    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return stream.min(comparator);
    }

    @NotNull
    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return stream.max(comparator);
    }

    @Override
    public long count() {
        return stream.count();
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return stream.anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return stream.allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        return stream.noneMatch(predicate);
    }

    @NotNull
    @Override
    public Optional<T> findFirst() {
        return stream.findFirst();
    }

    @NotNull
    @Override
    public Optional<T> findAny() {
        return stream.findAny();
    }

    public void forEachIndexed(@NotNull BiConsumer<Integer, ? super T> action) {
        final var index = new int[1];
        stream.forEachOrdered(t -> action.accept(index[0]++, t));
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return stream.iterator();
    }

    @NotNull
    @Override
    public Spliterator<T> spliterator() {
        return stream.spliterator();
    }

    @Override
    public boolean isParallel() {
        return stream.isParallel();
    }

    @NotNull
    @Override
    public Stream<T> sequential() {
        return wrap(stream.sequential());
    }

    @NotNull
    @Override
    public Stream<T> parallel() {
        return wrap(stream.parallel());
    }

    @NotNull
    @Override
    public Stream<T> unordered() {
        return wrap(stream.unordered());
    }

    @NotNull
    @Override
    public Stream<T> onClose(Runnable closeHandler) {
        return wrap(stream.onClose(closeHandler));
    }

    @Override
    public void close() {
        stream.close();
    }
}
