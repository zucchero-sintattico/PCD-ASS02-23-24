package part2.reactiveProgramming.controller;

import io.reactivex.rxjava3.core.Observer;
import part2.reactiveProgramming.report.ErrorReport;
import part2.reactiveProgramming.report.SearchReport;

public interface SearchController {
    void startSearch(String url, String word, int depth);
    void stopSearch();
    void subscribeNewResult(Observer<SearchReport> observer);
    void subscribeNewError(Observer<ErrorReport> observer);
}
