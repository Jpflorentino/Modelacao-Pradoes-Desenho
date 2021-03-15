package org.isel.boardstar.util;

import java.util.ArrayList;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

public class SpliteratorCache<T> extends Spliterators.AbstractSpliterator<T> {
    private final Spliterator<T> spliterator;
    private final ArrayList<T> cache;
    private int i = 0;

    public SpliteratorCache(Spliterator<T> spliterator, ArrayList<T> cache) {
        super(spliterator.estimateSize(), 0);
        this.spliterator = spliterator;
        this.cache = cache;
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        if (i < cache.size()) {
            T toConsume = cache.get(i++);
            action.accept(toConsume);
            return true;
        }
        return spliterator.tryAdvance(element -> {
            action.accept(element);
            cache.add(element);
            i++;
        });
    }
}
 
