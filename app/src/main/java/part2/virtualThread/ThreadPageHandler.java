package part2.virtualThread;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import part2.virtualThread.monitor.SafeCounter;
import part2.virtualThread.monitor.SearchState;
import part2.virtualThread.utils.parser.HtmlParser;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;



public class ThreadPageHandler extends Thread{
    private final String urlString;
    private final String word;
    private final int depth;
    private final SearchState searchState;
    private final SearchListener listener;

    public ThreadPageHandler(String urlString, String word, int depth, SearchState searchState, SearchListener listener){
        this.urlString = urlString;
        this.word = word;
        this.depth = depth;
        this.searchState = searchState;
        this.listener = listener;
    }

    public void run(){
        try {
//            HttpClient client=HttpClient.newHttpClient();
//            HttpRequest request = HttpRequest.newBuilder().uri(new URI(urlString)).GET().build();
//            HttpResponse<String> response=client.send(request, HttpResponse.BodyHandlers.ofString());
//            read(response.body());
            HttpClient client=HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(urlString)).GET().build();
            HttpResponse<String> response=client.send(request, HttpResponse.BodyHandlers.ofString());
            for (int i = 0; i < 10; i++) {
                if(response.statusCode() > 300 && response.statusCode() < 400){
                    String url = response.headers().firstValue("Location").get();
                    request = HttpRequest.newBuilder()
                            .uri(new URI(url))
                            .setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 14_4_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.4.1 Safari/605.1.15")
                            .GET().build();
                    response=client.send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println(response.statusCode());
                }else{
                    read(response.body());
                    break;
                }
            }
//            HttpURLConnection conn=(HttpURLConnection)(new URI(urlString).toURL().openConnection());
//            conn.setConnectTimeout(2000);
//            InputStream is=conn.getInputStream();
//            String str=new String(is.readAllBytes());
//            is.close();
//            read(str);

//            OkHttpClient client = new OkHttpClient();
//            Request request = new Request.Builder()
//                    .url(urlString)
//                    .build();
//            try (Response response = client.newCall(request).execute()) {
//                if (response.body() != null) {
//                    this.read(response.body().string());
//                }else{
//                    //TODO see when empty
//                }
//            }
        }  catch (IOException e) {
            System.out.println(e +" "+ urlString);
            searchState.getLinkDown().add(urlString);
//            throw new RuntimeException(e);
//        } catch (URISyntaxException e) {
//            System.out.println(e +" "+ urlString);
////            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            System.out.println(e +" "+ urlString);
////            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            System.out.println(e +" "+ urlString);
//            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.out.println(e +" "+ urlString);
        }
//        catch (URISyntaxException e) {
////            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
////            throw new RuntimeException(e);
//        }


    }

    private void read(String text) {
        try{
            List<Thread> handlers = new ArrayList<>();
            List<String> toVisit = new ArrayList<>();
            SafeCounter wordFound = new SafeCounter();

            HtmlParser.parse(text)
                    .foreach(line -> getLinks(line, toVisit))
                    .doAction(() -> visitLinks(toVisit, handlers))
                    .foreach(line -> matchLine(line, wordFound));

            this.updateWordCount(wordFound);
            for (Thread t: handlers) {
                t.join();
            }

        } catch (InterruptedException e) {
            System.out.println("Cannot join threads");
        }
    }

    private void visitLinks(List<String> toVisit, List<Thread> handlers) {
        if(this.depth > 0){
            for (String link: toVisit) {
                this.listener.pageRequested(link);
                this.searchState.getLinkExplored().add(link);
                Thread t = new ThreadPageHandler(link, word, depth-1, searchState,listener);
                t.start();
                handlers.add(t);
            }
        }
    }

    private void updateWordCount(SafeCounter wordFound) {
        int count = wordFound.getValue();
        if (count > 0) {
            this.searchState.getWordOccurrences().update(count);
            this.listener.countUpdated(wordFound.getValue(), this.urlString);
        }
    }

    private void matchLine(String line, SafeCounter wordFound) {
        HtmlParser.match(line, this.word, (s) -> wordFound.inc());
    }

    private void getLinks(String line, List<String> toVisit) {
        HtmlParser.findLinks(line, link -> {
            if (!this.searchState.getLinkFound().contains(link)) {
                this.searchState.getLinkFound().add(link);
                toVisit.add(link);
            }
        });
    }

}
