package part2.virtualThread.view;

import part2.virtualThread.state.SearchState;

public record SearchInfo(int totalPageRequested, int totalWordFound, int treadAlive, String newLog) {
    public static SearchInfo from(SearchState state) {
        return new SearchInfo(state.getLinkState().getLinkExplored().size(), state.getWordOccurrences(), state.getThreadAlive().size(), state.getNewLog());
    }
}
