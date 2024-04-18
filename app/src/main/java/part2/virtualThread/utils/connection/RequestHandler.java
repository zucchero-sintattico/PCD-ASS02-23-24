package part2.virtualThread.utils.connection;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import java.io.IOException;

public class RequestHandler {

    private RequestHandler() {}

    public static String getBody(String url) throws IOException {
        HttpGet request = new HttpGet(url);
        org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            return client.execute(request, new BasicHttpClientResponseHandler());
        }
    }

}
