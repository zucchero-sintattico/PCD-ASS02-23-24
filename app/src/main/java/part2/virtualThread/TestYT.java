package part2.virtualThread;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.Stream;

public class TestYT {
    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
        System.setProperty("http.agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 14_4_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.4.1 Safari/605.1.15");
        org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);
        HttpGet request = new HttpGet("https://www.youtube.com/studiumgeneraleperugia1308");
        CloseableHttpClient client = HttpClients.createDefault();
        String response = client.execute(request, new BasicHttpClientResponseHandler());
        System.out.println(response);

//        HttpURLConnection conn=(HttpURLConnection)(new URI("https://www.youtube.com/studiumgeneraleperugia1308").toURL().openConnection());
//        BufferedReader is= new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        is.lines().forEach(System.out::println);
//        String str=new String(is.readAllBytes());
//        is.close();
//        read(str);
    }

}
