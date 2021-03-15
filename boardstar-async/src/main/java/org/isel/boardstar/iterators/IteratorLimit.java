package org.isel.boardstar.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IteratorLimit<T> implements Iterator<T> {
    private final Iterator<T> src;
    private final int nr;
    int idx = 0;

    public IteratorLimit(Iterable<T> src, int nr) {
        this.src = src.iterator();
        this.nr = nr;
    }

    @Override
    public boolean hasNext() {
        if (idx < nr && src.hasNext()) {
            return true;
        }
        return false;
    }

    @Override
    public T next() {
        if (!hasNext()) throw new NoSuchElementException();
        idx++;
        return src.next();
    }
}
