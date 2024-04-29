package part2.virtualThread.utils.connection;


import org.apache.hc.client5.http.cookie.CookieRestrictionViolationException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import part2.virtualThread.utils.parser.Body;

import java.io.IOException;
import java.nio.charset.CoderMalfunctionError;
import java.util.Arrays;
import java.util.stream.Stream;

import static part2.virtualThread.utils.parser.HtmlParser.extensionToFilter;
import static part2.virtualThread.utils.parser.HtmlParser.linkToMatch;

public class RequestHandlerJSoup implements RequestHandler<Element>{

    public RequestHandlerJSoup() {}

    public Body<Element> getBody(String url) throws Exception {
        return new JsoupBody(Jsoup.connect(url).get().body());
    }

    private static class JsoupBody extends Body<Element> {
        public JsoupBody(Element body) {
            super(body);
        }

        @Override
        public Stream<String> getLinks() {
            return body.select("a").stream()
                    .map(e -> e.attr("href"))
                    .filter(e -> extensionToFilter.stream().noneMatch(e::endsWith))
                    .filter(e -> e.matches(linkToMatch));
        }

        @Override
        public Stream<String> getWords() {
            return Arrays.stream(getWords(body.text()));
        }

    }
}
