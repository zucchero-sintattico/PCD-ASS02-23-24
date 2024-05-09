package part2.utils.connection;

import part2.utils.parser.Body;

public interface RequestHandler<T> {

    Body<T> getBody(String url) throws Exception;

}
