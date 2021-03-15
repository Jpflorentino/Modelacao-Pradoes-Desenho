package pt.isel.mpd.util.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class IteratorTakeWhile<T> implements Iterator<T> {
    private final Iterator<T> srcIter;
    private final Predicate<T> f;
    T curr;
    boolean hasAdvanced = false;

    public IteratorTakeWhile(Iterable<T> src, Predicate<T> f) {
        srcIter = src.iterator();
        this.f = f;
    }

    @Override
    public boolean hasNext() {
        if (hasAdvanced) {
            return true;
        }
        if (srcIter.hasNext()) {
            curr = srcIter.next();
            if (f.test(curr)) {
                hasAdvanced = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public T next() {
        if (!hasNext())
            throw new NoSuchElementException();
        hasAdvanced = false;
        return curr;
    }
}