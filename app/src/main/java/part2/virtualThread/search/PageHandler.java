package part2.virtualThread.search;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import part2.virtualThread.monitor.SafeCounter;
import part2.virtualThread.utils.parser.HtmlParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class PageHandler extends Thread{

    private final String urlString;
    private final String word;
    private final int depth;
    private final SearchState searchState;
    private final SearchListener listener;

    public PageHandler(String urlString, String word, int depth, SearchState searchState, SearchListener listener){
        this.urlString = urlString;
        this.word = word;
        this.depth = depth;
        this.searchState = searchState;
        this.listener = listener;
    }

    @Override
    public void run() {
        try {
            HttpGet request = new HttpGet(urlString);
            CloseableHttpClient client = HttpClients.createDefault();
            String response = client.execute(request, new BasicHttpClientResponseHandler());
            read(response);
        } catch (IOException e) {  // IOException
            System.out.println(e +"aa");
            searchState.getLinkDown().add(urlString);
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
                this.searchState.getLinkExplored().add(link);
                Thread t = Thread.ofVirtual().start(new PageHandler(link, word, depth-1, searchState,listener));
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
