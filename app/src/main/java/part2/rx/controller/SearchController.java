package part2.rx.controller;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import part2.rx.model.ErrorReport;
import part2.rx.model.Flag;
import part2.rx.model.SearchReport;
import part2.utils.connection.RequestHandlerJSoup;
import part2.utils.parser.Body;
import part2.utils.parser.HtmlParser;

import javax.swing.text.Element;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class SearchController{

    private Flowable<String> searchObservable;
    private Subject<SearchReport> searchReportSubject;
    private Subject<ErrorReport> errorReportSubject;
    private final RequestHandlerJSoup requestHandler;
    private AtomicInteger count;
    private final Flag flag;

    public SearchController(Flag flag, boolean test) {
        this.requestHandler = new RequestHandlerJSoup(!test);
        this.init();
        this.flag = flag;
    }

    public SearchController(Flag flag) {
        this(flag, false);
    }

    public SearchController(boolean test) {
        this(new Flag(), test);
    }

    private void init() {
        this.searchObservable = Flowable.empty();
        this.searchReportSubject = PublishSubject.create();
        this.errorReportSubject = PublishSubject.create();
        this.count = new AtomicInteger(0);
    }

    public void wordCount(String url, String word, int depth){
        this.searchObservable = Flowable.just(url).subscribeOn(Schedulers.io());
        for (int i = 0; i <= depth; i++) {
            int index = i;
            this.searchObservable = this.searchObservable.map(link -> {
                if(this.flag.getFlag()){
                    try{
                        SearchReport report = this.getReport(link, word, index);
                        this.searchReportSubject.onNext(report);
                        return report.links().toList();
                    }catch (Exception e){
                        this.errorReportSubject.onNext(new ErrorReport(link, e.getMessage()));
                        return Stream.of(e.getMessage()).toList();
                    }
                }
                return Stream.of(link).toList(); //To change
            })
                    .flatMap(Flowable::fromIterable);
        }
        this.searchObservable.doOnComplete(this::reset).subscribe();
    }

    private SearchReport getReport(String url, String word, int depth) throws Exception {
        Body<?> body = this.requestHandler.getBody(url);
        Stream<String> links = body.getLinks();
        int wordCounter = HtmlParser.parse(body).countWords(word);
        int totalCount = this.count.addAndGet(wordCounter);
        return new SearchReport(url, wordCounter, depth, links, totalCount);
    }

    public void reset(){
        this.searchReportSubject.onComplete();
        this.errorReportSubject.onComplete();
        this.init();
    }

    public void attachObserver(Observer<SearchReport> observer){
        this.searchReportSubject.subscribe(observer);
    }

    public void attachErrorObserver(Observer<ErrorReport> observer){
        this.errorReportSubject.subscribe(observer);
    }
}
