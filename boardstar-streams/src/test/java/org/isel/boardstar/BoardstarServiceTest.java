package org.isel.boardstar;

import org.isel.boardstar.model.Category;
import org.isel.boardstar.model.Game;
import org.junit.Test;
import pt.isel.mpd.util.req.HttpRequest;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

//SUBSTITUIR POR STREAMS

public class BoardstarServiceTest {

    //DONE
    @Test
    public void getCategoriesServiceTest() {
        int expected = 128;
        BgaWebApi api = new BgaWebApi(new HttpRequest());
        BoardstarService service = new BoardstarService(api);
        Stream<Category> actual = service.getCategories();
        assertEquals(expected, actual.count());
    }

    @Test
    public void searchCategoriesServiceTest() {
        /*
        int expectedCount = 84;
        BoardstarService boardstarService = new BoardstarService(new BgaWebApi(new HttpRequest()));
        Iterable<Game> games = boardstarService.searchB yCategory("KUBCKBkGxV");
        int count = 0;
        for (Game game : games) {
            count++;
        }
        assertEquals(expectedCount, count);
         */
        int expected = 84;
        String ids = "KUBCKBkGxV";
        BgaWebApi api = new BgaWebApi(new HttpRequest());
        BoardstarService service = new BoardstarService(api);
        Stream<Game> actual = service.searchByCategory(ids);
        assertEquals(expected, actual.count());
    }

    @Test
    public void searchArtistTest() {
        int expected = 19;
        String name = "Dimitri%20Bielak";
        BgaWebApi api = new BgaWebApi(new HttpRequest());
        BoardstarService service = new BoardstarService(api);
        Stream<Game> actual = service.searchByArtist(name);
        //actual.forEach((n) -> System.out.println(n.getId()));
        assertEquals(expected, actual.count());
    }
}
