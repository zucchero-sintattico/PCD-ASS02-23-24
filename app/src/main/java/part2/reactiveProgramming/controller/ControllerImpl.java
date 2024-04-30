package part2.reactiveProgramming.controller;

import part2.reactiveProgramming.model.ConcurrentReader;
import part2.reactiveProgramming.model.Reader;
import part2.reactiveProgramming.model.ReportListener;

import java.util.concurrent.ExecutionException;

public class ControllerImpl implements Controller{
    private Reader reader;

    public ControllerImpl(ReportListener listener){
        this.reader = new ConcurrentReader(listener);
    }

    @Override
    public void startSearch(String url, String word, int depth) throws ExecutionException, InterruptedException {
        this.reader.counter(url, word, depth);
    }

    @Override
    public void stopSearch() {

    }
}
