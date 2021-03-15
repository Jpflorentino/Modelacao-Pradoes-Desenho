package org.isel.boardstar.mappers;

import org.isel.boardstar.html.Element;

public interface HtmlMapper<T> {
    Element map(T entity);
}
