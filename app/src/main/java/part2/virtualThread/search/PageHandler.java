package part2.virtualThread.search;

import part2.virtualThread.utils.connection.RequestHandler;
import part2.virtualThread.utils.parser.Body;
import part2.virtualThread.utils.parser.HtmlParser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PageHandler extends Thread{

    private final String urlString;
    private final String word;
    private final int depth;
    private final SearchState searchState;
    private final List<Thread> handlers = new ArrayList<>();
    private final RequestHandler<?> requestHandler;

    public PageHandler(String urlString, String word, int depth, SearchState searchState, RequestHandler<?> requestHandler){
        this.urlString = urlString;
        this.word = word;
        this.depth = depth;
        this.searchState = searchState;
        this.requestHandler = requestHandler;
    }

    @Override
    public void run() {
            try {
                this.searchState.addThreadAlive(urlString);
                if (this.searchState.isSimulationRunning()) {
                    this.searchState.addLinkExplored(urlString);
                    this.searchState.log("Page requested: " + urlString + "\n");
                    this.read(requestHandler.getBody(urlString));
                }
            } catch (Exception e) {
                this.searchState.log("Page down: " + urlString + " Reason: " + e + "\n");
                this.searchState.addLinkDown(urlString);
            } finally {
                this.searchState.removeThreadAlive(urlString);
            }
    }

    private void read(Body<?> html) {

        try{
            List<String> toVisit = new ArrayList<>();
            AtomicInteger wordFound = new AtomicInteger();

            HtmlParser.parse(html)
                    .foreachLink(link -> evaluateLink(link, toVisit))
                    .doAction(() -> visitLinks(toVisit, handlers))
                    .foreachWord(word -> matchWord(word, wordFound));

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
            StringBuilder sb = new StringBuilder();
            for (String link: toVisit) {
                sb.append("Page found: ").append(link).append("\n");
                Thread vt = Thread.ofVirtual().start(new PageHandler(link, word, depth-1, searchState,requestHandler));
                handlers.add(vt);
            }
            this.searchState.log(sb.toString());

        }
    }

    private void updateWordCount(AtomicInteger wordFound) {
        int count = wordFound.get();
        if (count > 0) {
            this.searchState.updateWordOccurrences(count);
            this.searchState.log("Word found: " + count + " times in " + urlString + "\n");
        }
    }

    private void matchWord(String word, AtomicInteger wordFound) {
        if(word.equals(this.word)){
            wordFound.incrementAndGet();
        }
    }

    private void evaluateLink(String line, List<String> toVisit) {
       if (!this.searchState.getLinkFound().contains(line)) {
           this.searchState.addLinkFound(line);
           toVisit.add(line);
       }
    }

}
