package part2.reactiveProgramming.model.concurrent;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observer;
import kotlin.Pair;

public interface ConcurrentHtmlReader {
    void getWordCount(String url, String word, int depth);
    void handlerNewLinkFound(Observer<Flowable<String>> observer);
    void handlerNewLineProcessed(Observer<Flowable<Pair<String, Integer>>> observer);
}
