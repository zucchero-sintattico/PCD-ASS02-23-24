package part2.virtualThread.search;

import part2.virtualThread.state.SearchReport;
import part2.virtualThread.utils.connection.RequestHandlerJSoup;
import part2.virtualThread.state.SearchInfo;

import java.util.Optional;

public class SearchController {

    private final SearchListener listener;
    private Thread virtualSearchThread;
    private boolean searchEnded = false;
    private SearchReport searchState;


    public SearchController(SearchListener listener) {
        this.listener = listener;
    }

    public void start(String address, String word, int depth) {
        this.searchEnded = false;
        this.searchState = new SearchReport(address);
        this.searchState.setListener(this.listener);

        Thread.ofVirtual().start(() -> {
            this.virtualSearchThread = Thread.ofVirtual().start(new PageHandler(address, word, depth, searchState, new RequestHandlerJSoup()));
            this.listener.searchStarted();
            try {
                this.virtualSearchThread.join();
            } catch (InterruptedException e) {
                System.out.println("Main Thread interrupted");
            }
            this.searchEnded = true;
            this.searchState.removeListener().ifPresent(this::notifySearchEnded);
        });
    }

    public void stop() {
        if(this.virtualSearchThread != null && !this.searchEnded){
            this.searchState.stopSimulation();
        }
    }

    public void bruteStop() {
        if(this.virtualSearchThread != null && !this.searchEnded){
            this.searchState.removeListener().ifPresent(this::notifySearchEnded);
            this.searchState.stopSimulation();
        }
    }

    private void notifySearchEnded(SearchListener listener) {
        listener.searchEnded(searchState.getLinkState().getLinkFound().size(), searchState.getLinkState().getLinkDown().size(), SearchInfo.from(searchState));
    }


    public Optional<SearchInfo> getSearchInfo() {
            return searchState.getSearchInfo();
    }


}
