package part2.virtualThread;

import part2.virtualThread.monitor.SearchState;


public class Main {
    public static void main(String[] args){
        //set user agent
        System.setProperty("http.agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 14_4_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.4.1 Safari/605.1.15");
        String url = "https://www.unipg.it/";
        String word = "ingegneria";

        SearchState state = new SearchState();

        SearchListener listener = new SearchListener() {
            @Override
            public void pageRequested(String url) {
//               System.out.println("Requested: "+url);
            }
            @Override
            public void countUpdated(int count, String urlString) {
                System.out.println("Total: "+state.getWordOccurrences().getValue()+" inc: "+count+ " from: "+urlString);
            }
        };

        PageHandler handler = new PageHandler(url, word, 3, state, listener);
        handler.start();

        try {
            handler.join();
        } catch (InterruptedException e) {
            System.out.println("Cannot join threads");
        }
        System.out.println("Link Found: "+state.getLinkFound().size());
//            System.out.println("Link Found: "+state.getLinkFound().toString());
        System.out.println("Link Explored: "+state.getLinkExplored().size());
//            System.out.println("Link Explored: "+state.getLinkExplored().toString());
        System.out.println("Link Down: "+state.getLinkDown().size());
        System.out.println("Link Up: "+(state.getLinkExplored().size() - state.getLinkDown().size()));
        System.out.println("Total Occurrences: "+state.getWordOccurrences().getValue());



    }

}
