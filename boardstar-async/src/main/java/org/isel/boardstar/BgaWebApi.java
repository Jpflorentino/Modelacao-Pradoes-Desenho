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
import org.isel.boardstar.reqAsync.AsyncRequest;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BgaWebApi {
    private static final String CLIENT_ID = "ObUYHaoaCN";
    private static final String BGA_HOST = "https://www.boardgameatlas.com/api/";
    private static final String BGA_GET_CATEGORIES = BGA_HOST + "game/categories?client_id=" + CLIENT_ID;
    private static final String BGA_SEARCH_BY_CATEGORIES = BGA_HOST + "search?categories=%s&limit=%d&skip=%d&client_id=" + CLIENT_ID;
    private static final String BGA_SEARCH_BY_ARTIST = BGA_HOST + "search?artist=%s&limit=%d&skip=%d&client_id=" + CLIENT_ID;
    //Para fazer o ultimo caminho pedido
    private static final String BGA_SEARCH_ARTIST_BY_NAME = BGA_HOST + "search?ids=%s&limit=%d&skip=%d&client_id=" + CLIENT_ID;

    private final AsyncRequest request;
    protected final Gson gson;

    public static int elementPerPage = 30;

    //para testar
    public BgaWebApi(AsyncRequest request) {
        this(request, new Gson());
    }

    public BgaWebApi(AsyncRequest request, Gson gson) {
        this.request = request;
        this.gson = gson;
    }
/*
    public List<CategoryDto> getCategories() {
        Iterable<String> line = request.getContent(BGA_GET_CATEGORIES);
        String s = String.join("", line);
        GetCategoriesDto getCategoriesDto = gson.fromJson(s, GetCategoriesDto.class);
        return getCategoriesDto.getCategories();
    }*/

    public CompletableFuture<List<CategoryDto>> getCategories() {
        return request.getContent(BGA_GET_CATEGORIES)
                .thenApply(string -> gson.fromJson(string, GetCategoriesDto.class).getCategories());
    }

    public CompletableFuture<List<GameDto>> searchByCategories(int skip, String... ids) {
        String id = String.join(",", ids);
        String path = String.format(BGA_SEARCH_BY_CATEGORIES, id, elementPerPage, (skip - 1) * elementPerPage);

        return request.getContent(path)
                .thenApply(string -> gson.fromJson(string, SearchDto.class).getGames());
    }
    //skip = 270
    //limit = 30

    public CompletableFuture<List<GameDto>> searchByArtist(int skip, String id) {
        String path = String.format(BGA_SEARCH_BY_ARTIST, id, elementPerPage, (skip - 1) * elementPerPage);
        return request.getContent(path)
                .thenApply(string -> gson.fromJson(string, SearchDto.class).getGames());

    }

    //Ultimas Rotas
    public CompletableFuture<List<GameDto>> searchArtistByGame(int skip, String id) {
        String path = String.format(BGA_SEARCH_ARTIST_BY_NAME, id, elementPerPage, (skip - 1) * elementPerPage);
        return request.getContent(path)
                .thenApply(string -> gson.fromJson(string, SearchDto.class).getGames());
    }
}
