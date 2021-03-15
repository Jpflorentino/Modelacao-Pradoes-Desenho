package org.isel.boardstar;

import org.isel.boardstar.dto.CategoryDto;
import org.isel.boardstar.dto.GameDto;
import org.isel.boardstar.reqAsync.HttpRequest;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static junit.framework.TestCase.assertEquals;

//SUBSTITUIR POR STREAMS

public class BgaWebApiTest {
    //DONE
    @Test
    public void getCategoriesTest() {
        int expected = 112;
        BgaWebApi api = new BgaWebApi(new HttpRequest());
        CompletableFuture<List<CategoryDto>> listCompletableFuture = api.getCategories();
        //streamCompletableFuture.thenAccept(list -> {
        //   list.forEach(System.out::println);
        //});
        //streamCompletableFuture.join();

        CompletableFuture<Integer> size = listCompletableFuture.thenApply(list -> list.size());
        assertEquals(expected, size.join().intValue());
    }


    //DONE
    @Test
    public void searchCategoriesTest() {
        int expected = 30;
        String[] ids = {"a8NM5cugJX", "eFaACC6y2c"};
        String[] id = {"KUBCKBkGxV"};
        int skip = 0;
        BgaWebApi api = new BgaWebApi(new HttpRequest());
        CompletableFuture<List<GameDto>> list = api.searchByCategories(skip, id);
        CompletableFuture<Integer> size = list.thenApply(l -> l.size());
        assertEquals(expected, size.join().intValue());
    }

    //DONE
    @Test
    public void searchArtistTest() {
        int expected = 19;
        String name = "Dimitri%20Bielak";
        int skip = 0;
        BgaWebApi api = new BgaWebApi(new HttpRequest());
        CompletableFuture<List<GameDto>> actual = api.searchByArtist(skip, name);
        CompletableFuture<Integer> size = actual.thenApply(l -> l.size());
        assertEquals(expected, size.join().intValue());
    }
}
