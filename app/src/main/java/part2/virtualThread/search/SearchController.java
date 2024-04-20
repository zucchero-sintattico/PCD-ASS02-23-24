package part2.virtualThread.search;

public class SearchController {

    private final SearchListener listener;
    private Thread virtualSearchThread;
    private boolean searchEnded = false;
    private SearchState searchState;

    public SearchController(SearchListener listener) {
        this.listener = listener;
    }

    public void start(String address, String word, int depth) {
        searchEnded = false;
        Thread.ofVirtual().start(() -> {
            searchState = new SearchState(address);
            virtualSearchThread = Thread.ofVirtual().start(new PageHandler(address, word, depth, searchState, listener));
            try {
                virtualSearchThread.join();
            } catch (InterruptedException e) {
                System.out.println("Main Thread interrupted");
            }
            searchEnded = true;
            listener.searchEnded(searchState.getLinkFound().size(),searchState.getLinkExplored().size(),searchState.getLinkDown().size(),searchState.getWordOccurrences().getValue());
        });
    }

    public void stop() {
        if(virtualSearchThread != null && !searchEnded){
            searchState.getSearchEnded().stopSimulation();
        }
    }
}
