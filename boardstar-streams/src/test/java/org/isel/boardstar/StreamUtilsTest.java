package org.isel.boardstar;

import org.isel.boardstar.model.Category;
import org.isel.boardstar.model.Game;
import org.junit.Test;
import pt.isel.mpd.util.req.HttpRequest;

import java.util.Random;
import java.util.Spliterator;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Stream.generate;
import static org.isel.boardstar.util.StreamUtils.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class StreamUtilsTest {
    @Test
    public void streamCacheTest() {
        Random r = new Random();
        Stream<Integer> nrs = generate(() -> r.nextInt(100));
        Supplier<Stream<Integer>> nrs1 = cache(nrs);
        Object[] expected = nrs1.get().limit(10).toArray();
        Object[] actual = nrs1.get().limit(10).toArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void interleaveTest() {
        Stream<String> src = Stream.of("1", "2", "3");
        Stream<String> other = Stream.of("a", "b", "c", "d", "e", "f");
        Stream<String> interleave = interleave(src, other);
        Object[] actual = interleave.toArray();
        Stream<String> expectedStream = Stream.of("1", "a", "2", "b", "3", "c", "d", "e", "f");
        Object[] expected = expectedStream.toArray();
        for (Object element : actual) {
            System.out.println(element.toString());
        }
        assertArrayEquals(expected, actual);
    }

    /// JM made
    @Test
    public void interleaveTest2() {
        Stream<String> src = Stream.of("1");
        Stream<String> other = Stream.of("a", "b");
        Stream<String> interleave = interleave(src, other);

        int[] count = {0};

        Spliterator<String> it = interleave.spliterator();

        int count2 = 0;
        while(it.tryAdvance(s -> count[0]++)) count2++;
        System.out.printf("count =%d, count2 =%d\n", count[0], count2);

        assertEquals(count[0], count2);
     }

    @Test
    public void intersectionSimple() {
        Stream<String> src = Stream.of("1", "2", "3");
        Stream<String> other = Stream.of("4", "5", "2", "1", "4", "10");
        Stream<String> intersection = intersection(src, other);
        Object[] actual = intersection.toArray();
        for (Object element : actual) {
            System.out.println(element.toString());
        }
        Stream<String> expectedStream = Stream.of("1", "2");
        Object[] expected = expectedStream.toArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void intersectionTest() {
        BgaWebApi api = new BgaWebApi(new HttpRequest());
        BoardstarService service = new BoardstarService(api);
        //Nossos valores
        //Stream<Game> intersection = intersection(service.searchByCategory("a8NM5cugJX"), service.searchByCategory("eFaACC6y2c"));
        //Valores dados pelo professor
        Stream<Game> intersection = intersection(service.searchByCategory("ssZjU3HETz"), service.searchByCategory("KUBCKBkGxV"));
        Object[] actual = intersection.toArray();
        int expected = 2;
        System.out.println(actual.length);
        assertEquals(expected, actual.length);
    }

    @Test
    public void concatTest() {
        BgaWebApi api = new BgaWebApi(new HttpRequest());
        BoardstarService service = new BoardstarService(api);

        Stream<String> headers = service.getCategories().map(c -> c.getName() + ":");

        Stream<Stream<String>> content = service.getCategories()
                .map(Category::getGames)
                .map(games -> games.limit(10).map(Game::getName));

        Stream<String> concat = concat(headers, content).limit(10);
        Object[] actual = concat.toArray();
        for (Object element : actual) {
            System.out.println(element.toString());
        }
        System.out.println(actual.length);
    }
}
