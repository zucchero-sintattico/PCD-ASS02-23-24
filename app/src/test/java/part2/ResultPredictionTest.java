package part2;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import org.junit.jupiter.api.Test;
import part2.reactiveProgramming.controller.SearchHandler;
import part2.reactiveProgramming.report.SearchReport;
import part2.virtualThread.state.SearchState;
import part2.virtualThread.search.PageHandler;
import part2.utils.connection.RequestHandlerJSoup;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResultPredictionTest {

    private final int numberOfWords = 1, depth = 7, numberOfLinks = 3;
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
    public void testResult() {
        SearchState state = new SearchState(url);
        Thread t = new PageHandler(url, word, depth, state, new RequestHandlerJSoup(false));
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(predictResult(numberOfWords, depth, numberOfLinks), state.getWordOccurrences());
    }

    @Test
    public void testResultRx() throws ExecutionException, InterruptedException {
        SearchHandler controller = new SearchHandler(true);
        CompletableFuture<Void> future = new CompletableFuture<>();
        final AtomicInteger wordFound = new AtomicInteger(0);
        controller.attachObserver(new Observer<SearchReport>() {
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
