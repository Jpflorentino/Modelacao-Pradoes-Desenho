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

import io.reactivex.Observable;
import org.isel.boardstar.dto.CategoryDto;
import org.isel.boardstar.dto.GameDto;
import org.isel.boardstar.model.Artist;
import org.isel.boardstar.model.Category;
import org.isel.boardstar.model.Game;

import static org.isel.boardstar.util.ObservableUtils.fromCompletableFuture;


public class BoardstarService {
    private BgaWebApi api;

    public BoardstarService(BgaWebApi api) {
        this.api = api;
    }

    //Done
    //teste completo
    public Observable<Category> getCategories() {
        /*return Observable.fromFuture(api.getCategories())
                .map(list -> list.stream().toArray(size -> new CategoryDto[size]))
                .flatMap(a -> Observable.fromArray(a))
                .map(this::DtoToCategory);*/
        return fromCompletableFuture(api.getCategories())
                .flatMap(Observable::fromIterable)
                .map(this::DtoToCategory);
    }


    public Observable<Game> searchByCategory(String name, int nElem) {
        int perPage = BgaWebApi.elementPerPage;
        int nPag;

        if (nElem % perPage == 0) nPag = nElem / perPage;
        else nPag = (nElem / perPage) + 1;

        return Observable.range(1, nPag)
                //.doOnNext(System.out::println)
                //.map(page -> fromCompletableFuture(api.searchByCategories(page, name)))
                //.flatMap(observable -> observable)
                .flatMap(page -> fromCompletableFuture(api.searchByCategories(page, name)))
                //.map(list -> list.stream().limit(nPag).collect(Collectors.toList()))
                .flatMap(Observable::fromIterable)
                .take(nElem)
                //.map(list -> list.stream().toArray(size -> new GameDto[size]))
                //.flatMap(Observable::fromArray)
                .map(this::DtoToGame);

    }


    public Observable<Game> searchByArtist(String name, int nElem) {
        int perPage = BgaWebApi.elementPerPage;
        int nPag;

        if (nElem % perPage == 0) nPag = nElem / perPage;
        else nPag = (nElem / perPage) + 1;

        return Observable.range(1, nPag)
                //.map(page -> fromCompletableFuture(api.searchByArtist(page, name)))
                //.flatMap(listObservable -> listObservable)
                .flatMap(page -> fromCompletableFuture(api.searchByArtist(page, name)))
                .flatMap(Observable::fromIterable)
                .take(nElem)
                //.map(list -> list.stream().toArray(size -> new GameDto[size]))
                //.flatMap(Observable::fromArray)
                .map(this::DtoToGame);
    }

    //Para fazer o ultimo caminho
    public Observable<Artist> searchArtistByGame(String name, int nElem) {
        int perPage = BgaWebApi.elementPerPage;
        int nPag;

        if (nElem % perPage == 0) nPag = nElem / perPage;
        else nPag = (nElem / perPage) + 1;

        return Observable.range(1, nPag)
                .flatMap(page -> fromCompletableFuture(api.searchArtistByGame(page, name)))
                .flatMap(Observable::fromIterable)
                .take(nElem)
                .flatMap(this::DtoToObservableArtist);
    }

    //Rota adicional aka ROTA DO DIABO
    /// JM Realmente é o que parece...
    public Observable<Category> searchCategoryByGame(String name, int nElem) {
        int perPage = BgaWebApi.elementPerPage;
        int nPag;

        if (nElem % perPage == 0) nPag = nElem / perPage;
        else nPag = (nElem / perPage) + 1;

        return Observable.range(1, nPag)
                .flatMap(page -> fromCompletableFuture(api.searchArtistByGame(page, name)))
                .flatMap(Observable::fromIterable)
                .take(nElem)
                .flatMap(this::DtotoObservableCategory);
    }

    public Observable<Artist> DtoToObservableArtist(GameDto dto) {
        return Observable.fromArray(dto.getArtists())
                .flatMap(Observable::fromIterable)
                .map(artist -> new Artist(artist, (n) -> searchByArtist(dto.getName(), n)));
    }

    public Observable<Category> DtotoObservableCategory(GameDto dto) {
        return Observable.fromArray(dto.getCategories())
                .flatMap(Observable::fromIterable)
                .map(this::DtoToCategory);
    }

    private Game DtoToGame(GameDto dto) {
        return new Game(dto.getId(),
                dto.getName(),
                dto.getYearPublished(),
                dto.getDescription(),
                // () -> dto.getCategories().stream().map(this::DtoToCategory),
                DtotoObservableCategory(dto),
                DtoToObservableArtist(dto));
    }

    private Category DtoToCategory(CategoryDto dto) {
        return new Category(dto.getId(),
                dto.getName(),
                (n) -> searchByArtist(dto.getId(), n));
    }
}