package part2.virtualThread.utils.connection;

import part2.virtualThread.utils.parser.Body;

public interface RequestHandler<T> {

    Body<T> getBody(String url) throws Exception;

}
