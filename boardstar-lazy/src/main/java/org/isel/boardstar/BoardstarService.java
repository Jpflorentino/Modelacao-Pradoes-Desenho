/*
 * GNU General Public License v3.0
 *
 * Copyright (c) 2020, Miguel Gamboa (gamboa.pt)
 *
 *   All rights granted under this License are granted for the term of
 * copyright on the Program, and are irrevocable provided the stated
 * conditions are met.  This License explicitly affirms your unlimited
 * permission to run the unmodified Program.  The output from running a
 * covered work is covered by this License only if the output, given its
 * content, constitutes a covered work.  This License acknowledges your
 * rights of fair use or other equivalent, as provided by copyright law.
 *
 *   You may make, run and propagate covered works that you do not
 * convey, without conditions so long as your license otherwise remains
 * in force.  You may convey covered works to others for the sole purpose
 * of having them make modifications exclusively for you, or provide you
 * with facilities for running those works, provided that you comply with
 * the terms of this License in conveying all material for which you do
 * not control copyright.  Those thus making or running the covered works
 * for you must do so exclusively on your behalf, under your direction
 * and control, on terms that prohibit them from making any copies of
 * your copyrighted material outside their relationship with you.
 *
 *   Conveying under any other circumstances is permitted solely under
 * the conditions stated below.  Sublicensing is not allowed; section 10
 * makes it unnecessary.
 */

package org.isel.boardstar;

import org.isel.boardstar.dto.CategoryDto;
import org.isel.boardstar.dto.GameDto;
import org.isel.boardstar.model.Artist;
import org.isel.boardstar.model.Category;
import org.isel.boardstar.model.Game;

import java.util.List;

import static pt.isel.mpd.util.LazyQueries.*;

public class BoardstarService {
    private BgaWebApi api;

    public BoardstarService(BgaWebApi api) {
        this.api = api;
    }

    //Done
    //teste completo
    public Iterable<Category> getCategories() {
        /// JM invoca logo a api, ao contrário
        /// do que diz o enunciado
        List<CategoryDto> categoryDto = api.getCategories();
        return () -> map(categoryDto, this::DtoToCategory).iterator();
    }

    //Done
    public Iterable<Game> searchByCategory(String name) {
        Iterable<Integer> number = iterate(1, i -> i + 1);
        Iterable<List<GameDto>> listIterable = map(number, (p) -> api.searchByCategories(p, name));
        Iterable<List<GameDto>> listIterable1 = takeWhile(listIterable, (element) -> element.toArray().length > 0);
        Iterable<GameDto> gameDtoIterable = flatMap(listIterable1, a -> toList(a));
        return map(gameDtoIterable, this::DtoToGame);
    }

    //Done
    public Iterable<Game> searchByArtist(String name) {
        Iterable<Integer> number = iterate(1, i -> i + 1);
        Iterable<List<GameDto>> listIterable = map(number, (p) -> api.searchByArtist(p, name));
        Iterable<List<GameDto>> listIterable1 = takeWhile(listIterable, (element) -> element.toArray().length > 0);
        Iterable<GameDto> gameDtoIterable = flatMap(listIterable1, a -> toList(a));
        return map(gameDtoIterable, this::DtoToGame);
    }

    //builders
    //Nao usado
    /// JM porque não?
    private Artist DtoToArtist(GameDto dto) {
        return new Artist(dto.getName(),
                searchByArtist(dto.getId()));
    }

    /// JM em java, os nomes dos métodos começam com minúscula
    private Game DtoToGame(GameDto dto) {
        return new Game(dto.getId(),
                dto.getName(),
                dto.getYearPublished(),
                dto.getDescription(),
                map(dto.getCategories(), (categoryDto) -> DtoToCategory(categoryDto)),
                map(dto.getArtists(), (string) -> new Artist(string, searchByArtist(string)))
        );
    }

    private Category DtoToCategory(CategoryDto dto) {
        return new Category(dto.getId(),
                dto.getName(),
                searchByCategory(dto.getId()));
    }
}
