package org.isel.boardstar.util;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

public class SpliteratorInterleave<T> extends Spliterators.AbstractSpliterator<T> {

    private Spliterator<T> src;
    private Spliterator<T> other;
    boolean sequenceSrcEnd = false;
    boolean sequenceOtherEnd = false;
    //Saber qual sequencia escolher
    //Se true entao src
    //Se false entao other
    boolean sequence = true;


    public SpliteratorInterleave(Spliterator<T> src, Spliterator<T> other) {
        super(src.estimateSize() + other.estimateSize(), 0);
        this.src = src;
        this.other = other;
    }

    /*
    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        Spliterator<T> aux = src;
        if (aux.tryAdvance(action)) {
            src = other;
            other = aux;
            return true;
        }
        return other.tryAdvance(action);
    }
     */

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        /// JM Esta complicação já não tem nada a ver com MPD...
        if (sequence) {
            if (src.tryAdvance(element -> {
                action.accept(element);
            })) {
                sequence = false;
                return true;
            } else {

                /// JM senão houver mais elementos não é tentada
                /// a segunda sequência!
                sequenceSrcEnd = true;
            }
        } else if (!sequence && !sequenceSrcEnd) {
            if (other.tryAdvance(element -> {
                action.accept(element);
            })) {
                sequence = true;
                return true;
            } else {
                sequenceOtherEnd = true;
            }
        } else {
            if (other.tryAdvance(element -> {
                action.accept(element);
            })) {
                return true;
            } else {
                sequenceOtherEnd = true;
                return false;
            }
        }
        if (sequenceSrcEnd || sequenceOtherEnd) {
            sequence = false;
            return true;
        }
        return false;
    }
}
