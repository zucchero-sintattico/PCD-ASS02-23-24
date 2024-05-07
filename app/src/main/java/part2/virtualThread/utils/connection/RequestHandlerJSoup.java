package part2.virtualThread.utils.connection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import part2.virtualThread.utils.parser.Body;
import java.util.Arrays;
import java.util.stream.Stream;

import static part2.virtualThread.utils.parser.HtmlParser.*;

public class RequestHandlerJSoup implements RequestHandler<Element>{

    private final boolean safe;

    public RequestHandlerJSoup() {this.safe = true;}

    public RequestHandlerJSoup(boolean safe) {this.safe = safe;}

    public Body<Element> getBody(String url) throws Exception {
        return new JsoupBody(Jsoup.connect(url).get().body(), safe);
    }

    private static class JsoupBody extends Body<Element> {

        private final boolean safe;

        public JsoupBody(Element body, boolean safe) {
            super(body);
            this.safe = safe;
        }

        @Override
        public Stream<String> getLinks() {
            return body.select("a").stream()
                    .map(e -> e.attr("href"))
                    .filter(e -> extensionToFilter.stream().noneMatch(e::endsWith))
                    .filter(e -> e.matches(safe ? linkToMatch : linkToMatchUnsafe));
        }

        @Override
        public Stream<String> getWords() {
            return Arrays.stream(getWords(body.text()));
        }

    }
}
