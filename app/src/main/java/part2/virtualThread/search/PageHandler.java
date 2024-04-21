package part2.virtualThread.search;

import part2.virtualThread.monitor.SafeCounter;
import part2.virtualThread.utils.connection.RequestHandler;
import part2.virtualThread.utils.parser.HtmlParser;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.List;

public class PageHandler extends Thread{

    private final String urlString;
    private final String word;
    private final int depth;
    private final SearchState searchState;
    private final List<Thread> handlers = new ArrayList<>();

    public PageHandler(String urlString, String word, int depth, SearchState searchState){
        this.urlString = urlString;
        this.word = word;
        this.depth = depth;
        this.searchState = searchState;
    }

    @Override
    public void run() {
            try {
                this.searchState.getThreadAlive().add(urlString);
                this.searchState.getListener().ifPresent(l -> l.threadAliveUpdated(this.searchState.getThreadAlive()));
                if (this.searchState.getSearchEnded().isSimulationRunning()) {
                    this.searchState.getLinkExplored().add(urlString);
                    this.searchState.getListener().ifPresent(l -> l.pageRequested(urlString, this.searchState.getLinkExplored()));
                    this.read(RequestHandler.getBody(urlString));
                }
            } catch (IOException | URISyntaxException | IllegalArgumentException e) {
                this.searchState.getListener().ifPresent(l -> l.pageDown(e.getMessage(), urlString));
                searchState.getLinkDown().add(urlString);
            } finally {
                searchState.getThreadAlive().remove(urlString);
                this.searchState.getListener().ifPresent(l -> l.threadAliveUpdated(this.searchState.getThreadAlive()));
            }
    }

    private void read(String text) {

        try{
            List<String> toVisit = new ArrayList<>();
            SafeCounter wordFound = new SafeCounter();

            HtmlParser.parse(text)
                    .foreach(line -> getLinks(line, toVisit))
                    .doAction(() -> visitLinks(toVisit, handlers))
                    .foreach(line -> matchLine(line, wordFound));

            this.updateWordCount(wordFound);
            for (Thread t : handlers) {
                t.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }
    }

    private void visitLinks(List<String> toVisit, List<Thread> handlers) {
        if(this.depth > 0){
            for (String link: toVisit) {
                this.searchState.getListener().ifPresent(l -> l.pageFound(link));
                Thread vt = Thread.ofVirtual().start(new PageHandler(link, word, depth-1, searchState));
//                Thread vt = new PageHandler(link, word, depth-1, searchState,listener);
//                vt.start();
                handlers.add(vt);
            }
        }
    }

    private void updateWordCount(SafeCounter wordFound) {
        int count = wordFound.getValue();
        if (count > 0) {
            this.searchState.getWordOccurrences().update(count);
            this.searchState.getListener().ifPresent(l -> l.countUpdated(wordFound.getValue(), this.urlString, this.searchState.getWordOccurrences()));
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
