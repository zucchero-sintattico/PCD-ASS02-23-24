package part2.reactiveProgramming.controller;


import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.ExecutionException;

public interface Controller {
    void startSearch(String url, String word, int depth) throws ExecutionException, InterruptedException;
    void subscribe(Observer<String> observer);
    void stopSearch();
}
