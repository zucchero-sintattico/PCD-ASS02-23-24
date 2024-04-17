package part2.virtualThread.monitor;

public class SearchState {

    //TODO synchronized not needed, only getters are present
    //TODO for real sync update listener should be in the counter
    private final SafeSet linkFound = new SafeSet();
    private final SafeSet linkExplored = new SafeSet();
    private final SafeSet linkDown = new SafeSet();
    private final SafeCounter wordOccurrences = new SafeCounter();


    public SafeSet getLinkFound() {
//        synchronized (this.linkFound) {
            return this.linkFound;
//        }
    }

    public SafeSet getLinkExplored() {
//        synchronized (this.linkExplored) {
            return this.linkExplored;
//        }
    }

    public SafeSet getLinkDown() {
//        synchronized (this.linkDown) {
            return this.linkDown;
//        }
    }

    public SafeCounter getWordOccurrences() {
//        synchronized (this.wordOccurrences){
            return this.wordOccurrences;
//        }
    }

}
