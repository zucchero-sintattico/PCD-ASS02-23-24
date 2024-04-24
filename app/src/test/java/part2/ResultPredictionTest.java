package part2;

import org.junit.jupiter.api.Test;
import part2.virtualThread.search.PageHandler;
import part2.virtualThread.search.SearchState;
import part2.virtualThread.utils.connection.RequestHandlerJSoup;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResultPredictionTest {

    private String predictResult(int numberOfWords, int depth, int numberOfLinks){
        int sum = 0;
        for (int i = 0; i <= deep; i++) {
            sum = sum + numberOfWords *  (int)Math.pow(numberOfLinks, i);
        }
    }

    @Test
    public void testResult() {
        int numberOfWords = 1, depth = 5, numberOfLinks = 3;
        String word = "a";
        String url = "http://localhost:4000/?word="+word+"&numberOfWords="+numberOfWords+"&numberOfLinks="+numberOfLinks+"&path=0";
        SearchState state = new SearchState(url);
        Thread t = new PageHandler(url, word, depth, state, new RequestHandlerJSoup());
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(predictResult(numberOfWords, depth, numberOfLinks), state.getWordOccurrences().getValue());
    }
}
