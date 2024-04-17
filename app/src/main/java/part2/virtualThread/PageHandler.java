package part2.virtualThread;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import part1.utils.Point2D;
import part2.virtualThread.monitor.SafeCounter;
import part2.virtualThread.monitor.SafeSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


public class PageHandler extends Thread{
    private final String urlString;
    private final String word;
    private final int depth;
    private final SafeCounter safeCounter;
    private final SafeSet safeSet,request;
    private final PageListener listener;

    public PageHandler(String urlString, String word, int depth, SafeCounter occurrenceCounter, SafeSet linkManager, SafeSet request, PageListener listener){
        this.urlString = urlString;
        this.word = word;
        this.depth = depth;
        this.safeCounter = occurrenceCounter;
        this.safeSet = linkManager;
        this.request = request;
        this.listener = listener;
    }

    public void run(){
        try {
//            HttpURLConnection conn=(HttpURLConnection)(new URL(urlString).openConnection());
//            InputStream is=conn.getInputStream();
//            String str=new String(is.readAllBytes());
//            is.close();
//            read(str);

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(urlString)
                    .build();
            Response response = client.newCall(request).execute();
            read(response.body().string());

//            HttpClient client=HttpClient.newHttpClient();
//            HttpRequest request = HttpRequest.newBuilder().uri(new URI(urlString)).GET().build();
//            HttpResponse<String> response=client.send(request, HttpResponse.BodyHandlers.ofString(Charset.defaultCharset()));
//            read(response.body());
        }  catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException(e);
//        }
//        catch (URISyntaxException e) {
//            System.out.println("URI "+e);
////            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            System.out.println("INT" +e);
////            throw new RuntimeException(e);
        }


    }

    private void read(String text) {
        List<Thread> handlers = new ArrayList<>();
        try{

            List<String> toVisit = new ArrayList<>();
            String[] split = text.toLowerCase().split(" ");
            Stream.of(split).forEach(s ->{
                if(s.trim().contains("href=\"https://")){
                    String href = s.split("\"")[1];
                    if(!safeSet.contains(href)) {
                        safeSet.add(href);
                        toVisit.add(href);
                    }
                }
            });
            int count = (int) Stream.of(split).filter(s -> s.trim().equals(word)).count();
            safeCounter.update(count);
            if(count > 0){
                listener.countUpdated(count, urlString);
            }
            if(depth > 0){
                for (String s1: toVisit) {
                    listener.pageRequested(s1);
                    request.add(s1);
                    Thread t = Thread.ofVirtual().unstarted(new PageHandler(s1, word, depth-1, safeCounter, safeSet,request,listener));
                    handlers.add(t);
                    t.start();
                }
                for (Thread t: handlers) {
                    t.join();
                }

            }
        }
//        catch (IOException e){
//            System.out.println("Cannot read line");
//        }
        catch (InterruptedException e) {
            System.out.println("Cannot join threads");
        }
    }

//    private Optional<BufferedReader> openStream() {
//        try {
//            URL url = new URI(urlString).toURL();
//            return Optional.of(new BufferedReader(new InputStreamReader(url.openStream())));
//        } catch (MalformedURLException | URISyntaxException e) {
//            System.out.println("Invalid URL");
//        } catch (IOException e) {
//            System.out.println("Cannot open stream");
//        }
//        return Optional.empty();
//    }
}
