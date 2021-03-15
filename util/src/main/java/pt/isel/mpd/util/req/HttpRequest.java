package pt.isel.mpd.util.req;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

public class HttpRequest extends AbstractRequest {
    @Override
    protected InputStream getStream(String path) throws IOException {
        URL url = new URL(path);
        return url.openStream();
    }

    /// JM ai, ai, este é que dava jeito usar!...
    @Override
    public Reader getReader(String path) {
        return null;
    }
}
