package part2.reactiveProgramming.model;

import io.reactivex.rxjava3.core.Observer;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public interface Reader {
    Future<Long> getWordCount(String url, String word);
    Future<Set<String>> getAllPageLinks(String url);
    void counter(String url, String word, int depth) throws InterruptedException, ExecutionException;
    void subscribe(Observer<String> observer);
}