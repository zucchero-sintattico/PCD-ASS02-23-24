package part2.virtualThread;

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

        HttpClient client=HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://www.youtube.com/studiumgeneraleperugia1308"))
                .setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 14_4_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.4.1 Safari/605.1.15")
                .GET().build();
        HttpResponse<Stream<String>> response=client.send(request, HttpResponse.BodyHandlers.ofLines());
        System.out.println(response.statusCode());
        System.out.println(
                response.headers().map()
        );
        while(response.statusCode() > 300){
            String url = response.headers().firstValue("Location").get();
            System.out.println(url);
            request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 14_4_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.4.1 Safari/605.1.15")
                    .GET().build();
            response=client.send(request, HttpResponse.BodyHandlers.ofLines());
            System.out.println(response.statusCode());

        }
        System.out.println(response.statusCode());
        response.body().forEach(l -> System.out.println(l));

//        HttpURLConnection conn=(HttpURLConnection)(new URI("https://www.youtube.com/studiumgeneraleperugia1308").toURL().openConnection());
//        BufferedReader is= new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        is.lines().forEach(System.out::println);
//        String str=new String(is.readAllBytes());
//        is.close();
//        read(str);
    }

}
