package part2.virtualThread.state;

public record SearchInfo(int totalPageRequested, int totalWordFound, int treadAlive, LogBuffer newLog) {
    public static SearchInfo from(SearchReport state) {
        return new SearchInfo(state.getLinkState().getLinkExplored().size(), state.getWordOccurrences(), state.getThreadAlive().size(), state.getLogs());
    }
}
