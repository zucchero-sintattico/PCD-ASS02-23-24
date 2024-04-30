package part2.virtualThread.search;

import part2.virtualThread.monitor.SafeCounter;
import part2.virtualThread.monitor.SearchState;
import part2.virtualThread.utils.connection.RequestHandler;
import part2.virtualThread.utils.parser.Body;
import part2.virtualThread.utils.parser.HtmlParser;

import java.util.ArrayList;
import java.util.List;

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
//                this.searchState.getListener().ifPresent(l -> l.threadAliveUpdated(this.searchState.getThreadAlive()));
                if (this.searchState.isSimulationRunning()) {
                    this.searchState.addLinkExplored(urlString);
//                    this.searchState.getListener().ifPresent(l -> l.pageRequested(urlString, this.searchState.getLinkExplored()));
                    this.searchState.log("Page requested: " + urlString + "\n");
                    this.read(requestHandler.getBody(urlString));
                }
            } catch (Exception e) {
                this.searchState.log("Page down: " + urlString + " Reason: " + e + "\n");
                searchState.addLinkDown(urlString);
            } finally {
                searchState.removeThreadAlive(urlString);
//                this.searchState.getListener().ifPresent(l -> l.threadAliveUpdated(this.searchState.getThreadAlive()));
            }
    }

    private void read(Body<?> html) {

        try{
            System.out.println("read");
            List<String> toVisit = new ArrayList<>();
            SafeCounter wordFound = new SafeCounter();

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
//                this.searchState.getListener().ifPresent(l -> l.pageFound(link));
                sb.append("Page found: " + link + "\n");

                Thread vt = Thread.ofVirtual().start(new PageHandler(link, word, depth-1, searchState,requestHandler));
                handlers.add(vt);
            }
            this.searchState.log(sb.toString());

        }
    }

    private void updateWordCount(SafeCounter wordFound) {
        int count = wordFound.getValue();
        if (count > 0) {
            this.searchState.updateWordOccurrences(count);
            this.searchState.log("Word found: " + wordFound.getValue() + " times in " + urlString + "\n");
//            this.searchState.getListener().ifPresent(l -> l.countUpdated(wordFound.getValue(), this.urlString, this.searchState.getWordOccurrences()));
        }
    }

    private void matchWord(String word, SafeCounter wordFound) {
        if(word.equals(this.word)){
            wordFound.inc();
        }
    }

    private void evaluateLink(String line, List<String> toVisit) {
            if (!this.searchState.getLinkFound().contains(line)) {
                this.searchState.addLinkFound(line);
                toVisit.add(line);
            }
    }

}
