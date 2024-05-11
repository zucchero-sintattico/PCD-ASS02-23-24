package part2;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import org.junit.jupiter.api.Test;
import part2.reactiveProgramming.controller.SearchHandler;
import part2.reactiveProgramming.report.SearchReport;
import part2.virtualThread.search.SearchControllerImpl;
import part2.virtualThread.search.SearchListener;
import part2.virtualThread.state.SearchState;
import part2.virtualThread.search.PageHandler;
import part2.utils.connection.RequestHandlerJSoup;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResultPredictionTest {

    private final int numberOfWords = 1, depth = 5, numberOfLinks = 3;
    private final String word = "a";
    private final String url = "http://localhost:4000/?word="+word+"&numberOfWords="+numberOfWords+"&numberOfLinks="+numberOfLinks+"&path=0";

    private int predictResult(int numberOfWords, int depth, int numberOfLinks){
        int sum = 0;
        for (int i = 0; i <= depth; i++) {
            sum = sum + numberOfWords *  (int)Math.pow(numberOfLinks, i);
        }
        return sum;
    }

    @Test
    public void testResult() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        new SearchControllerImpl(new SearchListener() {
            @Override
            public void searchStarted() {

            }

            @Override
            public void searchEnded(int linkFound, int linkDown, part2.virtualThread.state.SearchReport info) {
                future.complete(info.totalWordFound());
            }
        }, false).start(url, word, depth);

        assertEquals(predictResult(numberOfWords, depth, numberOfLinks), future.get());
    }

    @Test
    public void testResultRx() throws ExecutionException, InterruptedException {
        SearchHandler controller = new SearchHandler(true);
        CompletableFuture<Void> future = new CompletableFuture<>();
        final AtomicInteger wordFound = new AtomicInteger(0);
        controller.attachObserver(new Observer<>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {}

            @Override
            public void onNext(@NonNull SearchReport searchReport) {
                wordFound.addAndGet(searchReport.wordFound());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onComplete() {
                assertEquals(predictResult(numberOfWords, depth, numberOfLinks), wordFound.get());
                future.complete(null);
            }
        });
        controller.wordCount(url, word, depth);
        future.get();
    }
}
