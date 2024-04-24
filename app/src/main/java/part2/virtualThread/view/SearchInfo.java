package part2.virtualThread.view;

import part2.virtualThread.search.SearchState;

public record SearchInfo(int totalPageRequested, int totalWordFound, int treadAlive, String newLog) {
    public static SearchInfo from(SearchState state) {
        return new SearchInfo(state.getLinkExplored().size(), state.getWordOccurrences().getValue(), state.getThreadAlive().size(), state.getNewLog());
    }
}
