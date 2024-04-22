package part2.virtualThread.utils.connection;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URISyntaxException;

public class RequestHandlerJSoup {

    private RequestHandlerJSoup() {}

    public static Document getBody(String url) throws IOException, URISyntaxException, IllegalArgumentException {
        return Jsoup.connect(url).get();
    }

}
