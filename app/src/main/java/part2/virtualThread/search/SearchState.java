package part2.virtualThread.search;

import part2.virtualThread.monitor.SafeCounter;
import part2.virtualThread.monitor.SafeFlag;
import part2.virtualThread.monitor.SafeSet;

import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SearchState {

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

    public SearchState(String url) {
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
        return this.threadAlive;
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
}
