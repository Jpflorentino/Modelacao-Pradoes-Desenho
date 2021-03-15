package org.isel.boardstar.util;

import io.reactivex.Observable;

import java.util.concurrent.CompletableFuture;

public class ObservableUtils {
    public static <T> Observable<T> fromCompletableFuture(CompletableFuture<T> cf) {
        return Observable.create(subscriber -> {
            cf.whenComplete((t, e) -> {
                if (e == null) {
                    subscriber.onNext(t);
                    subscriber.onComplete();
                } else {
                    subscriber.onError(e);
                }
            });
        });
    }
}
