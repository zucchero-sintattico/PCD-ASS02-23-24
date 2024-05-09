package part2.reactiveProgramming.controller;

import io.reactivex.rxjava3.core.Observer;
import part2.reactiveProgramming.report.ErrorReport;
import part2.reactiveProgramming.report.SearchReport;
import part2.reactiveProgramming.monitor.Flag;

public class SearchControllerImpl implements SearchController {

    private final Flag flag;
    private final SearchHandler controller;

    public SearchControllerImpl() {
        this.flag = new Flag();
        this.controller = new SearchHandler(this.flag);
    }

    @Override
    public void startSearch(String url, String word, int depth) {
        this.flag.setFlag();
        this.controller.wordCount(url, word, depth);
    }

    @Override
    public void stopSearch() {
        this.flag.unsetFlag();
        this.controller.reset();
    }

    @Override
    public void subscribeNewResult(Observer<SearchReport> observer) {
        this.controller.attachObserver(observer);
    }

    @Override
    public void subscribeNewError(Observer<ErrorReport> observer) {
        this.controller.attachErrorObserver(observer);
    }
}
