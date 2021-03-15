package org.isel.boardstar.mappers;

import org.isel.boardstar.html.Element;
import org.isel.boardstar.model.Game;

import static org.isel.boardstar.html.Elements.*;

public class GameMappers {
    public static Element mapToTableRaw(Game game) {
        String id = game.getId();
        String name = game.getName();
        int year = game.getYear();
        String urlToArtist = String.format("/games/%s/artists", game.getId());
        String urlToCategory = String.format("/games/%s/categories", game.getId());
        return tr(
                td(id),
                td(name),
                td(String.valueOf(year)),
                td(a("Category", urlToCategory)),
                td(a("Artists", urlToArtist))
        );
    }
}
