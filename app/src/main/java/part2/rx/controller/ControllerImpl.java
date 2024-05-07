package part2.rx.controller;

import io.reactivex.rxjava3.core.Observer;
import part2.rx.model.ErrorReport;
import part2.rx.model.SearchReport;

public class ControllerImpl implements Controller{

    SearchController controller = new SearchController();

    @Override
    public void startSearch(String url, String word, int depth) {
        this.controller.wordCount(url, word, depth);
    }

    @Override
    public void stopSearch() {
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
