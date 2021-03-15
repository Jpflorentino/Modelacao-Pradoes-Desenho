/*
 * GNU General Public License v3.0
 *
 * Copyright (c) 2020, Miguel Gamboa (gamboa.pt)
 *
 *   All rights granted under this License are granted for the term of
 * copyright on the Program, and are irrevocable provided the stated
 * conditions are met.  This License explicitly affirms your unlimited
 * permission to run the unmodified Program.  The output from running a
 * covered work is covered by this License only if the output, given its
 * content, constitutes a covered work.  This License acknowledges your
 * rights of fair use or other equivalent, as provided by copyright law.
 *
 *   You may make, run and propagate covered works that you do not
 * convey, without conditions so long as your license otherwise remains
 * in force.  You may convey covered works to others for the sole purpose
 * of having them make modifications exclusively for you, or provide you
 * with facilities for running those works, provided that you comply with
 * the terms of this License in conveying all material for which you do
 * not control copyright.  Those thus making or running the covered works
 * for you must do so exclusively on your behalf, under your direction
 * and control, on terms that prohibit them from making any copies of
 * your copyrighted material outside their relationship with you.
 *
 *   Conveying under any other circumstances is permitted solely under
 * the conditions stated below.  Sublicensing is not allowed; section 10
 * makes it unnecessary.
 */

package pt.isel.mpd.util;

import pt.isel.mpd.util.iterators.*;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;


/// JM em geral Ok, incluindo os iterators

public class LazyQueries {
    /************************** Parte1 **************************/
    //Exercicio 1

    //done
    public static <T> Iterable<T> generate(Supplier<T> sup) {
        return () -> new IteratorGenerate(sup);
    }

    //done
    public static <T> Iterable<T> iterate(T seed, UnaryOperator<T> acc) {

        return () -> new IteratorIterate(seed, acc);
    }

    //done
    public static <T> Iterable<T> filter(Iterable<T> src, Predicate<T> pred) {
        return () -> new IteratorFilter(src, pred);
    }

    //done
    public static <T> Iterable<T> limit(Iterable<T> src, int nr) {

        return () -> new IteratorLimit(src, nr);
    }

    //done
    public static <T> Iterable<T> skip(Iterable<T> src, int nr) {

        return () -> new IteratorSkip(src, nr);
    }

    //done
    public static <T, R> Iterable<R> map(Iterable<T> src, Function<T, R> mapper) {
        return () -> new IteratorMap(src, mapper);
    }

    //done
    //Operação Terminal
    public static <T> int count(Iterable<T> src) {
        int count = 0;
        Iterator<T> it = src.iterator();

        while (it.hasNext()) {
            it.next();
            count++;
        }
        return count;
    }

    //done
    //Operação Terminal
    public static <T> Object[] toArray(Iterable<T> src) {
        List<T> list = new ArrayList<>();
        src.forEach(v -> list.add(v));
        return list.toArray();
    }

    //Exercicio2

    //done
    //Operação Terminal
    public static <T> Optional<T> first(Iterable<T> src) {
        Optional<T> first = Optional.empty();
        Iterator<T> it = src.iterator();
        if (it.hasNext()) {
            T t = it.next();
            first = Optional.of(t);
        }
        return first;
    }

    //done
    //Operação Terminal
    public static <T> Optional<T> max(Iterable<T> src, Comparator<T> cmp) {
        Optional<T> max = Optional.empty();
        Iterator<T> it = src.iterator();
        T aux = null;
        if (it.hasNext()) {
            max = Optional.of(it.next());
        }
        while (it.hasNext()) {
            aux = it.next();
            if (cmp.compare(aux, max.get()) > 0) {
                max = Optional.of(aux);
            }
        }
        return max;
    }

    //Exercicio 3

    //done
    //Operação Terminal
    public static <T> Optional<T> last(Iterable<T> src) {
        Optional<T> last = Optional.empty();
        Iterator<T> it = src.iterator();
        while (it.hasNext()) {
            T t = it.next();
            if (!it.hasNext()) {
                last = Optional.of(t);
            }
        }
        return last;
        //Versao 2 para testar
        /*
        Optional<T> last = Optional.empty();
        Iterator<T> it = src.iterator();
        while (it.hasNext()) last.of(it.next());
        return last;
        */
    }

    //Exercicio 4

    //done
    public static <T> Iterable<T> takeWhile(Iterable<T> src, Predicate<T> f) {
        return () -> new IteratorTakeWhile(src, f);
    }

    //Exercicio 5

    //done
    public static <T, R> Iterable<R> flatMap(Iterable<T> src, Function<T, Iterable<R>> mapper) {
        return () -> new IteratorFlatMap(src, mapper);
    }

    //Funções extra
    //Funcao adicional para testes
    public static <T> List<T> toList(Iterable<T> src) {
        List<T> list = new ArrayList<>();
        src.forEach(list::add);
        return list;
    }

    //Problema 4
    //Cache
    public static <T> Iterable<T> cache(Iterable<T> src) {
        /// JM Iterable??
        /// Porque não criaram um iterator como em todos os outros casos?
        return new IterableCache<>(src);
    }
}
