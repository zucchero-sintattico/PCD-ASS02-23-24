package part2.virtualThread.search;

import part2.virtualThread.monitor.SafeSet;

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
            this.listener.searchStarted();
            this.searchState = new SearchState(address);
            this.searchState.setListener(this.listener);
            this.virtualSearchThread = Thread.ofVirtual().start(new PageHandler(address, word, depth, searchState));
//            virtualSearchThread = new PageHandler(address, word, depth, searchState, listener);
//            virtualSearchThread.start();
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
            this.searchState.getSearchEnded().stopSimulation();
        }
    }

    public void bruteStop() {
        if(this.virtualSearchThread != null && !this.searchEnded){
            this.searchState.setListener(null);
            this.notifySearchEnded(this.listener);
            this.searchState.getSearchEnded().stopSimulation();
        }
    }

    private void notifySearchEnded(SearchListener listener) {
        listener.searchEnded(searchState.getLinkFound(), searchState.getLinkExplored(), searchState.getLinkDown(), searchState.getWordOccurrences());
    }
}
