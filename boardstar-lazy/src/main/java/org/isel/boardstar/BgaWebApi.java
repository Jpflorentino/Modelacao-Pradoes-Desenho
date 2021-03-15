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

import com.google.gson.Gson;
import org.isel.boardstar.dto.CategoryDto;
import org.isel.boardstar.dto.GameDto;
import org.isel.boardstar.dto.GetCategoriesDto;
import org.isel.boardstar.dto.SearchDto;
import pt.isel.mpd.util.req.Request;

import java.util.List;

public class BgaWebApi {
    private static final String CLIENT_ID = "ObUYHaoaCN";
    private static final String BGA_HOST = "https://www.boardgameatlas.com/api/";
    private static final String BGA_GET_CATEGORIES = BGA_HOST + "game/categories?client_id=" + CLIENT_ID;
    private static final String BGA_SEARCH_BY_CATEGORIES = BGA_HOST + "search?categories=%s&limit=%d&skip=%d&client_id=" + CLIENT_ID;
    private static final String BGA_SEARCH_BY_ARTIST = BGA_HOST + "search?artist=%s&limit=%d&skip=%d&client_id=" + CLIENT_ID;

    private final Request request;
    protected final Gson gson;

    private int limit = 30;

    //para testar
    public BgaWebApi(Request request) {
        this(request, new Gson());
    }

    public BgaWebApi(Request request, Gson gson) {
        this.request = request;
        this.gson = gson;
    }

    public List<CategoryDto> getCategories() {
        Iterable<String> line = request.getLines(BGA_GET_CATEGORIES);
        /// JM Para quê inventar, se o que se pretende é uma redução?
        /// E porquê o getLines se o método fromJson já recebe um Reader?
        String s = String.join("", line);
        GetCategoriesDto getCategoriesDto = gson.fromJson(s, GetCategoriesDto.class);
        return getCategoriesDto.getCategories();
    }

    public List<GameDto> searchByCategories(int skip, String... ids) {
        String id = String.join(",", ids);
        String path = String.format(BGA_SEARCH_BY_CATEGORIES, id, limit, (skip - 1) * limit);
        Iterable<String> line = request.getLines(path);
        String s = String.join("", line);
        SearchDto searchDto = gson.fromJson(s, SearchDto.class);
        return searchDto.getGames();
    }

    public List<GameDto> searchByArtist(int skip, String name) {
        String path = String.format(BGA_SEARCH_BY_ARTIST, name, limit, (skip - 1) * limit);
        Iterable<String> line = request.getLines(path);
        String s = String.join("", line);
        SearchDto searchDto = gson.fromJson(s, SearchDto.class);
        return searchDto.getGames();
    }
}
