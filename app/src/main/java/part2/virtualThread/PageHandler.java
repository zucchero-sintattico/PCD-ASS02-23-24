package part2.virtualThread;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import part1.utils.Point2D;
import part2.virtualThread.monitor.SafeCounter;
import part2.virtualThread.monitor.SafeSet;
import part2.virtualThread.utils.parser.HtmlParser;

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
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(urlString)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (response.body() != null) {
                    this.read(response.body().string());
                }else{
                    //TODO see when empty
                }
            }
        }  catch (IOException e) {
            System.out.println(e);
//            throw new RuntimeException(e);
        }


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
                this.request.add(link);
                Thread t = Thread.ofVirtual().start(new PageHandler(link, word, depth-1, safeCounter, safeSet,request,listener));
                handlers.add(t);
            }
        }
    }

    private void updateWordCount(SafeCounter wordFound) {
        int count = wordFound.getValue();
        if (count > 0) {
            this.safeCounter.update(count);
            this.listener.countUpdated(wordFound.getValue(), this.urlString);
        }
    }

    private void matchLine(String line, SafeCounter wordFound) {
        HtmlParser.match(line, this.word, (s) -> wordFound.inc());
    }

    private void getLinks(String line, List<String> toVisit) {
        HtmlParser.findLinks(line, link -> {
            if (!this.safeSet.contains(link)) {
                this.safeSet.add(link);
                toVisit.add(link);
            }
        });
    }

}
