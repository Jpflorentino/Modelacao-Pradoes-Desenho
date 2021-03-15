package org.isel.boardstar.mappers;

import org.isel.boardstar.html.Element;
import org.isel.boardstar.model.Artist;

import static org.isel.boardstar.html.Elements.*;

public class ArtistMappers {
    public static Element mapToTableRaw(Artist artist) {
        String name = artist.getName();
        String urlToGames = String.format("/artists/%s/games", artist.getName());
        return tr(
                td(name),
                td(a("Games", urlToGames))
        );
    }
}
