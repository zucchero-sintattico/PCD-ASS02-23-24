package part2.virtualThread.view;

import part2.virtualThread.state.LogBuffer;
import part2.virtualThread.state.SearchState;

import java.util.concurrent.atomic.AtomicReference;

public record SearchInfo(int totalPageRequested, int totalWordFound, int treadAlive, LogBuffer newLog) {
    public static SearchInfo from(SearchState state) {
        return new SearchInfo(state.getLinkState().getLinkExplored().size(), state.getWordOccurrences(), state.getThreadAlive().size(), state.getLogs());
    }
}
