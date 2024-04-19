package part2.virtualThread.search;

import part2.virtualThread.GUI;

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
            System.out.println("alive: "+searchState.getThreadAlive().getValue());
            searchEnded = true;
            System.out.println("f2");
            listener.searchEnded(searchState.getLinkFound(),searchState.getLinkExplored(),searchState.getLinkDown(),searchState.getWordOccurrences().getValue());
            System.out.println("f3");
            System.out.println("alive2: "+searchState.getThreadAlive().getValue());


        });
    }

    public void stop() {
        //TODO implement better
        System.out.println("stop");
        System.out.println(virtualSearchThread != null);
        System.out.println(!searchEnded);
        if(virtualSearchThread != null && !searchEnded){
            searchState.getSearchEnded().stopSimulation();
        }
    }
}
