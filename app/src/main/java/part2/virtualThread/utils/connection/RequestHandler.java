package part2.virtualThread.utils.connection;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.BasicHttpClientConnectionManager;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

public class RequestHandler {

    private RequestHandler() {}

    public static String getBody(String url) throws IOException, URISyntaxException, IllegalArgumentException {
        URI uri = new URI(url);
        HttpGet request = new HttpGet(uri);

        ConnectionConfig connConfig = ConnectionConfig.custom()
                .setConnectTimeout(5, TimeUnit.SECONDS)
                .setSocketTimeout(5, TimeUnit.SECONDS)
                .build();

        BasicHttpClientConnectionManager cm = new BasicHttpClientConnectionManager();
        cm.setConnectionConfig(connConfig);

//        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");
//        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(Timeout.ofSeconds(2)).setResponseTimeout(Timeout.ofSeconds(2)).build();
//        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(Timeout.ofSeconds(2)).build();

        try (CloseableHttpClient client = HttpClients.custom().setConnectionManager(cm).build()) {
            return client.execute(request, new BasicHttpClientResponseHandler());
        }
//        try (CloseableHttpClient client = HttpClients.createDefault()) {
//            return client.execute(request, new BasicHttpClientResponseHandler());
//        }


//        return Jsoup.connect(url).get().toString();
    }

}
