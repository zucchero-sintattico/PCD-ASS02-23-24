package part2.reactiveProgramming.model.concurrent;

import io.reactivex.rxjava3.core.Flowable;
import kotlin.Pair;

public interface FlowableWorker {
    Flowable<String> getPageLinks(String url);
    Flowable<Pair<String, Integer>> getLinesWordCount(String url, String word);
}
