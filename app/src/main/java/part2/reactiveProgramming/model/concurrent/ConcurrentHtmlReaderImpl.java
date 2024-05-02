package part2.reactiveProgramming.model.concurrent;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import kotlin.Pair;
import part2.virtualThread.monitor.SafeCounter;
import part2.virtualThread.monitor.SafeSet;

public class ConcurrentHtmlReaderImpl implements ConcurrentHtmlReader{

    private final FlowableWorker flowableWorker;
    private final Subject<SearchState> state;
    private final Subject<Flowable<Pair<String , Integer>>> lines;
    private SafeSet linksAlredyProcessed;
    private SafeCounter wordCounter;

    public ConcurrentHtmlReaderImpl() {
        this.flowableWorker = new FlowableWorkerImpl();
        this.state = PublishSubject.create();
        this.lines = PublishSubject.create();
        this.linksAlredyProcessed = new SafeSet();
        this.subscribeNewSearch();
        this.subscribeWordCountInLink();
        this.wordCounter = new SafeCounter();
    }

    @Override
    public void getWordCount(String url, String word, int depth) {
        if(depth > 0 && !linksAlredyProcessed.contains(url)){
            var linksInPage = this.flowableWorker.getPageLinks(url);
            this.state.onNext(new SearchState(linksInPage, word, depth - 1));
            this.lines.onNext(this.flowableWorker.getLinesWordCount(url, word));
        }
    }

    @Override
    public void handlerNewLinkFound(Observer<Flowable<String>> observer) {
        this.state.map(SearchState::url).subscribe(observer);
    }

    @Override
    public void handlerNewLineProcessed(Observer<Flowable<Pair<String, Integer>>> observer) {
        this.lines.subscribe(observer);
    }

    /*
    @Override
    public void handlerNewCounter(Observer<Flowable<Integer>> observer) {
       // Flowable.just(this.wordCounter).doOnComplete(() -> {}).subscribe(observer);
    }
     */

    private void subscribeNewSearch(){
        this.state.forEach(element -> {
            String word = element.word();
            int depth = element.depth();
            element.url().subscribe(link -> {
                getWordCount(link, word, depth);
                this.linksAlredyProcessed.add(link);
                System.out.println("Processed link: " + link);
            });
        });
    }

    private void subscribeWordCountInLink(){
        this.lines.forEach(element -> {
            element.map(Pair::component2)
                    .filter(e -> e > 0)
                    .doOnComplete(() -> {}).subscribe(e -> {
                        this.wordCounter.update(e);
                        System.out.println("[Counter]: " + this.wordCounter.getValue());
                    });
        });
    }

    private void stopExecution(){
        this.state.onComplete();
        this.lines.onComplete();
        this.linksAlredyProcessed = new SafeSet();
        this.wordCounter = new SafeCounter();
    }

}
