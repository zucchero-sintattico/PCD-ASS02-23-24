package part2.virtualThread.utils.connection;

import part2.virtualThread.utils.parser.Body;

import java.io.IOException;
import java.net.URISyntaxException;

public interface RequestHandler<T> {
    Body<T> getBody(String url) throws IOException, URISyntaxException;
}
