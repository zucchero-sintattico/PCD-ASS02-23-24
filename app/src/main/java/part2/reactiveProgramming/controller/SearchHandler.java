package part2.reactiveProgramming.controller;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import part2.reactiveProgramming.report.ErrorReport;
import part2.reactiveProgramming.monitor.Flag;
import part2.reactiveProgramming.report.SearchReport;
import part2.utils.connection.RequestHandlerJSoup;
import part2.utils.parser.Body;
import part2.utils.parser.HtmlParser;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class SearchHandler {

    private Flowable<String> searchObservable;
    private Subject<SearchReport> searchReportSubject;
    private Subject<ErrorReport> errorReportSubject;
    private final RequestHandlerJSoup requestHandler;
    private AtomicInteger count;
    private final Flag flag;
    private long startTime;

    public SearchHandler(Flag flag, boolean test) {
        this.requestHandler = new RequestHandlerJSoup(!test);
        this.init();
        this.flag = flag;
    }

    public SearchHandler(Flag flag) {
        this(flag, false);
    }

    public SearchHandler(boolean test) {
        this(new Flag(), test);
    }

    private void init() {
        this.searchObservable = Flowable.empty();
        this.searchReportSubject = PublishSubject.create();
        this.errorReportSubject = PublishSubject.create();
        this.count = new AtomicInteger(0);
    }

    public void wordCount(String url, String word, int depth){
        startTime = System.currentTimeMillis();
        this.searchObservable = Flowable.just(url);
        for (int i = 0; i <= depth; i++) {
            int currentDepth = i;
            this.searchObservable = this.searchObservable.flatMap(
                   element -> Flowable.just(element).observeOn(Schedulers.io()).map(link -> {
                        if(this.flag.getFlag()){
                            try{
                                SearchReport report = this.getReport(link, word, currentDepth);
                                this.searchReportSubject.onNext(report);
                                return report.links().toList();
                            }catch (Exception e){
                                this.errorReportSubject.onNext(new ErrorReport(link, e.getMessage()));
                            }
                        }
                        return new ArrayList<String>();
                   })
                    .observeOn(Schedulers.computation()).flatMap(Flowable::fromIterable));
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
        System.out.println("Search Time: " + (System.currentTimeMillis() - startTime) + "ms");
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
