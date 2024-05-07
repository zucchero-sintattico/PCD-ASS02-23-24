package part2.virtualThread.utils.connection;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.BasicHttpClientConnectionManager;
import part2.virtualThread.utils.parser.Body;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import static part2.virtualThread.utils.parser.HtmlParser.extensionToFilter;
import static part2.virtualThread.utils.parser.HtmlParser.linkToMatch;

public class RequestHandlerApache implements RequestHandler<String>{

    public RequestHandlerApache() {}

    public Body<String> getBody(String url) throws IOException, URISyntaxException, IllegalArgumentException {
        URI uri = new URI(url);
        HttpGet request = new HttpGet(uri);

        ConnectionConfig connConfig = ConnectionConfig.custom()
                .setConnectTimeout(5, TimeUnit.SECONDS)
                .setSocketTimeout(5, TimeUnit.SECONDS)
                .build();

        BasicHttpClientConnectionManager cm = new BasicHttpClientConnectionManager();
        cm.setConnectionConfig(connConfig);

        try (CloseableHttpClient client = HttpClients.custom().setConnectionManager(cm).build()) {
            return new ApacheBody(client.execute(request, new BasicHttpClientResponseHandler()));
        }

    }

    private static class ApacheBody extends Body<String> {

        public ApacheBody(String body) {
            super(body);
        }

        @Override
        public Stream<String> getLinks() {
            return this.findLinks(getWords(body));
        }

        @Override
        public Stream<String> getWords() {
            return Arrays.stream(getWords(body));
        }

        private Stream<String> findLinks(String[] word){
            List<String> links = new ArrayList<>();

            for (String w : word) {
                //TODO improve parsing
                if(w.matches("href=\""+linkToMatch)){
                    String href = w.split("\"")[1];
                    if(extensionToFilter.stream().noneMatch(href::endsWith)){
                        links.add(href);
                    }
                }
            }

            return links.stream();
        }

    }

}
