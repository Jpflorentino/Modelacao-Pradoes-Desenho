package org.isel.boardstar.mappers;

import org.isel.boardstar.html.Element;
import org.isel.boardstar.model.Category;

import static org.isel.boardstar.html.Elements.*;

public class CategoryMappers {
    public static Element mapToTableRaw(Category category) {
        String urlToGames = String.format("/categories/%s/games", category.getId());
        return tr(
                td(category.getId()),
                td(category.getName()),
                td(a("Games", urlToGames))
        );
    }
}
