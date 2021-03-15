package org.isel.boardstar;

import org.isel.boardstar.dto.CategoryDto;
import org.isel.boardstar.dto.GameDto;
import org.junit.Test;
import pt.isel.mpd.util.req.HttpRequest;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BgaWebApiTest {
    //DONE
    @Test
    public void getCategoriesTest() {
        int expected = 128;
        BgaWebApi api = new BgaWebApi(new HttpRequest());
        List<CategoryDto> actual = api.getCategories();
        actual.forEach((id) -> {
            System.out.println("id:" + id.getId());
            System.out.println("name:" + id.getName());
            System.out.println("checked:" + id.isChecked() + "\n");
        });
        assertEquals(expected, actual.size());
    }

    //DONE
    @Test
    public void searchCategoriesTest() {
        int expected = 30;
        String[] ids = {"a8NM5cugJX", "eFaACC6y2c"};
        String[] id = {"KUBCKBkGxV"};
        int skip = 0;
        BgaWebApi api = new BgaWebApi(new HttpRequest());
        List<GameDto> actual = api.searchByCategories(skip, id);
        actual.forEach((g) -> System.out.println(g.getId()));
        assertEquals(expected, actual.size());
    }

    //DONE
    @Test
    public void searchArtistTest() {
        int expected = 19;
        String name = "Dimitri%20Bielak";
        int skip = 0;
        BgaWebApi api = new BgaWebApi(new HttpRequest());
        List<GameDto> actual = api.searchByArtist(skip, name);
        actual.forEach((id) -> {
            System.out.println("id:" + id.getId());
        });
        assertEquals(expected, actual.size());
    }
}
