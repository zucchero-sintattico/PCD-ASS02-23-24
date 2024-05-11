package part2.virtualThreadFuture.state;

public record SearchReport(int totalPageRequested, int totalWordFound, int treadAlive, LogBuffer newLog) {
    public static SearchReport from(SearchState state) {
        return new SearchReport(state.getLinkState().getLinkExplored().size(), state.getWordOccurrences(), state.getThreadAlive().size(), state.getLogs());
    }
}
