package org.isel.boardstar.reqAsync;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/// JM o que está aqui a fazer esta classe?
public abstract class AbstractRequest implements Request {
    @Override
    public Stream<String> getContent(String path) {
        try {
            InputStream input = getStream(path);
            List<String> lines = new ArrayList<>(); // where the lines are collected

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
                String line;
                while ((line = reader.readLine()) != null)
                    lines.add(line);
                return lines.stream();
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
