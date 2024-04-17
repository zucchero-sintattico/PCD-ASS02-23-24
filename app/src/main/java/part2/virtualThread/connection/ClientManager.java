package part2.virtualThread.connection;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ClientManager {

    //dump connection test

    //            HttpURLConnection conn=(HttpURLConnection)(new URL(urlString).openConnection());
//            InputStream is=conn.getInputStream();
//            String str=new String(is.readAllBytes());
//            is.close();
//            read(str);

//    OkHttpClient client = new OkHttpClient();
//
//    Request request = new Request.Builder()
//            .url(urlString)
//            .build();
//    Response response = client.newCall(request).execute();
//    read(response.body().string());

//            client.connectionPool().;
//            HttpClient client=HttpClient.newHttpClient();
//            HttpRequest request = HttpRequest.newBuilder().uri(new URI(urlString)).GET().build();
//            HttpResponse<String> response=client.send(request, HttpResponse.BodyHandlers.ofString(Charset.defaultCharset()));
//            read(response.body());

//                  Document doc = Jsoup.connect("https://www.google.it").userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 14_4_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.4.1 Safari/605.1.15").get();

}
