package part2.rx;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import part2.virtualThread.utils.connection.RequestHandlerJSoup;

import java.util.concurrent.atomic.AtomicInteger;

public class SearchController {

    private BehaviorSubject<SearchReport> result;
    private final RequestHandlerJSoup requestHandler;

    public SearchController() {
        this.requestHandler = new RequestHandlerJSoup();
        this.result = BehaviorSubject.create();
        //Print report
        this.result.subscribeOn(Schedulers.io()).distinct().doOnComplete(() -> {}).subscribe(e -> {
            System.out.println(e.url() + "\n ----> " + e.wordFind() + " find" + "\n -------> " + e.links());
        });
    }

    public void wordCount(String url, String word, int depth){
        if(depth >= 0){
            try{
                SearchReport report = this.getReport(url, word);
                this.result.onNext(report);
                report.links().forEach(link -> this.wordCount(link, word, depth - 1));
            }catch (Exception e){
                this.result.onError(e);
            }
        }
    }

    private SearchReport getReport(String url, String word) throws Exception {
        var body = this.requestHandler.getBody(url);
        var links = body.getLinks();
        var wordCounter = Long.valueOf(body.getWords().filter(w -> w.contains(word)).count()).intValue();
        return new SearchReport(url, wordCounter, links);
    }


    public static void main(String[] args) {
        SearchController controller = new SearchController();
        //controller.recursiveObservable("https://en.wikipedia.org/", "wikipedia", 1);
        controller.wordCount("https://en.wikipedia.org/", "wikipedia", 1);
    }

}
