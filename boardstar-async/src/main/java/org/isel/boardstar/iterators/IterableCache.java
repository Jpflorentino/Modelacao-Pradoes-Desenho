package org.isel.boardstar.iterators;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class IterableCache<T> implements Iterable<T> {
    private final Iterator<T> src;
    private final List<T> cache = new LinkedList<>();

    public IterableCache(Iterable<T> src) {
        this.src = src.iterator();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < cache.size() || src.hasNext();
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                if (i < cache.size()) return cache.get(i++);
                else {
                    T next = src.next();
                    cache.add(next);
                    i++;
                    return next;
                }
            }
        };
    }
}
