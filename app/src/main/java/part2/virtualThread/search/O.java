package part2.virtualThread.search;

import part2.virtualThread.monitor.SafeCounter;
import part2.virtualThread.monitor.SafeFlag;
import part2.virtualThread.monitor.SafeSet;
import part2.virtualThread.view.SearchInfo;

import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class O {

    //TODO synchronized not needed, only getters are present
    //TODO for real sync update listener should be in the counter
    private final SafeSet linkFound;
    private final SafeSet linkExplored = new SafeSet();
    private final SafeSet linkDown = new SafeSet();
    private final SafeCounter wordOccurrences = new SafeCounter();
    private final SafeFlag searchEnded = new SafeFlag(true);
    private SearchListener listener;
    private final Lock listenerLock = new ReentrantLock();
    private final StringBuilder newLog = new StringBuilder();
    private final Lock logLock = new ReentrantLock();
    //debug
    private final SafeSet threadAlive = new SafeSet();
    private final BlockingQueue<SearchInfo> searchInfoQueue = new ArrayBlockingQueue<>(1);
    private final SafeCounter opCounter = new SafeCounter();
    private final ReentrantLock lock = new ReentrantLock();


    public O(String url) {
        linkFound = new SafeSet(url);
    }



    public SafeSet getLinkFound() {
        return this.linkFound;
    }

    public SafeSet getLinkExplored() {
        return this.linkExplored;
    }

    public SafeSet getLinkDown() {
        return this.linkDown;
    }



    public SafeCounter getWordOccurrences() {
        return this.wordOccurrences;
    }

    public SafeFlag getSearchEnded() {
        return this.searchEnded;
    }

    //debug
    public SafeSet getThreadAlive() {
        try{
            lock.lock();
            updateState();
            return this.threadAlive;
        } finally {
            lock.unlock();
        }
    }



    public Optional<SearchListener> getListener() {
        try {
            listenerLock.lock();
            return listener != null ? Optional.of(listener) : Optional.empty();
        } finally {
            listenerLock.unlock();
        }
    }

    public void setListener(SearchListener listener) {
       try {
              listenerLock.lock();
              this.listener = listener;
         } finally {
              listenerLock.unlock();
       }
    }
    public void log(String log){
        try{
            logLock.lock();
            newLog.append(log);
            updateState();
            opCounter.inc();
        } finally {
            logLock.unlock();
        }
    }
    public String getNewLog() {
        try{
            logLock.lock();
            String log = newLog.toString();
            newLog.setLength(0);
            return log;
        } finally {
            logLock.unlock();
        }
    }
    private final ReentrantLock lock2 = new ReentrantLock();
    private void updateState(){

//            try {
//                lock2.lock();
                opCounter.inc();
                if (opCounter.getValue() >= threadAlive.size() / 1000) {
                    opCounter.reset();
                    SearchInfo info = new SearchInfo(linkExplored.size(), wordOccurrences.getValue(), threadAlive.size(), getNewLog());
                    searchInfoQueue.offer(info);
                }
//            }

    }

    public Optional<SearchInfo> getSearchInfo(){
        try {
            return Optional.ofNullable(searchInfoQueue.poll());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
