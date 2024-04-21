package part2.virtualThread.utils.connection;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.util.Timeout;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.UnsupportedCharsetException;

public class RequestHandler {

    private RequestHandler() {}

    public static String getBody(String url) throws IOException, URISyntaxException, IllegalArgumentException, UnsupportedCharsetException {
        URI uri = new URI(url);
        HttpGet request = new HttpGet(uri);
//        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");
        org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(Timeout.ofSeconds(5)).setResponseTimeout(Timeout.ofSeconds(5)).build();
//        try (CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(requestConfig).build()) {
//            return client.execute(request, new BasicHttpClientResponseHandler());
//        }
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            return client.execute(request, new BasicHttpClientResponseHandler());
        }


//        return Jsoup.connect(url).get().toString();
    }

}
