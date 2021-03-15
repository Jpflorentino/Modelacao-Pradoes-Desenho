package org.isel.boardstar.reqAsync;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.concurrent.CompletableFuture;

import static org.asynchttpclient.Dsl.asyncHttpClient;


public class HttpRequest implements AsyncRequest {
    private static void ahcClose(AsyncHttpClient client) {
        try {
            client.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }


    @Override
    public CompletableFuture<String>
    getContent(String path) {

        AsyncHttpClient client = asyncHttpClient();

        return client.prepareGet(path)
                .execute()
                .toCompletableFuture()
                .thenApply(Response::getResponseBody)
                .whenComplete((s, e) -> ahcClose(client));

    }
}
