package part2.rx.controller;

import io.reactivex.rxjava3.core.Observer;
import part2.rx.model.ErrorReport;
import part2.rx.model.SearchReport;
import part2.rx.model.Flag;

public class ControllerImpl implements Controller{

    private final Flag flag;
    private final SearchController controller;

    public ControllerImpl() {
        this.flag = new Flag();
        this.controller = new SearchController(this.flag);
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
