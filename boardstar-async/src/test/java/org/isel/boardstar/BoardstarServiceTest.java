package org.isel.boardstar;

//SUBSTITUIR POR STREAMS

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.isel.boardstar.model.Category;
import org.isel.boardstar.model.Game;
import org.isel.boardstar.reqAsync.HttpRequest;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

import static junit.framework.TestCase.assertEquals;

public class BoardstarServiceTest {

    private static void threadInfo(String msg) {
        System.out.printf("%s: in thread %s\n",
                msg, Thread.currentThread().getName());
    }

    //DONE
    @Test
    public void getCategoriesServiceTest() {
        int expected = 120;
        BgaWebApi api = new BgaWebApi(new HttpRequest());
        BoardstarService service = new BoardstarService(api);
        Observable<Category> actual = service.getCategories();

        CompletableFuture<Void> done = new CompletableFuture<>();
        int[] count = {0};

        actual.subscribe(new Observer<Category>() {
            @Override
            public void onSubscribe(Disposable d) {
                threadInfo("onSubscribe");
            }

            @Override
            public void onNext(Category category) {
                threadInfo("onNext");
                System.out.println(category);
                count[0] = count[0] + 1;

            }

            @Override
            public void onError(Throwable e) {
                threadInfo("onError");
                System.out.println(e.getMessage());
            }

            @Override
            public void onComplete() {
                threadInfo("onComplete");
                done.complete(null);
            }
        });
        done.join();
        assertEquals(expected, count[0]);
    }

    //DONE
    @Test
    public void searchCategoriesServiceTest() {
        int expected = 45;
        BgaWebApi api = new BgaWebApi(new HttpRequest());
        BoardstarService service = new BoardstarService(api);
        Observable<Game> actual = service.searchByCategory("KUBCKBkGxV", 45);

        CompletableFuture<Void> done = new CompletableFuture<>();
        int[] count = {0};

        actual.subscribe(new Observer<Game>() {
            @Override
            public void onSubscribe(Disposable d) {
                threadInfo("onSubscribe");
            }

            @Override
            public void onNext(Game game) {
                threadInfo("onNext");
                //System.out.println(game);
                count[0] = count[0] + 1;
            }

            @Override
            public void onError(Throwable e) {
                threadInfo("onError");
                System.out.println(e.getMessage());
            }

            @Override
            public void onComplete() {
                threadInfo("onComplete");
                done.complete(null);
            }
        });
        done.join();
        assertEquals(expected, count[0]);
    }

    //DONE
    @Test
    public void searchArtistTest() {
        int expected = 18;
        BgaWebApi api = new BgaWebApi(new HttpRequest());
        BoardstarService service = new BoardstarService(api);
        Observable<Game> actual = service.searchByArtist("Dimitri%20Bielak", 18);

        CompletableFuture<Void> done = new CompletableFuture<>();
        int[] count = {0};

        actual.subscribe(new Observer<Game>() {
            @Override
            public void onSubscribe(Disposable d) {
                threadInfo("onSubscribe");
            }

            @Override
            public void onNext(Game game) {
                threadInfo("onNext");
                //System.out.println(game);
                count[0] = count[0] + 1;
            }

            @Override
            public void onError(Throwable e) {
                threadInfo("onError");
                System.out.println(e.getMessage());
            }

            @Override
            public void onComplete() {
                threadInfo("onComplete");
                done.complete(null);
            }
        });

        done.join();
        assertEquals(expected, count[0]);

    }
}
