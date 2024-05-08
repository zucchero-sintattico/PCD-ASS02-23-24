package part2.rx.controller;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import part2.rx.model.ErrorReport;
import part2.rx.model.Flag;
import part2.rx.model.SearchReport;
import part2.virtualThread.utils.connection.RequestHandlerJSoup;
import java.util.stream.Stream;

public class SearchController{

    private Flowable<String> searchObservable;
    private Subject<SearchReport> searchReportSubject;
    private Subject<ErrorReport> errorReportSubject;
    private final RequestHandlerJSoup requestHandler;
    private int count = 0;
    private final Flag flag;

    public SearchController(Flag flag) {
        this.requestHandler = new RequestHandlerJSoup();
        this.searchObservable = Flowable.empty();
        this.searchReportSubject = PublishSubject.create();
        this.errorReportSubject = PublishSubject.create();
        this.flag = flag;
    }

    //Only for test
    public SearchController(boolean isSafe){
        this.requestHandler = new RequestHandlerJSoup(isSafe);
        this.searchObservable = Flowable.empty();
        this.searchReportSubject = PublishSubject.create();
        this.errorReportSubject = PublishSubject.create();
        this.flag = new Flag();
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
        this.searchObservable.doOnComplete(this::reset).subscribe(e -> System.out.println("Finish: " + e));
    }

    private SearchReport getReport(String url, String word, int depth) throws Exception {
        var body = this.requestHandler.getBody(url);
        var links = body.getLinks();
        var wordCounter = Long.valueOf(body.getWords().filter(w -> w.contains(word)).count()).intValue();
        this.count += wordCounter;
        return new SearchReport(url, wordCounter, depth, links, count);
    }

    public void reset(){
        this.searchReportSubject.onComplete();
        this.errorReportSubject.onComplete();
        this.searchObservable = Flowable.empty();
        this.searchReportSubject = PublishSubject.create();
        this.errorReportSubject = PublishSubject.create();
        this.count = 0;
    }

    public void attachObserver(Observer<SearchReport> observer){
        this.searchReportSubject.subscribe(observer);
    }

    public void attachErrorObserver(Observer<ErrorReport> observer){
        this.errorReportSubject.subscribe(observer);
    }
}
