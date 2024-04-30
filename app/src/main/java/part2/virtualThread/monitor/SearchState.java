package part2.virtualThread.monitor;

import part2.virtualThread.search.SearchListener;
import part2.virtualThread.view.SearchInfo;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SearchState {

    private final Set<String> linkFound = new HashSet();
    private final Set<String> linkExplored = new HashSet();
    private final Set<String> linkDown = new HashSet();
    private final Set<String> threadAlive = new HashSet<>();
    private int wordOccurrences = 0;


    private final StringBuilder allLog = new StringBuilder();
    private final StringBuilder newLog = new StringBuilder();

    private boolean searchEnded = false;
    private SearchListener listener;

    private int opCounter = 0;
    private final ReentrantLock mutex = new ReentrantLock();

    public SearchState(String url) {
        this.linkFound.add(url);
    }

    public Set<String> getLinkFound() {
        try {
            this.mutex.lock();
            return Collections.unmodifiableSet(this.linkFound);
        } finally {
            this.mutex.unlock();
        }
    }

    public Set<String> getLinkExplored() {
        try {
            this.mutex.lock();
            return Collections.unmodifiableSet(this.linkExplored);
        } finally {
            this.mutex.unlock();
        }
    }

    public Set<String> getLinkDown() {
        try {
            this.mutex.lock();
            return Collections.unmodifiableSet(this.linkDown);
        } finally {
            this.mutex.unlock();
        }
    }

    public int getWordOccurrences() {
        try {
            this.mutex.lock();
            return this.wordOccurrences;
        } finally {
            this.mutex.unlock();
        }
    }

    public Set<String> getThreadAlive() {
        try {
            this.mutex.lock();
            return Collections.unmodifiableSet(this.threadAlive);
        } finally {
            this.mutex.unlock();
        }
    }

    public String getNewLog() {
        try{
            mutex.lock();
            String log = newLog.toString();
            newLog.setLength(0);
            return log;
        } finally {
            mutex.unlock();
        }
    }


    public void setListener(SearchListener listener) {
        try {
            this.mutex.lock();
            this.listener = listener;
        } finally {
            this.mutex.unlock();
        }
    }

    public void addThreadAlive(String urlString) {
        try {
            this.mutex.lock();
            this.threadAlive.add(urlString);
        } finally {
            this.mutex.unlock();
        }
    }

    public boolean isSimulationRunning() {
        try {
            this.mutex.lock();
            return !this.searchEnded;
        } finally {
            this.mutex.unlock();
        }
    }

    public void log(String s) {
        try {
            this.mutex.lock();
            this.allLog.append(s);
            this.newLog.append(s);
        } finally {
            this.mutex.unlock();
        }
    }

    public void updateWordOccurrences(int count) {
        try {
            this.mutex.lock();
            this.wordOccurrences += count;
        } finally {
            this.mutex.unlock();
        }
    }

    public Optional<SearchListener> getListener() {
        try {
            this.mutex.lock();
            return this.listener != null ? Optional.of(this.listener) : Optional.empty();
        } finally {
            this.mutex.unlock();
        }
    }

    public void stopSimulation() {
        try {
            this.mutex.lock();
            this.searchEnded = true;
        } finally {
            this.mutex.unlock();
        }
    }

    public Optional<SearchInfo> getSearchInfo() {
        try {
            this.mutex.lock();
            return Optional.of(new SearchInfo(this.linkExplored.size(), this.wordOccurrences, this.threadAlive.size(), this.getNewLog()));
        } finally {
            this.mutex.unlock();
        }
    }

    public void addLinkDown(String urlString) {
        try {
            this.mutex.lock();
            this.linkDown.add(urlString);
        } finally {
            this.mutex.unlock();
        }
    }

    public void removeThreadAlive(String urlString) {
        try {
            this.mutex.lock();
            this.threadAlive.remove(urlString);
        } finally {
            this.mutex.unlock();
        }
    }
}
