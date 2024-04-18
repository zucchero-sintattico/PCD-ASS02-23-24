package part2.virtualThread.search;

import part2.virtualThread.GUI;

public class SearchController {

    private final SearchListener listener;
    private Thread searchThread;
    private boolean searchEnded = false;
    private SearchState searchState;

    public SearchController(SearchListener listener) {
        this.listener = listener;
    }

    public void start(String address, String word, int depth) {
        Thread.ofVirtual().start(() -> {
            searchState = new SearchState(address);
            searchThread = Thread.ofVirtual().start(new PageHandler(address, word, depth, searchState, listener));
            try {
                searchThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                searchEnded = true;
                listener.searchEnded(searchState.getLinkFound(),searchState.getLinkExplored(),searchState.getLinkDown(),searchState.getWordOccurrences().getValue());
            }
        });
    }

    public void stop() {
        //TODO implement better
        if(searchThread != null && !searchEnded){
            searchThread.interrupt();
        }
    }
}
