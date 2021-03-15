package pt.isel.mpd.util.iterators;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class IteratorCache<T> implements Iterator<T> {

    private final Iterator<T> src;
    private List<T> cache = new LinkedList<>();
    private int i = 0;

    public IteratorCache(Iterable<T> src) {
        this.src = src.iterator();
    }

    @Override
    public boolean hasNext() {
        if (i < cache.size() || src.hasNext()) {
            return true;
        }
        return false;
    }

    @Override
    public T next() {
        if (i < cache.size()) return cache.get(i++);
        T t = src.next();
        cache.add(t);
        return t;
    }
}
