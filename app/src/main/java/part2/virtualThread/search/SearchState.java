package part2.virtualThread.search;

import part2.virtualThread.monitor.SafeCounter;
import part2.virtualThread.monitor.SafeSet;

public class SearchState {

    //TODO synchronized not needed, only getters are present
    //TODO for real sync update listener should be in the counter
    private final SafeSet linkFound = new SafeSet();
    private final SafeSet linkExplored = new SafeSet();
    private final SafeSet linkDown = new SafeSet();
    private final SafeCounter wordOccurrences = new SafeCounter();

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



}
