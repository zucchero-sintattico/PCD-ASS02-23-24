package part2.rx.controller;

import io.reactivex.rxjava3.core.Observer;
import part2.rx.model.ErrorReport;
import part2.rx.model.SearchReport;

public interface Controller {
    void startSearch(String url, String word, int depth);
    void stopSearch();
    void subscribeNewResult(Observer<SearchReport> observer);
    void subscribeNewError(Observer<ErrorReport> observer);
}
