package pt.isel.mpd.util.req;

import java.io.InputStream;
import java.io.Reader;

public class CountRequest implements Request {

    private final Request request;
    private int count = 0;

    public CountRequest(Request request) {
        this.request = request;
    }

    @Override
    public Iterable<String> getLines(String path) {
        count++;
        return request.getLines(path);
    }

    @Override
    public Reader getReader(String path) {
        return null;
    }

    @Override
    public InputStream openStream(String path) {
        return null;
    }
}
