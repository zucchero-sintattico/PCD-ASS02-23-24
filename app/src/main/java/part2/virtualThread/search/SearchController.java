package part2.virtualThread.search;

import part2.virtualThread.state.SearchState;
import part2.virtualThread.utils.connection.RequestHandlerJSoup;
import part2.virtualThread.view.SearchInfo;

import java.util.Optional;

public class SearchController {

    private final SearchListener listener;
    private Thread virtualSearchThread;
    private boolean searchEnded = false;
    private SearchState searchState;


    public SearchController(SearchListener listener) {
        this.listener = listener;
    }

    public void start(String address, String word, int depth) {
        this.searchEnded = false;
        Thread.ofVirtual().start(() -> {

            this.searchState = new SearchState(address);
            this.searchState.setListener(this.listener);
            this.virtualSearchThread = Thread.ofVirtual().start(new PageHandler(address, word, depth, searchState, new RequestHandlerJSoup()));
            this.listener.searchStarted();

            try {
                this.virtualSearchThread.join();
            } catch (InterruptedException e) {
                System.out.println("Main Thread interrupted");
            }
            this.searchEnded = true;
            //controlled checkandact, bruteStop can't be executed now

            this.searchState.getListener().ifPresent(this::notifySearchEnded);
        });
    }

    public void stop() {
        if(this.virtualSearchThread != null && !this.searchEnded){
            this.searchState.stopSimulation();
        }
    }

    public void bruteStop() {
        if(this.virtualSearchThread != null && !this.searchEnded){
            this.searchState.setListener(null);
            this.notifySearchEnded(this.listener);
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
