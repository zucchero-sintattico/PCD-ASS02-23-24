package part2.rx.controller;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import part2.rx.model.SearchReport;
import part2.virtualThread.utils.connection.RequestHandlerJSoup;

public class SearchController {

    private Observable<String> searchObservable;
    private Subject<SearchReport> searchReportSubject;
    private final RequestHandlerJSoup requestHandler;

    public SearchController() {
        this.requestHandler = new RequestHandlerJSoup();
        this.searchObservable = Observable.empty();
        this.searchReportSubject = PublishSubject.create();
        this.searchReportSubject.subscribeOn(Schedulers.io()).subscribe(e -> {
            System.out.println("[Link]: " + e.url() + "\n--->[Word count]: " + e.wordFind() + "\n--->[Depth]: " + e.depth());
        });
    }

    public void wordCount(String url, String word, int depth){
        this.searchObservable = Observable.just(url);
        for (int i = 0; i < depth; i++) {
            int index = i;
            this.searchObservable = this.searchObservable.map(link -> {
                SearchReport report = this.getReport(link, word, index);
                this.searchReportSubject.onNext(report);
                return report.links().toList();
            }).flatMap(Observable::fromIterable).subscribeOn(Schedulers.io());
        }
        this.searchObservable.blockingSubscribe();
        this.reset();
    }

    private SearchReport getReport(String url, String word, int depth) throws Exception {
        var body = this.requestHandler.getBody(url);
        var links = body.getLinks();
        var wordCounter = Long.valueOf(body.getWords().filter(w -> w.contains(word)).count()).intValue();
        return new SearchReport(url, wordCounter, depth, links);
    }

    private void reset(){
        this.searchReportSubject.onComplete();
        this.searchObservable = Observable.empty();
        this.searchReportSubject = PublishSubject.create();
    }

    public void attachObserver(Observer<SearchReport> observer){
        this.searchReportSubject.subscribeOn(Schedulers.io()).subscribe(observer);
    }


    public static void main(String[] args) {
        /*
        String root = "https://www.google.com";
        int depth = 2;
        RequestHandler<Element> requestHandler1 = new RequestHandlerJSoup();
        Observable<String> linkStream = Observable.just(root);
        for (int i = 0; i < depth; i++) {
            int finalI = i;
            linkStream = linkStream.map(link -> {
                System.out.println("Link " + link + " at depth" + finalI);
                return requestHandler1.getBody(link).getLinks().toList();
            }).flatMap(Observable::fromIterable).subscribeOn(Schedulers.io());
        }

        linkStream.blockingSubscribe();
        System.out.println("Fine");
        */
        SearchController controller = new SearchController();
        controller.wordCount("https://www.google.com", "google", 2);
    }

}
