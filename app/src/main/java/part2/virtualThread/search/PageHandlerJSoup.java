package part2.virtualThread.search;

import org.jsoup.nodes.Document;
import part2.virtualThread.monitor.SafeCounter;
import part2.virtualThread.utils.connection.RequestHandler;
import part2.virtualThread.utils.connection.RequestHandlerJSoup;
import part2.virtualThread.utils.parser.HtmlParser;
import part2.virtualThread.utils.parser.HtmlParserJSoup;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class PageHandlerJSoup extends Thread{

    private final String urlString;
    private final String word;
    private final int depth;
    private final SearchState searchState;
    private final List<Thread> handlers = new ArrayList<>();

    public PageHandlerJSoup(String urlString, String word, int depth, SearchState searchState){
        this.urlString = urlString;
        this.word = word;
        this.depth = depth;
        this.searchState = searchState;
    }

    @Override
    public void run() {
            try {
                this.searchState.getThreadAlive().add(urlString);
//                this.searchState.getListener().ifPresent(l -> l.threadAliveUpdated(this.searchState.getThreadAlive()));
                if (this.searchState.getSearchEnded().isSimulationRunning()) {
                    this.searchState.getLinkExplored().add(urlString);
//                    this.searchState.getListener().ifPresent(l -> l.pageRequested(urlString, this.searchState.getLinkExplored()));
                    this.searchState.log("Page requested: " + urlString + "\n");
                    this.read(RequestHandlerJSoup.getBody(urlString));
                }
            } catch (IOException | URISyntaxException | IllegalArgumentException e) {
                this.searchState.log("Page down: " + urlString + " Reason: " + e + "\n");
                searchState.getLinkDown().add(urlString);
            } finally {
                searchState.getThreadAlive().remove(urlString);
//                this.searchState.getListener().ifPresent(l -> l.threadAliveUpdated(this.searchState.getThreadAlive()));
            }
    }

    private void read(Document text) {

        try{
            List<String> toVisit = new ArrayList<>();
            SafeCounter wordFound = new SafeCounter();

            HtmlParserJSoup.parse(text)
                    .foreachLink(line -> getLinks(line, toVisit))
                    .doAction(() -> visitLinks(toVisit, handlers))
                    .foreachWord(line -> matchLine(line, wordFound));

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
//                this.searchState.getListener().ifPresent(l -> l.pageFound(link));
                this.searchState.log("Page found: " + link + "\n");
                Thread vt = Thread.ofVirtual().start(new PageHandlerJSoup(link, word, depth-1, searchState));
                handlers.add(vt);
            }
        }
    }

    private void updateWordCount(SafeCounter wordFound) {
        int count = wordFound.getValue();
        if (count > 0) {
            this.searchState.getWordOccurrences().update(count);
            this.searchState.log("Word found: " + wordFound.getValue() + " times in " + urlString + "\n");
//            this.searchState.getListener().ifPresent(l -> l.countUpdated(wordFound.getValue(), this.urlString, this.searchState.getWordOccurrences()));
        }
    }

    private void matchLine(String line, SafeCounter wordFound) {
        HtmlParserJSoup.match(line, this.word, (s) -> wordFound.inc());
    }

    private void getLinks(String line, List<String> toVisit) {
            if (!this.searchState.getLinkFound().contains(line)) {
                this.searchState.getLinkFound().add(line);
                toVisit.add(line);
            }
    }

}
