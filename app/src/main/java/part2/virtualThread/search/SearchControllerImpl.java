package part2.virtualThread.search;

import part2.virtualThread.state.SearchState;
import part2.virtualThread.Configuration;
import part2.utils.connection.RequestHandlerJSoup;
import part2.virtualThread.state.SearchReport;
import java.util.Optional;

public class SearchControllerImpl implements SearchController {

    private final SearchListener listener;
    private Thread virtualSearchThread;
    private boolean searchEnded = false;
    private SearchState searchState;

    public SearchControllerImpl(SearchListener listener) {
        this.listener = listener;
    }

    @Override
    public void start(String address, String word, int depth) {
        this.searchEnded = false;
        this.searchState = new SearchState(address);
        this.searchState.setListener(this.listener);
        Thread.ofVirtual().start(() -> {
            long startTime = System.currentTimeMillis();
            this.virtualSearchThread = Thread.ofVirtual().start(
                    new PageHandler(address, word, depth, searchState, new RequestHandlerJSoup(Configuration.SAFE_SEARCH))
            );
            this.listener.searchStarted();
            try {
                this.virtualSearchThread.join();
            } catch (InterruptedException e) {
                System.out.println("Main Thread interrupted");
            }
            this.searchEnded = true;
            this.searchState.removeListener().ifPresent(this::notifySearchEnded);
            System.out.println("Search Time: " + (System.currentTimeMillis() - startTime) + "ms");
        });
    }

    @Override
    public void stop() {
        if(this.virtualSearchThread != null && !this.searchEnded){
            this.searchState.stopSimulation();
        }
    }

    @Override
    public void bruteStop() {
        if(this.virtualSearchThread != null && !this.searchEnded){
            this.searchState.removeListener().ifPresent(this::notifySearchEnded);
            this.searchState.stopSimulation();
        }
    }

    private void notifySearchEnded(SearchListener listener) {
        listener.searchEnded(searchState.getLinkState().getLinkFound().size(), searchState.getLinkState().getLinkDown().size(), SearchReport.from(searchState));
    }

    @Override
    public Optional<SearchReport> getSearchInfo() {
            return searchState.getSearchInfo();
    }

}
