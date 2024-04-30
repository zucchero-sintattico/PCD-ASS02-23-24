package part2.reactiveProgramming.controller;

import java.util.concurrent.ExecutionException;

public interface Controller {
    void startSearch(String url, String word, int depth) throws ExecutionException, InterruptedException;
    void stopSearch();
}
