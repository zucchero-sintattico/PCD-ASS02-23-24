package part2.virtualThread.utils.connection;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class RequestHandler {

    private RequestHandler() {}

    public static String getBody(String url) throws IOException {
//        HttpGet request = new HttpGet(url);
////        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");
//        org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);
//        try (CloseableHttpClient client = HttpClients.createDefault()) {
//            return client.execute(request, new BasicHttpClientResponseHandler());
//        }

        return Jsoup.connect(url).get().body().toString();
    }

}
