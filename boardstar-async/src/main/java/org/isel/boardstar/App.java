package org.isel.boardstar;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.isel.boardstar.html.Body;
import org.isel.boardstar.html.Element;
import org.isel.boardstar.html.Html;
import org.isel.boardstar.mappers.ArtistMappers;
import org.isel.boardstar.mappers.CategoryMappers;
import org.isel.boardstar.mappers.GameMappers;
import org.isel.boardstar.mappers.HtmlMapper;
import org.isel.boardstar.model.Artist;
import org.isel.boardstar.model.Category;
import org.isel.boardstar.model.Game;
import org.isel.boardstar.reqAsync.HttpRequest;

import static org.isel.boardstar.html.Elements.table;

public class App {

    private static BgaWebApi bgaWebApi = new BgaWebApi(new HttpRequest());
    private static BoardstarService boardstarService = new BoardstarService(bgaWebApi);


    private static void sendErrorResponse(HttpServerResponse resp, int errorCode) {
        resp.putHeader("content-type", "text/html");
        String msg = "<h1> Error" + errorCode + "! </h1>";
        resp.putHeader("content-length", "" + msg.length());
        resp.write(msg);

        resp.setStatusCode(errorCode);
        resp.end();
        resp.close();

    }

    private static void sendOkResponse(HttpServerResponse resp) {
        resp.end();
        resp.close();
    }


    private static Html html = new Html();
    private static Body body = new Body();

    private static void startDoc(HttpServerResponse resp) {
        resp.write(html.startText(0));
        resp.write(body.startText(1));
    }

    private static void endDoc(HttpServerResponse resp) {
        resp.write(body.endText(1));
        resp.write(html.endText(0));

    }

    private static <T> void buildView(
            HttpServerResponse resp, Observable<T> rows,
            HtmlMapper<T> mapper, String... columns) {

        resp.putHeader("content-type", "text/html");
        resp.setStatusCode(200);
        resp.setChunked(true);
        resp.setWriteQueueMaxSize(128);

        //startDoc(resp);

        Element table = table(columns);

        rows.subscribeWith(new Observer<T>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                startDoc(resp);
                resp.write(table.startText(2));
            }

            @Override
            public void onNext(@NonNull T t) {
                resp.write(mapper.map(t).toString(3));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                sendErrorResponse(resp, 500);
            }

            @Override
            public void onComplete() {
                resp.write(table.endText(2));
                endDoc(resp);
                sendOkResponse(resp);
            }
        });
    }

    //DONE
    private static void getCategories(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        Observable<Category> categoryObservable = boardstarService.getCategories(); //Nao é usado nElem
        buildView(response, categoryObservable, CategoryMappers::mapToTableRaw, "ID", "Name", "Games");
    }

    //DONE
    private static void searchByCategory(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        String id = routingContext.request().getParam("id");
        Observable<Game> gameObservable = boardstarService.searchByCategory(id, 20);
        buildView(response, gameObservable, GameMappers::mapToTableRaw, "ID", "Name", "Year", "Category", "Artist");
    }

    private static void searchByArtistID(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        String id = routingContext.request().getParam("id");
        Observable<Game> gameObservable = boardstarService.searchByArtist(id, 10);
        buildView(response, gameObservable, GameMappers::mapToTableRaw, "ID", "Name", "Year", "Artist");
    }

    //DONE
    private static void searchByGameID(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        String id = routingContext.request().getParam("id");
        Observable<Artist> artistObservable = boardstarService.searchArtistByGame(id, 10);
        buildView(response, artistObservable, ArtistMappers::mapToTableRaw, "Name", "Games");
    }

    private static void searchCategoryByGameID(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        String id = routingContext.request().getParam("id");
        Observable<Category> artistObservable = boardstarService.searchCategoryByGame(id, 10);
        buildView(response, artistObservable, CategoryMappers::mapToTableRaw, "ID", "Name", "Game");
    }

    public static void main(String[] args) throws Exception {
        Router router = Router.router(Vertx.vertx());

        router.route("/categories").handler(App::getCategories);
        router.route("/categories/:id/games").handler(App::searchByCategory);
        router.route("/artists/:id/games").handler(App::searchByArtistID); //Caminho opcional ao anterior
        router.route("/games/:id/artists").handler(App::searchByGameID);
        router.route("/games/:id/categories").handler(App::searchCategoryByGameID);

        Vertx.vertx().createHttpServer().requestHandler(router::accept).listen(8000);
    }
}
