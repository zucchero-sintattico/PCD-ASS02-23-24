package part2.virtualThread;

import part2.virtualThread.monitor.SafeCounter;
import part2.virtualThread.monitor.SafeSet;
import part2.virtualThread.search.PageHandler;
import part2.virtualThread.search.SearchListener;
import part2.virtualThread.search.SearchState;
import part2.virtualThread.utils.Configuration;

import java.io.IOException;

public class TestMain {
    public static void main(String[] args) throws InterruptedException{

        String url = "https://www.unipg.it/";
        String word = "ingegneria";

        Configuration.setup();
        SearchState state = new SearchState(url);

        SearchListener listener = new SearchListener() {
            @Override
            public void pageFound(String url) {
                System.out.println("Found: "+url);
            }

            @Override
            public void pageRequested(String pageUrl, SafeSet totalPageRequested) {

            }

//            @Override
            public void pageRequested(String pageUrl, int totalPageRequested) {

            }


            @Override
            public void pageDown(String exceptionMessage, String url) {
                System.out.println("Down: "+url+ " Reason: "+exceptionMessage);
            }

            @Override
            public void countUpdated(int wordFound, String pageUrl, SafeCounter totalWordFound) {

            }

//            @Override
            public void countUpdated(int count, String url) {
                System.out.println("Total: "+state.getWordOccurrences().getValue()+" inc: "+count+ " from: "+url);
            }

            @Override
            public void searchEnded(int linkFound, int linkExplored, int linkDown, int wordFound) {

            }

            @Override
            public void threadAliveUpdated(SafeCounter treadAlive) {

            }


//            @Override
            public void threadAliveUpdated(int treadAlive) {

            }
        };

        PageHandler handler = new PageHandler(url, word, 2, state, listener);
        handler.start();
        handler.join();

        System.out.println("Link Found: "+state.getLinkFound().size());
        System.out.println("Link Explored: "+state.getLinkExplored().size());
        System.out.println("Link Down: "+state.getLinkDown().size());
        System.out.println("Link Up: "+(state.getLinkExplored().size() - state.getLinkDown().size()));
        System.out.println("Total Occurrences: "+state.getWordOccurrences().getValue());

    }

}
