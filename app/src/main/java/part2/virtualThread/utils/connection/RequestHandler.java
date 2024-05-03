package part2.virtualThread.utils.connection;

import org.apache.hc.client5.http.cookie.CookieRestrictionViolationException;
import part2.virtualThread.utils.parser.Body;

import java.io.IOException;
import java.net.URISyntaxException;

public interface RequestHandler<T> {
    Body<T> getBody(String url) throws IOException, URISyntaxException, CookieRestrictionViolationException, Exception;
}
