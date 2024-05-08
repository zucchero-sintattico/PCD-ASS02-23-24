package part2.virtualThread.search;

import part2.virtualThread.state.LogType;
import part2.virtualThread.state.SearchState;
import part2.virtualThread.utils.Configuration;
import part2.virtualThread.utils.connection.RequestHandler;
import part2.virtualThread.utils.parser.Body;
import part2.virtualThread.utils.parser.HtmlParser;
import java.util.ArrayList;
import java.util.List;

public class PageHandler extends Thread {

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
                    this.searchState.getLinkState().addLinkExplored(urlString);
                    this.searchState.getLogs().append("Page requested: " + urlString + "\n", LogType.INFO);
                    this.read(requestHandler.getBody(urlString));
                }
            } catch (Exception e) {
                this.searchState.getLogs().append("Page down: " + urlString + " Reason: " + e + "\n", LogType.ERROR);
                this.searchState.getLinkState().addLinkDown(urlString);
            } finally {
                this.searchState.removeThreadAlive(urlString);
            }
    }

    private void read(Body<?> html) {
        try{
            List<String> toVisit = new ArrayList<>();
            int wordFound = HtmlParser.parse(html)
                    .foreachLink(link -> evaluateLink(link, toVisit))
                    .doAction(() -> visitLinks(toVisit, handlers))
                    .countWords(word);
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
                Thread vt = Thread.ofVirtual().start(new PageHandler(link, word, depth - 1, searchState,requestHandler));
                handlers.add(vt);
            }
            this.searchState.getLogs().append((sb.toString()), LogType.INFO);
        }
    }

    private void updateWordCount(int wordFound) {
        if (wordFound > 0) {
            this.searchState.updateWordOccurrences(wordFound);
            this.searchState.getLogs().append("Word found: " + wordFound + " times in " + urlString + "\n", LogType.UPDATE);
        }
    }

    private void evaluateLink(String line, List<String> toVisit) {
       if (Configuration.VISIT_SAME_LINK || !this.searchState.getLinkState().getLinkFound().contains(line)) {
           this.searchState.getLinkState().addLinkFound(line);
           toVisit.add(line);
       }
    }

}
