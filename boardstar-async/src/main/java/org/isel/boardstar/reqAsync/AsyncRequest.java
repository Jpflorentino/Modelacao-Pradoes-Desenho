package org.isel.boardstar.reqAsync;

import java.util.concurrent.CompletableFuture;

/// JM ok, mas não existe uma implementação assíncrona
/// do mock (file) request?. Os testes são todos online, então.
/// Isso contraria o enunciado
public interface AsyncRequest {
    CompletableFuture<String> getContent(String path);
}
