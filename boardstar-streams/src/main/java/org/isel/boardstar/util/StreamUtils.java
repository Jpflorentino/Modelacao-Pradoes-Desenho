package org.isel.boardstar.util;

import java.util.ArrayList;
import java.util.Spliterator;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtils {

    public static <T> Supplier<Stream<T>> cache(Stream<T> src) {
        Spliterator<T> spliterator = src.spliterator();
        ArrayList<T> arrayToFill = new ArrayList<>();
        return () -> {
            Spliterator<T> spliteratorAux = new SpliteratorCache<>(spliterator, arrayToFill);
            return StreamSupport.stream(spliteratorAux, false);
        };
    }

    /// JM problemas com o spliterator
    public static <T> Stream<T> interleave(Stream<T> src, Stream<T> other) {
        Spliterator<T> spliterator1 = src.spliterator();
        Spliterator<T> spliterator2 = other.spliterator();
        SpliteratorInterleave<T> i = new SpliteratorInterleave<>(spliterator1, spliterator2);
        return StreamSupport.stream(i, false);
    }

    /// JM ok
    public static <T> Stream<T> intersection(Stream<T> src, Stream<T> other) {
        //List<T> list = other.collect(Collectors.toList());
        //return src.filter((element) -> list.contains(element));

        Supplier<Stream<T>> otherCached = cache(other);
        return src.filter((element) -> otherCached.get().anyMatch(element::equals));
    }

    public static Stream<String> concat(Stream<String> headers, Stream<Stream<String>> contents) {
        //return interleave(headers, contents.flatMap(Stream::sequential));
        Stream<Stream<String>> head = headers.map(Stream::of); //return Stream.of(element);

        Stream<Stream<String>> res = interleave(head, contents);
        return res.flatMap(Stream::sequential);
        //return res.flatMap(Function.identity());
    }
}
