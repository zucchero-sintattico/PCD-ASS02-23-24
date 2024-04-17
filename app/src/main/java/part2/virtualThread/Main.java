package part2.virtualThread;

import part2.virtualThread.monitor.SafeCounter;
import part2.virtualThread.monitor.SafeSet;


public class Main {
    public static void main(String[] args){
        //set user agent
        System.setProperty("http.agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 14_4_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.4.1 Safari/605.1.15");
        String url = "https://www.unipg.it/";
        String word = "ingegneria";
        SafeCounter counter = new SafeCounter();
        SafeSet set = new SafeSet(url);
        SafeSet setRequested = new SafeSet(url);
        PageListener listener = new PageListener() {
            @Override
            public void pageRequested(String url) {
                System.out.println("Requested: "+url);
            }
            @Override
            public void countUpdated(int count, String urlString) {
                System.out.println("Total: "+counter.getValue()+" inc: "+count+ " from: "+urlString);
            }
        };

        PageHandler handler = new PageHandler(url, word, 1, counter, set, setRequested, listener);
        Thread t = Thread.ofVirtual().start(handler);

        try {
            t.join();
        } catch (InterruptedException e) {
            System.out.println("Cannot join threads");
        }
        System.out.println("Found: "+set.size());
        System.out.println("Found: "+set.toString());
        System.out.println("req: "+setRequested.size());
        System.out.println("req: "+setRequested.toString());
        System.out.println("Total: "+counter.getValue());

    }

}
